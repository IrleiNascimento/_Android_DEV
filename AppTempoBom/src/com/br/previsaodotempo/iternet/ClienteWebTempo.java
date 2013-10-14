package com.br.previsaodotempo.iternet;
/**
 *
 * @author Irlei
 */
public class ClienteWebTempo {

	public static final String APLICATION_JSON = "application/json"; // para soliciar resposta no formato json
	public static final String APLICATION_XML = "application/xml";// para soliciar resposta no formato xml
	public static final int HTTP_TIMEOUT = 30 * 1000; // Tempo de espera espara para conexao abortar caso o serviço esteja indisponível
	public static final String STATUS_HTTP = "200"; // para verificação de sucesso da conexão
	public static String URL_SERVICO = "http://api.worldweatheronline.com/free/v1/weather.ashx?"; // url base da api
	public static String KEY = "bu67tknqweqwt488djvbnr8r"; // chave de acesso da api

        /*metodo responsavel por montar a url  de acordo com a cidade escolhida e a quantidade de dias para previsão  */
	public static String getUrl(String cidade, int qtddias,String formatoDados) {
	return URL_SERVICO+"key="+KEY+"&q="+cidade+"&um_of_days="+qtddias+"&format="+formatoDados;
}
}
