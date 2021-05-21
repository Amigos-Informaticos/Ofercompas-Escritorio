package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import modelo.CodigoDescuento;
import modelo.Oferta;

public class ItemCodigosController {
    @FXML
    private Label lblTitulo;

    @FXML
    private ImageView imgTienda;

    private CodigoDescuento codigoDescuento;

    public void setData(CodigoDescuento codigo) {
        this.codigoDescuento = codigo;
        lblTitulo.setText(codigoDescuento.getTitulo());

    }
}
