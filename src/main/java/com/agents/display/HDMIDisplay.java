package com.agents.display;

public class HDMIDisplay implements HDMI {
    private String name;

    public HDMIDisplay(String name) {
        this.name = name;
    }

    @Override
    public void print(String message) {
        System.out.println("[" + name + " - HDMI Display] " + message);
    }
}
