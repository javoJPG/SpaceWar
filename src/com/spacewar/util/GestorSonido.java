package com.spacewar.util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.InputStream;

public final class GestorSonido {
    private static final GestorSonido INSTANCIA = new GestorSonido();
    private static final String CARPETA = "/resources/sonidos/";
    private static final String MUSICA_FONDO =
            "YOGURCITO-REMIX-_-BLESSD-❌-ANUEL-AA-❌-YAN-BLOCK-❌-LUAR-LA-L-❌-ROA-❌-KRIS-R.mp3";
    /** ~50 % de volumen respecto al nivel original (20·log10(0,5) ≈ −6 dB). */
    private static final float VOLUMEN_DISPARO_DB = -6.0f;

    private volatile boolean musicaActiva;
    private volatile Player reproductorMusica;
    private Thread hiloMusica;

    private GestorSonido() {}

    public static GestorSonido getInstancia() {
        return INSTANCIA;
    }

    public synchronized void iniciarMusicaFondo() {
        detenerMusicaFondo();
        musicaActiva = true;
        hiloMusica = new Thread(this::reproducirMusicaEnBucle, "musica-fondo");
        hiloMusica.setDaemon(true);
        hiloMusica.start();
    }

    public synchronized void detenerMusicaFondo() {
        musicaActiva = false;
        cerrarReproductorMusica();
        if (hiloMusica != null) {
            hiloMusica.interrupt();
            try {
                hiloMusica.join(1000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            hiloMusica = null;
        }
    }

    private void reproducirMusicaEnBucle() {
        while (musicaActiva) {
            Player player = null;
            try (InputStream in = abrir(MUSICA_FONDO);
                 BufferedInputStream bis = new BufferedInputStream(in)) {
                player = new Player(bis);
                reproductorMusica = player;
                player.play();
            } catch (Exception ignored) {
            } finally {
                if (reproductorMusica == player) {
                    reproductorMusica = null;
                }
                if (player != null) {
                    player.close();
                }
            }
            if (!musicaActiva || Thread.currentThread().isInterrupted()) {
                break;
            }
        }
    }

    private void cerrarReproductorMusica() {
        Player player = reproductorMusica;
        reproductorMusica = null;
        if (player != null) {
            player.close();
        }
    }

    public void reproducirProyectil() {
        reproducirEfectoConVolumen("proyectiles.mp3", VOLUMEN_DISPARO_DB);
    }

    public void reproducirPowerUp() {
        reproducirEfecto("power ups.mp3");
    }

    public void reproducirExplosionDerrota() {
        reproducirEfecto("explosionDerrota.mp3");
    }

    private void reproducirEfecto(String archivo) {
        reproducirEfectoConVolumen(archivo, 0f);
    }

    private void reproducirEfectoConVolumen(String archivo, float volumenDb) {
        Thread hilo = new Thread(() -> {
            try (InputStream in = abrir(archivo);
                 BufferedInputStream bis = new BufferedInputStream(in)) {
                if (volumenDb == 0f) {
                    new Player(bis).play();
                } else {
                    new AdvancedPlayer(bis, new DispositivoConVolumen(volumenDb)).play();
                }
            } catch (Exception ignored) {
            }
        }, "sfx-" + archivo);
        hilo.setDaemon(true);
        hilo.start();
    }

    private static final class DispositivoConVolumen extends JavaSoundAudioDevice {
        private final float volumenDb;

        DispositivoConVolumen(float volumenDb) {
            this.volumenDb = volumenDb;
        }

        @Override
        protected void writeImpl(short[] samples, int offs, int len) throws JavaLayerException {
            float factor = (float) Math.pow(10, volumenDb / 20.0);
            for (int i = 0; i < len; i++) {
                samples[offs + i] = (short) (samples[offs + i] * factor);
            }
            super.writeImpl(samples, offs, len);
        }
    }

    private InputStream abrir(String nombre) {
        InputStream in = GestorSonido.class.getResourceAsStream(CARPETA + nombre);
        if (in == null) {
            throw new IllegalStateException("No se encontro el sonido: " + CARPETA + nombre);
        }
        return in;
    }
}
