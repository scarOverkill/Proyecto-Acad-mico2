package modelo;

import Modelo.ListaFlechas;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class Juego {

    public static int ANCHO_ZONA_SELECCION;
    private int puntuacion;
    private int tiempoLimite; // Tiempo límite en segundos
    private int tiempoRestante; // Tiempo restante en segundos
    private Timer temporizador;
    private ListaFlechas flechas;
    private Random random;
    

    private static final int VELOCIDAD_FLECHAS = 5; // Ajusta la velocidad de las flechas aquí
    public static final int ZONA_SELECCION_Y = 500; // Ajusta la posición Y de la zona de selección
    public static final int ALTO_ZONA_SELECCION = 10; // Ajusta el alto de la zona de selección
    private static final int INTERVALO_APARICION_FLECHAS = 50; // Intervalo en milisegundos para la aparición de flechas
    private static final int PUNTOS_ACIERTO = 10; // Puntos por acierto
    private static final int PUNTOS_FALLO = -5; // Puntos por fallo
    private static final int DURACION_IMAGEN = 500; // Duración en milisegundos para mostrar las imágenes de acierto y fallo

    private String direccionSeleccionada;
    private boolean mostrarAcierto;
    private boolean mostrarFallo;
    private Timer temporizadorImagen;

    public Juego() {
        this.puntuacion = 0;
        this.tiempoLimite = 6000; // Tiempo límite en segundos (1 minuto)
        this.tiempoRestante = tiempoLimite;
        this.flechas = new ListaFlechas();
        this.random = new Random();
        this.direccionSeleccionada = "";
        this.mostrarAcierto = false;
        this.mostrarFallo = false;

        // Temporizador para actualizar el juego cada 1000 / 60 ms (aproximadamente 16.67 ms)
        int fps = 60; // FPS del juego
        temporizador = new Timer(1000 / fps, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizar();
            }
        });
        temporizador.start();

        // Temporizador para mostrar imágenes de acierto y fallo
        temporizadorImagen = new Timer(DURACION_IMAGEN, e -> {
            mostrarAcierto = false;
            mostrarFallo = false;
        });
        temporizadorImagen.setRepeats(false); // Solo se ejecuta una vez

        // Reproducir música al iniciar el juego
        reproducirMusica("/audio/musica_fondo.wav"); // Ruta del archivo de audio
    }

    private void reproducirMusica(String rutaArchivo) {
        try {
            File archivo = new File(getClass().getResource(rutaArchivo).toURI());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle continuo
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        // Actualizar el temporizador
        if (tiempoRestante > 0) {
            tiempoRestante--;
        } else {
            temporizador.stop();
            mostrarPuntuacionFinal();
        }

        // Mover las flechas
        moverFlechas();

        // Añadir nuevas flechas con intervalo
        if (random.nextInt(INTERVALO_APARICION_FLECHAS) < 1) { //Ajusta la frecuencia aqui
            añadirFlecha();
        }
    }
    
    private void moverFlechas() {
        int tamaño = flechas.tamaño();
        for (int i = 0; i < tamaño; i++) {
            Flecha flecha = flechas.obtener(i);
            flecha.mover(VELOCIDAD_FLECHAS); // Usar la velocidad constante
            // Verificar si la flecha ha alcanzado la zona de selección
            if (flecha.getY() > ZONA_SELECCION_Y && flecha.getY() < ZONA_SELECCION_Y + ALTO_ZONA_SELECCION) { // Ajusta el rango de la zona de selección
                if (flecha.getDireccion().equals(direccionSeleccionada)) {
                    puntuacion += PUNTOS_ACIERTO; // Sumar puntos si la dirección es correcta
                    mostrarAcierto = true; // Mostrar imagen de acierto
                } else {
                    puntuacion += PUNTOS_FALLO; // Restar puntos si la dirección es incorrecta
                    mostrarFallo = true; // Mostrar imagen de fallo
                }
                flechas.eliminar(i); // Elimina la flecha del juego
                i--; // Ajustar el índice después de eliminar
                tamaño--; // Ajustar el tamaño después de eliminar
                direccionSeleccionada = ""; // Reiniciar la dirección seleccionada
                // Iniciar el temporizador para ocultar la imagen después de DURACION_IMAGEN ms
                temporizadorImagen.restart();
            }
            // Verificar si la flecha ha llegado al final del panel
            if (flecha.getY() > 600) { // Ajusta según el tamaño del panel
                flechas.eliminar(i); // Elimina la flecha del juego
                i--; // Ajustar el índice después de eliminar
                tamaño--; // Ajustar el tamaño después de eliminar
                direccionSeleccionada = ""; // Reiniciar la dirección seleccionada
            }
        }
    }

    public void añadirFlecha() {
        int x = random.nextInt(800); // Ajusta según el tamaño del panel
        String direccion = obtenerDireccionAleatoria(); // Dirección aleatoria
        Flecha nuevaFlecha = new Flecha(x, 0, direccion); // Crea una nueva flecha en la parte superior
        flechas.agregar(nuevaFlecha);
    }

    private String obtenerDireccionAleatoria() {
        String[] direcciones = {"arriba", "abajo", "izquierda", "derecha"};
        return direcciones[random.nextInt(direcciones.length)];
    }

    public ListaFlechas getFlechas() {
        return flechas;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void seleccionarDireccion(String direccion) {
        direccionSeleccionada = direccion;
    }

    private void mostrarPuntuacionFinal() {
        // Mostrar el puntaje final usando un cuadro de diálogo
        JOptionPane.showMessageDialog(null, "¡El tiempo ha terminado! \nPuntuación final: " + puntuacion,
                "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
    }

    public String getDireccionSeleccionada() {
        return direccionSeleccionada;
    }

    public boolean isMostrarAcierto() {
        return mostrarAcierto;
    }

    public boolean isMostrarFallo() {
        return mostrarFallo;
    }
}
