package Modelo;

import modelo.Flecha;  // Asegúrate de que esta importación es correcta
import modelo.Nodo;    // Asegúrate de que esta importación es correcta

public class ListaFlechas {
   
    private Nodo Lista;
    
    public ListaFlechas(){
        this.Lista = null;
    }
    
    public void agregar(Flecha flecha) {
        if (Lista == null) {
            Lista = new Nodo(flecha);
        } else {
            agregarRecursivo(Lista, flecha);
        }
    }

    private void agregarRecursivo(Nodo nodo, Flecha flecha) {
        if (nodo.siguiente == null) {
            nodo.siguiente = new Nodo(flecha);
        } else {
            agregarRecursivo(nodo.siguiente, flecha);
        }
    }

    public void eliminar(int index) {
        Lista = eliminarRecursivo(Lista, index);
    }

    private Nodo eliminarRecursivo(Nodo nodo, int index) {
        if (index == 0) {
            return nodo.siguiente;
        }
        nodo.siguiente = eliminarRecursivo(nodo.siguiente, index - 1);
        return nodo;
    }

    public Flecha obtener(int index) {
        return obtenerRecursivo(Lista, index);
    }

    private Flecha obtenerRecursivo(Nodo nodo, int index) {
        if (index == 0) {
            return nodo.flecha;
        }
        return obtenerRecursivo(nodo.siguiente, index - 1);
    }

    public int tamaño() {
        return tamañoRecursivo(Lista);
    }

    private int tamañoRecursivo(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + tamañoRecursivo(nodo.siguiente);
    }

    public void ordenarBurbuja() {
        boolean swapped;
        do {
            swapped = false;
            Nodo actual = Lista;
            while (actual != null && actual.siguiente != null) {
                if (actual.flecha.getY() > actual.siguiente.flecha.getY()) {
                    Flecha temp = actual.flecha;
                    actual.flecha = actual.siguiente.flecha;
                    actual.siguiente.flecha = temp;
                    swapped = true;
                }
                actual = actual.siguiente;
            }
        } while (swapped);
    }

    public void quickSort() {
        Lista = quickSortRecursivo(Lista);
    }

    private Nodo quickSortRecursivo(Nodo nodo) {
        if (nodo == null || nodo.siguiente == null) {
            return nodo;
        }

        Nodo pivote = nodo;
        Nodo menor = null, mayor = null;

        Nodo actual = nodo.siguiente;
        while (actual != null) {
            if (actual.flecha.getY() < pivote.flecha.getY()) {
                menor = agregarNodo(menor, actual.flecha);
            } else {
                mayor = agregarNodo(mayor, actual.flecha);
            }
            actual = actual.siguiente;
        }

        menor = quickSortRecursivo(menor);
        mayor = quickSortRecursivo(mayor);

        return combinarListas(menor, pivote, mayor);
    }

    private Nodo agregarNodo(Nodo cabeza, Flecha flecha) {
        if (cabeza == null) {
            return new Nodo(flecha);
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = new Nodo(flecha);
            return cabeza;
        }
    }

    private Nodo combinarListas(Nodo menor, Nodo pivote, Nodo mayor) {
        Nodo resultado = menor;

        if (menor == null) {
            resultado = pivote;
        } else {
            Nodo actual = menor;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = pivote;
        }

        pivote.siguiente = mayor;

        return resultado;
    }

    public int busquedaBinaria(int y) {
        int[] posiciones = toArray();
        return busquedaBinariaRecursiva(posiciones, 0, posiciones.length - 1, y);
    }

    private int busquedaBinariaRecursiva(int[] arr, int izq, int der, int x) {
        if (der >= izq) {
            int medio = izq + (der - izq) / 2;

            if (arr[medio] == x) {
                return medio;
            }

            if (arr[medio] > x) {
                return busquedaBinariaRecursiva(arr, izq, medio - 1, x);
            }

            return busquedaBinariaRecursiva(arr, medio + 1, der, x);
        }

        return -1;
    }

    public int[] toArray() {
        int tamaño = tamaño();
        int[] arr = new int[tamaño];
        Nodo actual = Lista;
        for (int i = 0; i < tamaño; i++) {
            arr[i] = actual.flecha.getY();
            actual = actual.siguiente;
        }
        return arr;
    }
    
    public Nodo getLista(){
        return Lista;
    }
}
