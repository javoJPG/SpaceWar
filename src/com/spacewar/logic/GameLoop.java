package com.spacewar.logic;

import com.spacewar.entities.Nave;
import com.spacewar.gui.PantallaJuego;
import com.spacewar.gui.PantallaMenu;
import com.spacewar.gui.PantallaGameOver;
import com.spacewar.util.GestorSonido;

import javax.swing.JPanel;
import java.awt.Graphics;

public class GameLoop extends JPanel implements Runnable {
    private static final int FPS = 60;
    private static final int ANCHO = 800;
    private static final int ALTO = 700;

    private Thread hilo;
    private boolean corriendo;

    private GameState estado;
    private Nave nave;
    private Vida vida;
    private Puntaje puntaje;
    private Nivel nivel;
    private Colision colision;
    private ControlNave controlNave;

    private PantallaMenu pantallaMenu;
    private PantallaJuego pantallaJuego;
    private PantallaGameOver pantallaGameOver;

    public GameLoop() {
        setPreferredSize(new java.awt.Dimension(ANCHO, ALTO));
        setBackground(java.awt.Color.BLACK);
        inicializar();
    }

    private void inicializar() {
        estado = GameState.MENU;

        nave          = new Nave(ANCHO / 2, ALTO - 100);
        vida          = new Vida(3);
        puntaje       = new Puntaje();
        nivel         = new Nivel(ANCHO, ALTO);
        colision      = new Colision(nave, vida, puntaje, nivel);
        controlNave   = new ControlNave(nave, ANCHO, ALTO);

        pantallaMenu     = new PantallaMenu();
        pantallaJuego    = new PantallaJuego(nave, vida, puntaje, nivel);
        pantallaGameOver = new PantallaGameOver(puntaje);

        addKeyListener(controlNave);
        addKeyListener(pantallaMenu);
        addKeyListener(pantallaGameOver);
        setFocusable(true);
    }

    public void iniciarHilo() {
        hilo = new Thread(this);
        hilo.start();
        corriendo = true;
    }

    @Override
    public void run() {
        double intervalo = 1_000_000_000.0 / FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();

        while (corriendo) {
            long ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo) / intervalo;
            ultimoTiempo = ahora;

            if (delta >= 1) {
                actualizar();
                repaint();
                delta--;
            }
        }
    }

    private void actualizar() {
        switch (estado) {
            case MENU -> {
                if (pantallaMenu.isIniciar()) {
                    pantallaMenu.resetear();
                    estado = GameState.JUGANDO;
                    GestorSonido.getInstancia().iniciarMusicaFondo();
                }
            }
            case JUGANDO -> {
                controlNave.actualizar();
                nave.actualizar();
                nivel.actualizar();
                colision.verificar();
                if (!vida.estaVivo()) {
                    GestorSonido.getInstancia().detenerMusicaFondo();
                    GestorSonido.getInstancia().reproducirExplosionDerrota();
                    estado = GameState.GAME_OVER;
                }
            }
            case PAUSA -> {}
            case GAME_OVER -> {
                if (pantallaGameOver.isReiniciar()) {
                    pantallaGameOver.resetear();
                    inicializar();
                    estado = GameState.JUGANDO;
                    GestorSonido.getInstancia().iniciarMusicaFondo();
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (estado) {
            case MENU      -> pantallaMenu.dibujar(g);
            case JUGANDO   -> pantallaJuego.dibujar(g);
            case GAME_OVER -> pantallaGameOver.dibujar(g);
        }
    }
}