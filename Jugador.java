package modelo;

public class Jugador {
    private int puntuacion;

    public Jugador() {
        this.puntuacion = 0;
    }

    public void añadirPuntos(int puntos) {
        this.puntuacion += puntos;
    }
}

