package com.spacewar.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public final class Recursos {
    private static final String SPRITES_NAVES = "/resources/spritesNaves/";
    private static final String SPRITES_PROYECTILES = "/resources/spritesProyectiles/";
    private static final String SPRITES_UI = "/resources/spritesUI/";

    public static final BufferedImage JUGADOR;
    public static final BufferedImage JEFE;
    private static final BufferedImage[] ENEMIGOS = new BufferedImage[3];

    public static final BufferedImage PROYECTIL_JUGADOR;
    public static final BufferedImage PROYECTIL_JEFE;
    public static final BufferedImage VIDA;
    private static final BufferedImage[] PROYECTILES_ENEMIGO = new BufferedImage[3];

    static {
        JUGADOR = cargarNave("jugador.png");
        JEFE = cargarNave("jefe.png");
        ENEMIGOS[0] = cargarNave("enemigos.png");
        ENEMIGOS[1] = cargarNave("enemigos2.png");
        ENEMIGOS[2] = cargarNave("enemigos3.png");

        PROYECTIL_JUGADOR = cargarProyectil("proyectil jugador.png");
        PROYECTIL_JEFE = cargarProyectil("proyectil jefe.png");
        PROYECTILES_ENEMIGO[0] = cargarProyectil("proyectil enemigos.png");
        PROYECTILES_ENEMIGO[1] = cargarProyectil("proyectil enemigos2.png");
        PROYECTILES_ENEMIGO[2] = cargarProyectil("proyectil enemigos3.png");

        VIDA = cargarUI("playerLife2_red.png");
    }

    private Recursos() {}

    private static BufferedImage cargarNave(String nombre) {
        return cargar(SPRITES_NAVES, nombre);
    }

    private static BufferedImage cargarProyectil(String nombre) {
        return cargar(SPRITES_PROYECTILES, nombre);
    }

    private static BufferedImage cargarUI(String nombre) {
        return cargar(SPRITES_UI, nombre);
    }

    private static BufferedImage cargar(String carpeta, String nombre) {
        String ruta = carpeta + nombre;
        try (InputStream in = Recursos.class.getResourceAsStream(ruta)) {
            if (in == null) {
                throw new IllegalStateException("No se encontro el recurso: " + ruta);
            }
            return ImageIO.read(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static BufferedImage enemigo(int variante) {
        return ENEMIGOS[Math.floorMod(variante, ENEMIGOS.length)];
    }

    public static BufferedImage proyectilEnemigo(int variante) {
        return PROYECTILES_ENEMIGO[Math.floorMod(variante, PROYECTILES_ENEMIGO.length)];
    }
}
