package modelo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import datos.API;
import vista.MainController;


import java.io.IOException;
import java.util.HashMap;

public class Oferta extends Publicacion {
    private String vinculo;
    private String precio;

    public Oferta(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, EstadoPublicacion estado, int categoria, String vinculo, String precio) {
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
        HashMap respuesta = this.api.connect("POST", "ofertas", null, this.obtenerHashmap(), API.getToken(), false);
        if (respuesta.get("status").toString().equals("201")){
            HashMap ofertaRecibida = (HashMap) respuesta.get("json");
            Double idDouble = (Double) ofertaRecibida.get("idPublicacion");
            this.idPublicacion = idDouble.intValue();
        }
        return (int) respuesta.get("status");
    }

    public HashMap obtenerHashmap() {
        MiembroOfercompas miembro = (MiembroOfercompas) MainController.get("miembroLogeado");
        HashMap<String, String> oferta = new HashMap<String, String>();
        oferta.put("titulo", this.titulo);
        oferta.put("descripcion", this.descripcion);
        oferta.put("fechaCreacion", this.fechaCreacion);
        oferta.put("fechaFin", this.fechaFin);
        oferta.put("categoria", String.valueOf(this.categoria));
        oferta.put("vinculo", this.getVinculo());
        oferta.put("precio", this.precio);
        oferta.put("publicador", String.valueOf(miembro.getIdMiembro()));
        System.out.println(oferta.toString());

        return oferta;

    }

    public boolean estaCompleta() {
        return super.estaCompleta() && this.vinculo != null && this.precio != null&&
                this.foto != null;
    }


    public Oferta deJsonAObjeto(JsonObject ofertaJson) {
        Oferta oferta = new Oferta();
        oferta.setIdPublicacion(Integer.parseInt(String.valueOf(ofertaJson.get("idPublicacion"))));
        oferta.setTitulo(ofertaJson.get("titulo").getAsString());
        oferta.setDescripcion(ofertaJson.get("descripcion").getAsString());
        oferta.setFechaCreacion(ofertaJson.get("fechaCreacion").getAsString());
        oferta.setFechaFin(ofertaJson.get("fechaFin").getAsString());
        oferta.setPrecio(ofertaJson.get("precio").getAsString());
        oferta.setVinculo(ofertaJson.get("vinculo").getAsString());
        oferta.setPuntuacion(Integer.parseInt(String.valueOf(ofertaJson.get("puntuacion"))));
        oferta.setIdPublicador(Integer.parseInt((ofertaJson.get("publicador").getAsString())));
        oferta.setCategoria(Integer.parseInt(String.valueOf(ofertaJson.get("categoria"))));
        Multimedia foto = new Multimedia();
        foto.setUrl(ofertaJson.get("imagen").getAsString());
        oferta.setFoto(foto);
        Multimedia video = new Multimedia();
        video.setUrl(ofertaJson.get("video").getAsString());
        oferta.setVideo(video);


        System.out.println(oferta.getIdPublicador());
        return oferta;
    }

    public Oferta[] obtenerOfertasSinCategoria(int pagina) throws IOException {
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
        HashMap respuesta = this.api.connect("GET", "ofertas", parametros, null, API.getToken(), true);
        System.out.println("LA RESPUESTA ES:" + respuesta);
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

    public Oferta[] obtenerOfertasPorPublicador(int pagina, int idPublicador) throws IOException {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        parametros.put("idPublicador", String.valueOf(idPublicador));
        return getOfertas(parametros);
    }


    public int actualizar() throws IOException {
        HashMap respuesta = this.api.connect("PUT", ("ofertas/"+this.idPublicacion), null, this.obtenerHashmap(), API.getToken(), false);
        return (int) respuesta.get("status");
    }

}
