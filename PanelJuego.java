package vista;


import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import javax.swing.Timer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import modelo.Juego;
import modelo.Flecha;
import modelo.Nodo;

public class PanelJuego extends JPanel {
    private Juego juego;
    private Image fondo;
    private Image flechaArriba;
    private Image flechaAbajo;
    private Image flechaIzquierda;
    private Image flechaDerecha;
    private Image imagenAcierto;
    private Image imagenFallo;
    private Image figuraImagen;  // Imagen que reemplazará al círculo
    private Timer timer;

    private int figuraX = 50;  // Coordenada X inicial de la figura
    private int figuraY = 50;  // Coordenada Y inicial de la figura
    private final int FIGURA_ANCHO = 190;  // Ancho de la imagen
    private final int FIGURA_ALTO = 190;  // Alto de la imagen

    public PanelJuego(Juego juego) {
        this.juego = juego;
        ImagenLoader imagenLoader = new ImagenLoader();
        fondo = imagenLoader.cargarImagen("/imagenes/fondo.jpg");
        flechaArriba = imagenLoader.cargarImagen("/imagenes/flecha_arriba.png");
        flechaAbajo = imagenLoader.cargarImagen("/imagenes/flecha_abajo.png");
        flechaIzquierda = imagenLoader.cargarImagen("/imagenes/flecha_izquierda.png");
        flechaDerecha = imagenLoader.cargarImagen("/imagenes/flecha_derecha.png");
        imagenAcierto = imagenLoader.cargarImagen("/imagenes/acierto.png");
        imagenFallo = imagenLoader.cargarImagen("/imagenes/fallo.png");

        // Cargar la imagen desde el archivo
        try {
            figuraImagen = ImageIO.read(new File("C:\\Users\\scarl\\Downloads/personaje.png"));
            figuraImagen = figuraImagen.getScaledInstance(FIGURA_ANCHO, FIGURA_ALTO, Image.SCALE_SMOOTH); // Redimensionar imagen
        } catch (IOException e) {
            e.printStackTrace();
        }

        int fps = 60;
        timer = new Timer(1000 / fps, e -> {
            repaint(); 
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Mover la figura a la posición de la flecha correspondiente
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        juego.seleccionarDireccion("arriba");
                        moverFiguraAFlecha("arriba");
                        break;
                    case KeyEvent.VK_DOWN:
                        juego.seleccionarDireccion("abajo");
                        moverFiguraAFlecha("abajo");
                        break;
                    case KeyEvent.VK_LEFT:
                        juego.seleccionarDireccion("izquierda");
                        moverFiguraAFlecha("izquierda");
                        break;
                    case KeyEvent.VK_RIGHT:
                        juego.seleccionarDireccion("derecha");
                        moverFiguraAFlecha("derecha");
                        break;
                }
                repaint(); // Redibujar el panel para reflejar el movimiento
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    private void moverFiguraAFlecha(String direccion) {
        Nodo actual = (Nodo) juego.getFlechas().getLista();

        while (actual != null) {
            Flecha flecha = actual.flecha;
            if (flecha.getDireccion().equals(direccion)) {
                figuraX = flecha.getX(); // Mueve la figura a la posición X de la flecha
                figuraY = flecha.getY(); // Mueve la figura a la posición Y de la flecha
                break;
            }
            actual = actual.siguiente;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);

        // Dibujar la imagen en lugar del círculo
        if (figuraImagen != null) {
            g.drawImage(figuraImagen, figuraX, figuraY, this);
        }

        Nodo actual = (Nodo) juego.getFlechas().getLista();
        
        while(actual != null){
            Flecha flecha = actual.flecha;
            Image imagenFlecha;
            switch(flecha.getDireccion()){
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
                    imagenFlecha = null;
            }
            if(imagenFlecha != null){
                g.drawImage(imagenFlecha, flecha.getX(), flecha.getY(), this);
            }
            actual = actual.siguiente;
        }

        int zonaY = Juego.ZONA_SELECCION_Y;
        g.setColor(Color.GRAY);
        g.fillRect(0, zonaY, getWidth(), Juego.ALTO_ZONA_SELECCION);
        g.setColor(Color.BLACK);
        g.drawRect(0, zonaY, getWidth(), Juego.ALTO_ZONA_SELECCION);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        int minutos = juego.getTiempoRestante() / 60;
        int segundos = juego.getTiempoRestante() % 60;
        g.drawString(String.format("Tiempo: %02d:%02d", minutos, segundos), 10, 30);

        g.drawString("Puntuación: " + juego.getPuntuacion(), 10, 60);

        if (juego.isMostrarAcierto()) {
            g.drawImage(imagenAcierto, getWidth() / 2 - imagenAcierto.getWidth(null) / 2, getHeight() / 2 - imagenAcierto.getHeight(null) / 2, this);
        } else if (juego.isMostrarFallo()) {
            g.drawImage(imagenFallo, getWidth() / 2 - imagenFallo.getWidth(null) / 2, getHeight() / 2 - imagenFallo.getHeight(null) / 2, this);
        }
    }
}



