package com.agents.aspects;

import com.agents.aspects.annotations.Cachable;
import com.agents.model.Transaction;

import java.util.HashMap;
import java.util.Map;

public class CachingAspect {
    
    // Cache pour stocker les résultats
    private static final Map<String, Object> cache = new HashMap<>();
    
    // Intercepte les méthodes annotées avec @Cachable
    public static Object cacheResult(String cacheKey, CacheableOperation operation) {
        // Vérifier si le résultat est en cache
        if (cache.containsKey(cacheKey)) {
            System.out.println("╔════════════════════════════════════════════════════════╗");
            System.out.println("║ [CACHE] Cache HIT pour: " + cacheKey);
            System.out.println("║ [CACHE] Retour de la valeur en cache");
            System.out.println("╚════════════════════════════════════════════════════════╝\n");
            return cache.get(cacheKey);
        }
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [CACHE] Cache MISS pour: " + cacheKey);
        System.out.println("║ [CACHE] Calcul du résultat...");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        // Calculer le résultat
        Object result = operation.execute();
        
        // Mettre en cache
        cache.put(cacheKey, result);
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║ [CACHE] Résultat mis en cache pour: " + cacheKey);
        System.out.println("╚════════════════════════════════════════════════════════╝\n");
        
        return result;
    }
    
    // Vider le cache
    public static void clearCache() {
        cache.clear();
        System.out.println("[CACHE] Cache vidé");
    }
    
    // Invalider une entrée spécifique du cache
    public static void invalidate(String cacheKey) {
        cache.remove(cacheKey);
        System.out.println("[CACHE] Cache invalidé pour: " + cacheKey);
    }
    
    // Interface fonctionnelle pour les opérations cachables
    @FunctionalInterface
    public interface CacheableOperation {
        Object execute();
    }
}
