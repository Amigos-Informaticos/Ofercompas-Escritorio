package vista.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.CodigoDescuento;
import modelo.Comentario;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerCodigoDescuentoController {
    public TextField txtComentario;
    public ImageView btnPuntuarDislike;
    public ImageView btnPuntuarLike;
    public ImageView btnEliminar;
    public ImageView btnActualizar;
    public Button btnDenunciar;
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
        obtenerInteraccion();
    }
    public void obtenerInteraccion(){
        try {
            HashMap respuesta = codigoDescuento.obtenerInteraccion(miembroOfercompas.getIdMiembro());
            System.out.println(respuesta.toString());

            int status = (int) respuesta.get("status");
            if(status == 200){
                System.out.println("La respuesta con json es: " + respuesta.toString());
                HashMap jsonRespuesta = (HashMap) respuesta.get("json");

                System.out.println("La respuesta es: ");
                System.out.println(jsonRespuesta.toString());
                boolean denunciada = (boolean) jsonRespuesta.get("denunciada");
                boolean puntuada = (boolean) jsonRespuesta.get("puntuada");
                System.out.println("LOS VALORES RECUPERADOS SON");

                System.out.println(denunciada);
                System.out.println(puntuada);
                this.btnDenunciar.setDisable(denunciada);
                this.btnPuntuarDislike.setDisable(puntuada);
                this.btnPuntuarLike.setDisable(puntuada);
            }

        } catch (IOException e) {
            System.out.println("EXCEPCIOOOOON!!!");
            e.printStackTrace();
        }
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
            MainController.alert(Alert.AlertType.ERROR, "Si conexión con el servidor", "Intente más tarde");
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
            MainController.alert(Alert.AlertType.ERROR, "Si conexión con el servidor", "Intente más tarde");
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
    public void addComenterio(Comentario comentario){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/vista/Comentario.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComentarioController comentarioController = fxmlLoader.getController();
        comentarioController.setData(comentario);
        vbox.getChildren().add(anchorPane);
        VBox.setMargin(anchorPane, new Insets(10));
    }


    public void clicDenunciar(ActionEvent actionEvent) {
        MainController.save("pantallaAnterior", "codigos");
        MainController.save("codigoDescuento", this.codigoDescuento);
        MainController.activate("DenunciarOferta", "Denunciar Codigo", MainController.Sizes.MID);
    }

    public void clicComentar(ActionEvent actionEvent) {
        if(this.comentarioValido()){
            Comentario comentario = new Comentario();
            comentario.setContenido(this.txtComentario.getText());
            try {
                System.out.println(codigoDescuento.toString());
                int status = comentario.comentarPublicacion(codigoDescuento.getIdPublicacion(), miembroOfercompas.getIdMiembro());
                if(status == 201){
                    MainController.alert(Alert.AlertType.INFORMATION, "Comentario exitoso", "El " +
                            "comentario ha sido guardado con éxito");
                    comentario.setNicknameComentador(miembroOfercompas.getNickname());
                    this.addComenterio(comentario);
                    this.txtComentario.setText("");
                }else {
                    MainController.alert(Alert.AlertType.ERROR, "Error al guardar el comentario", "No se " +
                            "pudo registrar su comentario, inténtelo más tarde");
                }

            } catch (IOException e) {
                MainController.alert(Alert.AlertType.ERROR, "Error de conexión", "No se pudo conectar " +
                        "el servidor");
            }
        }
    }
    private  boolean comentarioValido(){
        boolean valido = false;
        if(this.txtComentario.getText() != null ){
            int longitudComentario =this.txtComentario.getText().length();
            if(longitudComentario>4 && longitudComentario<=50){
                valido = true;
            }else{
                MainController.alert(Alert.AlertType.ERROR, "Comentario inválido", "El comentario debe " +
                        "tener entre 5 y 50 caracteres");
            }

        }else {
            MainController.alert(Alert.AlertType.ERROR, "Campo vacío", "Ingresa un comentario por favor");
        }

        return  valido;
    }
}
