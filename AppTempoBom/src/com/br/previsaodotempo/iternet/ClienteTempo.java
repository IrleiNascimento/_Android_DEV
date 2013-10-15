package com.br.previsaodotempo.iternet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
/**
 *
 * @author Irlei
 */
public class ClienteTempo {
	
	private static HttpClient mHttpClient;
	
        /*Cria um httpCliente que é responsavel por fazer o pedido de serviço a api
         HttpClient é quem faz a intermediação entre o Aplicativo e o sevrviço oferecida na WEB
         */
	private static HttpClient getHttpClient() {
		
		if (mHttpClient == null) { // so será criado uma vez
			mHttpClient = new DefaultHttpClient();			
			final HttpParams params = mHttpClient.getParams();			
			HttpConnectionParams.setConnectionTimeout(params,ClienteWebTempo.HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, ClienteWebTempo.HTTP_TIMEOUT);	//confiurando o tempo de espera caso aja falha na requisição		
			ConnManagerParams.setTimeout(params, ClienteWebTempo.HTTP_TIMEOUT);			
		}		
		return mHttpClient;
	}


/*Metodo que utiliza o HttpClient para fazer o pedido da previsão passando a url*/
		public final String[] GET(String url) {
			
		String[] result = new String[2]; /* para termos de verificação será guardo 2 respostas : o valor do status do 
                 * serviço na primeira posição e o texto do json na seguda posição*/ 
		
		HttpGet httpget = new HttpGet(url); // recebe a url que foi passada no parametro
		HttpResponse response;
		try {
			httpget.addHeader("Accept", ClienteWebTempo.APLICATION_JSON);// especificando que a resposta de ser um arquivo json
			HttpClient client = getHttpClient();// cria o HttpClient
			response = client.execute(httpget); // momento em que a conexão com a internet e chamada e o pedido vai direto para a url api 
			HttpEntity entity = response.getEntity(); // entity recebe a resposta (texto) json ou xml
			if (entity != null) {
				// Guarda o status do resultado http exemplo: 200=ok , 404=Erro
				result[0] = String.valueOf(response.getStatusLine()
						.getStatusCode());
                                /* o texto que é recebido, na maioria dos casos, perde informações se for passado diretamente para um JSONObject 
                                 então ele será jogado para um InputStream uqe é um manipulador de arquivos e terá seus bytes  varridos um por um*/
				InputStream instream = entity.getContent();
				// Guarda o resultado json ou xml
				result[1] = toString(instream);//o metodo toString "varre os bytes do texto json recebido e devolve um String limpa que agoga esta´ra pronta para o  JSONObject "
				instream.close();//depois que a string foi limpa, o InputStream é fechado
				// log para debug, importante para ser verificar erros 
				Log.i("GET", "STATUS" + result[0] + "RESULTADO DO GET  JOSN OU XML"+ result[1]);
			}
		} catch (Exception e) {// se tudo de errado
			// log para debug
			Log.e("ERRO", "Falha ao acessar Web service", e);
			result[0] = "0";
			result[1] = "Falha de rede!";	//como houve falha na conexão, o lugar onde seriao json, passa a ser a mensagem da falha		
		}
		return result; // retorna o resultado do pedido de previsão do tempo faito a api dentro do result encontra-se o nosso json
	}
		
	/*metodo que utliza o GET e recebe como resposta o nosso json dentro do result*/	
	public JSONArray getRequestJSONArray(String url) throws Exception {
		String[] resposta = GET(url);
		if (resposta[0].equals(ClienteWebTempo.STATUS_HTTP)) {//o result tem na sua primeira posição o status , entao se ele for  200(ok) prossiga
			JSONArray jarray = new JSONArray(resposta[1]);/*na segunda posição do result está o texto json que 
                        ja foi varrido e agora será jogado para dentro do JSONObject*/
			
                        return jarray; // se tudo ocorreu bem então um JSONArray com os dados solicitados será retornado
                        
		} else {
			throw new Exception(resposta[1]);// mensagem que será mostrada no debug caso tudo de errado 
		}
	}
	
	
        
        /*esse metodo tem as mesmas funcionalidade do metodo acima, porem  retorna um JSONObject  e não JSONArray */
	public JSONObject getRequestJSONObject(String url) throws Exception {
		String[] resposta = GET(url);
		if (resposta[0].equals(ClienteWebTempo.STATUS_HTTP)) {
			JSONObject jsonObj;
			jsonObj = new JSONObject(resposta[1]);
			return jsonObj;
		} else {			
			throw new Exception(resposta[1]);
		}
	}
	
	
	/*Metodo para limpar o texto json ou xml que foi recebido como resposta
         ele será utlizado pelo metodo GET()*/
	private String toString(InputStream is) throws IOException {
		byte[] bytes = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int lidos;
		while ((lidos = is.read(bytes)) > 0) {
			baos.write(bytes, 0, lidos);
		}
		return new String(baos.toByteArray());
	}


}
