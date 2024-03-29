package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Oferta;
import vista.MyListener;

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

    private MyListener myListener;

    @FXML
    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(this.oferta);
    }

    public void setData(Oferta oferta, MyListener myListener) {
        this.oferta = oferta;
        this.myListener = myListener;
        lblTitulo.setText(oferta.getTitulo());
        lblPrecio.setText("$" + oferta.getPrecio());
        lblPuntuacion.setText(String.valueOf(oferta.getPuntuacion()));
        System.out.println(oferta.getFoto().getUrl());

        try {
            if(!oferta.getFoto().getUrl().equals("")){
                Image imagen = new Image(oferta.getFoto().getUrl());
                imgProducto.setImage(imagen);
            }

        }catch (Exception e){

        }


    }
}
