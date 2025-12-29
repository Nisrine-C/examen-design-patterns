package com.agents.tests;

import com.agents.model.Agent;
import com.agents.model.AgentContainer;
import com.agents.display.*;
import com.agents.model.Transaction;
import com.agents.model.TransactionType;

import java.util.Date;

public class TestAgentContainer {
    public static void main(String[] args) {
        System.out.println("=== Test de la classe AgentContainer ===\n");

        System.out.println("Pattern Singleton");
        AgentContainer container1 = AgentContainer.getInstance();
        AgentContainer container2 = AgentContainer.getInstance();
        System.out.println("container1 == container2: " + (container1 == container2));
        System.out.println();


        System.out.println("Connexion d'un afficheur HDMI");
        HDMI hdmiDisplay = new HDMIDisplay("Main Screen");
        container1.connect(hdmiDisplay);
        System.out.println();

        System.out.println("Ajout d'agents au conteneur");
        Agent agent1 = new Agent("Alice");
        Agent agent2 = new Agent("Bob");
        Agent agent3 = new Agent("Charlie");
        
        container1.addAgent(agent1);
        container1.addAgent(agent2);
        container1.addAgent(agent3);
        System.out.println();

        System.out.println("Ajout de transactions aux agents");
        Transaction t1 = new Transaction.Builder()
                .setId("1")
                .setDate(new Date())
                .setMontant(1200.0)
                .setType(TransactionType.VENTE)
                .build();
        
        Transaction t2 = new Transaction.Builder()
                .setId("2")
                .setDate(new Date())
                .setMontant(800.0)
                .setType(TransactionType.ACHAT)
                .build();
        
        agent1.addTransaction(t1);
        agent2.addTransaction(t2);
        System.out.println();

        System.out.println("Recherche d'un agent");
        Agent foundAgent = container1.getAgent("Bob");
        System.out.println("Agent trouvé: " + (foundAgent != null ? foundAgent.getNom() : "null"));
        System.out.println();

        System.out.println("Affichage de l'état du conteneur");
        container1.displayState();
        System.out.println();

        System.out.println("Suppression d'un agent");
        container1.deleteAgent("Charlie");
        System.out.println();

        System.out.println("Affichage après suppression");
        container1.displayState();
        System.out.println();

        System.out.println("Connexion d'un afficheur VGA");
        VGA vgaDisplay = new VGADisplay("Écran VGA");
        HDMI adapter = new VgaHdmiAdapter(vgaDisplay);
        container1.connect(adapter);
        System.out.println();


        System.out.println("Test avec afficheur VGA adapté");
        container1.send("Test d'affichage via adaptateur VGA");
        container1.displayState();

    }
}
