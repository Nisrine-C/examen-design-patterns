package com.agents.model;

import java.util.Date;

public interface TransactionBuilder {
    TransactionBuilder setId(String id);
    TransactionBuilder setDate(Date date);
    TransactionBuilder setMontant(double montant);
    TransactionBuilder setType(TransactionType type);
    Transaction build();
}
