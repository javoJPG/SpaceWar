package com.spacewar.logic;

import com.spacewar.entities.PowerUp;
import com.spacewar.entities.enemies.Boss;
import com.spacewar.entities.enemies.Enemigo;
import com.spacewar.entities.enemies.EnemigoBasico;
import com.spacewar.entities.enemies.EnemigoEspecial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nivel {
    private int nivelActual;
    private int enemigosPorNivel;
    private int enemigosEliminados;
    private List<Enemigo> enemigos;
    private List<PowerUp> powerUps;
    private int anchoPantalla;
    private int altoPantalla;
    private Random random;
    private boolean hayBoss;

    public Nivel(int anchoPantalla, int altoPantalla) {
        this.nivelActual = 1;
        this.enemigosPorNivel = 5;
        this.enemigosEliminados = 0;
        this.enemigos = new ArrayList<>();
        this.powerUps = new ArrayList<>();
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.random = new Random();
        this.hayBoss = false;
        generarEnemigos();
    }

    public void actualizar() {
        enemigos.removeIf(e -> !e.isActivo());
        for (Enemigo e : enemigos) {
            e.actualizar();
        }
        separarEnemigosNoBoss();

        powerUps.removeIf(p -> !p.isActivo());
        for (PowerUp p : powerUps) {
            p.actualizar(altoPantalla);
        }

        if (enemigos.isEmpty() && !hayBoss) {
            siguienteNivel();
        }
    }

    /**
     * Empuja enemigos que no son el boss para evitar solapamiento entre sí.
     */
    private void separarEnemigosNoBoss() {
        for (int iter = 0; iter < 10; iter++) {
            for (int i = 0; i < enemigos.size(); i++) {
                Enemigo a = enemigos.get(i);
                if (!debeSeparar(a)) {
                    continue;
                }
                for (int j = i + 1; j < enemigos.size(); j++) {
                    Enemigo b = enemigos.get(j);
                    if (!debeSeparar(b)) {
                        continue;
                    }
                    empujarYRebotarSiSolapan(a, b);
                }
            }
        }
    }

    private boolean debeSeparar(Enemigo e) {
        return e.isActivo() && !(e instanceof Boss);
    }

    private void empujarYRebotarSiSolapan(Enemigo a, Enemigo b) {
        int wa = a.getAncho();
        int ha = a.getAlto();
        int wb = b.getAncho();
        int hb = b.getAlto();
        int overlapX = Math.min(a.getX() + wa, b.getX() + wb) - Math.max(a.getX(), b.getX());
        int overlapY = Math.min(a.getY() + ha, b.getY() + hb) - Math.max(a.getY(), b.getY());
        if (overlapX <= 0 || overlapY <= 0) {
            return;
        }

        int cxA = a.getX() + wa / 2;
        int cxB = b.getX() + wb / 2;
        int cyA = a.getY() + ha / 2;
        int cyB = b.getY() + hb / 2;

        if (overlapX < overlapY) {
            int push = Math.max(1, (overlapX + 1) / 2);
            int dir = Integer.compare(cxB, cxA);
            if (dir == 0) {
                dir = 1;
            }
            a.setX(clampX(a, a.getX() - push * dir));
            b.setX(clampX(b, b.getX() + push * dir));
        } else {
            int push = Math.max(1, (overlapY + 1) / 2);
            int dir = Integer.compare(cyB, cyA);
            if (dir == 0) {
                dir = 1;
            }
            a.setY(clampY(a, a.getY() - push * dir));
            b.setY(clampY(b, b.getY() + push * dir));
        }

        if (a.puedeRebotarConEnemigos() && b.puedeRebotarConEnemigos()) {
            a.empujarHorizontalLejosDe(b);
            b.empujarHorizontalLejosDe(a);
        }
    }

    private int clampX(Enemigo e, int nx) {
        int w = e.getAncho();
        if (nx < 0) {
            return 0;
        }
        if (nx + w > anchoPantalla) {
            return anchoPantalla - w;
        }
        return nx;
    }

    private int clampY(Enemigo e, int ny) {
        int h = e.getAlto();
        int minY = -600;
        int maxY = altoPantalla - h;
        return Math.max(minY, Math.min(ny, maxY));
    }

    /**
     * Cada fila queda más abajo que la anterior; dentro de la fila hay variación.
     * Valores más pequeños = se paran más arriba en pantalla.
     */
    private int calcularLimiteDescenso(int row) {
        int minLimite = 72;
        int pasoEntreFilas = 46;
        int jitter = random.nextInt(28);
        int limite = minLimite + row * pasoEntreFilas + jitter;
        int techoInferior = altoPantalla - 140;
        return Math.min(techoInferior, Math.max(minLimite, limite));
    }

    private void generarEnemigos() {
        enemigos.clear();
        powerUps.clear();
        if (nivelActual % 3 == 0) {
            hayBoss = true;
            enemigos.add(new Boss(anchoPantalla / 2, 0));
        } else {
            hayBoss = false;
            int gap = 10;
            int cell = Enemigo.TAMANO_HITBOX + gap;
            int cols = Math.max(1, (anchoPantalla - gap) / cell);

            int col = 0;
            int row = 0;
            for (int i = 0; i < enemigosPorNivel; i++) {
                int x = gap + col * cell;
                int y = -Enemigo.TAMANO_HITBOX - row * cell;
                int limiteY = calcularLimiteDescenso(row);
                enemigos.add(new EnemigoBasico(x, y, anchoPantalla, limiteY));
                col++;
                if (col >= cols) {
                    col = 0;
                    row++;
                }
            }

            int espCol = random.nextInt(cols);
            int espRow = row + 1;
            int espX = gap + espCol * cell;
            int espY = -Enemigo.TAMANO_HITBOX - espRow * cell;
            enemigos.add(new EnemigoEspecial(espX, espY, anchoPantalla,
                    calcularLimiteDescenso(espRow)));
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

    public void dejarCaerPowerUp(Enemigo e) {
        if (!(e instanceof EnemigoEspecial)) {
            return;
        }
        PowerUp.Tipo[] tipos = PowerUp.Tipo.values();
        PowerUp.Tipo tipo = tipos[random.nextInt(tipos.length)];
        int px = e.getX() + e.getAncho() / 2 - PowerUp.TAMANO / 2;
        int py = e.getY() + e.getAlto() / 2;
        powerUps.add(new PowerUp(px, py, tipo));
    }

    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public int getEnemigosEliminados() {
        return enemigosEliminados;
    }

    public boolean hayBoss() {
        return hayBoss;
    }
}
