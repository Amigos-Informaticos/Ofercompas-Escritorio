import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class API {
	private String URL;
	private int port;
	
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
	
	public HashMap connect(String metodo, String recurso) {
		return this.connect(metodo, recurso, null, null);
	}
	
	public HashMap connect(String metodo, String recurso, HashMap parametros) {
		return this.connect(metodo, recurso, parametros, null);
	}
	
	public HashMap connect(String metodo, String recurso, HashMap<String, String> parametros, HashMap<String, String> payload) {
		HashMap<String, Object> resultados = new HashMap();
		try {
			URL url = new URL(this.buildURL(recurso, parametros));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(metodo);
			if (metodo != "GET" && metodo != "get") {
				connection.setRequestProperty("Accept", "application/json");
			}
			resultados.put("status", connection.getResponseCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String retorno = "";
			String salida;
			while ((salida = reader.readLine()) != null) {
				retorno += salida;
			}
			connection.disconnect();
			if (retorno != "") {
				HashMap mapaRetorno = new Gson().fromJson(retorno, HashMap.class);
				resultados.put("json", mapaRetorno);
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		
		return resultados;
	}
	
	public String buildURL(String recurso, HashMap<String, String> parametros) {
		StringBuilder completeUrl = new StringBuilder(this.URL + ":" + this.port);
		if (recurso != null) {
			completeUrl.append("/").append(recurso);
		}
		int parametrosSize = 0;
		if (parametros != null && parametros.isEmpty()) {
			completeUrl.append("?");
			for (Map.Entry<String, String> parametro: parametros.entrySet()) {
				completeUrl.append(parametro.getKey()).append("=").append(parametro.getValue());
			}
			if (parametrosSize < parametros.size()) {
				completeUrl.append("&");
			}
			parametrosSize++;
		}
		return completeUrl.toString();
	}
}
