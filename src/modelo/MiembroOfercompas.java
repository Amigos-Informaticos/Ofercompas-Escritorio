package modelo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import datos.API;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.validator.routines.EmailValidator;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
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

    public int registrar() throws IOException {
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
        this.contrasenia = encriptar(this.contrasenia);

        HashMap respuesta = api.connect("POST","miembros",null, this.obtenerHashmap(), API.getToken(), false);
        return (int) respuesta.get("status");
    }
    public  int actualizar(String oldEmail) throws IOException{
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
        String recurso = "miembros/" + oldEmail;
        System.out.println(obtenerHashmap().toString());
        HashMap respuesta = api.connect("PUT",recurso, null, this.obtenerHashmap(), API.getToken(), false);
        return (int) respuesta.get("status");

    }

    public HashMap logear() throws IOException {
        API api = new API();
        api.setURL("http://127.0.0.1");
        api.setPort(5000);
        this.contrasenia = encriptar(contrasenia);
        System.out.println(this.contrasenia);
        HashMap respuesta = api.connect("POST", "login", null, this.obtenerHashmapLogin(), API.getToken(), false);
        return respuesta;
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
    public HashMap obtenerHashmap(String token){
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
    public static boolean validarEmail(String email){
        boolean emailValido = false;
        EmailValidator validator = EmailValidator.getInstance();
        if (validator.isValid(email)) {
            emailValido = true;
        }
        return  emailValido;
    }

    public  static boolean validarNickname(String nickname){
        boolean valido = false;
        if(nickname.length()<= 20 && nickname.length()>=4){
            valido = true;
        }
        return  valido;
    }
    public static boolean validadarContrasenia(String contrasenia){
        boolean valida = false;
        if(contrasenia.length()>=6 &&  contrasenia.length()<=15){
            valida = true;
        }
        return  valida;
    }
    public static String encriptar(String value) {
        try {
            String clave = "FooBar1234567890"; // 128 bit
            byte[] iv = new byte[16];
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec sks = new SecretKeySpec(clave.getBytes("UTF-8"), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks, new IvParameterSpec(iv));

            byte[] encriptado = cipher.doFinal(value.getBytes());
            return DatatypeConverter.printBase64Binary(encriptado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static ObservableList<MiembroDetalleDenuncias> obtenerMiembrosConDenuncias() throws IOException {
        ObservableList<MiembroDetalleDenuncias> miembrosRetorno = FXCollections.observableArrayList();
        API api = new API();
        HashMap respuesta = api.connect("GET", "miembros/reportes", null, null, null, true);
        System.out.println("LA RESPUESTA ES:" + respuesta);

        if (respuesta.get("status").equals(200)){
            JsonArray  miembrosObtenidos = (JsonArray) respuesta.get("json");
            for (int i =0; i< miembrosObtenidos.size(); i++){
                MiembroDetalleDenuncias miembroAux =MiembroDetalleDenuncias.deJsonAobjeto((JsonObject) miembrosObtenidos.get(i));
                miembrosRetorno.add(miembroAux);
            }
        }

        return  miembrosRetorno;

    }

    public  static HashMap prohibir(int idMiembroParam) throws IOException {
        API api = new API();
        String url = "miembros/"+ idMiembroParam + "/expulsion";
        HashMap respuesta = api.connect("PUT", url,null, null, API.getToken(), false);
        return respuesta;
    }
}
