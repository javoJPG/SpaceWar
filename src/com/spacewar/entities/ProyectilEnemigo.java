package com.spacewar.entities;

public class ProyectilEnemigo extends Proyectil {
    private final int danio;
    private final int varianteSprite;
    private final boolean deJefe;

    public ProyectilEnemigo(int x, int y, int varianteSprite) {
        super(x, y);
        this.danio = 1;
        this.varianteSprite = varianteSprite;
        this.deJefe = false;
    }

    public ProyectilEnemigo(int x, int y, boolean deJefe) {
        super(x, y);
        this.danio = 1;
        this.varianteSprite = 0;
        this.deJefe = deJefe;
    }

    @Override
    public void actualizar() {
        setY(getY() + getVelocidad());
        if (getY() > 800) {
            setActivo(false);
        }
    }

    public int getDanio() { return danio; }
    public int getVarianteSprite() { return varianteSprite; }
    public boolean isDeJefe() { return deJefe; }
}
