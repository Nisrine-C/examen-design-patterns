package com.agents.display;

// Pattern Adapter - Permet d'adapter un afficheur VGA pour utiliser l'interface HDMI
public class VgaHdmiAdapter implements HDMI {
    private VGA vga;

    public VgaHdmiAdapter(VGA vga) {
        this.vga = vga;
    }

    public void setVga(VGA vga) {
        this.vga = vga;
    }

    @Override
    public void print(String message) {
        // Adapter le message String en byte[] pour l'interface VGA
        byte[] data = message.getBytes();
        vga.show(data);
    }
}
