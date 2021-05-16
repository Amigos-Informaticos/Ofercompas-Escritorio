package modelo;

public class Oferta extends Publicacion{
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

    public void publicar(){
        System.out.println(this.toString());
    }

    public boolean estaCompleta(){
        return super.estaCompleta() && this.vinculo != null && this.precio != null;
    }
}
