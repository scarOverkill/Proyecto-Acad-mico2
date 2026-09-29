package modelo;

import java.awt.Graphics;
import java.awt.Image;

public class Flecha {
    private int x, y;
    private String direccion;

    public Flecha(int x, int y, String direccion) {
        this.x = x;
        this.y = y;
        this.direccion = direccion;
    }

    public void mover(int velocidad) {
        y += velocidad;
    }

    public void dibujar(Graphics g, Image flechaArriba, Image flechaAbajo, Image flechaIzquierda, Image flechaDerecha) {
        Image imagenFlecha;
        switch (direccion) {
            case "arriba":
                imagenFlecha = flechaArriba;
                break;
            case "abajo":
                imagenFlecha = flechaAbajo;
                break;
            case "izquierda":
                imagenFlecha = flechaIzquierda;
                break;
            case "derecha":
                imagenFlecha = flechaDerecha;
                break;
            default:
                imagenFlecha = flechaArriba; // Default
                break;
        }
        g.drawImage(imagenFlecha, x, y, null);
    }

    public int getY() {
        return y;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getX() {
        return x;
    }
}
