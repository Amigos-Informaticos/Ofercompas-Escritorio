package vista.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import modelo.Oferta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioController {
    @FXML
    private ComboBox<?> cmbCategoria;

    @FXML
    private MenuButton menuPublicar;

    @FXML
    private MenuButton menuPerfil;

    @FXML
    private ScrollPane scroll;

    //@FXML
    //private GridPane grid;

    @FXML
    private VBox vbox;

    private List<Oferta> ofertas = new ArrayList<>();

    public List<Oferta> getData() {
        List<Oferta> ofertas = new ArrayList<>();
        Oferta oferta;
        for (int i = 0; i < 10; i++) {
            oferta = new Oferta();
            oferta.setTitulo("Oferta " + i);
            oferta.setPrecio("200");
            oferta.setFechaCreacion("2021-05-20");
            ofertas.add(oferta);
        }
        return ofertas;
    }

    public void initialize() {
        ofertas.addAll(this.getData());
        //int columna = 0;
        //int fila = 0;
        try {
            for (int i = 0; i < ofertas.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/vista/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                ItemController itemController = fxmlLoader.getController();
                itemController.setData(ofertas.get(i));


                /*
                grid.add(anchorPane, columna, fila); //Quizá aquíu deba iniciar con 1
                GridPane.setMargin(anchorPane,new Insets(10));
                fila ++;

                 */

                vbox.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(10));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
