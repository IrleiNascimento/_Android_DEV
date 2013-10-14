package com.br.previsaodotempo.Dao;
/**
 *
 * @author Irlei
 */

/*Classe que representa o "weather" (tempo) será responsavel por permitir a manipulação dos dados que vem do json em nossas calsses  */
public class Tempo {

    //declaração de variáveis com o mesmo nome dos nós que se econtra no json ou xml
	private String data;
	private double precipMM;
	private int tempMaxC; // maxima em graus celsius
	private int tempMaxF; // maxima em graus farinhet
	private int tempMinC; // minima em graus celsius
	private int tempMinF; // minima em graus farinhet
	private int windspeedMiles; // velocidade do vento em milhas
	private int windspeedKmph; // velocidade do vento em km/h
	private String winddirection; // direção do vento
	private float winddirDegree; //
	private int id;
	private String urlImagem;//url da imagem fornecida pela api
	private String regiao;// local de onde partiu a pesquisa
	private String descricaoTempo; // resumo sobre o tempo 
        
        
	//contrutor completo : todos os seus atributos ja serão criados 
	public Tempo(int id, String data, double precipMM,int tempMaxC, int tempMaxF, int tempMinC,
			int tempMinF, int windspeedMiles, int windspeedKmph,
			String winddirection, float winddirDegree, String urlImagem) {
            
		this.data = data;
		this.precipMM=precipMM;
		this.tempMaxC = tempMaxC;
		this.tempMaxF = tempMaxF;
		this.tempMinC = tempMinC;
		this.tempMinF = tempMinF;
		this.windspeedMiles = windspeedMiles;
		this.windspeedKmph = windspeedKmph;
		this.winddirection = winddirection;
		this.winddirDegree = winddirDegree;
		this.id = id;
		this.urlImagem = urlImagem;
	}

        //contrutor padão : o obejto é criado vazio e terá seus atrubutos adicionados de acordo com a necessidade
	public Tempo() {} 

        
        /*Todos os metodos daqui abaixo são responsáveis por inserir, alterar , e consultar os atrubutos de um objeto do tipo Tempo */
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getPrecipMM() {
		return precipMM;
	}

	public void setPrecipMM(double precipMM) {
		this.precipMM = precipMM;
	}

	public int getTempMaxC() {
		return tempMaxC;
	}

	public void setTempMaxC(int tempMaxC) {
		this.tempMaxC = tempMaxC;
	}

	public int getTempMaxF() {
		return tempMaxF;
	}

	public void setTempMaxF(int tempMaxF) {
		this.tempMaxF = tempMaxF;
	}

	public String getRegiao() {
		return regiao;
	}

	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}

	public int getTempMinC() {
		return tempMinC;
	}

	public void setTempMinC(int tempMinC) {
		this.tempMinC = tempMinC;
	}

	public int getTempMinF() {
		return tempMinF;
	}

	public void setTempMinF(int tempMinF) {
		this.tempMinF = tempMinF;
	}

	public int getWindspeedMiles() {
		return windspeedMiles;
	}

	public void setWindspeedMiles(int windspeedMiles) {
		this.windspeedMiles = windspeedMiles;
	}

	public int getWindspeedKmph() {
		return windspeedKmph;
	}

	public void setWindspeedKmph(int windspeedKmph) {
		this.windspeedKmph = windspeedKmph;
	}

	public String getWinddirection() {
		return winddirection;
	}

	public void setWinddirection(String winddirection) {
		this.winddirection = winddirection;
	}

	public float getWinddirDegree() {
		return winddirDegree;
	}

	public void setWinddirDegree(float winddirDegree) {
		this.winddirDegree = winddirDegree;
	}

	public String getDescricaoTempo() {
		return descricaoTempo;
	}

	public void setDescricaoTempo(String descricaoTempo) {
		this.descricaoTempo = descricaoTempo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

        
        /*metodo resposável por imprimir todos os valores dos atributos de um  objeto Tempo*/
	@Override
	public String toString() {
		return "Tempo [data=" + data + ", precipMM=" + precipMM + ", tempMaxC="
				+ tempMaxC + ", tempMaxF=" + tempMaxF + ", tempMinC="
				+ tempMinC + ", tempMinF=" + tempMinF + ", windspeedMiles="
				+ windspeedMiles + ", windspeedKmph=" + windspeedKmph
				+ ", winddirection=" + winddirection + ", winddirDegree="
				+ winddirDegree + ", id=" + id + ", urlImagem=" + urlImagem
				+ ", regiao=" + regiao + ", descricaoTempo=" + descricaoTempo
				+ "]";
	}


}
