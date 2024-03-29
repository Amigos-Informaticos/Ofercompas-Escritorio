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

    private MyListener myListener;

    private List<Oferta> ofertas = new ArrayList();

    private MiembroOfercompas miembroOfercompasLogeado;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        miembroOfercompasLogeado = (MiembroOfercompas) MainController.get("miembroLogeado");
        myListener = new MyListener() {
            @Override
            public void onClickListener(Oferta oferta) {
                verOferta(oferta);
            }
        };
        cargarOfertas(pagina);
        llenarPagina();

    }



    @FXML
    private void avanzarPagina() {
        if(cargarOfertas(pagina+1)){
            this.pagina++;
            this.ofertas.clear();
            vbox.getChildren().clear();
            llenarPagina();
            this.lblPagina.setText(String.valueOf(this.pagina));
            System.out.println(pagina);
            System.out.println(ofertas);
        }

    }

    @FXML
    private void clicCerrarSesion() {
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
        System.out.println("Mis códigos");
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
        System.out.println("Retroceder página");
    }



    public void llenarPagina() {
        System.out.println("Ofersas to string: " +  ofertas.toString());
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
    public boolean cargarOfertas(int pagina) {
        boolean ofertasCargadas = false;
       this.ofertas = new ArrayList<>();
        Oferta oferta = new Oferta();
        Oferta[] ofertasArray = new Oferta[0];
        try {
            System.out.println("El id del miembro es:" + miembroOfercompasLogeado.getIdMiembro());
            ofertasArray = oferta.obtenerOfertasPorPublicador(pagina, miembroOfercompasLogeado.getIdMiembro());
            System.out.println("Las ofertas del publicador son:" + ofertasArray.toString());
        } catch (Exception e) {
            System.out.println("Get mesahe "+ e.getMessage());
            e.printStackTrace();
        }
        for (int i = 0; i < ofertasArray.length; i++) {
            ofertas.add(ofertasArray[i]);
            ofertasArray[i].toString();
        }

        if(ofertasArray.length > 0){
            ofertasCargadas = true;
        }

        return ofertasCargadas;
    }
    public void verOferta(Oferta oferta) {
        System.out.println(oferta.toString());
        MainController.save("oferta", oferta);
        MainController.activate("VerOferta", "Ver Oferta", MainController.Sizes.MID);
        MainController.save("pantallaAnterior", "InicioMisOfertas");
    }


}
