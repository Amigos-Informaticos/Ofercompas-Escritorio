package pruebas;

import datos.API;
import modelo.Categoria;
import modelo.Oferta;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class pruebasOferta {
    private Oferta ofertaPrueba;

    public void instanciaOferta(){
        ofertaPrueba = new Oferta();
        ofertaPrueba.setTitulo("Funki Punki");
        ofertaPrueba.setDescripcion("Volvieron!");
        ofertaPrueba.setPrecio("14");
        ofertaPrueba.setVinculo("ofercompas.shop.com");
        ofertaPrueba.setFechaCreacion("2021-05-17");
        ofertaPrueba.setFechaFin("2021-05-30");
        ofertaPrueba.setCategoria(Categoria.TECNOLOGIA);
        ofertaPrueba.setIdPublicador(7);
    }

    @Test
    public void pruebaDefault(){
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
        HashMap respuesta = null;
        try {
            respuesta = api.connect("GET","miembros");
        } catch (ConnectException connectException) {

        }
        HashMap jason = (HashMap) respuesta.get("json");
        System.out.println(jason.get("nickname"));
        assertEquals(respuesta.get("status"),200);
    }



    @Test
    public void registrarOferta(){
        instanciaOferta();
        try {
            assertEquals(ofertaPrueba.publicar(),201);
        } catch (ConnectException connectException) {

        }
    }

}
