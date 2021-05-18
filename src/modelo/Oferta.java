package modelo;

import datos.API;

import java.util.HashMap;

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

    public int publicar(){
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);

        HashMap respuesta = api.connect("POST","ofertas",null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }

    public HashMap obtenerHashmap(){
        HashMap<String ,String> oferta = new HashMap<String, String>();
        oferta.put("titulo",this.titulo);
        oferta.put("descripcion",this.descripcion);
        oferta.put("fechaCreacion",this.fechaCreacion);
        oferta.put("fechaFin",this.fechaFin);
        oferta.put("categoria", String.valueOf(this.categoria.getIndice()));
        oferta.put("vinculo",this.getVinculo());
        oferta.put("precio",this.precio);
        oferta.put("publicador", String.valueOf(this.idPublicador));

        return oferta;
    }

    public boolean estaCompleta(){
        return super.estaCompleta() && this.vinculo != null && this.precio != null;
    }


}
