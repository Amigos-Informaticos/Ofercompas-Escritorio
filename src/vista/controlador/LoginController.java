package vista.controlador;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.io.IOException;
import java.util.HashMap;

public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContrasenia;
    private MiembroOfercompas miembroOfercompas;

    public void initialize() {

    }

    public void clicRegistrarse() {
        MainController.activate("RegistrarMiembro", "Regístrate", MainController.Sizes.MID);
    }
    public void clicIniciarSesion() {
        instanciaMiembroOfercomas();
        try {
            this.miembroOfercompas.logear();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    private boolean instanciaMiembroOfercomas() {
        boolean instanciado = false;
        this.miembroOfercompas = new MiembroOfercompas();
        if (camposValidos()) {
            this.miembroOfercompas.setEmail(this.txtEmail.getText());
            this.miembroOfercompas.setContrasenia(this.txtContrasenia.getText());
            instanciado = true;
        }
        return instanciado;
    }

    private boolean camposValidos() {
        boolean campoEmailValido = false;
        boolean camposValidos = false;
        if (!this.txtEmail.getText().isEmpty()) {
            if (MiembroOfercompas.esEmail(this.txtEmail.getText())) {
                campoEmailValido = true;
            } else {
                MainController.alert(Alert.AlertType.WARNING,
                        "Información Incorrecta",
                        "Email invalido");
            }
        } else {
            MainController.alert(Alert.AlertType.WARNING,
                    "Campos Vacios",
                    "Por favor ingrese un email");
        }

        if (campoEmailValido) {
            if (!this.txtContrasenia.getText().isEmpty()) {
                camposValidos = true;
            } else {
                MainController.alert(Alert.AlertType.WARNING,
                        "Campos Vacios",
                        "Por favor ingrese una contraseña");
            }
        }
        return camposValidos;
    }

}
