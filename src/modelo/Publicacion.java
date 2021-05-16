package modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Publicacion {
    private String titulo;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private int puntuacion;
    private EstadoPublicacion estado;
    private Categoria categoria;

    public Publicacion() {

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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
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
        this.fechaInicio = fechaInicio;
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
                ", fechaInicio='" + fechaInicio + '\'' +
                ", fechaFin='" + fechaFin + '\'' +
                ", puntuacion=" + puntuacion +
                ", estado=" + estado +
                ", categoria=" + categoria +
                '}';
    }

    public boolean estaCompleta(){
        return this.titulo != null && this.descripcion != null && this.fechaInicio != null && this.fechaFin != null && this.categoria != null;
    }
}
