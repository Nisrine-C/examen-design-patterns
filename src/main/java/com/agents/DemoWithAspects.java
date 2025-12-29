package com.agents;

import com.agents.aspects.SecurityAspect;
import com.agents.model.Agent;
import com.agents.model.Transaction;
import com.agents.model.TransactionType;

import java.util.Date;

/**
 * Démonstration complète de l'application avec les aspects AOP
 */
public class DemoWithAspects {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║  APPLICATION DE GESTION D'AGENTS AVEC ASPECTS (AOP)           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        // ================================================================
        // SCÉNARIO 1: Sécurité - Authentification requise
        // ================================================================
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println(" SCÉNARIO 1: Sécurité avec @SecuredBy");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        Agent agentCommercial = new Agent("Marie Dupont");

        System.out.println("→ Tentative d'ajout de transaction sans authentification...\n");
        try {
            Transaction t1 = new Transaction.Builder()
                    .setId("TX001")
                    .setDate(new Date())
                    .setMontant(5000.0)
                    .setType(TransactionType.VENTE)
                    .build();
            agentCommercial.addTransaction(t1);
        } catch (SecurityException e) {
            System.out.println("🔒 ACCÈS REFUSÉ: " + e.getMessage() + "\n");
        }

        System.out.println("→ Authentification en tant qu'utilisateur...\n");
        SecurityAspect.authenticate("user", "user123");

        System.out.println("→ Nouvel essai d'ajout de transaction...\n");
        Transaction t1 = new Transaction.Builder()
                .setId("TX001")
                .setDate(new Date())
                .setMontant(5000.0)
                .setType(TransactionType.VENTE)
                .build();
        agentCommercial.addTransaction(t1);

        System.out.println("✅ Transaction ajoutée avec succès!\n");

        // ================================================================
        // SCÉNARIO 2: Logging - Mesure du temps d'exécution
        // ================================================================
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println(" SCÉNARIO 2: Logging avec @Log");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("→ Ajout de plusieurs transactions (observez le temps d'exécution)...\n");

        Transaction t2 = new Transaction.Builder()
                .setId("TX002")
                .setDate(new Date())
                .setMontant(3000.0)
                .setType(TransactionType.ACHAT)
                .build();
        agentCommercial.addTransaction(t2);

        Transaction t3 = new Transaction.Builder()
                .setId("TX003")
                .setDate(new Date())
                .setMontant(7500.0)
                .setType(TransactionType.VENTE)
                .build();
        agentCommercial.addTransaction(t3);

        Transaction t4 = new Transaction.Builder()
                .setId("TX004")
                .setDate(new Date())
                .setMontant(2000.0)
                .setType(TransactionType.ACHAT)
                .build();
        agentCommercial.addTransaction(t4);

        // ================================================================
        // SCÉNARIO 3: Cache - Optimisation des calculs répétés
        // ================================================================
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println(" SCÉNARIO 3: Cache avec @Cachable");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        System.out.println("→ Premier appel à maxTransaction() [Calcul + Mise en cache]...\n");
        Transaction max1 = agentCommercial.maxTransaction();
        System.out.println("📊 Transaction max: " + max1.getId() + " - Montant: " + max1.getMontant() + "€\n");

        System.out.println("→ Deuxième appel à maxTransaction() [Lecture depuis le cache]...\n");
        Transaction max2 = agentCommercial.maxTransaction();
        System.out.println("📊 Transaction max: " + max2.getId() + " - Montant: " + max2.getMontant() + "€\n");

        System.out.println("→ Troisième appel à maxTransaction() [Lecture depuis le cache]...\n");
        Transaction max3 = agentCommercial.maxTransaction();
        System.out.println("📊 Transaction max: " + max3.getId() + " - Montant: " + max3.getMontant() + "€\n");

        System.out.println("→ Ajout d'une nouvelle transaction importante [Invalidation du cache]...\n");
        Transaction t5 = new Transaction.Builder()
                .setId("TX005")
                .setDate(new Date())
                .setMontant(10000.0)
                .setType(TransactionType.VENTE)
                .build();
        agentCommercial.addTransaction(t5);

        System.out.println("\n→ Quatrième appel à maxTransaction() [Recalcul après invalidation]...\n");
        Transaction max4 = agentCommercial.maxTransaction();
        System.out.println("📊 Transaction max: " + max4.getId() + " - Montant: " + max4.getMontant() + "€\n");

        // ================================================================
        // SCÉNARIO 4: Gestion des rôles
        // ================================================================
        System.out.println("\n═══════════════════════════════════════════════════════════════");
        System.out.println(" SCÉNARIO 4: Gestion des rôles utilisateurs");
        System.out.println("═══════════════════════════════════════════════════════════════\n");

        SecurityAspect.logout();
        System.out.println("→ Connexion avec le rôle MANAGER (sans droit USER/ADMIN)...\n");
        SecurityAspect.authenticate("manager", "manager123");

        Agent agentSecurise = new Agent("Jean Martin");

        System.out.println("→ Tentative d'ajout de transaction avec rôle insuffisant...\n");
        try {
            Transaction t6 = new Transaction.Builder()
                    .setId("TX006")
                    .setDate(new Date())
                    .setMontant(1500.0)
                    .setType(TransactionType.VENTE)
                    .build();
            agentSecurise.addTransaction(t6);
        } catch (SecurityException e) {
            System.out.println("🔒 ACCÈS REFUSÉ: " + e.getMessage() + "\n");
        }

        SecurityAspect.logout();
        System.out.println("→ Connexion avec le rôle ADMIN (tous les droits)...\n");
        SecurityAspect.authenticate("admin", "admin123");

        System.out.println("→ Ajout de transaction avec rôle ADMIN...\n");
        Transaction t7 = new Transaction.Builder()
                .setId("TX007")
                .setDate(new Date())
                .setMontant(8000.0)
                .setType(TransactionType.VENTE)
                .build();
        agentSecurise.addTransaction(t7);
        System.out.println("✅ Transaction ajoutée avec succès!\n");

        // ================================================================
        // RÉSUMÉ FINAL
        // ================================================================
        System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    RÉSUMÉ DE LA DÉMONSTRATION                  ║");
        System.out.println("╠════════════════════════════════════════════════════════════════╣");
        System.out.println("║                                                                ║");
        System.out.println("║  ✅ Aspect @Log - Journalisation automatique                   ║");
        System.out.println("║     • Enregistre la durée d'exécution de chaque méthode       ║");
        System.out.println("║     • Affiche les paramètres d'entrée                          ║");
        System.out.println("║                                                                ║");
        System.out.println("║  ✅ Aspect @Cachable - Optimisation par mise en cache          ║");
        System.out.println("║     • Évite les recalculs répétés                              ║");
        System.out.println("║     • Invalidation automatique lors de modifications           ║");
        System.out.println("║                                                                ║");
        System.out.println("║  ✅ Aspect @SecuredBy - Sécurité basée sur les rôles           ║");
        System.out.println("║     • Authentification obligatoire                             ║");
        System.out.println("║     • Contrôle d'accès par rôles (USER, ADMIN, MANAGER)       ║");
        System.out.println("║     • Protection des méthodes sensibles                        ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");

        SecurityAspect.logout();
        System.out.println("\n🔓 Déconnexion effectuée.");
    }
}
