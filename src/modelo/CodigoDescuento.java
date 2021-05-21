package modelo;

import java.util.HashMap;

public class CodigoDescuento extends Publicacion{
    private String codigo;

    public CodigoDescuento() {
        super();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public CodigoDescuento(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, EstadoPublicacion estado, String codigo, Categoria categoria) {
        super(titulo, descripcion, fechaInicio, fechaFin, puntuacion, estado, categoria);
        this.codigo = codigo;
    }

    public int publicar() {
        HashMap respuesta = this.api.connect("POST", "ofertas", null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }

    public HashMap obtenerHashmap() {
        HashMap<String, String> codigo = new HashMap();
        codigo.put("titulo", this.titulo);
        codigo.put("descripcion", this.descripcion);
        codigo.put("fechaCreacion", this.fechaCreacion);
        codigo.put("fechaFin", this.fechaFin);
        codigo.put("categoria", String.valueOf(this.categoria.getIndice()));
        codigo.put("publicador", String.valueOf(this.idPublicador));
        codigo.put("codigo", this.codigo);

        return codigo;
    }

    public boolean estaCompleta() {
        return super.estaCompleta() && this.codigo != null;
    }

    public Oferta[] obtenerCodigos(int pagina, int categoria) {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        parametros.put("categoria", String.valueOf(categoria));
        return getCodigos(parametros);
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

    public Oferta[] obtenerCodigos(int pagina) {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        return getCodigos(parametros);
    }

    private Oferta[] getCodigos(HashMap<String, String> parametros) {
        HashMap respuesta = this.api.connect("GET", "codigos", parametros);
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
