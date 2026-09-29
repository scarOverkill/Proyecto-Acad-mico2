package modelo;

public class Nodo {
    public Flecha flecha;
    public Nodo siguiente;

    public Nodo(Flecha flecha) {
        this.flecha = flecha;
        this.siguiente = null;
    }
}
