package vista.controlador;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.MiembroDetalleDenuncias;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MiembrosDenunciadosController implements Initializable {
    public TableView tablaMiembros;
    public TableColumn columnaNickname;
    public TableColumn columnaNoDenuncias;
    private ObservableList<MiembroDetalleDenuncias> miembrosTabla;


    public void clicVerReporte(ActionEvent actionEvent) {
    }

    public void clicAtras(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarTabla();

    }


    private void inicializarTabla(){
        try {
            this.miembrosTabla = MiembroOfercompas.obtenerMiembrosConDenuncias();
            if(miembrosTabla.size() >0){
                tablaMiembros.setItems(miembrosTabla);
                this.columnaNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
                this.columnaNoDenuncias.setCellValueFactory(new PropertyValueFactory<>("numeroDePublicacionesDenunciadas"));
            }else{
                MainController.alert(Alert.AlertType.ERROR, "Error Conexión", "Error al conectar con el servidor");
            }

        } catch (IOException e) {
            MainController.alert(Alert.AlertType.ERROR, "Error Conexión", "Error al conectar con el servidor");
        }
    }



}
