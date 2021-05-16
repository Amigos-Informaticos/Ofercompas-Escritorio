package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Oferta;

public class PublicarOfertaController {
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private ComboBox cmbCategoria;

    private Oferta oferta;

    public void initialize(){
        llenarComboCategorias();
    }

    public void instanciaOferta(){
        oferta = new Oferta();
        oferta.setTitulo(txtTitulo.getText());
        oferta.setDescripcion(txtDescripcion.getText());
        oferta.setPrecio(txtPrecio.getText());
        oferta.setFechaInicio(String.valueOf(fechaInicio.getValue()));
        oferta.setFechaFin(String.valueOf(fechaFin.getValue()));
    }

    public void llenarComboCategorias(){
        ObservableList<String> listaCategorias = FXCollections.observableArrayList();
        listaCategorias.add("Tecnologia");
        listaCategorias.add("Moda de mujer");
        listaCategorias.add("Moda de hombre");
        listaCategorias.add("Hogar");
        listaCategorias.add("Mascotas");
        listaCategorias.add("Viaje");
        listaCategorias.add("Entretenimiento");
        listaCategorias.add("Comida y bebida");cmbCategoria.setItems(listaCategorias);
    }

    public void publicar(){
        instanciaOferta();
        if(oferta.estaCompleta()){
            oferta.publicar();
        }else{
            System.out.println("Llena bien los campos");
        }
    }



}
