package modelo;

import com.google.gson.annotations.SerializedName;
import datos.API;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

public abstract class Publicacion {
    @SerializedName("titulo")
    protected String titulo;
    @SerializedName("descripcion")
    protected String descripcion;
    @SerializedName("fechaCreacion")
    protected String fechaCreacion;
    @SerializedName("fechaFin")
    protected String fechaFin;
    @SerializedName("puntuacion")
    protected int puntuacion;
    @SerializedName("estado")
    protected EstadoPublicacion estado;
    @SerializedName("categria")
    protected int categoria = 0;
    @SerializedName("idPublicacion")
    protected int idPublicacion;

    protected Multimedia foto;
    protected Multimedia video;

    public Multimedia getFoto() {
        return foto;
    }

    public void setFoto(Multimedia foto) {
        this.foto = foto;
    }

    public Multimedia getVideo() {
        return video;
    }

    public void setVideo(Multimedia video) {
        this.video = video;
    }

    public int getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(int idPublicador) {
        this.idPublicador = idPublicador;
    }

    @SerializedName("idPublicador")
    protected int idPublicador;

    protected API api;

    public int getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(int idPublicacion) {
        this.idPublicacion = idPublicacion;
        api = new API();
        this.api.setURL("http://127.0.0.1");
        api.setPort(5000);
    }

    public Publicacion() {
        api = new API();
        this.api.setURL("http://127.0.0.1");
        api.setPort(5000);

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacion estado) {
        this.estado = estado;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public Publicacion(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, EstadoPublicacion estado, int categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaInicio;
        this.fechaFin = fechaFin;
        this.puntuacion = puntuacion;
        this.estado = estado;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "ID " + idPublicacion + '\'' +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", puntuacion=" + puntuacion +
                ", estado=" + estado +
                ", categoria=" + categoria +
                ", publicador=" + idPublicador +
                '}';
    }

    public boolean estaCompleta() {
        return this.titulo != null && this.descripcion != null && this.fechaCreacion != null && this.fechaFin != null && this.categoria != 0;
    }

    public void setCategoriaCmbBox(String categoria) {
        System.out.println("CATEGORIA: " + categoria);
        if (categoria != null){
            switch (categoria) {
                case "Tecnologia":
                    this.categoria = Categoria.TECNOLOGIA.getIndice();
                    break;
                case "Moda de mujer":
                    this.categoria = Categoria.MODAMUJER.getIndice();
                    break;
                case "Moda de hombre":
                    this.categoria = Categoria.MODAHOMBRE.getIndice();
                    break;
                case "Hogar":
                    this.categoria = Categoria.HOGAR.getIndice();
                    break;
                case "Mascotas":
                    this.categoria = Categoria.MASCOTAS.getIndice();
                    break;
                case "Viaje":
                    this.categoria = Categoria.VIAJE.getIndice();
                    break;
                case "Entretenimiento":
                    this.categoria = Categoria.COMIDABEBIDA.getIndice();
                    break;
                case "Comida y bebida":
                    this.categoria = Categoria.COMIDABEBIDA.getIndice();
                    break;
                default:
                    this.categoria = Categoria.TECNOLOGIA.getIndice();
                    break;
            }
        }
    }

    public String deCategoriaACmbBox() {
        String categoriaString = "";
        int categoria = this.categoria;
        switch (categoria) {
            case 1:
                categoriaString = "Tecnologia";
                break;
            case 2:
                categoriaString = "Moda de mujer";
                break;
            case 3:
                categoriaString = "Moda de hombre";
                break;
            case 4:
                categoriaString = "Hogar";
                break;
            case 5:
                categoriaString = "Mascotas";
                break;
            case 6:
                categoriaString = "Viaje";
                break;
            case 7:
                categoriaString = "Entretenimiento";
                break;
            case 8:
                categoriaString = "Comida y bebida";
                break;
            default:
                categoriaString = "Tecnologia";
                break;
        }
        return categoriaString;
    }

    public int puntuar(int idMiembro, int esPositiva) throws IOException {
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("idMiembro", String.valueOf(idMiembro));
        parametros.put("esPositiva", String.valueOf(esPositiva));
        HashMap respuesta = api.connect("POST", ("publicaciones/" + this.idPublicacion + "/puntuaciones"), null, parametros, API.getToken(), false );
        return (int) respuesta.get("status");
    }

    public int eliminar() throws IOException {
        HashMap respuesta = this.api.connect("DELETE", ("publicaciones/" + this.idPublicacion), null, null, API.getToken(), false);
        return (int) respuesta.get("status");
    }
    public int prohibir() throws IOException {
        HashMap respuesta = this.api.connect("DELETE", ("publicaciones/" + this.idPublicacion +"/prohibir"), null, null, API.getToken(), false);
        return (int) respuesta.get("status");
    }

    public HashMap obtenerInteraccion(int idMiembroLogeado) throws IOException {
        String url = "publicaciones/"+ this.idPublicacion + "/interaccion";
        HashMap<String, String> json = new HashMap<>();
        json.put("idMiembro", String.valueOf(idMiembroLogeado));
        API api = new API();
        System.out.println(json.toString());
        HashMap respuesta = api.connect("GET", url, null , null, json, false);


        return respuesta;

    }
    public  int denunciar(int idDenunciante, String comentario, int motivo) throws IOException {
        API api = new API();
        String url = "publicaciones/"+ this.idPublicacion +"/denuncias";
        HashMap<String, String> denuncia = new HashMap<>();
        denuncia.put("idDenunciante", String.valueOf(idDenunciante));
        denuncia.put("comentario", comentario);
        denuncia.put("motivo", String.valueOf(motivo));

        HashMap respuesta = api.connect("POST", url, null, denuncia, API.getToken(), false);

        return (int)respuesta.get("status");

    }

    public boolean validarFechas() {
        LocalDate fechaInicial = LocalDate.parse(fechaCreacion);
        LocalDate fechaFinal = LocalDate.parse(fechaFin);
        return fechaFinal.isAfter(fechaInicial);
    }

}
