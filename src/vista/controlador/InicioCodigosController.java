package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import modelo.Categoria;
import modelo.CodigoDescuento;
import modelo.MiembroOfercompas;
import vista.InicioCodigosListener;
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
    private Label lblPagina;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private int pagina = 1;

    private int categoria = -1;

    private List<CodigoDescuento> codigosDescuento = new ArrayList<>();

    private InicioCodigosListener listenerCodigos;

    public void initialize() {
        this.llenarComboCategorias();
        listenerCodigos = new InicioCodigosListener() {
            @Override
            public void onClickListener(CodigoDescuento codigoDescuento) {
                verCodigo(codigoDescuento);
            }
        };
        llenarPagina();
        guardaMiembro();

    }

    public void guardaMiembro(){
        MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
        miembroOfercompas.setIdMiembro(16);
        MainController.save("miembro",miembroOfercompas);
    }

    public void verCodigo(CodigoDescuento codigoDescuento) {
        System.out.println(codigoDescuento.toString());
        MainController.save("codigoDescuento", codigoDescuento);
        MainController.activate("VerCodigoDescuento", "Ver Oferta", MainController.Sizes.MID);
    }


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

    public List<CodigoDescuento> cargarCodigos(int pagina, int categoria) {
        List<CodigoDescuento> codigoDescuentos = new ArrayList<>();
        CodigoDescuento codigoDescuento = new CodigoDescuento();
        CodigoDescuento[] codigosArray = new CodigoDescuento[0];
        try {
            codigosArray = codigoDescuento.obtenerCodigos(pagina,categoria);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < codigosArray.length; i++) {
            codigoDescuentos.add(codigosArray[i]);
            codigosArray[i].toString();
        }
        return codigoDescuentos;
    }



    public void llenarPagina(){
        codigosDescuento.addAll(this.cargarCodigos(pagina, categoria));
        int columna = 0;
        int fila = 0;
        try {
            for (int i = 0; i < codigosDescuento.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/vista/itemCodigo.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemCodigosController itemCodigosController = fxmlLoader.getController();
                itemCodigosController.setData(codigosDescuento.get(i),listenerCodigos);

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
        MainController.activate("PublicarOferta","Publicar Oferta",MainController.Sizes.MID);
    }

    public void clicPublicarCodigo(){
        MainController.activate("PublicarCodigo","Publicar Codigo",MainController.Sizes.MID);
    }

    public void avanzarPagina() {
        this.pagina++;
        this.codigosDescuento.clear();
        grid.getChildren().clear();
        llenarPagina();
        this.lblPagina.setText(String.valueOf(this.pagina));
        System.out.println(pagina);
        System.out.println(codigosDescuento);
    }

    public void retrcoderPagina() {
        this.pagina--;
        this.codigosDescuento.clear();
        grid.getChildren().clear();
        llenarPagina();

    }

    public void cambiarAOfertas() {
        MainController.activate("InicioOfertas", "Inicio", MainController.Sizes.MID);
    }

    public void buscarPorCategoria() {
        String categoriaBuscar = cmbCategoria.getValue();
        System.out.println("Categoria:" + categoriaBuscar);
        if (categoriaBuscar != null) {
            cambiarCategoria(cmbCategoria.getValue());
            this.codigosDescuento.clear();
            grid.getChildren().clear();
            llenarPagina();
            this.lblPagina.setText(String.valueOf(1));
            System.out.println(pagina);
            System.out.println(codigosDescuento);
        }
    }

    public void cambiarCategoria(String categoria) {
        System.out.println(categoria);
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
            case "Comida y bebida":
                this.categoria = Categoria.COMIDABEBIDA.getIndice();
                break;
            default:
                this.categoria = Categoria.TECNOLOGIA.getIndice();
                break;
        }
    }


}
