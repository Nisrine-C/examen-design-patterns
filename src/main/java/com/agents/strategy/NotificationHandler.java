package com.agents.strategy;

import com.agents.model.Evenement;

public interface NotificationHandler {
    void apply(Evenement e);
}
