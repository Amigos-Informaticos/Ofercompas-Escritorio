package vista.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Multimedia;
import modelo.Oferta;
import utils.LimitadorTextField;
import utils.VerificarArchivo;
import vista.MainController;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ActualizarOfertaController {
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtVinculo;
    @FXML
    private DatePicker fechaCreacion;
    @FXML
    private DatePicker fechaFin;
    @FXML
    private ComboBox cmbCategoria;
    @FXML
    private Label lblNombreFoto;

    @FXML
    private Label lblNombreVideo;

    private Oferta oferta;

    public void initialize() {
        this.oferta = (Oferta) MainController.get("oferta");
        llenarComboCategorias();
        mostrarInformacionOferta();
        limitarTextfields();
    }

    public void limitarTextfields() {
        LimitadorTextField.limitarTamanio(txtTitulo,30);
        LimitadorTextField.limitarTamanioArea(txtDescripcion, 200);
        LimitadorTextField.limitarTamanio(txtPrecio, 6);
        LimitadorTextField.limitarTamanio(txtVinculo, 2048);

        LimitadorTextField.soloNumeros(txtPrecio);
    }

    public void mostrarInformacionOferta() {
        this.txtTitulo.setText(oferta.getTitulo());
        this.txtDescripcion.setText(oferta.getDescripcion());
        this.fechaCreacion.setValue(LocalDate.parse(oferta.getFechaCreacion()));
        this.fechaFin.setValue(LocalDate.parse(oferta.getFechaFin()));
        this.txtPrecio.setText(oferta.getPrecio());
        this.txtVinculo.setText(oferta.getVinculo());
        this.cmbCategoria.setValue(oferta.deCategoriaACmbBox());
        System.out.println(oferta.getIdPublicacion());
    }

    public void instanciaOferta() {
        oferta.setTitulo(txtTitulo.getText());
        oferta.setDescripcion(txtDescripcion.getText());
        oferta.setPrecio(txtPrecio.getText());
        oferta.setFechaCreacion(String.valueOf(fechaCreacion.getValue()));
        oferta.setFechaFin(String.valueOf(fechaFin.getValue()));
        oferta.setVinculo(txtVinculo.getText());
        oferta.setCategoriaCmbBox((String) cmbCategoria.getValue());
        System.out.println(oferta.toString());
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

    public void actualizar() {
        instanciaOferta();
        if (oferta.estaCompleta()) {
            try {
                if(oferta.actualizar() == 200) {
                    if (!lblNombreFoto.getText().equals("Actualiza imagen")){
                        oferta.getFoto().actualizar(oferta.getIdPublicacion());
                    }
                    if (!lblNombreVideo.getText().equals("Actualiza video")){
                        oferta.getVideo().actualizar(oferta.getIdPublicacion());
                    }
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Actualización Exitosa",
                            "Publicación registrada exitosamente");
                }else {
                    MainController.alert(Alert.AlertType.ERROR,
                            "Error del servidor",
                            "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            MainController.alert(Alert.AlertType.WARNING,
                    "Información Incorrecta",
                    "Llene todos los campos correctamente");
        }
    }

    public void clicAtras(){
        MainController.activate("InicioOfertas","Inicio",MainController.Sizes.MID);
    }

    public void buscarFoto() {
        File archivo = new File(String.valueOf(MainController.fileExplorer()));
        Multimedia foto = new Multimedia();
        foto.setArchivo(archivo.getPath());
        if (foto.getArchivo().getName() != null) {
            VerificarArchivo verificador = new VerificarArchivo();
            if (verificador.fotoValida(foto.getArchivo().getPath())) {
                oferta.setFoto(foto);
                lblNombreFoto.setText(archivo.getName());
            } else {
                MainController.alert(
                        Alert.AlertType.WARNING,
                        "Formato incorrecto",
                        "Sube una foto en formato PNG o JPG"
                );
            }
        }
    }

    public void buscarVideo() {
        File archivo = new File(String.valueOf(MainController.fileExplorer()));
        Multimedia video = new Multimedia();
        video.setArchivo(archivo.getPath());
        if (video.getArchivo().getName() != null) {
            VerificarArchivo verificador = new VerificarArchivo();
            if (verificador.videoValido(video.getArchivo().getPath())) {
                oferta.setVideo(video);
                lblNombreVideo.setText(archivo.getName());
            } else {
                MainController.alert(
                        Alert.AlertType.WARNING,
                        "Formato incorrecto",
                        "Sube una foto en formato PNG o JPG"
                );
            }
        }
    }
}