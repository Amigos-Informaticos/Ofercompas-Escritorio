package modelo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;

public class Oferta extends Publicacion {
    private String vinculo;
    private String precio;


    public Oferta(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, EstadoPublicacion estado, Categoria categoria, String vinculo, String precio) {
        super(titulo, descripcion, fechaInicio, fechaFin, puntuacion, estado, categoria);
        this.vinculo = vinculo;
        this.precio = precio;
    }

    public Oferta() {
        super();
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return super.toString() + "Oferta{" +
                "vinculo='" + vinculo + '\'' +
                ", precio='" + precio + '\'' +
                '}';
    }

    public int publicar() throws IOException {
        HashMap respuesta = this.api.connect("POST", "ofertas", null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }

    public HashMap obtenerHashmap() {
        HashMap<String, String> oferta = new HashMap<String, String>();
        oferta.put("titulo", this.titulo);
        oferta.put("descripcion", this.descripcion);
        oferta.put("fechaCreacion", this.fechaCreacion);
        oferta.put("fechaFin", this.fechaFin);
        oferta.put("categoria", String.valueOf(this.categoria.getIndice()));
        oferta.put("vinculo", this.getVinculo());
        oferta.put("precio", this.precio);
        oferta.put("publicador", String.valueOf(this.idPublicacion));
        System.out.println(oferta.toString());

        return oferta;

    }

    public boolean estaCompleta() {
        return super.estaCompleta() && this.vinculo != null && this.precio != null;
    }



    public Oferta deJsonAObjeto(JsonObject ofertaJson) {
        Oferta oferta = new Oferta();
        oferta.setTitulo(String.valueOf(ofertaJson.get("titulo")));
        oferta.setDescripcion(String.valueOf(ofertaJson.get("descripcion")));
        oferta.setFechaCreacion(String.valueOf(ofertaJson.get("fechaCreacion")));
        oferta.setFechaFin(String.valueOf(ofertaJson.get("fechaFin")));
        oferta.setPrecio(String.valueOf(ofertaJson.get("precio")));
        oferta.setVinculo(String.valueOf(ofertaJson.get("vinculo")));
        return oferta;
    }

    public Oferta[] obtenerOfertas(int pagina) throws IOException {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        return getOfertas(parametros);
    }

    public Oferta[] obtenerOfertas(int pagina, int categoria) throws IOException {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        parametros.put("categoria", String.valueOf(categoria));
        return getOfertas(parametros);
    }

    private Oferta[] getOfertas(HashMap<String, String> parametros) throws IOException {
        HashMap respuesta = this.api.connect("GET", "ofertas", parametros, null, null, true);
        Oferta[] ofertasConvertidas = new Oferta[0];
        if (respuesta.get("status").equals(200)) {
            JsonArray ofertasObtenidas = (JsonArray) respuesta.get("json");
            ofertasConvertidas = new Oferta[ofertasObtenidas.size()];
            for (int i = 0; i < ofertasObtenidas.size(); i++) {
                ofertasConvertidas[i] = deJsonAObjeto((JsonObject) ofertasObtenidas.get(i));
            }
        }
        return ofertasConvertidas;
    }


}
