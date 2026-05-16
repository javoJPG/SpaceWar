package com.spacewar.gui;

import com.spacewar.entities.Nave;
import com.spacewar.entities.PowerUp;
import com.spacewar.entities.Proyectil;
import com.spacewar.entities.ProyectilEnemigo;
import com.spacewar.entities.enemies.Enemigo;
import com.spacewar.entities.enemies.EnemigoEspecial;
import com.spacewar.entities.enemies.Boss;
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
        dibujarPowerUps(g);
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
            if (e instanceof Boss) {
                g.setColor(new Color(180, 0, 60));
            } else if (e instanceof EnemigoEspecial) {
                g.setColor(new Color(200, 50, 255));
            } else {
                g.setColor(Color.RED);
            }
            g.fillRect(e.getX(), e.getY(), e.getAncho(), e.getAlto());

            g.setColor(Color.ORANGE);
            for (ProyectilEnemigo p : e.getProyectiles()) {
                g.fillRect(p.getX(), p.getY(), 5, 10);
            }
        }
    }

    private void dibujarPowerUps(Graphics g) {
        for (PowerUp p : nivel.getPowerUps()) {
            if (!p.isActivo()) {
                continue;
            }
            switch (p.getTipo()) {
                case VIDA -> g.setColor(Color.GREEN);
                case DISPARO_RAPIDO -> g.setColor(Color.YELLOW);
                case ESCUDO -> g.setColor(Color.CYAN);
            }
            g.fillOval(p.getX(), p.getY(), PowerUp.TAMANO, PowerUp.TAMANO);
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