package com.spacewar.logic;

import com.spacewar.entities.Nave;
import com.spacewar.entities.Proyectil;
import com.spacewar.entities.ProyectilEnemigo;
import com.spacewar.entities.enemies.Enemigo;
import java.awt.Rectangle;
import java.util.List;

public class Colision {
    private Nave nave;
    private Vida vida;
    private Puntaje puntaje;
    private Nivel nivel;

    public Colision(Nave nave, Vida vida, Puntaje puntaje, Nivel nivel) {
        this.nave = nave;
        this.vida = vida;
        this.puntaje = puntaje;
        this.nivel = nivel;
    }

    public void verificar() {
        verificarProyectilesNave();
        verificarProyectilesEnemigos();
        verificarContactoDirecto();
    }

    private void verificarProyectilesNave() {
        for (Proyectil p : nave.getProyectiles()) {
            for (Enemigo e : nivel.getEnemigos()) {
                if (p.isActivo() && e.isActivo() && colisiona(
                        p.getX(), p.getY(), 5, 10,
                        e.getX(), e.getY(), 40, 40)) {
                    p.setActivo(false);
                    e.recibirDanio(1);
                    if (!e.isActivo()) {
                        puntaje.sumar(100);
                        nivel.enemigoEliminado();
                        puntaje.aumentarMultiplicador();
                    }
                }
            }
        }
    }

    private void verificarProyectilesEnemigos() {
        for (Enemigo e : nivel.getEnemigos()) {
            for (ProyectilEnemigo p : e.getProyectiles()) {
                if (p.isActivo() && vida.estaVivo() && colisiona(
                        p.getX(), p.getY(), 5, 10,
                        nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto())) {
                    p.setActivo(false);
                    vida.recibirDanio(p.getDanio());
                    puntaje.resetearMultiplicador();
                }
            }
        }
    }

    private void verificarContactoDirecto() {
        for (Enemigo e : nivel.getEnemigos()) {
            if (e.isActivo() && colisiona(
                    e.getX(), e.getY(), 40, 40,
                    nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto())) {
                e.recibirDanio(e.getVida());
                vida.recibirDanio(1);
                puntaje.resetearMultiplicador();
            }
        }
    }

    private boolean colisiona(int x1, int y1, int w1, int h1,
                              int x2, int y2, int w2, int h2) {
        Rectangle r1 = new Rectangle(x1, y1, w1, h1);
        Rectangle r2 = new Rectangle(x2, y2, w2, h2);
        return r1.intersects(r2);
    }
}