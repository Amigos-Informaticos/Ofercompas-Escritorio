package datos;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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

	public HashMap connect(String metodo, String recurso, HashMap<String, String> parametros) {
		return this.connect(metodo, recurso, parametros, null);
	}

	public HashMap connect(String metodo, String recurso, HashMap<String, String> parametros, HashMap<String, String> payload) {
		HashMap<String, Object> resultados = new HashMap();
		try {
			URL url = new URL(this.buildURL(recurso, parametros));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
			resultados.put("status", connection.getResponseCode());
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder retorno = new StringBuilder();
			String salida;
			while ((salida = reader.readLine()) != null) {
				retorno.append(salida);
			}
			connection.disconnect();
			if (!retorno.toString().equals("")) {
				HashMap mapaRetorno = new Gson().fromJson(retorno.toString(), HashMap.class);
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