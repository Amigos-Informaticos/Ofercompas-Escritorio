package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import modelo.MiembroOfercompas;
import utils.LimitadorTextField;
import vista.MainController;

import java.net.ConnectException;

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
        LimitadorTextField.limitarTamanio(txtEmail,40);
        LimitadorTextField.limitarTamanio(txtNickname,20);
        LimitadorTextField.limitarTamanio(txtContrasenia,20);
    }


    public void registrar()  {
        if (validarCampos()) {
            instanciaMiembro();
            int codigoRespuesta = 0;
            try {
                codigoRespuesta = miembro.registrar();
                if (codigoRespuesta == 201) {
                    MainController.alert(Alert.AlertType.INFORMATION,
                            "Registro Exitoso",
                            "¡Te has registrado en Ofercompas!");
                } else if (codigoRespuesta == 409){
                    MainController.alert(Alert.AlertType.ERROR,
                            "Nickname o Email previamente registrado",
                            "Intenta con otro email o nickname por favor");
                }else{
                    MainController.alert(Alert.AlertType.ERROR,
                            "Error al conectar con el servidor",
                            "Error al conectar con el servidor, por favor inténtelo más tarde");
                }
            } catch (ConnectException connectException) {
                MainController.alert(Alert.AlertType.ERROR,
                        "Error al conectar con el servidor",
                        "Error al conectar con el servidor, por favor inténtelo más tarde");
            }

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

    public void regresar(MouseEvent mouseEvent) {
        MainController.activate("Login","Login",MainController.Sizes.SMALL);
    }


    private boolean camposCompletos(){
        boolean camposCompletos = false;
        if(!this.txtEmail.getText().isEmpty()){
            if(!this.txtNickname.getText().isEmpty()){
                if(!this.txtContrasenia.getText().isEmpty()){
                    if(!this.txtRepetirContrasenia.getText().isEmpty()){
                        camposCompletos = true;
                    }else{
                        mostrarMensaje("Campos Vacíos", "Por favor, ingrese la confirmación de su contraseña");
                    }
                }else{
                    mostrarMensaje("Campos Vacíos", "Por favor, ingrese una contaseña");
                }
            }else{
                mostrarMensaje("Campos Vacíos", "Por favor, ingrese un nickname");
            }
        }else {
            mostrarMensaje("Campos Vacíos", "Por favor, ingrese una email");
        }


        return  camposCompletos;
    }

    private boolean contraseniasIguales(){
        boolean contraseniasIguales = false;

        String contrasenia = txtContrasenia.getText();
        String contraseniaConfirmada = txtRepetirContrasenia.getText();

        if (contraseniaConfirmada.equals(contrasenia)){
            contraseniasIguales= true;
        }else{
            mostrarMensaje("Información inválida", "Las contraseñas no coinciden");
        }

        return  contraseniasIguales;
    }



    private boolean validarCampos(){
        boolean camposValidos = false;

        if(camposCompletos() && emailValido() && contraseniasIguales() ){
            camposValidos = true;
        }

        return  camposValidos;
    }

    private void mostrarMensaje(String titulo, String mensaje){
        MainController.alert(Alert.AlertType.WARNING,
                titulo,mensaje);
    }

    public void registrarse(MouseEvent mouseEvent) {
        try{
            this.registrar();
        }catch (NullPointerException exception){
            mostrarMensaje("Error", "Error al conectar con el servidor");
        }
    }
    public boolean emailValido(){
        boolean emailValido = false;
        if(MiembroOfercompas.esEmail(this.txtEmail.getText())){
            emailValido = true;
        }else{
            mostrarMensaje("Información inválida", "Por favor, ingrese un email válido");
        }
        return  emailValido;
    }
}
