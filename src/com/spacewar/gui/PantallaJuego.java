package com.spacewar.gui;

import com.spacewar.entities.Nave;
import com.spacewar.entities.PowerUp;
import com.spacewar.entities.Proyectil;
import com.spacewar.entities.ProyectilEnemigo;
import com.spacewar.entities.enemies.Boss;
import com.spacewar.entities.enemies.Enemigo;
import com.spacewar.logic.Vida;
import com.spacewar.logic.Puntaje;
import com.spacewar.logic.Nivel;
import com.spacewar.util.Recursos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

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
        g.drawImage(Recursos.JUGADOR, nave.getX(), nave.getY(),
                nave.getAncho(), nave.getAlto(), null);
    }

    private void dibujarProyectiles(Graphics g) {
        for (Proyectil p : nave.getProyectiles()) {
            dibujarProyectil(g, Recursos.PROYECTIL_JUGADOR, p);
        }
    }

    private void dibujarProyectil(Graphics g, BufferedImage sprite, Proyectil p) {
        int w = p.getAncho();
        int h = p.getAlto();
        g.drawImage(sprite, p.getX() - w / 2, p.getY(), w, h, null);
    }

    private void dibujarEnemigos(Graphics g) {
        for (Enemigo e : nivel.getEnemigos()) {
            BufferedImage sprite = e instanceof Boss
                    ? Recursos.JEFE
                    : Recursos.enemigo(e.getVarianteSprite());
            g.drawImage(sprite, e.getX(), e.getY(), e.getAncho(), e.getAlto(), null);

            for (ProyectilEnemigo p : e.getProyectiles()) {
                BufferedImage proyectil = p.isDeJefe()
                        ? Recursos.PROYECTIL_JEFE
                        : Recursos.proyectilEnemigo(p.getVarianteSprite());
                dibujarProyectil(g, proyectil, p);
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

        int tamanoVida = 20;
        int separacionVida = 25;
        for (int i = 0; i < vida.getVidaActual(); i++) {
            int x = 680 + (i * separacionVida);
            g.drawImage(Recursos.VIDA, x, 15, tamanoVida, tamanoVida, null);
        }
    }
}