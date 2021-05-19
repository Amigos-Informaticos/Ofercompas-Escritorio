package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modelo.MiembroOfercompas;
import vista.MainController;

public class RegistrarMiembroController {
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtNickname;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private TextField txtRepetirContrasenia;

    private MiembroOfercompas miembro;

    public void initialize() {
        miembro = new MiembroOfercompas();
    }

    public void registrar() {
        System.out.println(txtEmail.getText() + txtNickname.getText() + txtContrasenia.getText() + txtRepetirContrasenia.getText());
        if (verificarContrasenia()) {
            instanciaMiembro();
            if (miembro.estaCompleto()) {
                if (miembro.registrar() == 201){
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Registro Exitoso",
                            "Publicación registrada exitosamente");
                }else{
                    MainController.alert(Alert.AlertType.ERROR,
                            "Error del servidor",
                            "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
                }
            } else {
                MainController.alert(Alert.AlertType.WARNING,
                        "Información Incorrecta",
                        "Llene todos los campos correctamente");
            }
        } else {
            MainController.alert(Alert.AlertType.WARNING,
                    "Contraseña incorrecta",
                    "Verifique que coincidan sus contraseñas");
        }
    }

    public void instanciaMiembro() {
        this.miembro.setEmail(txtEmail.getText());
        this.miembro.setNickname(txtNickname.getText());
        this.miembro.setContrasenia(txtContrasenia.getText());
    }

    public boolean verificarContrasenia() {
        return txtContrasenia.getText().equals(txtRepetirContrasenia.getText());
    }
}
