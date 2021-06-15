package modelo;

import com.google.gson.JsonObject;

public class MiembroDetalleDenuncias {
    private String nickname;
    private int idMiembro;
    private  int numeroDePublicacionesDenunciadas;

    public MiembroDetalleDenuncias() {
    }

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
        MiembroDetalleDenuncias miembroAux = new MiembroDetalleDenuncias();
        miembroAux.setIdMiembro(jsonMimebro.get("idMiembro").getAsInt());
        miembroAux.setNumeroDePublicacionesDenunciadas(jsonMimebro.get("denuncias").getAsInt());
        miembroAux.setNickname(jsonMimebro.get("nickname").getAsString());
        return  miembroAux;
    }
}
