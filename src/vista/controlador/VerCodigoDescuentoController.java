package vista.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.CodigoDescuento;
import modelo.Comentario;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VerCodigoDescuentoController {
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblDescripcion;

    @FXML
    private Label lblPuntuacion;

    @FXML
    private Label lblFechaInicio;

    @FXML
    private Label lblFechaFin;

    @FXML
    private VBox vbox;

    private MiembroOfercompas miembroOfercompas;

    private CodigoDescuento codigoDescuento;

    private List<Comentario> comentarios = new ArrayList<>();

    public void initialize() {
        miembroOfercompas = (MiembroOfercompas) MainController.get("miembroLogeado");
        mostrarInformacionOferta();
        this.mostrarComentarios();
    }

    public void mostrarInformacionOferta() {
        codigoDescuento = (CodigoDescuento) MainController.get("codigoDescuento");
        this.lblTitulo.setText(codigoDescuento.getTitulo());
        this.lblDescripcion.setText(codigoDescuento.getDescripcion());
        this.lblFechaInicio.setText(codigoDescuento.getFechaCreacion());
        this.lblFechaFin.setText(codigoDescuento.getFechaFin());

        this.lblPuntuacion.setText(String.valueOf(codigoDescuento.getPuntuacion()));
        System.out.println(codigoDescuento.getIdPublicacion());
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
            comentariosArray = comentario.obtenerComentarios(codigoDescuento.getIdPublicacion());
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
        System.out.println(miembroOfercompas.getIdMiembro());
        try {
            if(codigoDescuento.puntuar(miembroOfercompas.getIdMiembro(), 1) == 201){
                lblPuntuacion.setText(String.valueOf(codigoDescuento.getPuntuacion() + 1));
            }else{
                MainController.alert(Alert.AlertType.INFORMATION,
                        "Ya puntuaste esta oferta",
                        "Ya has puntuado esta oferta anteriormente");
            }
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }

    public void puntuarNegativamente() {
        System.out.println(miembroOfercompas.getIdMiembro());
        try {
            if(codigoDescuento.puntuar(miembroOfercompas.getIdMiembro(), 0) == 201){
                lblPuntuacion.setText(String.valueOf(codigoDescuento.getPuntuacion() - 1));
            }else{
                MainController.alert(Alert.AlertType.INFORMATION,
                        "Ya puntuaste esta oferta",
                        "Ya has puntuado esta oferta anteriormente");
            }
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }

    public void clicAtras(){
        MainController.activate("InicioCodigos", "Ver Oferta", MainController.Sizes.MID);
    }

    public void eliminar(){
        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea eliminar este código?", "")){
            try {
                if (codigoDescuento.eliminar() == 200){
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Eliminación Exitosa",
                            "Publicación eliminada exitosamente");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void actualizar(){
        MainController.activate("ActualizarCodigoDescuento", "Actualizar Codigo", MainController.Sizes.MID);
    }


}
