package controlador;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import modelo.Juego;
import vista.PanelJuego;

public class ControladorJuego extends KeyAdapter {
    private Juego juego;
    private PanelJuego vista;

    public ControladorJuego(Juego juego, PanelJuego vista) {
        this.juego = juego;
        this.vista = vista;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Manejar eventos de teclado y actualizar el juego
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                // Comprobar si una flecha hacia arriba ha sido acertada
                break;
            case KeyEvent.VK_DOWN:
                // Comprobar si una flecha hacia abajo ha sido acertada
                break;
            case KeyEvent.VK_LEFT:
                // Comprobar si una flecha hacia la izquierda ha sido acertada
                break;
            case KeyEvent.VK_RIGHT:
                // Comprobar si una flecha hacia la derecha ha sido acertada
                break;
        }
    }
}

