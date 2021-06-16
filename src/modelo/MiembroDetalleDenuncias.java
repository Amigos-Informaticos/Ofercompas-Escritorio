package modelo;

import com.google.gson.JsonObject;
import datos.API;

import java.io.IOException;
import java.util.HashMap;

public class MiembroDetalleDenuncias {
    private String nickname;
    private int idMiembro;
    private  int numeroDePublicacionesDenunciadas;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getIdMiembro() {
        return idMiembro;
    }

    public void setIdMiembro(int idMiembro) {
        this.idMiembro = idMiembro;
    }

    public int getNumeroDePublicacionesDenunciadas() {
        return numeroDePublicacionesDenunciadas;
    }

    public void setNumeroDePublicacionesDenunciadas(int numeroDePublicacionesDenunciadas) {
        this.numeroDePublicacionesDenunciadas = numeroDePublicacionesDenunciadas;
    }

    public static MiembroDetalleDenuncias deJsonAobjeto(JsonObject jsonMimebro){
        System.out.println("El json es:" + jsonMimebro.toString());

        MiembroDetalleDenuncias miembroAux = new MiembroDetalleDenuncias();
        miembroAux.setIdMiembro(Integer.parseInt((String.valueOf(jsonMimebro.get("idMiembro")))));
        miembroAux.setNumeroDePublicacionesDenunciadas(Integer.parseInt(String.valueOf(jsonMimebro.get("denuncias"))));
        miembroAux.setNickname(jsonMimebro.get("nickname").getAsString());
        return  miembroAux;
    }

    public HashMap obtenerReporte() throws IOException {
        API api = new API();
        String url = "miembros/"+this.idMiembro+ "/reporte";
        HashMap respuesta = api.connect("GET", url);
        return respuesta;
    }
}
