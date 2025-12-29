package com.agents.aspects;

import com.agents.aspects.annotations.SecuredBy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SecurityAspect {
    
    // Contexte de sécurité - utilisateur courant
    private static String currentUsername = null;
    private static String currentPassword = null;
    private static Set<String> currentUserRoles = new HashSet<>();
    
    // Base de données d'utilisateurs simulée
    private static final Map<String, UserCredentials> users = new HashMap<>();
    
    static {
        // Initialiser quelques utilisateurs de test
        users.put("admin", new UserCredentials("admin", "admin123", new String[]{"ADMIN", "USER"}));
        users.put("user", new UserCredentials("user", "user123", new String[]{"USER"}));
        users.put("manager", new UserCredentials("manager", "manager123", new String[]{"MANAGER", "USER"}));
    }
    
    // Authentifier un utilisateur
    public static boolean authenticate(String username, String password) {
        UserCredentials credentials = users.get(username);
        
        if (credentials != null && credentials.password.equals(password)) {
            currentUsername = username;
            currentPassword = password;
            currentUserRoles = new HashSet<>(Arrays.asList(credentials.roles));
            
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║ [SECURITY] Authentification réussie");
            System.out.println("║ [SECURITY] Utilisateur: " + username);
            System.out.println("║ [SECURITY] Rôles: " + Arrays.toString(credentials.roles));
            System.out.println("╚════════════════════════════════════════════════════════╝\n");
            return true;
        }
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [SECURITY] Authentification échouée");
        System.out.println("║ [SECURITY] Utilisateur: " + username);
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        return false;
    }
    
    // Se déconnecter
    public static void logout() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [SECURITY] Déconnexion de: " + currentUsername);
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        currentUsername = null;
        currentPassword = null;
        currentUserRoles.clear();
    }
    
    // Vérifier l'autorisation avant l'exécution d'une méthode
    public static void checkAuthorization(String[] requiredRoles, String methodName) throws SecurityException {
        if (currentUsername == null) {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║ [SECURITY] ACCÈS REFUSÉ - Non authentifié");
            System.out.println("║ [SECURITY] Méthode: " + methodName);
            System.out.println("╚════════════════════════════════════════════════════════╝\n");
            throw new SecurityException("Utilisateur non authentifié. Veuillez vous connecter.");
        }
        
        // Vérifier si l'utilisateur a au moins un des rôles requis
        boolean hasRequiredRole = false;
        for (String role : requiredRoles) {
            if (currentUserRoles.contains(role)) {
                hasRequiredRole = true;
                break;
            }
        }
        
        if (!hasRequiredRole) {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║ [SECURITY] ACCÈS REFUSÉ - Rôle insuffisant");
            System.out.println("║ [SECURITY] Utilisateur: " + currentUsername);
            System.out.println("║ [SECURITY] Rôles requis: " + Arrays.toString(requiredRoles));
            System.out.println("║ [SECURITY] Rôles actuels: " + currentUserRoles);
            System.out.println("║ [SECURITY] Méthode: " + methodName);
            System.out.println("╚════════════════════════════════════════════════════════╝\n");
            throw new SecurityException("Accès refusé. Rôle insuffisant pour accéder à: " + methodName);
        }
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [SECURITY] ACCÈS AUTORISÉ");
        System.out.println("║ [SECURITY] Utilisateur: " + currentUsername);
        System.out.println("║ [SECURITY] Méthode: " + methodName);
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    // Obtenir l'utilisateur courant
    public static String getCurrentUsername() {
        return currentUsername;
    }
    
    // Vérifier si l'utilisateur est authentifié
    public static boolean isAuthenticated() {
        return currentUsername != null;
    }
    
    // Ajouter un utilisateur (pour les tests)
    public static void addUser(String username, String password, String[] roles) {
        users.put(username, new UserCredentials(username, password, roles));
    }
    
    // Classe interne pour stocker les credentials
    private static class UserCredentials {
        String username;
        String password;
        String[] roles;
        
        UserCredentials(String username, String password, String[] roles) {
            this.username = username;
            this.password = password;
            this.roles = roles;
        }
    }
    
    private static class Map<K, V> extends java.util.HashMap<K, V> {}
}
