package modelo;

import datos.API;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MiembroOfercompas {
    private String email;
    private String nickname;
    private String contrasenia;
    private int idMiembro;
    private int tipoMiembro;

    public MiembroOfercompas(String email, String nickname, String contrasenia) {
        this.email = email;
        this.nickname = nickname;
        this.contrasenia = contrasenia;
    }

    public MiembroOfercompas() {

    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getTipoMiembro() {
        return tipoMiembro;
    }

    public void setTipoMiembro(int tipoMiembro) {
        this.tipoMiembro = tipoMiembro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean estaCompleto() {
        return this.email != null && this.nickname != null && this.contrasenia != null;
    }

    public int registrar() throws ConnectException {
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);

        HashMap respuesta = api.connect("POST","miembros",null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }
    public HashMap logear() throws ConnectException{
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
        HashMap respuesta = api.connect("POST","login",null, this.obtenerHashmapLogin());

        return respuesta;
    }




    public HashMap obtenerHashmap(){
        HashMap<String ,String> miembro = new HashMap<String, String>();
        miembro.put("email",this.email);
        miembro.put("nickname",this.nickname);
        miembro.put("contrasenia",this.contrasenia);

        return miembro;
    }
    public HashMap obtenerHashmapLogin(){
        HashMap<String ,String> miembro = new HashMap<String, String>();
        miembro.put("email",this.email);
        miembro.put("contrasenia",this.contrasenia);

        return miembro;
    }
    public static boolean esEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]" +
                "+@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])" +
                "?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)*$";
        return Pattern.compile(emailRegex).matcher(email==null?"":email).matches();
    }

    @Override
    public String toString() {
        return "MiembroOfercompas{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", idMiembro=" + idMiembro +
                ", tipoMiembro=" + tipoMiembro +
                '}';
    }
}
