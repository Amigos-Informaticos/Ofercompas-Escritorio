package utils;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LimitadorTextfield {

    public static void limitarTamanio(TextField textField, int tamanio) {
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() > (tamanio - 1)) {
                event.consume();
            }
        });
    }
    public static void limitarTamanio(PasswordField passwordField, int tamanio) {
        passwordField.setOnKeyTyped(event -> {
            if (passwordField.getText().length() > (tamanio - 1)) {
                event.consume();
            }
        });
    }

    public static void limitarTamanioArea(TextArea textField, int tamanio) {
        textField.setOnKeyTyped(event -> {
            if (textField.getText().length() > (tamanio - 1)) {
                event.consume();
            }
        });
    }

    public static void soloNumeros(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloNumerosEnterosEvent);
    }

    public static void soloCaracteres(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloCaracteresEvent);
    }

    public static void soloTexto(TextField textField) {
        textField.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloTextoEvent);
    }

    public static void soloTextoArea(TextArea textArea) {
        textArea.addEventHandler(KeyEvent.KEY_TYPED, LimitadorTextfield::soloTextoEvent);
    }

    public static void soloNumerosEnterosEvent(KeyEvent keyEvent) {
        try {
            if (!Character.isDigit(keyEvent.getCharacter().charAt(0))) {
                keyEvent.consume();
            }
        } catch (Exception ex) {

        }
    }

    public static void soloCaracteresEvent(KeyEvent keyEvent) {
        try {
            if (Character.isDigit(keyEvent.getCharacter().charAt(0))) {
                keyEvent.consume();
            }
        } catch (Exception ex) {

        }
    }

    public static void soloTextoEvent(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);
            if (!Character.isLetterOrDigit(key) && !Character.isSpaceChar(key)) {
                keyEvent.consume();
            }
        } catch (Exception ex) {

        }
    }
    public static void soloNombres(TextField textField){
        textField.addEventHandler(KeyEvent.KEY_TYPED, event -> soloNombresEvent(event));
    }
    public static void soloNombresEvent(KeyEvent keyEvent){
        if (!keyEvent.getCode().toString().matches("[a-zA-Z]")
                && keyEvent.getCode() != KeyCode.BACK_SPACE
                && keyEvent.getCode() != KeyCode.SPACE
                && keyEvent.getCode() != KeyCode.SHIFT) {
            keyEvent.consume();
        }
    }
}
