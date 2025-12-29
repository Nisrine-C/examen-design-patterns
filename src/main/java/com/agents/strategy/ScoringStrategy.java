package com.agents.strategy;

import com.agents.model.Evenement;
import com.agents.model.Transaction;
import com.agents.model.TransactionType;

public class ScoringStrategy implements NotificationHandler {
    private double solde;

    public ScoringStrategy() {
        this.solde = 0;
    }

    @Override
    public void apply(Evenement e) {
        Transaction t = e.getTransaction();
        if (t.getType() == TransactionType.VENTE) {
            solde += t.getMontant();
        } else if (t.getType() == TransactionType.ACHAT) {
            solde -= t.getMontant();
        }
        System.out.println("ScoringStrategy - Solde mis à jour: " + solde + 
                           " (Transaction: " + t.getType() + " de " + t.getMontant() + ")");
    }

    public double getSolde() {
        return solde;
    }
}
