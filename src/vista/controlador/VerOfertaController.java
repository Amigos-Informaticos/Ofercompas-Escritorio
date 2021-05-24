package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import modelo.Oferta;
import vista.MainController;

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

    private Oferta oferta;

    public void initialize(){
        mostrarInformacionOferta();

    }

    public void mostrarInformacionOferta(){

        oferta = (Oferta) MainController.get("oferta");
        this.lblTitulo.setText(oferta.getTitulo());
        this.lblDescripcion.setText(oferta.getDescripcion());
        this.lblFechaInicio.setText(oferta.getFechaCreacion());
        this.lblFechaFin.setText(oferta.getFechaFin());
        this.lblPrecio.setText(oferta.getPrecio());

    }

}
