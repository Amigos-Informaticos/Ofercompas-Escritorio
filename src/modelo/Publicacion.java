package modelo;

import datos.API;

public abstract class Publicacion {
    protected String titulo;
    protected String descripcion;
    protected String fechaCreacion;
    protected String fechaFin;
    protected int puntuacion;
    protected EstadoPublicacion estado;
    protected Categoria categoria;
    protected int idPublicador;
    protected API api;

    public int getIdPublicador() {
        return idPublicador;
    }

    public void setIdPublicador(int idPublicador) {
        this.idPublicador = idPublicador;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Publicacion(String titulo, String descripcion, String fechaInicio, String fechaFin, int puntuacion, EstadoPublicacion estado, Categoria categoria) {
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
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", puntuacion=" + puntuacion +
                ", estado=" + estado +
                ", categoria=" + categoria +
                '}';
    }

    public boolean estaCompleta(){
        return this.titulo != null && this.descripcion != null && this.fechaCreacion != null && this.fechaFin != null && this.categoria != null;
    }

    public void setCategoriaCmbBox(String categoria){
        switch (categoria){
            case "Tecnologia":
                this.categoria = Categoria.TECNOLOGIA;
                break;
            case "Moda de mujer":
                this.categoria = Categoria.MODAMUJER;
                break;
            case "Moda de hombre":
                this.categoria = Categoria.MODAHOMBRE;
                break;
            case "Hogar":
                this.categoria = Categoria.HOGAR;
                break;
            case "Mascotas":
                this.categoria = Categoria.MASCOTAS;
                break;
            case "Viaje":
                this.categoria = Categoria.VIAJE;
                break;
            case "Comida y bebida":
                this.categoria = Categoria.COMIDABEBIDA;
                break;
            default:
                this.categoria = Categoria.TECNOLOGIA;
                break;
        }

    }
}
