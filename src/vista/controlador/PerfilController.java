package vista.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import modelo.MiembroOfercompas;
import utils.LimitadorTextField;
import vista.MainController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController implements Initializable {

    @FXML
    private TextField txtNickname;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private PasswordField txtConfirmarContrasenia;

    private MiembroOfercompas miembroOfercompasRecuperado;
    private MiembroOfercompas miembroOfercompasActualizado;
    private String token;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.inicializarCampos();
        this.miembroOfercompasActualizado = new MiembroOfercompas();
        this.limitarTxtfields();
    }


    public void clicActualizar(ActionEvent actionEvent) {
        if (sinCambiarContrasenia()) {
            if (validarCamposSinContrasenia()) {
                this.instanciarSinContrasenia();
                actualizarMiembro();
            }
        } else if (validarCamposConContrasenia()) {
                actualizarMiembro();
        }
    }

    private void actualizarMiembro(){
        int status = 0;
        try {
            System.out.println("El token es: " + this.token);
            status= this.miembroOfercompasActualizado.actualizar(miembroOfercompasRecuperado.getEmail(), this.token);

            if(status==200){
                MainController.alert(Alert.AlertType.WARNING, "Exito", "Información actualizada" +
                        "extitosamente");
            }else  if(status == 409){
                MainController.alert(Alert.AlertType.WARNING, "Error", "El nickname o correo electronico" +
                        "ya están siendo utilizados por otro usuario, intenta con otro.");

            }else{
                MainController.alert(Alert.AlertType.WARNING, "Error", "Error al conectar con el servidor");
            }
        } catch (IOException e) {
            MainController.alert(Alert.AlertType.WARNING, "Error", "Error al conectar con el servidor");
        }
    }

    private void instanciarSinContrasenia() {
        System.out.println("La contrasenia es: " + miembroOfercompasRecuperado.getContrasenia());
        this.miembroOfercompasActualizado.setContrasenia(miembroOfercompasRecuperado.getContrasenia());
        this.token = (String) MainController.get("token");
    }


    private void inicializarMiembro() {
        this.miembroOfercompasRecuperado = (MiembroOfercompas) MainController.get("miembroLogeado");
        this.token = (String) MainController.get("token");
    }

    private void inicializarCampos() {
        inicializarMiembro();
        this.txtEmail.setText(this.miembroOfercompasRecuperado.getEmail());
        this.txtNickname.setText(this.miembroOfercompasRecuperado.getNickname());
    }

    private void limitarTxtfields() {
        LimitadorTextField.limitarTamanio(txtNickname, 20);
        LimitadorTextField.limitarTamanio(txtContrasenia, 20);
        LimitadorTextField.limitarTamanio(txtConfirmarContrasenia, 20);
        LimitadorTextField.limitarTamanio(txtEmail, 40);


    }

    private boolean sinCambiarContrasenia() {
        boolean sinContrasenia = false;
        if (this.txtConfirmarContrasenia.getText().length() == 0 && this.txtContrasenia.getText().length() == 0) {
            sinContrasenia = true;
        }
        return sinContrasenia;
    }

    private boolean validarCamposSinContrasenia() {
        boolean validos = false;
        if (nickNameValido() && emailValido()) {
            validos = true;
        }

        return validos;
    }

    private boolean validarCamposConContrasenia() {
        boolean validos = false;
        if (nickNameValido() && emailValido() && contraseniaValida()) {
            validos = true;
        }

        return validos;
    }


    private boolean nickNameValido() {
        boolean valido = false;
        try {
            if (this.txtNickname.getText().length() <= 20 && this.txtNickname.getText().length() >= 4 && !this.txtNickname.getText().contains(" ")) {
                miembroOfercompasActualizado.setNickname(this.txtNickname.getText());
                valido = true;
            } else {
                MainController.alert(Alert.AlertType.WARNING, "Informacion Incorrecta", "El nick name debe" +
                        "tener entre 4 y 20 caracteres, sin espacios");
            }
        } catch (Exception e) {
            MainController.alert(Alert.AlertType.WARNING, "Ataque detectado", "Por favor, respete nuestro" +
                    "sistema o procederemos legalmente");
        }
        return valido;

    }

    private boolean contraseniaValida() {
        boolean valida = false;
        try {
            if (this.txtContrasenia.getText().length() <= 20 && this.txtContrasenia.getText().length() >= 4 && !this.txtContrasenia.getText().contains(" ")) {
                if (txtContrasenia.getText().equals(txtConfirmarContrasenia.getText())) {
                    String contraseniaEncriptada = MiembroOfercompas.encriptar(this.txtContrasenia.getText());
                    System.out.println("La contraseñia encryptada es: " + contraseniaEncriptada);
                    miembroOfercompasActualizado.setContrasenia(contraseniaEncriptada);
                    valida = true;
                } else if (txtContrasenia.getText().isEmpty()){
                    this.instanciarSinContrasenia();
                }else{
                    MainController.alert(Alert.AlertType.WARNING, "Informacion Incorrecta", "Las " +
                            "contraseñas no coinciden");
                }
            } else {
                MainController.alert(Alert.AlertType.WARNING, "Informacion Incorrecta", "La contrasenia" +
                        "debe tener entre 4 y 20 caracteres");
            }
        } catch (Exception e) {
            MainController.alert(Alert.AlertType.WARNING, "Ataque detectado", "Por favor, respete nuestro" +
                    "sistema o procederemos legalmente");
        }
        return valida;

    }

    private boolean emailValido() {
        boolean emailValido = false;

        try {
            if (this.txtEmail.getText().length() <= 64 && MiembroOfercompas.validarEmail(this.txtEmail.getText())) {
                emailValido = true;
                this.miembroOfercompasActualizado.setEmail(this.txtEmail.getText());
            }else{
                MainController.alert(Alert.AlertType.WARNING, "Información Inválida", "Ingrese un email" +
                        "válido por favor");
            }

        } catch (Exception exception) {
            MainController.alert(Alert.AlertType.WARNING, "Ataque detectado", "Por favor, respete nuestro" +
                    "sistema o procederemos legalmente");
        }
        return emailValido;
    }


    public void clicCancelar(ActionEvent actionEvent) {
        MainController.activate("InicioOfertas", "Inicio Ofertas", MainController.Sizes.MID);
    }
}
