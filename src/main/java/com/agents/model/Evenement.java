package com.agents.model;

public class Evenement {
    private String nomAgent;
    private Transaction transaction;

    public Evenement(String nomAgent, Transaction transaction) {
        this.nomAgent = nomAgent;
        this.transaction = transaction;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "nomAgent='" + nomAgent + '\'' +
                ", transaction=" + transaction +
                '}';
    }
}
