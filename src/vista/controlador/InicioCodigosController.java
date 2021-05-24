package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import modelo.CodigoDescuento;
import modelo.Oferta;
import vista.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioCodigosController {
    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private MenuButton menuPublicar;

    @FXML
    private MenuButton menuPerfil;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<CodigoDescuento> codigosDescuento = new ArrayList<>();


    public List<CodigoDescuento> getData() {
        List<CodigoDescuento> codigos = new ArrayList<>();
        CodigoDescuento codigoDescuento;
        for (int i = 0; i < 10; i++) {
            codigoDescuento = new CodigoDescuento();
            codigoDescuento.setTitulo("Codigo " + i);
            codigos.add(codigoDescuento);
        }
        return codigos;
    }

    public List<CodigoDescuento> cargarCodigos() {
        List<CodigoDescuento> codigoDescuentos = new ArrayList<>();
        CodigoDescuento codigoDescuento = new CodigoDescuento();
        CodigoDescuento[] codigosArray = new CodigoDescuento[0];
        try {
            codigosArray = codigoDescuento.obtenerCodigos(1,-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < codigosArray.length; i++) {
            codigoDescuentos.add(codigosArray[i]);
            codigosArray[i].toString();
        }
        return codigoDescuentos;
    }

    public void initialize() {
        this.llenarComboCategorias();
        codigosDescuento.addAll(this.cargarCodigos());
        int columna = 0;
        int fila = 0;
        try {
            for (int i = 0; i < codigosDescuento.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/vista/itemCodigo.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemCodigosController itemCodigosController = fxmlLoader.getController();
                itemCodigosController.setData(codigosDescuento.get(i));

                if (columna == 2) {
                    columna = 0;
                    fila++;
                }

                grid.add(anchorPane, columna++, fila);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);


                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void clicPublicarOferta(){
        MainController.activate("PublicarOferta","Regístrate",MainController.Sizes.MID);
    }


}
