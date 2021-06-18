package vista.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerOfertaController {
    public TextField txtComentario;
    public Button btnDenunciar;
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

    @FXML
    private ImageView ivImagen;

    @FXML
    private ImageView btnPuntuarLike;

    @FXML
    private ImageView btnPuntuarDislike;

    private Oferta oferta;

    private MiembroOfercompas miembroOfercompas;

    private boolean esModeradorNoAutor;

    private List<Comentario> comentarios = new ArrayList<>();

    public void initialize() {
        miembroOfercompas = (MiembroOfercompas) MainController.get("miembroLogeado");
        oferta = (Oferta) MainController.get("oferta");
        mostrarInformacionOferta();
        this.mostrarComentarios();
        soyAutor();
        obtenerInteraccion();
    }

    public void obtenerInteraccion(){
        try {
            HashMap respuesta = oferta.obtenerInteraccion(miembroOfercompas.getIdMiembro());
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
        this.lblTitulo.setText(oferta.getTitulo());
        this.lblDescripcion.setText(oferta.getDescripcion());
        this.lblFechaInicio.setText(oferta.getFechaCreacion());
        this.lblFechaFin.setText(oferta.getFechaFin());
        this.lblPrecio.setText("$" + oferta.getPrecio());
        this.lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion()));

        Image imagen = new Image(oferta.getFoto().getUrl());
        ivImagen.setImage(imagen);


        System.out.println(oferta.getIdPublicacion());

    }

    public void verVideo(){
        System.out.println(oferta.getVinculo());
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI.create(oferta.getVideo().getUrl()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void irAOferta() {
        System.out.println(oferta.getVinculo());
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI.create(oferta.getVinculo()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
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
        System.out.println(miembroOfercompas.getIdMiembro());
        try {
            if(oferta.puntuar(miembroOfercompas.getIdMiembro(), 1) == 201){
                lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion() + 1));
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
            if(oferta.puntuar(miembroOfercompas.getIdMiembro(), 0) == 201){
                lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion() - 1));
            }else{
                MainController.alert(Alert.AlertType.INFORMATION,
                        "Ya puntuaste esta oferta",
                        "Ya has puntuado esta oferta anteriormente");
            }
        } catch (IOException ioException) {
            MainController.alert(Alert.AlertType.ERROR, "Si conexión con el servidor", "Intente más tarde");
        }
    }

    public void clicAtras() {
        String pantallaAnterior = (String) MainController.get("pantallaAnterior");
        if(pantallaAnterior.equals("InicioMisOfertas")){
            MainController.activate("InicioMisOfertas", "Mis Oferta", MainController.Sizes.MID);
        }else {
            MainController.activate("InicioOfertas", "Ofertas", MainController.Sizes.MID);
        }


    }

    public void soyAutor() {
        if (miembroOfercompas.getIdMiembro() == oferta.getIdPublicador()) {
            btnEliminar.setVisible(true);
            btnActualizar.setVisible(true);
        }else if(miembroOfercompas.getTipoMiembro() == 2){
            btnEliminar.setVisible(true);
            btnActualizar.setVisible(false);
            this.esModeradorNoAutor = true;
        }else {
            btnEliminar.setVisible(false);
            btnActualizar.setVisible(false);
        }
    }

    public void eliminar(){
        if (MainController.alert(Alert.AlertType.CONFIRMATION, "¿Está seguro que desea eliminar esta Oferta?", "")){
            try {

                if(!this.esModeradorNoAutor){
                    if (oferta.eliminar() == 200){
                        MainController.alert(Alert.AlertType.INFORMATION,
                                "Eliminación Exitosa",
                                "Publicación eliminada exitosamente");
                        MainController.activate("InicioOfertas", "Actualizar Oferta", MainController.Sizes.MID);
                    }else{
                        MainController.alert(Alert.AlertType.INFORMATION,
                                "Eliminación sin éxito",
                                "Intenta más tarde");
                        MainController.activate("InicioOfertas", "Actualizar Oferta", MainController.Sizes.MID);
                    }
                }else{
                    if(oferta.prohibir() == 200){
                        MainController.alert(Alert.AlertType.INFORMATION,
                                "Prohibicion Exitosa",
                                "Publicación prohibida exitosamente");
                        MainController.activate("InicioOfertas", "Actualizar Oferta", MainController.Sizes.MID);
                    }else{
                        MainController.alert(Alert.AlertType.INFORMATION,
                                "Eliminación sin éxito",
                                "Intenta más tarde");
                        MainController.activate("InicioOfertas", "Actualizar Oferta", MainController.Sizes.MID);
                    }
                }


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public void actualizar(){
        MainController.activate("ActualizarOferta", "Actualizar Oferta", MainController.Sizes.MID);
    }


    public void denunciarClic(ActionEvent actionEvent) {
        MainController.save("ofertaDenunciar", this.oferta);
        MainController.save("pantallaAnterior", "ofertas");
        MainController.activate("DenunciarOferta", "Denunciar Oferta", MainController.Sizes.SMALL);

    }
    public void actualizarComentarios(){
        this.comentarios.clear();
        this.mostrarComentarios();
    }

    public void comentarClic(ActionEvent actionEvent) {
        if(this.comentarioValido()){
            Comentario comentario = new Comentario();
            comentario.setContenido(this.txtComentario.getText());
            try {
                int status = comentario.comentarPublicacion(oferta.getIdPublicacion(), miembroOfercompas.getIdMiembro());
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
