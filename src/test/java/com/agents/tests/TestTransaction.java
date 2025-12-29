package com.agents.tests;

import com.agents.model.Transaction;
import com.agents.model.TransactionType;

import java.util.Date;

public class TestTransaction {
    public static void main(String[] args) {
        System.out.println("=== Test de la classe Transaction ===\n");


        System.out.println("Création d'une transaction type vente :");
        Transaction t1 = new Transaction.Builder()
                .setId("1")
                .setDate(new Date())
                .setMontant(1500.50)
                .setType(TransactionType.VENTE)
                .build();
        System.out.println(t1);
        System.out.println();

        System.out.println("Création d'une transaction type Achat :");
        Transaction t2 = new Transaction.Builder()
                .setId("2")
                .setDate(new Date())
                .setMontant(2300.75)
                .setType(TransactionType.ACHAT)
                .build();
        System.out.println("Test de toString");
        System.out.println(t1);
        System.out.println(t2);





    }
}
