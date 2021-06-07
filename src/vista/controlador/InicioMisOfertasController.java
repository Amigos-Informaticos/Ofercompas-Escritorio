package vista.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.MiembroOfercompas;
import modelo.Oferta;
import vista.MainController;
import vista.MyListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InicioMisOfertasController implements Initializable {

    @FXML
    private MenuButton menuPublicar;

    @FXML
    private MenuButton menuPerfil;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vbox;

    @FXML
    private Button btnAnterior;

    @FXML
    private Label lblPagina;

    @FXML
    private Button btnSiguiente;

    private int pagina = 1;

    private int categoria = -1;

    private MyListener myListener;

    private List<Oferta> ofertas = new ArrayList();

    private MiembroOfercompas miembroOfercompasLogeado;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        miembroOfercompasLogeado = (MiembroOfercompas) MainController.get("MiembroLogeado");
        myListener = new MyListener() {
            @Override
            public void onClickListener(Oferta oferta) {
                verOferta(oferta);
            }
        };
        llenarPagina();

    }



    @FXML
    void avanzarPagina(ActionEvent event) {

    }

    @FXML
    void clicCerrarSesion(ActionEvent event) {
        MainController.save("miembroLogeado", null);
        MainController.save("token", null);
        MainController.activate("Login", "Login");
    }

    @FXML
    void clicEditarPerfil(ActionEvent event) {
        MainController.activate("Perfil", "Perfil", MainController.Sizes.MID);
    }

    @FXML
    void clicIrCodigos(ActionEvent event) {
        MainController.activate("InicioCodigos", "Códigos", MainController.Sizes.MID);
    }

    @FXML
    void clicIrOfertas(ActionEvent event) {
        MainController.activate("InicioOfertas", "Ver Oferta", MainController.Sizes.MID);
    }

    @FXML
    void clicMisCodigos(ActionEvent event) {

    }

    @FXML
    void clicMisOfertas(ActionEvent event) {

    }

    @FXML
    void clicPublicarCodigo(ActionEvent event) {
        MainController.activate("PublicarCodigo", "Publicar Código", MainController.Sizes.MID);
    }

    @FXML
    void clicPublicarOferta(ActionEvent event) {
        MainController.activate("PublicarOferta", "Regístrar oferta", MainController.Sizes.MID);
    }

    @FXML
    void retrcoderPagina(ActionEvent event) {

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
            ofertasArray = oferta.obtenerOfertasPorPublicador(pagina, miembroOfercompasLogeado.getIdMiembro());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < ofertasArray.length; i++) {
            ofertas.add(ofertasArray[i]);
            ofertasArray[i].toString();
        }
        return ofertas;
    }
    public void verOferta(Oferta oferta) {
        System.out.println(oferta.toString());
        MainController.save("oferta", oferta);
        MainController.activate("VerOferta", "Ver Oferta", MainController.Sizes.MID);
    }


}
