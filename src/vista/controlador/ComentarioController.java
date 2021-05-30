package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import modelo.Comentario;

public class ComentarioController {
    @FXML
    private Label lblNickname;

    @FXML
    private Label lblContenido;

    public void setData(Comentario comentario) {
        this.lblNickname.setText(comentario.getNicknameComentador());
        this.lblContenido.setText(comentario.getContenido());
    }
}
