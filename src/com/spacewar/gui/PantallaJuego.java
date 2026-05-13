package com.spacewar.gui;

import com.spacewar.entities.Nave;
import com.spacewar.entities.Proyectil;
import com.spacewar.entities.ProyectilEnemigo;
import com.spacewar.entities.enemies.Enemigo;
import com.spacewar.logic.Vida;
import com.spacewar.logic.Puntaje;
import com.spacewar.logic.Nivel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PantallaJuego {
    private Nave nave;
    private Vida vida;
    private Puntaje puntaje;
    private Nivel nivel;

    public PantallaJuego(Nave nave, Vida vida, Puntaje puntaje, Nivel nivel) {
        this.nave    = nave;
        this.vida    = vida;
        this.puntaje = puntaje;
        this.nivel   = nivel;
    }

    public void dibujar(Graphics g) {
        dibujarNave(g);
        dibujarProyectiles(g);
        dibujarEnemigos(g);
        dibujarHUD(g);
    }

    private void dibujarNave(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto());
    }

    private void dibujarProyectiles(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Proyectil p : nave.getProyectiles()) {
            g.fillRect(p.getX(), p.getY(), 5, 10);
        }
    }

    private void dibujarEnemigos(Graphics g) {
        for (Enemigo e : nivel.getEnemigos()) {
            g.setColor(Color.RED);
            g.fillRect(e.getX(), e.getY(), 40, 40);

            g.setColor(Color.ORANGE);
            for (ProyectilEnemigo p : e.getProyectiles()) {
                g.fillRect(p.getX(), p.getY(), 5, 10);
            }
        }
    }

    private void dibujarHUD(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Puntaje: " + puntaje.getPuntaje(), 20, 30);
        g.drawString("Nivel: "   + nivel.getNivelActual(), 20, 55);

        if (puntaje.getMultiplicador() > 1) {
            g.setColor(Color.YELLOW);
            g.drawString("x" + puntaje.getMultiplicador(), 20, 80);
        }

        g.setColor(Color.RED);
        for (int i = 0; i < vida.getVidaActual(); i++) {
            g.fillOval(680 + (i * 25), 15, 20, 20);
        }
    }
}