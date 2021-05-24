package pruebas;

import datos.API;
import modelo.Categoria;
import modelo.Oferta;
import modelo.Oferta;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class pruebasOferta {
    private Oferta ofertaPrueba;

    public void instanciaOferta(){
        ofertaPrueba = new Oferta();
        ofertaPrueba.setTitulo("Volovanes");
        ofertaPrueba.setDescripcion("De Don volo");
        ofertaPrueba.setPrecio("18");
        ofertaPrueba.setVinculo("ofercompas.shop.com");
        ofertaPrueba.setFechaCreacion("2021-05-17");
        ofertaPrueba.setFechaFin("2021-05-30");
        ofertaPrueba.setCategoria(Categoria.COMIDABEBIDA);
        ofertaPrueba.setIdPublicacion(7);
    }

    @Test
    public void pruebaDefault(){
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
        HashMap respuesta = null;
        try {
            respuesta = api.connect("GET","miembros");
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (IOException connectException) {

        }
    }

    @Test
    public void obtenerOfertas(){
        try {
            instanciaOferta();
            Oferta[] ofertas = ofertaPrueba.obtenerOfertas(1,-1);
            ofertas[0].toString();
            assertNotNull(ofertas[0].getTitulo());
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

}
