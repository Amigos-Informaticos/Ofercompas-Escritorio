package datos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import vista.MainController;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class API {
    private static CookieManager cookieManager = new CookieManager();
    private String URL = "http://127.0.0.1";
    private int port = 5000;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public HashMap connect(String metodo, String recurso) throws IOException {
        return this.connect(metodo, recurso, null);
    }

    public HashMap connect(String metodo, String recurso, HashMap<String, String> parametros) throws IOException {
        return this.connect(metodo, recurso, parametros, null);
    }

    public HashMap connect(String metodo, String recurso, HashMap<String, String> parametros, HashMap<String, String> payload)
            throws IOException {
        return this.connect(metodo, recurso, parametros, payload, null, false);
    }

    public HashMap connect(String metodo, String recurso, HashMap<String, String> params,
                           HashMap<String, String> payload, HashMap<String, String> headers, boolean isArray)
            throws IOException {

        HashMap<String, Object> resultados = new HashMap();
        URL url = new URL(this.buildURL(recurso, params));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(20000);
        if (headers != null) {
            for (Map.Entry<String, String> entry: headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        if (API.cookieManager.getCookieStore().getCookies().size() > 0) {
            System.out.println("Las verdaderas coockies son" + API.cookieManager.getCookieStore().getCookies().toString());
            for (int i = 0; i < API.cookieManager.getCookieStore().getCookies().size(); i++) {
                String cookieValue = API.cookieManager.getCookieStore().getCookies().get(i).getName()
                        + "="
                        + API.cookieManager.getCookieStore().getCookies().get(i).getValue();
                if (i < API.cookieManager.getCookieStore().getCookies().size() - 1) {
                    cookieValue += ";";
                }
                connection.setRequestProperty(
                        "Cookie",
                        cookieValue
                );
                System.out.println("LAS COOCKIES SON: " + cookieValue);
            }
        }
        if (!metodo.equals("GET") && !metodo.equals("get")) {
            String jsonString = new Gson().toJson(payload);
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod(metodo);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(jsonString.length()));
            connection.setUseCaches(false);
            try (OutputStream stream = connection.getOutputStream()) {
                byte[] output = jsonString.getBytes(StandardCharsets.UTF_8);
                stream.write(output);
            }
        }
        connection.connect();
        resultados.put("status", connection.getResponseCode());
        StringBuilder retorno = new StringBuilder();
        if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 299) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String salida;
            while ((salida = reader.readLine()) != null) {
                retorno.append(salida);
            }
            List<String> cookiesHeader = connection.getHeaderFields().get("Set-Cookie");
            if (cookiesHeader != null) {
                for (String cookie: cookiesHeader) {
                    API.cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }
            connection.disconnect();
            if (!retorno.toString().equals("")) {
                if (!isArray) {
                    HashMap mapaRetorno = new Gson().fromJson(retorno.toString(), HashMap.class);
                    resultados.put("json", mapaRetorno);
                } else {
                    JsonArray mapaRetorno = new Gson().fromJson(retorno.toString(), JsonArray.class);
                    resultados.put("json", mapaRetorno);
                }
            }
        } else {
            connection.disconnect();
        }
        return resultados;
    }

    public String buildURL(String recurso, HashMap<String, String> parametros) {
        StringBuilder completeUrl = new StringBuilder(this.URL + ":" + this.port);
        if (recurso != null) {
            completeUrl.append("/").append(recurso);
        }
        int parametrosSize = 0;
        if (parametros != null && !parametros.isEmpty()) {
            completeUrl.append("?");
            for (Map.Entry<String, String> parametro: parametros.entrySet()) {
                completeUrl.append(parametro.getKey()).append("=").append(parametro.getValue());
                if (parametrosSize < parametros.size()) {
                    completeUrl.append("&");
                }
                parametrosSize++;
            }
        }
        return completeUrl.toString();
    }

    public HashMap enviarFormulario(String metodo, String recurso, HashMap<String, String> params,
                                    HashMap<String, String> payload, HashMap<String, String> headers, File archivos) throws IOException {
        return this.enviarFormulario(metodo,recurso,params,payload,headers,archivos,false);
    }

    public HashMap enviarFormulario(String metodo, String recurso, HashMap<String, String> params,
                             HashMap<String, String> payload, HashMap<String, String> headers, File archivos, boolean isVideo) throws IOException {
        HashMap<String, Object> resultados = new HashMap();
        URL url = new URL(this.buildURL(recurso, params));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        connection.setDoOutput(true);
        String boundary = Long.toHexString(System.currentTimeMillis());
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary = " + boundary);
        connection.setRequestMethod(metodo);

        try (
                OutputStream outputStream = connection.getOutputStream();
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
        ) {
            String saltoLinea = "\r\n";
            printWriter.append("--" + boundary).append(saltoLinea);
            if (archivos!=null) {
                printWriter.append("Content-Disposition: form-data; name = \"archivo\"; filename = \"  " + archivos.getName() + "\"").append(saltoLinea);
                printWriter.append("Content-Type: " + URLConnection.guessContentTypeFromName(archivos.getName())).append(saltoLinea);
                printWriter.append("Content-Transfer-Encoding: binary").append(saltoLinea);
                printWriter.append(saltoLinea).flush();
                Files.copy(archivos.toPath(), outputStream);
            }
            if (payload!=null) {
                for (Iterator<Map.Entry<String, String>> iterator = payload.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entrada = iterator.next();

                    String valorFormulario = entrada.getKey() + "=" + entrada.getValue();
                    if (iterator.hasNext()){
                        valorFormulario += "&";
                    }
                    outputStream.write(valorFormulario.getBytes(StandardCharsets.UTF_8));

                }
            }

            outputStream.flush();
            printWriter.append(saltoLinea).flush();
            printWriter.append("--" + boundary + "--").append(saltoLinea).flush();
        }
        resultados.put("status",connection.getResponseCode());
        return resultados;
    }

    public  static HashMap<String, String> getToken(){
        HashMap<String, String> token = new HashMap<>();
        String tokenString = (String) MainController.get("token");
        System.out.println("El token es:" + tokenString);
        token.put("token", tokenString);
        return token;
    }

}