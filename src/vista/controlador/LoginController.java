package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import vista.MainController;

public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContrasenia;

    public void initialize(){

    }

    public void clicRegistrarse(){
        MainController.activate("RegistrarMiembro","Regístrate",MainController.Sizes.MID);
    }

    public void clicIniciarSesion(){
        MainController.activate("PublicarOferta","Regístrate",MainController.Sizes.MID);
    }
}
