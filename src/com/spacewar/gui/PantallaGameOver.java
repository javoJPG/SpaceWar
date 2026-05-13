package com.spacewar.gui;

import com.spacewar.logic.Puntaje;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PantallaGameOver implements KeyListener {
    private Puntaje puntaje;
    private boolean reiniciar;

    public PantallaGameOver(Puntaje puntaje) {
        this.puntaje  = puntaje;
        this.reiniciar = false;
    }
    public void resetear() {
        reiniciar = false;
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 56));
        g.drawString("GAME OVER", 210, 250);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Puntaje: " + puntaje.getPuntaje(), 320, 330);
        g.drawString("Record:  " + puntaje.getRecord(),  320, 365);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Presiona ENTER para reiniciar", 255, 450);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) reiniciar = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isReiniciar() { return reiniciar; }
}