package vista.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modelo.MiembroOfercompas;
import vista.MainController;

import java.util.HashMap;

public class LoginController {
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtContrasenia;
    private  MiembroOfercompas miembroOfercompas;

    public void initialize(){

    }

    public void clicRegistrarse(){
        MainController.activate("RegistrarMiembro","Regístrate",MainController.Sizes.MID);
    }

    public void clicIniciarSesion(){
        if(instanciaMiembroOfercomas()){
            HashMap resultado  =this.miembroOfercompas.logear();
            String status = (String) resultado.get("status");

            if(status.equals("200")){
                HashMap payLoad = (HashMap) resultado.get("json");
                MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
                miembroOfercompas.setIdMiembro((Integer) payLoad.get("idMiembro"));
                miembroOfercompas.setTipoMiembro((Integer) payLoad.get("tipoMiembro"));
                miembroOfercompas.setNickname((String) payLoad.get("nickname"));
                String token = (String) payLoad.get("token");
                MainController.save("miembroLogeado", miembroOfercompas);
                MainController.save("token", token);
                MainController.activate("InicioOfertas","Inicio",MainController.Sizes.MID);

            }else if(status.equals("404")) {
                MainController.alert(Alert.AlertType.WARNING,
                        "Usuario no encontrado",
                        "Verifica la información ingresada");
            }else {
                MainController.alert(Alert.AlertType.ERROR,
                        "Error del servidor",
                        "No se pudo establecer conexión con el servidor. Inténtalo más tarde");
            }
        }
    }



    private boolean instanciaMiembroOfercomas(){
        boolean instanciado = false;
        this.miembroOfercompas = new MiembroOfercompas();
        if(camposValidos()){
            this.miembroOfercompas.setEmail(this.txtEmail.getText());
            this.miembroOfercompas.setContrasenia(this.txtContrasenia.getText());
            instanciado = true;
        }


        return  instanciado;
    }

    private boolean camposValidos(){
        boolean campoEmailValido = false;
        boolean camposValidos=false;
        if(!this.txtEmail.getText().isEmpty()){
            if(MiembroOfercompas.esEmail(this.txtEmail.getText())){
                campoEmailValido = true;
            }else{
                MainController.alert(Alert.AlertType.WARNING,
                        "Información Incorrecta",
                        "Email invalido");
            }
        }else {
            MainController.alert(Alert.AlertType.WARNING,
                    "Información Incorrecta",
                    "Email invalido");
        }

        if(campoEmailValido && !this.txtContrasenia.getText().isEmpty()){
            camposValidos = true;
        }

        return camposValidos;
    }

}
