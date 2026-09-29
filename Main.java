package Principal;

import javax.swing.JFrame;
import vista.PanelJuego;
import modelo.Juego;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Just Dance 4");
        Juego juego = new Juego();
        PanelJuego panel = new PanelJuego(juego);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Ajusta el tamaño del frame
        frame.add(panel);
        frame.setVisible(true);
    }
}

