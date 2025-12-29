package com.agents.strategy;

import com.agents.model.Evenement;
import com.agents.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class HistoryStrategy implements NotificationHandler {
    private List<Transaction> historique;

    public HistoryStrategy() {
        this.historique = new ArrayList<>();
    }

    @Override
    public void apply(Evenement e) {
        historique.add(e.getTransaction());
        System.out.println("HistoryStrategy - Transaction ajoutée à l'historique. " +
                           "Total: " + historique.size() + " transactions");
    }

    public List<Transaction> getHistorique() {
        return new ArrayList<>(historique);
    }

    public void afficherHistorique() {
        System.out.println("=== Historique des transactions ===");
        for (Transaction t : historique) {
            System.out.println(t);
        }
    }
}
