package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.net.ConnectException;
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
    //componer
    public void clicIniciarSesion() {
        if (instanciaMiembroOfercomas()) {
            HashMap resultado = null;
            try {
                resultado = this.miembroOfercompas.logear();
                int status = (int) resultado.get("status");

                if (status == 200) {
                    HashMap payLoad = (HashMap) resultado.get("json");
                    MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
                    miembroOfercompas.setIdMiembro((double) payLoad.get("idMiembro"));
                    miembroOfercompas.setTipoMiembro((double) payLoad.get("tipoMiembro"));
                    miembroOfercompas.setNickname((String) payLoad.get("nickname"));
                    String token = (String) payLoad.get("token");
                    MainController.save("miembroLogeado", miembroOfercompas);
                    MainController.save("token", token);
                    MainController.activate("InicioOfertas", "Inicio", MainController.Sizes.MID);

                } else if (status == 404) {
                    MainController.alert(Alert.AlertType.WARNING,
                            "Usuario no encontrado",
                            "Verifica la información ingresada");
                } else {
                    MainController.alert(Alert.AlertType.ERROR,
                            "Error del servidor",
                            "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
                }
            } catch (ConnectException connectException) {
                MainController.alert(Alert.AlertType.ERROR,
                        "Error del servidor",
                        "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
            } catch (NullPointerException nullPointerException){
                MainController.alert(Alert.AlertType.ERROR,
                        "Error del servidor",
                        "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
            }

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
