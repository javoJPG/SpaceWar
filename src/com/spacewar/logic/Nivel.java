package com.spacewar.logic;

import com.spacewar.entities.enemies.Enemigo;
import com.spacewar.entities.enemies.EnemigoBasico;
import com.spacewar.entities.enemies.Boss;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nivel {
    private int nivelActual;
    private int enemigosPorNivel;
    private int enemigosEliminados;
    private List<Enemigo> enemigos;
    private int anchoPantalla;
    private Random random;
    private boolean hayBoss;

    public Nivel(int anchoPantalla) {
        this.nivelActual = 1;
        this.enemigosPorNivel = 5;
        this.enemigosEliminados = 0;
        this.enemigos = new ArrayList<>();
        this.anchoPantalla = anchoPantalla;
        this.random = new Random();
        this.hayBoss = false;
        generarEnemigos();
    }

    public void actualizar() {
        enemigos.removeIf(e -> !e.isActivo());
        for (Enemigo e : enemigos) {
            e.actualizar();
        }
        if (enemigos.isEmpty() && !hayBoss) {
            siguienteNivel();
        }
    }

    private void generarEnemigos() {
        enemigos.clear();
        if (nivelActual % 3 == 0) {
            hayBoss = true;
            enemigos.add(new Boss(anchoPantalla / 2, 0));
        } else {
            hayBoss = false;
            for (int i = 0; i < enemigosPorNivel; i++) {
                int x = random.nextInt(anchoPantalla - 50);
                enemigos.add(new EnemigoBasico(x, 0));
            }
        }
    }

    private void siguienteNivel() {
        nivelActual++;
        enemigosPorNivel += 2;
        generarEnemigos();
    }

    public void enemigoEliminado() {
        enemigosEliminados++;
    }

    public List<Enemigo> getEnemigos() { return enemigos; }
    public int getNivelActual() { return nivelActual; }
    public int getEnemigosEliminados() { return enemigosEliminados; }
    public boolean hayBoss() { return hayBoss; }
}