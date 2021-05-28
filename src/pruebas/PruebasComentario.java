package pruebas;

import modelo.Comentario;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PruebasComentario {

    @Test
    public void obtenerComentarios(){
        Comentario comentario = new Comentario();
        try {
            Comentario[] comentarios = comentario.obtenerComentarios(27);
            System.out.println(comentarios[0].toString());
            assertNotNull(comentarios[0].getContenido());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
