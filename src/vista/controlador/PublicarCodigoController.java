package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.CodigoDescuento;
import utils.LimitadorTextField;
import vista.MainController;

import java.io.IOException;

public class PublicarCodigoController {
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private DatePicker fechaCreacion;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private ComboBox cmbCategoria;
    @FXML
    private TextField txtCodigo;

    private CodigoDescuento codigoDescuento;

    public void initialize() {
        codigoDescuento = new CodigoDescuento();
        llenarComboCategorias();
        limitarTextfields();
    }

    public void limitarTextfields() {
        LimitadorTextField.limitarTamanio(txtTitulo,30);
        LimitadorTextField.limitarTamanioArea(txtDescripcion, 200);
        LimitadorTextField.limitarTamanio(txtCodigo, 50);

    }

    public void instanciaCodigoDescuento() {
        codigoDescuento.setTitulo(txtTitulo.getText());
        codigoDescuento.setDescripcion(txtDescripcion.getText());
        codigoDescuento.setFechaCreacion(String.valueOf(fechaCreacion.getValue()));
        codigoDescuento.setFechaFin(String.valueOf(fechaFin.getValue()));
        codigoDescuento.setCategoriaCmbBox((String) cmbCategoria.getValue());
        codigoDescuento.setCodigo(txtCodigo.getText());
        System.out.println(codigoDescuento.toString());
    }

    public void llenarComboCategorias() {
        ObservableList<String> listaCategorias = FXCollections.observableArrayList();
        listaCategorias.add("Tecnologia");
        listaCategorias.add("Moda de mujer");
        listaCategorias.add("Moda de hombre");
        listaCategorias.add("Hogar");
        listaCategorias.add("Mascotas");
        listaCategorias.add("Viaje");
        listaCategorias.add("Entretenimiento");
        listaCategorias.add("Comida y bebida");
        cmbCategoria.setItems(listaCategorias);
    }

    public void publicar() {
        instanciaCodigoDescuento();
        if (codigoDescuento.estaCompleta()) {
            if (codigoDescuento.validarFechas()){
                try {
                    if(codigoDescuento.publicar() == 201) {
                        MainController.alert(Alert.AlertType.INFORMATION,
                                "Registro Exitoso",
                                "Publicación registrada exitosamente");
                    }else {
                        MainController.alert(Alert.AlertType.ERROR,
                                "Error del servidor",
                                "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            MainController.alert(Alert.AlertType.WARNING,
                    "Información Incorrecta",
                    "Llene todos los campos correctamente");
        }
    }

    public void clicAtras(){
        MainController.activate("InicioCodigos","Inicio",MainController.Sizes.MID);
    }
}