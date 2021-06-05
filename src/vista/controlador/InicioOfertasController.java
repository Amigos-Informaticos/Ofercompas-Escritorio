package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.Categoria;
import modelo.MiembroOfercompas;
import modelo.Oferta;
import vista.MainController;
import vista.MyListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioOfertasController {
    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private MenuButton menuPublicar;

    @FXML
    private MenuButton menuPerfil;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;

    @FXML
    private Label lblPagina;

    private int pagina = 1;

    private int categoria = -1;

    private MyListener myListener;

    private  List<Oferta> ofertas = new ArrayList();



    public void initialize() {
        this.llenarComboCategorias();
        myListener = new MyListener() {
            @Override
            public void onClickListener(Oferta oferta) {
                verOferta(oferta);
            }
        };
        llenarPagina();
        guardaMiembro();
        System.out.println(ofertas.get(1).getIdPublicador());
    }

    public void guardaMiembro() {
        MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
        miembroOfercompas.setIdMiembro(7);
        MainController.save("miembro", miembroOfercompas);
    }

    public void llenarPagina() {
        ofertas.addAll(this.cargarOfertas(pagina, categoria));

        try {
            for (int i = 0; i < ofertas.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/vista/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemOfertasController itemOfertasController = fxmlLoader.getController();
                itemOfertasController.setData(ofertas.get(i), myListener);
                vbox.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Oferta> cargarOfertas(int pagina, int categoria) {
        List<Oferta> ofertas = new ArrayList<>();
        Oferta oferta = new Oferta();
        Oferta[] ofertasArray = new modelo.Oferta[0];
        try {
            ofertasArray = oferta.obtenerOfertas(pagina, categoria);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < ofertasArray.length; i++) {
            ofertas.add(ofertasArray[i]);
            ofertasArray[i].toString();
        }
        return ofertas;
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

    public void clicPublicarOferta() {
        MainController.activate("PublicarOferta", "Regístrate", MainController.Sizes.MID);
    }

    public void clicPublicarCodigo() {
        MainController.activate("PublicarCodigo", "Regístrate", MainController.Sizes.MID);
    }

    public void verOferta(Oferta oferta) {
        System.out.println(oferta.toString());
        MainController.save("oferta", oferta);
        MainController.activate("VerOferta", "Ver Oferta", MainController.Sizes.MID);
    }

    public void avanzarPagina() {
        this.pagina++;
        this.ofertas.clear();
        vbox.getChildren().clear();
        llenarPagina();
        this.lblPagina.setText(String.valueOf(this.pagina));
        System.out.println(pagina);
        System.out.println(ofertas);
    }

    public void retrcoderPagina() {
        this.pagina--;
        this.ofertas.clear();
        vbox.getChildren().clear();
        llenarPagina();
    }

    public void cambiarACodigos() {
        MainController.activate("InicioCodigos", "Códigos", MainController.Sizes.MID);
    }

    public void buscarPorCategoria() {
        String categoriaBuscar = cmbCategoria.getValue();
        System.out.println("Categoria:" + categoriaBuscar);
        if (categoriaBuscar != null) {
            cambiarCategoria(cmbCategoria.getValue());
            this.ofertas.clear();
            vbox.getChildren().clear();
            llenarPagina();
            this.lblPagina.setText(String.valueOf(1));
            System.out.println(pagina);
            System.out.println(ofertas);
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

    public void clicMisOfertas(ActionEvent actionEvent) {

    }

    public void clicMisCodigos(ActionEvent actionEvent) {
    }

    public void clicEditarPerfil(ActionEvent actionEvent) {
        MainController.activate("Perfil", "Perfil", MainController.Sizes.SMALL);
    }

    public void clicCerrarSesion(ActionEvent actionEvent) {
    }
}