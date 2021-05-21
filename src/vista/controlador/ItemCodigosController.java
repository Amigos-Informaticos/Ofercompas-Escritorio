package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import modelo.Oferta;

public class ItemOfertasController {
    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblPrecio;

    @FXML
    private Label lblPuntuacion;

    @FXML
    private ImageView imgProducto;

    private Oferta oferta;

    public void setData(Oferta oferta) {
        this.oferta = oferta;
        lblTitulo.setText(oferta.getTitulo());
        lblPrecio.setText("$" + oferta.getPrecio());
        lblPuntuacion.setText(oferta.getFechaCreacion());
    }
}
