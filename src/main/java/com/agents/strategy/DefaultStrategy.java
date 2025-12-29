package com.agents.strategy;

import com.agents.model.Evenement;

public class DefaultStrategy implements NotificationHandler {
    @Override
    public void apply(Evenement e) {
        System.out.println("Stratégie par défaut - Notification reçue: " + e);
    }
}
