package com.eduMap.edumap.GLOBALE.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class SystemStateService {
    private final AtomicReference<System> selectedSystem = new AtomicReference<>();

    public void setSystem(System system) {
        selectedSystem.set(system);
    }

    public System getSystem() {
        return selectedSystem.get();
    }


}
