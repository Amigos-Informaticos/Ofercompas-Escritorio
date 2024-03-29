package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.Categoria;
import modelo.MiembroOfercompas;
import modelo.Oferta;
import vista.MainController;
import vista.MyListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InicioOfertasController {
    public Button btnReporteUsuarios;
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

    public static HashMap<Integer, List<Oferta>> ofertasRecuperadas = new HashMap<>();


    public void initialize() {
        this.llenarComboCategorias();
        myListener = new MyListener() {
            @Override
            public void onClickListener(Oferta oferta) {
                verOferta(oferta);
            }
        };
        llenarPagina();
        validarTipoUsuario();

    }


    public void validarTipoUsuario(){
        MiembroOfercompas miembroOfercompas = (MiembroOfercompas) MainController.get("miembroLogeado");
        if(miembroOfercompas.getTipoMiembro() == 1){
            this.btnReporteUsuarios.setVisible(false);
        }
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
            System.out.println("E GET MESSAGE" +  e.getMessage());
        }
        for (int i = 0; i < ofertasArray.length; i++) {
            ofertas.add(ofertasArray[i]);
            ofertasArray[i].toString();
        }
        if(!ofertasRecuperadas.containsKey(pagina)){

            ofertasRecuperadas.put(categoria, ofertas);
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
        MainController.activate("PublicarOferta", "Regístrar oferta", MainController.Sizes.MID);
    }

    public void clicPublicarCodigo() {
        MainController.activate("PublicarCodigo", "Regístrate", MainController.Sizes.MID);
    }

    public void verOferta(Oferta oferta) {
        System.out.println(oferta.toString());
        MainController.save("oferta", oferta);
        MainController.activate("VerOferta", "Ver Oferta", MainController.Sizes.MID);
        MainController.save("pantallaAnterior", "InicioOfertas");
    }

    public void avanzarPagina() {
        this.pagina++;
        this.ofertas.clear();
        vbox.getChildren().clear();
        llenarPagina();
        this.lblPagina.setText(String.valueOf(this.pagina));
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
        MainController.activate("InicioMisOfertas", "Mis ofertas", MainController.Sizes.MID);
    }

    public void clicMisCodigos(ActionEvent actionEvent) {
        System.out.println("Mis codigos");
    }

    public void clicEditarPerfil(ActionEvent actionEvent) {
        MainController.activate("Perfil", "Perfil", MainController.Sizes.SMALL);
    }

    public void clicCerrarSesion(ActionEvent actionEvent) {
        MainController.activate("Login", "Login", MainController.Sizes.SMALL);
    }

    public void clicVerReporteUsuarios(ActionEvent actionEvent) {
        MainController.activate("MiembrosDenunciados", "Miembros denunciados", MainController.Sizes.MID);
    }
}