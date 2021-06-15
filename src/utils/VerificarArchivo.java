package utils;

public class VerificarArchivo {
    public String reverse(String string) {
        StringBuilder reversed = null;
        if (string.length() > 0) {
            reversed = new StringBuilder();
            for (int i = string.length() - 1; i >= 0; i--) {
                reversed.append(string.charAt(i));
            }
        }
        assert reversed != null;
        return reversed.toString();
    }

    public String getExt(String path) {
        String reversed = this.reverse(path);
        StringBuilder returnValue = new StringBuilder();
        int i = 0;
        while (reversed.charAt(i) != '.') {
            returnValue.append(reversed.charAt(i));
            i++;
        }
        return this.reverse(returnValue.toString());
    }

    public boolean fotoValida(String path) {
        String extension = this.getExt(path);

        return extension.equals("jpg") ||
                extension.equals("png") ||
                extension.equals("JPG") ||
                extension.equals("PNG") ||
                extension.equals("JPEG") ||
                extension.equals("jpeg");
    }

    public boolean videoValido(String path) {
        String extension = this.getExt(path);

        return extension.equals("mp4") || extension.equals("MP4");
    }
}
