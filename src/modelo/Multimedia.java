package modelo;

import java.io.File;

public class Multimedia {
    private File archivo;
    private String url;

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(String path) {
        this.archivo = new File(path);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
