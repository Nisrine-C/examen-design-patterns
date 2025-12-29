package com.agents.tests;

import com.agents.model.Agent;
import com.agents.model.Transaction;
import com.agents.model.TransactionType;
import com.agents.strategy.HistoryStrategy;
import com.agents.strategy.ScoringStrategy;

import java.util.Date;

public class TestAgent {
    public static void main(String[] args) {
        System.out.println("=== Test de la classe Agent ===\n");

        System.out.println("Création d'agents");
        Agent agent1 = new Agent("Agent_A");
        Agent agent2 = new Agent("Agent_B");
        Agent agent3 = new Agent("Agent_C");
        System.out.println("Agents créés: " + agent1.getNom() + ", " + 
                           agent2.getNom() + ", " + agent3.getNom());
        System.out.println();

        System.out.println("Ajout de transactions à Agent_A");
        Transaction t1 = new Transaction.Builder()
                .setId("T001")
                .setDate(new Date())
                .setMontant(1000.0)
                .setType(TransactionType.VENTE)
                .build();
        
        Transaction t2 = new Transaction.Builder()
                .setId("T002")
                .setDate(new Date())
                .setMontant(500.0)
                .setType(TransactionType.ACHAT)
                .build();
        
        Transaction t3 = new Transaction.Builder()
                .setId("T003")
                .setDate(new Date())
                .setMontant(1500.0)
                .setType(TransactionType.VENTE)
                .build();
        
        agent1.addTransaction(t1);
        agent1.addTransaction(t2);
        agent1.addTransaction(t3);
        System.out.println();


        System.out.println("Agent_B et Agent_C souscrivent à Agent_A");
        agent1.addObserver(agent2);
        agent1.addObserver(agent3);


        System.out.println("Agent_B utilise ScoringStrategy");
        ScoringStrategy scoringStrategy = new ScoringStrategy();
        agent2.setNotificationHandler(scoringStrategy);
        System.out.println();

        System.out.println("Agent_C utilise HistoryStrategy");
        HistoryStrategy historyStrategy = new HistoryStrategy();
        agent3.setNotificationHandler(historyStrategy);
        System.out.println();


        System.out.println("Ajout d'une nouvelle transaction - Notification des observateurs");
        Transaction t4 = new Transaction.Builder()
                .setId("T004")
                .setDate(new Date())
                .setMontant(3000.0)
                .setType(TransactionType.VENTE)
                .build();
        agent1.addTransaction(t4);
        System.out.println();


        System.out.println("Solde calculé par Agent_B (ScoringStrategy)");
        System.out.println("Solde: " + scoringStrategy.getSolde());
        System.out.println();


        System.out.println("Historique de Agent_C (HistoryStrategy)");
        historyStrategy.afficherHistorique();
        System.out.println();

        System.out.println("Transaction avec montant maximum pour Agent_A");
        Transaction maxTrans = agent1.maxTransaction();
        System.out.println("Transaction max: " + maxTrans);
        System.out.println();

        System.out.println("Affichage complet de Agent_A");
        System.out.println(agent1);
    }
}
