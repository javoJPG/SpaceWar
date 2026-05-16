package com.spacewar.logic;

import com.spacewar.entities.Nave;
import com.spacewar.entities.PowerUp;
import com.spacewar.entities.Proyectil;
import com.spacewar.entities.ProyectilEnemigo;
import com.spacewar.entities.enemies.Enemigo;
import com.spacewar.entities.enemies.EnemigoEspecial;

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
        verificarPowerUps();
    }

    private void verificarProyectilesNave() {
        for (Proyectil p : nave.getProyectiles()) {
            for (Enemigo e : nivel.getEnemigos()) {
                if (p.isActivo() && e.isActivo() && colisiona(
                        p.getX(), p.getY(), 5, 10,
                        e.getX(), e.getY(), e.getAncho(), e.getAlto())) {
                    p.setActivo(false);
                    e.recibirDanio(1);
                    if (!e.isActivo()) {
                        puntaje.sumar(100);
                        nivel.enemigoEliminado();
                        puntaje.aumentarMultiplicador();
                        if (e instanceof EnemigoEspecial) {
                            nivel.dejarCaerPowerUp(e);
                        }
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
                    aplicarDanioAlJugador(p.getDanio());
                    puntaje.resetearMultiplicador();
                }
            }
        }
    }

    private void verificarContactoDirecto() {
        for (Enemigo e : nivel.getEnemigos()) {
            if (e.isActivo() && colisiona(
                    e.getX(), e.getY(), e.getAncho(), e.getAlto(),
                    nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto())) {
                boolean eraEspecial = e instanceof EnemigoEspecial;
                e.recibirDanio(e.getVida());
                aplicarDanioAlJugador(1);
                puntaje.resetearMultiplicador();
                if (!e.isActivo() && eraEspecial) {
                    nivel.dejarCaerPowerUp(e);
                }
            }
        }
    }

    private void verificarPowerUps() {
        List<PowerUp> lista = nivel.getPowerUps();
        for (PowerUp p : lista) {
            if (!p.isActivo() || !vida.estaVivo()) {
                continue;
            }
            if (colisiona(
                    p.getX(), p.getY(), PowerUp.TAMANO, PowerUp.TAMANO,
                    nave.getX(), nave.getY(), nave.getAncho(), nave.getAlto())) {
                p.setActivo(false);
                switch (p.getTipo()) {
                    case VIDA -> vida.curar(1);
                    case DISPARO_RAPIDO -> nave.activarDisparoRapido();
                    case ESCUDO -> nave.agregarEscudo(1);
                }
            }
        }
    }

    private void aplicarDanioAlJugador(int danio) {
        if (nave.intentarAbsorberConEscudo()) {
            return;
        }
        vida.recibirDanio(danio);
    }

    private boolean colisiona(int x1, int y1, int w1, int h1,
                              int x2, int y2, int w2, int h2) {
        Rectangle r1 = new Rectangle(x1, y1, w1, h1);
        Rectangle r2 = new Rectangle(x2, y2, w2, h2);
        return r1.intersects(r2);
    }
}
