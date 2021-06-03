package vista.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.Comentario;
import modelo.MiembroOfercompas;
import modelo.Oferta;
import vista.MainController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class VerOfertaController {
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblDescripcion;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblPuntuacion;

    @FXML
    private Label lblFechaInicio;

    @FXML
    private Label lblFechaFin;

    @FXML
    private VBox vbox;

    @FXML
    private ImageView btnEliminar;

    @FXML
    private ImageView btnActualizar;

    private Oferta oferta;

    private MiembroOfercompas miembroOfercompas;

    private List<Comentario> comentarios = new ArrayList<>();

    public void initialize() {
        miembroOfercompas = (MiembroOfercompas) MainController.get("miembro");
        oferta = (Oferta) MainController.get("oferta");
        mostrarInformacionOferta();
        this.mostrarComentarios();

        soyAutor();
    }

    public void mostrarInformacionOferta() {
        this.lblTitulo.setText(oferta.getTitulo());
        this.lblDescripcion.setText(oferta.getDescripcion());
        this.lblFechaInicio.setText(oferta.getFechaCreacion());
        this.lblFechaFin.setText(oferta.getFechaFin());
        this.lblPrecio.setText("$" + oferta.getPrecio());
        this.lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion()));
        System.out.println(oferta.getIdPublicacion());
    }

    public void irAOferta() {
        System.out.println(oferta.getVinculo());
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://www.amazon.com.mx/dp/B07XJ8C8F5?pf_rd_r=PB8ABAXRVKNSEYPPE26D&pf_rd_p=b2217d1b-e925-4541-b5ed-feeb1ff7bf13&pd_rd_r=8d4003d7-60cc-44b3-8ed4-15416742c139&pd_rd_w=TOXPN&pd_rd_wg=LUX7z&ref_=pd_gw_unk"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrarComentarios() {
        comentarios.addAll(this.cargarComentarios());
        try {
            for (int i = 0; i < comentarios.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/vista/Comentario.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ComentarioController comentarioController = fxmlLoader.getController();
                comentarioController.setData(comentarios.get(i));
                vbox.getChildren().add(anchorPane);
                VBox.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Comentario> cargarComentarios() {
        List<Comentario> comentarios = new ArrayList<>();
        Comentario comentario = new Comentario();
        Comentario[] comentariosArray = new Comentario[0];
        try {
            comentariosArray = comentario.obtenerComentarios(oferta.getIdPublicacion());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < comentariosArray.length; i++) {
            comentarios.add(comentariosArray[i]);
            comentariosArray[i].toString();
        }
        return comentarios;
    }

    public void puntuarPositivamente() {
        MiembroOfercompas miembroOfercompas = (MiembroOfercompas) MainController.get("miembro");
        System.out.println(miembroOfercompas.getIdMiembro());
        try {
            oferta.puntuar(miembroOfercompas.getIdMiembro(), 1);
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
        lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion() + 1));
    }

    public void puntuarNegativamente() {
        MiembroOfercompas miembroOfercompas = (MiembroOfercompas) MainController.get("miembro");
        try {
            oferta.puntuar(miembroOfercompas.getIdMiembro(), 0);
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
        lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion() - 1));
    }

    public void clicAtras() {
        MainController.activate("InicioOfertas", "Ver Oferta", MainController.Sizes.MID);
    }

    public void soyAutor() {
        System.out.println("Miembro: " + miembroOfercompas.getIdMiembro() + " Oferta: " + oferta.getIdPublicador());
        if (miembroOfercompas.getIdMiembro() == oferta.getIdPublicador()) {
            btnEliminar.setVisible(true);
            btnActualizar.setVisible(true);
        } else {
            btnEliminar.setVisible(false);
            btnActualizar.setVisible(false);
        }
    }

    public void eliminar(){
        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea eliminar esta Oferta?", "")){
            try {
                oferta.eliminar();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void actualizar(){
        MainController.activate("ActualizarOferta", "Actualizar Oferta", MainController.Sizes.MID);
    }


}
