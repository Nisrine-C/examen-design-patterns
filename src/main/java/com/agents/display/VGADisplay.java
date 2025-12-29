package com.agents.display;

public class VGADisplay implements VGA {
    private String name;

    public VGADisplay(String name) {
        this.name = name;
    }

    @Override
    public void show(byte[] data) {
        String message = new String(data);
        System.out.println("[" + name + " - VGA Display] " + message);
    }
}
