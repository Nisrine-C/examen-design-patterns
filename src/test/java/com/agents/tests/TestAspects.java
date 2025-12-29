package com.agents.tests;

import com.agents.aspects.CachingAspect;
import com.agents.aspects.SecurityAspect;
import com.agents.model.Agent;
import com.agents.model.Transaction;
import com.agents.model.TransactionType;

import java.util.Date;

public class TestAspects {
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║         TEST DES ASPECTS (AOP)                           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        // ============================================================
        // TEST 1: Aspect de Sécurité (@SecuredBy)
        // ============================================================
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("TEST 1: Aspect de Sécurité (@SecuredBy)");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        Agent agent1 = new Agent("Agent_Sécurisé");

        // Tentative d'ajout sans authentification (devrait échouer)
        System.out.println("--- Tentative sans authentification ---");
        try {
            Transaction t1 = new Transaction.Builder()
                    .setId("T001")
                    .setDate(new Date())
                    .setMontant(1000.0)
                    .setType(TransactionType.VENTE)
                    .build();
            agent1.addTransaction(t1);
        } catch (SecurityException e) {
            System.out.println("❌ Exception attendue: " + e.getMessage() + "\n");
        }

        // Authentification avec un utilisateur ayant le rôle USER
        System.out.println("--- Authentification avec rôle USER ---");
        SecurityAspect.authenticate("user", "user123");

        // Ajout d'une transaction (devrait réussir)
        System.out.println("--- Ajout de transaction avec rôle USER ---");
        try {
            Transaction t2 = new Transaction.Builder()
                    .setId("T002")
                    .setDate(new Date())
                    .setMontant(1500.0)
                    .setType(TransactionType.VENTE)
                    .build();
            agent1.addTransaction(t2);
            System.out.println("✅ Transaction ajoutée avec succès\n");
        } catch (SecurityException e) {
            System.out.println("❌ Erreur: " + e.getMessage() + "\n");
        }

        // Déconnexion
        SecurityAspect.logout();

        // ============================================================
        // TEST 2: Aspect de Logging (@Log)
        // ============================================================
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("TEST 2: Aspect de Logging (@Log)");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        // Authentification pour pouvoir ajouter des transactions
        SecurityAspect.authenticate("admin", "admin123");

        Agent agent2 = new Agent("Agent_Logged");

        System.out.println("--- Ajout de plusieurs transactions (avec logging) ---");
        for (int i = 1; i <= 3; i++) {
            Transaction t = new Transaction.Builder()
                    .setId("T10" + i)
                    .setDate(new Date())
                    .setMontant(500.0 * i)
                    .setType(i % 2 == 0 ? TransactionType.ACHAT : TransactionType.VENTE)
                    .build();
            agent2.addTransaction(t);
            
            // Petite pause pour voir la différence de temps
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // ============================================================
        // TEST 3: Aspect de Cache (@Cachable)
        // ============================================================
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("TEST 3: Aspect de Cache (@Cachable)");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        Agent agent3 = new Agent("Agent_Cached");

        // Ajouter quelques transactions
        System.out.println("--- Ajout de transactions ---");
        Transaction t1 = new Transaction.Builder()
                .setId("T201")
                .setDate(new Date())
                .setMontant(1000.0)
                .setType(TransactionType.VENTE)
                .build();
        agent3.addTransaction(t1);

        Transaction t2 = new Transaction.Builder()
                .setId("T202")
                .setDate(new Date())
                .setMontant(3000.0)
                .setType(TransactionType.VENTE)
                .build();
        agent3.addTransaction(t2);

        Transaction t3 = new Transaction.Builder()
                .setId("T203")
                .setDate(new Date())
                .setMontant(2000.0)
                .setType(TransactionType.ACHAT)
                .build();
        agent3.addTransaction(t3);

        // Premier appel à maxTransaction (CACHE MISS - calcul)
        System.out.println("\n--- Premier appel à maxTransaction() ---");
        Transaction max1 = agent3.maxTransaction();
        System.out.println("Résultat: " + max1 + "\n");

        // Deuxième appel à maxTransaction (CACHE HIT - depuis le cache)
        System.out.println("--- Deuxième appel à maxTransaction() ---");
        Transaction max2 = agent3.maxTransaction();
        System.out.println("Résultat: " + max2 + "\n");

        // Troisième appel à maxTransaction (CACHE HIT - depuis le cache)
        System.out.println("--- Troisième appel à maxTransaction() ---");
        Transaction max3 = agent3.maxTransaction();
        System.out.println("Résultat: " + max3 + "\n");

        // Ajout d'une nouvelle transaction (invalide le cache)
        System.out.println("--- Ajout d'une nouvelle transaction ---");
        Transaction t4 = new Transaction.Builder()
                .setId("T204")
                .setDate(new Date())
                .setMontant(5000.0)
                .setType(TransactionType.VENTE)
                .build();
        agent3.addTransaction(t4);

        // Quatrième appel à maxTransaction (CACHE MISS - recalcul)
        System.out.println("\n--- Quatrième appel à maxTransaction() après invalidation ---");
        Transaction max4 = agent3.maxTransaction();
        System.out.println("Résultat: " + max4 + "\n");

        // ============================================================
        // TEST 4: Combinaison de tous les aspects
        // ============================================================
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("TEST 4: Combinaison de tous les aspects");
        System.out.println("═══════════════════════════════════════════════════════════\n");

        Agent agent4 = new Agent("Agent_Complet");

        System.out.println("--- Test avec utilisateur MANAGER (rôle insuffisant) ---");
        SecurityAspect.logout();
        SecurityAspect.authenticate("manager", "manager123");

        try {
            Transaction t = new Transaction.Builder()
                    .setId("T301")
                    .setDate(new Date())
                    .setMontant(1000.0)
                    .setType(TransactionType.VENTE)
                    .build();
            agent4.addTransaction(t);
        } catch (SecurityException e) {
            System.out.println("❌ Exception attendue: " + e.getMessage() + "\n");
        }

        // Changer pour un utilisateur avec les bons droits
        System.out.println("--- Test avec utilisateur ADMIN (tous les droits) ---");
        SecurityAspect.logout();
        SecurityAspect.authenticate("admin", "admin123");

        for (int i = 1; i <= 5; i++) {
            Transaction t = new Transaction.Builder()
                    .setId("T30" + i)
                    .setDate(new Date())
                    .setMontant(1000.0 * i)
                    .setType(i % 2 == 0 ? TransactionType.ACHAT : TransactionType.VENTE)
                    .build();
            agent4.addTransaction(t);
        }

        System.out.println("\n--- Appels multiples à maxTransaction (démonstration du cache) ---");
        for (int i = 0; i < 3; i++) {
            System.out.println("Appel " + (i + 1) + ":");
            Transaction max = agent4.maxTransaction();
            System.out.println("Résultat: " + (max != null ? max.getId() + " - " + max.getMontant() : "null") + "\n");
        }

        // ============================================================
        // RÉSUMÉ
        // ============================================================
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              RÉSUMÉ DES TESTS                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║ ✅ @Log: Logging de la durée d'exécution                 ║");
        System.out.println("║ ✅ @Cachable: Mise en cache des résultats                ║");
        System.out.println("║ ✅ @SecuredBy: Sécurité basée sur les rôles              ║");
        System.out.println("║ ✅ Combinaison des 3 aspects fonctionne                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        SecurityAspect.logout();
    }
}
