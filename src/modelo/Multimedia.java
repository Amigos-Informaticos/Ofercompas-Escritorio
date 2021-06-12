package modelo;

import java.io.File;

public class Multimedia {
    private File archivo;
    private String url;

    public Multimedia(){}

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
