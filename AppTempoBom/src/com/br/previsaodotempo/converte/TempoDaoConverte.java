package com.br.previsaodotempo.converte;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.br.previsaodotempo.Dao.Tempo;
/**
 *
 * @author Irlei
 */
public class TempoDaoConverte {
	/**
	 * Recebe um JSONObject e devolve um de objeto do tipo  Tempo
	 **/
	public static Tempo getTempo(JSONObject obj) {
		Tempo tempo = null;
		if (obj != null) {
			try {
				tempo = new Tempo();
				tempo.setId(obj.getInt("weatherCode"));
				tempo.setData(obj.getString("date"));
				tempo.setPrecipMM(obj.getDouble("precipMM"));
				tempo.setTempMaxC(obj.getInt("tempMaxC"));
				tempo.setTempMaxF(obj.getInt("tempMaxF"));
				tempo.setTempMinC(obj.getInt("tempMinC"));
				tempo.setTempMinF(obj.getInt("tempMinF"));
				JSONArray weatherDesc = obj.getJSONArray("weatherDesc");
				JSONArray weatherIconUrl = obj.getJSONArray("weatherIconUrl");
				JSONObject objDescricaoTempo = new JSONObject(
						weatherDesc.getString(0));
				JSONObject objUrlImagem = new JSONObject(
						weatherIconUrl.getString(0));
				tempo.setDescricaoTempo(objDescricaoTempo.getString("value"));
				tempo.setUrlImagem(objUrlImagem.getString("value"));
				tempo.setWinddirection(obj.getString("winddirection"));
				tempo.setWindspeedKmph(obj.getInt("windspeedKmph"));
				tempo.setWindspeedMiles(obj.getInt("windspeedMiles"));

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return tempo;
	}

        //como a região não esta dentro do nó "weather"  esse metodo pegara a informação de região no nó  "requset"
	public static String getRegiao(JSONArray jObj) {
		String regiao = null;
		try {
			for (int i = 0; i < jObj.length(); i++) {
				JSONObject obj = (JSONObject) jObj.get(i);
				regiao = obj.getString("query"); // pega o nome da (cidade,país)
			}
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		return regiao;
	}

	/**
	 * Recebe um JOSONArray e devolve um lista de objetos especificos, nesse
	 * caso uma Lista de "weather" (Tempo)
	 **/
	public static List<Tempo> getTempo(JSONArray jObj) {
		List<Tempo> tempo = null;
		if (jObj != null) {
			tempo = new ArrayList<Tempo>();
			try {
				for (int i = 0; i < jObj.length(); i++) {
					JSONObject obj = (JSONObject) jObj.get(i);
					tempo.add(getTempo(obj));
				}
			} catch (JSONException e) {				
				e.printStackTrace();
			}
		}
		return tempo;
	}

}
