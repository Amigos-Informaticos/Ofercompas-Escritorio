package pruebas;

import datos.API;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEnvioArchivos {
    @Test
    public void mandarArchivo(){
        String path = "C:\\Users\\griml\\OneDrive\\Escritorio\\Test mandar imagenes\\Blasphemous.png";
        File file = new File(path);
        API api = new API();
        try {
            HashMap resultados = api.sendFiles("POST","/ofertas/28/imagenes",null,null,null,file);
            assertEquals(resultados.get("status"),200);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }
}
