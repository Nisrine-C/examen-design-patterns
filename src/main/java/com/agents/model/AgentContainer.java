package com.agents.model;

import com.agents.display.HDMI;

import java.util.HashMap;

public class AgentContainer {
    private HashMap<String, Agent> agents;
    private static AgentContainer instance;
    private HDMI hdmi;

    // Constructeur privé pour Singleton
    private AgentContainer() {
        this.agents = new HashMap<>();
    }

    // Pattern Singleton
    public static AgentContainer getInstance() {
        if (instance == null) {
            synchronized (AgentContainer.class) {
                if (instance == null) {
                    instance = new AgentContainer();
                }
            }
        }
        return instance;
    }

    // Ajouter un agent
    public void addAgent(Agent agent) {
        if (agent != null && agent.getNom() != null) {
            agents.put(agent.getNom(), agent);
            send("Agent ajouté: " + agent.getNom());
        }
    }

    // Supprimer un agent
    public void deleteAgent(String nom) {
        if (agents.containsKey(nom)) {
            agents.remove(nom);
            send("Agent supprimé: " + nom);
        }
    }

    // Rechercher un agent
    public Agent getAgent(String nom) {
        return agents.get(nom);
    }

    // Obtenir tous les agents
    public HashMap<String, Agent> getAllAgents() {
        return new HashMap<>(agents);
    }

    // Connecter un afficheur HDMI
    public void connect(HDMI hdmi) {
        this.hdmi = hdmi;
        send("Afficheur HDMI connecté");
    }

    // Envoyer un message à l'afficheur
    public void send(String message) {
        if (hdmi != null) {
            hdmi.print(message);
        }
    }

    // Afficher l'état du conteneur
    public void displayState() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== État du Conteneur d'Agents ===\n");
        sb.append("Nombre d'agents: ").append(agents.size()).append("\n");
        
        for (Agent agent : agents.values()) {
            sb.append("\n").append(agent.toString());
        }
        
        send(sb.toString());
    }
}
