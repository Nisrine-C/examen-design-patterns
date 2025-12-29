package com.agents.model;

import java.util.Date;

public class Transaction {
    private String id;
    private Date date;
    private double montant;
    private TransactionType type;

    private Transaction(String id, Date date, double montant, TransactionType type) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getMontant() {
        return montant;
    }

    public TransactionType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", montant=" + montant +
                ", type=" + type +
                '}';
    }

    // Builder interne
    public static class Builder implements TransactionBuilder {
        private String id;
        private Date date;
        private double montant;
        private TransactionType type;

        @Override
        public TransactionBuilder setId(String id) {
            this.id = id;
            return this;
        }

        @Override
        public TransactionBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        @Override
        public TransactionBuilder setMontant(double montant) {
            this.montant = montant;
            return this;
        }

        @Override
        public TransactionBuilder setType(TransactionType type) {
            this.type = type;
            return this;
        }

        @Override
        public Transaction build() {
            return new Transaction(id, date, montant, type);
        }
    }
}
