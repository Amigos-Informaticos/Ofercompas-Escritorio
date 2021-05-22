package modelo;

import datos.API;

import java.net.ConnectException;
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

    public int publicar() throws ConnectException {
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
        oferta.put("publicador", String.valueOf(this.idPublicador));

        return oferta;
    }

    public boolean estaCompleta() {
        return super.estaCompleta() && this.vinculo != null && this.precio != null;
    }

    public Oferta[] obtenerOfertas(int pagina, int categoria) throws ConnectException {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        parametros.put("categoria", String.valueOf(categoria));
        return getOfertas(parametros);
    }

    public Oferta deHashmapAObjeto(HashMap ofertaHashmap) {
        Oferta oferta = new Oferta();
        oferta.setTitulo((String) ofertaHashmap.get("titulo"));
        oferta.setDescripcion((String) ofertaHashmap.get("descripcion"));
        oferta.setFechaCreacion((String) ofertaHashmap.get("fechaCreacion"));
        oferta.setFechaFin((String) ofertaHashmap.get("fechaFin"));
        oferta.setPrecio((String) ofertaHashmap.get("precio"));
        oferta.setVinculo((String) ofertaHashmap.get("vinculo"));
        return oferta;
    }

    public Oferta[] obtenerOfertas(int pagina) throws ConnectException {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        return getOfertas(parametros);
    }

    private Oferta[] getOfertas(HashMap<String, String> parametros) throws ConnectException {
        HashMap respuesta = this.api.connect("GET", "ofertas", parametros, null, null, false);
        Oferta[] ofertasConvertidas = new Oferta[0];
        if (respuesta.get("status").equals("200")) {
            HashMap[] ofertasObtenidas = (HashMap[]) respuesta.get("json");
            ofertasConvertidas = new Oferta[ofertasObtenidas.length];
            for (int i = 0, ofertasObtenidasLength = ofertasObtenidas.length; i < ofertasObtenidasLength; i++) {
                ofertasConvertidas[i] = deHashmapAObjeto(ofertasObtenidas[i]);
            }
        }
        return ofertasConvertidas;
    }


}
