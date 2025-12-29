package com.agents.aspects;

import com.agents.aspects.annotations.Log;

import java.lang.reflect.Method;

public class LoggingAspect {
    
    // Intercepte toutes les méthodes annotées avec @Log
    public static Object logExecutionTime(Object target, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {
            long startTime = System.currentTimeMillis();
            
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║ [LOG] Début d'exécution: " + method.getName());
            System.out.println("╚════════════════════════════════════════════════════════╝");
            
            Object result = null;
            try {
                result = method.invoke(target, args);
            } finally {
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                
                System.out.println("╔════════════════════════════════════════════════════════╗");
                System.out.println("║ [LOG] Fin d'exécution: " + method.getName());
                System.out.println("║ [LOG] Durée d'exécution: " + duration + " ms");
                System.out.println("╚════════════════════════════════════════════════════════╝\n");
            }
            
            return result;
        }
        
        return method.invoke(target, args);
    }
    
    // Méthode utilitaire pour logger l'exécution d'une action
    public static void logMethodEntry(String methodName, Object... params) {
        System.out.println("\n╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [LOG] Entrée dans la méthode: " + methodName);
        if (params != null && params.length > 0) {
            System.out.println("║ [LOG] Paramètres: ");
            for (int i = 0; i < params.length; i++) {
                System.out.println("║       [" + i + "] " + params[i]);
            }
        }
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    public static void logMethodExit(String methodName, long durationMs) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [LOG] Sortie de la méthode: " + methodName);
        System.out.println("║ [LOG] Durée: " + durationMs + " ms");
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
    }
}
