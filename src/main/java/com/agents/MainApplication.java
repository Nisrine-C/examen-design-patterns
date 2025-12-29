package com.agents;

import com.agents.model.Agent;
import com.agents.model.AgentContainer;
import com.agents.display.*;
import com.agents.model.Transaction;
import com.agents.model.TransactionType;
import com.agents.strategy.HistoryStrategy;
import com.agents.strategy.ScoringStrategy;

import java.util.Date;

public class MainApplication {
    public static void main(String[] args) {
        // 1. Créer le conteneur (Singleton)
        AgentContainer container = AgentContainer.getInstance();
        
        // 2. Connecter un afficheur HDMI
        HDMI hdmiDisplay = new HDMIDisplay("Moniteur Principal");
        container.connect(hdmiDisplay);
        System.out.println();

        // 3. Créer des agents
        Agent agent1 = new Agent("Agent_Commercial");
        Agent agent2 = new Agent("Agent_Comptable");
        Agent agent3 = new Agent("Agent_Auditeur");
        
        // 4. Configurer les stratégies
        agent2.setNotificationHandler(new ScoringStrategy());
        agent3.setNotificationHandler(new HistoryStrategy());
        
        // 5. Ajouter les agents au conteneur
        container.addAgent(agent1);
        container.addAgent(agent2);
        container.addAgent(agent3);
        System.out.println();

        // 6. Configurer les souscriptions (Observer Pattern)
        System.out.println("=== Configuration des souscriptions ===");
        agent1.addObserver(agent2); // Agent_Comptable observe Agent_Commercial
        agent1.addObserver(agent3); // Agent_Auditeur observe Agent_Commercial
        System.out.println("Agent_Comptable et Agent_Auditeur souscrivent à Agent_Commercial\n");

        // 7. Créer et ajouter des transactions (Builder Pattern)
        System.out.println("=== Ajout de transactions à Agent_Commercial ===\n");
        
        Transaction t1 = new Transaction.Builder()
                .setId("TR001")
                .setDate(new Date())
                .setMontant(5000.0)
                .setType(TransactionType.VENTE)
                .build();
        agent1.addTransaction(t1);
        System.out.println();

        Transaction t2 = new Transaction.Builder()
                .setId("TR002")
                .setDate(new Date())
                .setMontant(2000.0)
                .setType(TransactionType.ACHAT)
                .build();
        agent1.addTransaction(t2);
        System.out.println();

        Transaction t3 = new Transaction.Builder()
                .setId("TR003")
                .setDate(new Date())
                .setMontant(7500.0)
                .setType(TransactionType.VENTE)
                .build();
        agent1.addTransaction(t3);
        System.out.println();

        // 8. Afficher la transaction maximale
        System.out.println("=== Transaction avec montant maximum ===");
        Transaction maxTrans = agent1.maxTransaction();
        System.out.println("Transaction max de Agent_Commercial: " + maxTrans);
        System.out.println();

        // 9. Afficher l'état du conteneur via HDMI
        System.out.println("=== État du conteneur (HDMI) ===");
        container.displayState();
        System.out.println();

        // 10. Test avec adaptateur VGA
        System.out.println("=== Test avec adaptateur VGA ===");
        VGA vgaDisplay = new VGADisplay("Écran VGA Secondaire");
        HDMI adapter = new VgaHdmiAdapter(vgaDisplay);
        container.connect(adapter);
        container.send("Connexion via adaptateur VGA réussie");
        System.out.println();

    }
}
