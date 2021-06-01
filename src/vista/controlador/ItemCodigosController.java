package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.CodigoDescuento;
import modelo.Oferta;
import vista.InicioCodigosListener;

public class ItemCodigosController {
    @FXML
    private Label lblTitulo;

    @FXML
    private ImageView imgTienda;

    private CodigoDescuento codigoDescuento;

    private InicioCodigosListener inicioCodigosListener;

    @FXML
    public void click(MouseEvent mouseEvent) {
        inicioCodigosListener.onClickListener(this.codigoDescuento);
    }

    public void setData(CodigoDescuento codigo, InicioCodigosListener inicioCodigosListener) {
        this.codigoDescuento = codigo;
        this.inicioCodigosListener = inicioCodigosListener;
        lblTitulo.setText(codigoDescuento.getTitulo());

    }
}
