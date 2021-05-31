package vista.controlador;

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
        if (instanciaMiembroOfercomas()) {
            HashMap resultado = null;
            try {
                resultado = this.miembroOfercompas.logear();
                int status = (int) resultado.get("status");

                if (status == 200) {
                    HashMap payLoad = (HashMap) resultado.get("json");
                    MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
                    Double idMiembroDouble =(Double) payLoad.get("idMiembro");
                    Double tipoMiembroDouble = (Double) payLoad.get("tipoMiembro");
                    int idMiembroInt = idMiembroDouble.intValue();
                    int tipoMiembroInt = tipoMiembroDouble.intValue();


                    miembroOfercompas.setIdMiembro(idMiembroInt);
                    miembroOfercompas.setTipoMiembro(tipoMiembroInt);
                    miembroOfercompas.setNickname((String) payLoad.get("nickname"));
                    miembroOfercompas.setEmail((String) payLoad.get("email"));
                    miembroOfercompas.setContrasenia((String) payLoad.get("contrasenia"));
                    String token = (String) payLoad.get("token");
                    MainController.save("miembroLogeado", miembroOfercompas);
                    MainController.save("token", token);
                    System.out.println(miembroOfercompas.toString());
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
            } catch (IOException ioException) {
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