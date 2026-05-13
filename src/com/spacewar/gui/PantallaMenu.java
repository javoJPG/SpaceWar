package com.spacewar.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PantallaMenu implements KeyListener {
    private boolean iniciar;

    public PantallaMenu() {
        this.iniciar = false;
    }
    public void resetear() {
        iniciar = false;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("SPACE WAR", 250, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Presiona ENTER para jugar", 230, 350);

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        g.drawString("WASD o flechas para mover", 270, 450);
        g.drawString("ESPACIO para disparar", 290, 480);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) iniciar = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isIniciar() { return iniciar; }
}