package com.agents.model;

import com.agents.aspects.CachingAspect;
import com.agents.aspects.LoggingAspect;
import com.agents.aspects.SecurityAspect;
import com.agents.aspects.annotations.Cachable;
import com.agents.aspects.annotations.Log;
import com.agents.aspects.annotations.SecuredBy;
import com.agents.observer.Observable;
import com.agents.observer.Observer;
import com.agents.strategy.DefaultStrategy;
import com.agents.strategy.NotificationHandler;

import java.util.ArrayList;
import java.util.List;

public class Agent implements Observable, Observer {
    private String nom;
    private List<Transaction> transactions;
    private List<Observer> observers;
    private NotificationHandler notificationHandler;

    public Agent(String nom) {
        this.nom = nom;
        this.transactions = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.notificationHandler = new DefaultStrategy(); // Stratégie par défaut
    }

    public String getNom() {
        return nom;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    // Pattern Strategy - Permet de changer la stratégie dynamiquement
    public void setNotificationHandler(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    // Ajouter une transaction et notifier les observateurs
    @Log
    @SecuredBy(roles = {"USER", "ADMIN"})
    public void addTransaction(Transaction transaction) {
        // Vérification de sécurité
        SecurityAspect.checkAuthorization(new String[]{"USER", "ADMIN"}, "addTransaction");
        
        // Logging
        long startTime = System.currentTimeMillis();
        LoggingAspect.logMethodEntry("addTransaction", transaction);
        
        transactions.add(transaction);
        
        // Invalider le cache quand une nouvelle transaction est ajoutée
        CachingAspect.invalidate("maxTransaction_" + this.nom);
        
        // Notifier tous les observateurs
        notifyObservers(new Evenement(this.nom, transaction));
        
        // Logging de sortie
        LoggingAspect.logMethodExit("addTransaction", System.currentTimeMillis() - startTime);
    }

    // Pattern Observer - Observable
    @Override
    public void addObserver(Observer obs) {
        if (!observers.contains(obs)) {
            observers.add(obs);
        }
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(Evenement e) {
        for (Observer obs : observers) {
            obs.update(e);
        }
    }

    // Pattern Observer - Observer
    @Override
    public void update(Evenement e) {
        // Déléguer le traitement à la stratégie actuelle
        notificationHandler.apply(e);
    }

    // Trouver la transaction avec le montant le plus élevé
    @Cachable
    @Log
    public Transaction maxTransaction() {
        String cacheKey = "maxTransaction_" + this.nom;
        
        // Utiliser le cache
        return (Transaction) CachingAspect.cacheResult(cacheKey, () -> {
            long startTime = System.currentTimeMillis();
            LoggingAspect.logMethodEntry("maxTransaction");
            
            if (transactions.isEmpty()) {
                LoggingAspect.logMethodExit("maxTransaction", System.currentTimeMillis() - startTime);
                return null;
            }
            
            Transaction max = transactions.get(0);
            for (Transaction t : transactions) {
                if (t.getMontant() > max.getMontant()) {
                    max = t;
                }
            }
            
            LoggingAspect.logMethodExit("maxTransaction", System.currentTimeMillis() - startTime);
            return max;
        });
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Agent: ").append(nom).append("\n");
        sb.append("Nombre de transactions: ").append(transactions.size()).append("\n");
        sb.append("Transactions:\n");
        for (Transaction t : transactions) {
            sb.append("  - ").append(t).append("\n");
        }
        return sb.toString();
    }
}
