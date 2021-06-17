package vista.controlador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import modelo.CodigoDescuento;
import modelo.MiembroOfercompas;
import modelo.Oferta;
import vista.MainController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DenunciarOfertaController implements Initializable {

    @FXML
    public Label labelTituloOferta;
    @FXML
    public TextArea txtCommentary;
    @FXML
    public ComboBox comboMotivo;

    private MiembroOfercompas miembroOfercompas;
    private Oferta oferta;
    private ObservableList<String> listaMotivos;
    private boolean vieneDePantallaOfertas;
    private CodigoDescuento codigoDescuento;

    private void cargarDatos(){
        String pantallaAnterior = (String)MainController.get("pantallaAnterior");
        this.vieneDePantallaOfertas = "ofertas".equals(pantallaAnterior) ;

        if(this.vieneDePantallaOfertas){
            this.miembroOfercompas = (MiembroOfercompas) MainController.get("miembroLogeado");
            this.oferta = (Oferta) MainController.get("ofertaDenunciar");
        }else{
            this.miembroOfercompas = (MiembroOfercompas) MainController.get("miembroLogeado");
            this.codigoDescuento = (CodigoDescuento) MainController.get("codigoDescuento");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        inicializarVista();
    }


    private void inicializarVista(){
        if(this.vieneDePantallaOfertas){
            this.labelTituloOferta.setText(oferta.getTitulo());
        }else{
            this.labelTituloOferta.setText(codigoDescuento.getTitulo());
        }


        this.listaMotivos = FXCollections.observableArrayList();
        listaMotivos.add("Drogas");
        listaMotivos.add("Armas");
        listaMotivos.add("Pornografía");
        listaMotivos.add("Animales");
        listaMotivos.add("Fraude");
        listaMotivos.add("Spam");
        listaMotivos.add("Alcohol/Tabaco");
        listaMotivos.add("Ofensivo");
        listaMotivos.add("Pirateria");
        listaMotivos.add("Link Caido");

        this.comboMotivo.setItems(listaMotivos);
    }

    public void clicDenunciar(ActionEvent actionEvent) {
        if(validarCampos()){
            String comentario = this.txtCommentary.getText();
            String motivo = (String) this.comboMotivo.getValue();
            int indexMotivo = this.listaMotivos.indexOf(motivo) + 1;

            if(vieneDePantallaOfertas){
                try {
                    int status = this.oferta.denunciar(miembroOfercompas.getIdMiembro(), comentario, indexMotivo);
                    if(status == 201){
                        MainController.alert(Alert.AlertType.INFORMATION, "Denuncia exitosa", "Denuncia " +
                                "enviada exitosamente");
                    }else {
                        MainController.alert(Alert.AlertType.INFORMATION, "Denuncia no registrada", "Es" +
                                " posible que la publicación ya no exista");
                    }
                } catch (IOException e) {
                    MainController.alert(Alert.AlertType.ERROR, "Error de conexión", "Erro al conectar con" +
                            " el servidor, por favor, intente denunciar más tarde");
                }

            }else{
                try {
                    System.out.println(codigoDescuento);
                    int status = this.codigoDescuento.denunciar(miembroOfercompas.getIdMiembro(), comentario, indexMotivo);
                    if(status == 201){
                        MainController.alert(Alert.AlertType.INFORMATION, "Denuncia exitosa", "Denuncia " +
                                "enviada exitosamente");
                    }else {
                        MainController.alert(Alert.AlertType.INFORMATION, "Denuncia no registrada", "Es" +
                                " posible que la publicación ya no exista");
                    }
                } catch (IOException e) {
                    MainController.alert(Alert.AlertType.ERROR, "Error de conexión", "Erro al conectar con" +
                            " el servidor, por favor, intente denunciar más tarde");
                }
            }

            volvelPantallaAnterior();

        }

    }
    public boolean validarCampos(){
        boolean comentarioValido = false;
        boolean camposValidos = false;
        if(this.txtCommentary.getText()!= null &&
                this.txtCommentary.getText().length()>= 10 &&
                this.txtCommentary.getText().length()<=100){
            comentarioValido = true;
        }else{
            MainController.alert(Alert.AlertType.ERROR, "Comentario Inválido","El comentario debe tener entre 10 y 100 " +
                    "caracteres");
        }
        if(comentarioValido){
            if((String)this.comboMotivo.getValue()!= null){
                camposValidos = true;
            }else{
                MainController.alert(Alert.AlertType.ERROR, "Campo vacío","Seleccione el motivo de su " +
                        "denuncia por favor");
            }
        }
        return camposValidos;
    }

    public void clicCancelar(ActionEvent actionEvent) {
        volvelPantallaAnterior();
    }
    private void volvelPantallaAnterior(){
        if(vieneDePantallaOfertas){
            MainController.activate("VerOferta", "Ver oferta", MainController.Sizes.MID);
        }else{
            MainController.activate("VerCodigoDescuento", "Ver codigo descuento", MainController.Sizes.MID);
        }
    }


}
