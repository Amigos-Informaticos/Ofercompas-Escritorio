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
import javafx.scene.layout.VBox;
import modelo.Oferta;
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

    private MyListener myListener;

    private List<Oferta> ofertas = new ArrayList<modelo.Oferta>();

    public List<Oferta> cargarOfertas(int pagina) {
        List<Oferta> ofertas = new ArrayList<>();
        Oferta oferta = new Oferta();
        Oferta[] ofertasArray = new modelo.Oferta[0];
        try {
            ofertasArray = oferta.obtenerOfertas(pagina,-1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < ofertasArray.length; i++) {
            ofertas.add(ofertasArray[i]);
            ofertasArray[i].toString();
        }
        return ofertas;
    }

    public void initialize() {
        this.llenarComboCategorias();
        myListener = new MyListener() {
            @Override
            public void onClickListener(Oferta oferta) {
                verOferta(oferta);
            }
        };
        ofertas.addAll(this.cargarOfertas(1));
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

    public void verOferta(Oferta oferta){
        MainController.save("oferta",oferta);
        MainController.activate("VerOferta","Ver Oferta",MainController.Sizes.MID);
    }
}