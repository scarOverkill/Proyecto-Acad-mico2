package vista;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

public class ImagenLoader {

    public Image cargarImagen(String ruta) {
        Image imagen = null;
        try (InputStream is = getClass().getResourceAsStream(ruta)) {
            if (is != null) {
                imagen = ImageIO.read(is);
            } else {
                System.err.println("No se pudo encontrar la imagen en la ruta: " + ruta);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
        return imagen;
    }
}
