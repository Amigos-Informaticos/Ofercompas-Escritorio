package vista.controlador;

import com.google.gson.JsonObject;
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
import javafx.scene.media.MediaView;
import modelo.Comentario;
import modelo.MiembroOfercompas;
import modelo.Oferta;
import vista.MainController;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VerOfertaController {
    public TextField txtComentario;

    public ImageView btnPuntuarLike;

    public ImageView btnPuntuarDislike;

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
    private MediaView media;

    private Oferta oferta;

    private MiembroOfercompas miembroOfercompas;

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

            int status = (int) respuesta.get("status");
            if(status == 200){
                System.out.println("La respuesta con json es: " + respuesta.toString());
                HashMap jsonRespuesta = (HashMap) respuesta.get("json");

                System.out.println("La respuesta es: ");
                System.out.println(jsonRespuesta.toString());
                boolean denunciada = (boolean) jsonRespuesta .get("denunciada");
                boolean puntuada = (boolean) jsonRespuesta .get("puntuada");
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
        javafx.application.Application.launch(VerVideo.class);
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
            if(oferta.puntuar(miembroOfercompas.getIdMiembro(), 1) == 200){
                lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion() + 1));
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
            if(oferta.puntuar(miembroOfercompas.getIdMiembro(), 0) == 200){
                lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion() - 1));
            }else{
                MainController.alert(Alert.AlertType.INFORMATION,
                        "Ya puntuaste esta oferta",
                        "Ya has puntuado esta oferta anteriormente");
            }
        } catch (IOException ioException) {
            System.out.println(ioException);
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


    public void denunciarClic(ActionEvent actionEvent) {
        MainController.save("ofertaDenunciar", this.oferta);
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
