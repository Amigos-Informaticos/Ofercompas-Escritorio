package modelo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import datos.API;

import java.io.IOException;
import java.util.HashMap;

public class Comentario {
    private String contenido;
    private String nicknameComentador;

    public Comentario(String contenido, String nicknameComentador) {
        this.contenido = contenido;
        this.nicknameComentador = nicknameComentador;
    }

    public Comentario() {
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNicknameComentador() {
        return nicknameComentador;
    }

    public void setNicknameComentador(String nicknameComentador) {
        this.nicknameComentador = nicknameComentador;
    }

    public Comentario deJsonAObjeto(JsonObject comentarioJson){
        Comentario comentario = new Comentario();
        comentario.setContenido(String.valueOf(comentarioJson.get("contenido")));
        comentario.setNicknameComentador(String.valueOf(comentarioJson.get("nickname")));
        return comentario;

    }

    @Override
    public String toString() {
        return "Comentario{" +
                "contenido='" + contenido + '\'' +
                ", nicknameComentador='" + nicknameComentador + '\'' +
                '}';
    }

    public Comentario[] obtenerComentarios(int idPublicacion) throws IOException {
        API api = new API();
        HashMap<String, String> parametros = new HashMap();
        parametros.put("idPublicacion", String.valueOf(idPublicacion));
        HashMap respuesta = api.connect("GET", "comentarios", parametros, null, null, true);
        Comentario[] ComentariosConvertidos = new Comentario[0];
        if (respuesta.get("status").equals(200)) {
            JsonArray ComentariosObtenidos = (JsonArray) respuesta.get("json");
            ComentariosConvertidos = new Comentario[ComentariosObtenidos.size()];
            for (int i = 0; i < ComentariosObtenidos.size(); i++) {
                ComentariosConvertidos[i] = deJsonAObjeto((JsonObject) ComentariosObtenidos.get(i));
            }
        }
        return ComentariosConvertidos;


    }
}
