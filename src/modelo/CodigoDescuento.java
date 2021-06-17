package modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
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

    public CodigoDescuento(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, EstadoPublicacion estado, String codigo, int categoria) {
        super(titulo, descripcion, fechaInicio, fechaFin, puntuacion, estado, categoria);
        this.codigo = codigo;
    }

    public int publicar() throws IOException {
        HashMap respuesta = this.api.connect("POST", "codigos", null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }

    public HashMap obtenerHashmap() {
        HashMap<String, String> codigo = new HashMap();
        codigo.put("titulo", this.titulo);
        codigo.put("descripcion", this.descripcion);
        codigo.put("fechaCreacion", this.fechaCreacion);
        codigo.put("fechaFin", this.fechaFin);
        codigo.put("categoria", String.valueOf(this.categoria));
        codigo.put("publicador", String.valueOf(7));
        codigo.put("codigo", this.codigo);

        return codigo;
    }

    public boolean estaCompleta() {
        return super.estaCompleta() && this.codigo != null;
    }

    public CodigoDescuento[] obtenerCodigos(int pagina, int categoria) throws Exception {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        parametros.put("categoria", String.valueOf(categoria));
        return getCodigos(parametros);
    }

    public CodigoDescuento deHashmapAObjeto(HashMap codigoHashmap) {
        CodigoDescuento codigoDescuento = new CodigoDescuento();
        codigoDescuento.setTitulo((String) codigoHashmap.get("titulo"));
        codigoDescuento.setDescripcion((String) codigoHashmap.get("descripcion"));
        codigoDescuento.setFechaCreacion((String) codigoHashmap.get("fechaCreacion"));
        codigoDescuento.setFechaFin((String) codigoHashmap.get("fechaFin"));
        return codigoDescuento;
    }

    public CodigoDescuento[] obtenerCodigos(int pagina) throws Exception {
        HashMap<String, String> parametros = new HashMap();
        parametros.put("pagina", String.valueOf(pagina));
        return getCodigos(parametros);
    }

    private CodigoDescuento[] getCodigos(HashMap<String, String> parametros) throws IOException {
        HashMap respuesta = this.api.connect("GET", "codigos", parametros, null, null, true);
        CodigoDescuento[] codigosConvertidos = new CodigoDescuento[0];
        if (respuesta.get("status").equals(200)) {
            JsonArray codigosObtenidos = (JsonArray) respuesta.get("json");
            codigosConvertidos = new CodigoDescuento[codigosObtenidos.size()];
            for (int i = 0; i < codigosObtenidos.size(); i++) {
                codigosConvertidos[i] = deJsonAObjeto((JsonObject) codigosObtenidos.get(i));
            }
        }
        return codigosConvertidos;
    }

    public CodigoDescuento deJsonAObjeto(JsonObject codigoJson) {
        CodigoDescuento codigoDescuento = new CodigoDescuento();
        codigoDescuento.setIdPublicacion(Integer.parseInt(String.valueOf(codigoJson.get("idPublicacion"))));
        codigoDescuento.setTitulo(String.valueOf(codigoJson.get("titulo")));
        codigoDescuento.setDescripcion(String.valueOf(codigoJson.get("descripcion")));
        codigoDescuento.setFechaCreacion(String.valueOf(codigoJson.get("fechaCreacion")));
        codigoDescuento.setFechaFin(String.valueOf(codigoJson.get("fechaFin")));
        codigoDescuento.setCodigo(String.valueOf(codigoJson.get("codigo")));
        return codigoDescuento;
    }

    public int actualizar() throws IOException {
        HashMap respuesta = this.api.connect("PUT", ("codigos"+this.idPublicacion), null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }
}
