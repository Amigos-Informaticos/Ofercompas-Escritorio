package modelo;

import com.google.gson.JsonObject;
import datos.API;

import java.io.IOException;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class MiembroOfercompas {
    private String email;
    private String nickname;
    private String contrasenia;
    private int idMiembro;
    private int tipoMiembro;
    private API api;

    public MiembroOfercompas(String email, String nickname, String contrasenia) {
        this.email = email;
        this.nickname = nickname;
        this.contrasenia = contrasenia;
        api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
    }

    public MiembroOfercompas() {
        api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);

    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public double getTipoMiembro() {
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

    @Override
    public String toString() {
        return "MiembroOfercompas{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }

    public int registrar() throws IOException {
        HashMap respuesta = api.connect("POST","miembros",null, this.obtenerHashmap());
        return (int) respuesta.get("status");
    }
    public MiembroOfercompas logear() throws IOException {
        HashMap<String, String> respuesta = api.connect("POST","login", null, this.obtenerHashmapLogin());
        MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
        if (respuesta.get("status").equals(200)) {
            int idMiembro = (int) Math.round(Float.parseFloat(respuesta.get("idMiembro")));
            miembroOfercompas.setIdMiembro(idMiembro);
        }
        return miembroOfercompas;
    }

    public MiembroOfercompas deJsonAObjeto(JsonObject miembroJson) {
        MiembroOfercompas miembroOfercompas = new MiembroOfercompas();
        miembroOfercompas.setIdMiembro(Integer.parseInt(String.valueOf(miembroJson.get("idMiembro"))));
        miembroOfercompas.setEmail(miembroJson.get("titulo").getAsString());
        miembroOfercompas.setContrasenia(miembroJson.get("descripcion").getAsString());

        return miembroOfercompas;
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


}
