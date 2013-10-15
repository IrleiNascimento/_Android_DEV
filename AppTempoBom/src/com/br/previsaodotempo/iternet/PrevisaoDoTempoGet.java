package com.br.previsaodotempo.iternet;

// Todas as classes que serão utilizadas para auxiliar o processo
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.br.previsaodotempo.R;
import com.br.previsaodotempo.Dao.Tempo;
import com.br.previsaodotempo.converte.TempoDaoConverte;
/**
 *
 * @author Irlei
 */                                  
public class PrevisaoDoTempoGet extends AsyncTask<Void, String, Void> {

	private String url; // endere�o da api com a cidade escolhida
	private Context context; // referancia a tela atual que exibiráos dados
	private LinearLayout painel;// onde serã mostrado a prvis�o
	private ProgressDialog progress;// animação de conex�o
	private JSONObject jsonObject;// recebe o aquivo json da api

	private JSONArray tempo; // guarda todas as informa��s do n� "weather"
	private JSONArray tempoAtual;// guarda todas as informa��s do n�
									// "current_condition"
	private JSONArray infoRegiao;// guarda todas as informa��s do n� "request"

	// construtor que permite criar uma chamada de consuta em outras classes
	public PrevisaoDoTempoGet(String url, Context context, LinearLayout layout) {
		this.url = url;
		this.painel = layout;
		this.context = context;
		progress = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		// animaçao exibida enquanto conecta-se ao servi�o
		progress = new ProgressDialog(this.context);
		progress.setMessage("Conectando...");
		progress.show();
	}

	/* atualiza a caixa de dialogo enquanto processa os dados */
	@Override
	protected void onProgressUpdate(String... values) {
		progress.setMessage(values[0]);
	}

	/* executa a tarefa em segundo plano sem travar o dispositivo */
	@Override
	protected Void doInBackground(Void... params) {
		consulta();
		return null;
	}

	/* exibe o resultado da consulta */
	@Override
	protected void onPostExecute(Void result) {
		progress.dismiss();
		getResultado();
	}

	public void consulta() {
		ClienteTempo cli = new ClienteTempo();

		// tente se houver falha o processo ser� abosrado e uma mensagem de
		// servi�o indispon�vel ssrá xibida
		try {
			// recebendo o resultado do serviço da api
			jsonObject = cli.getRequestJSONObject(this.url);

			// pegando a raiz do json
			JSONObject obj = new JSONObject(jsonObject.getString("data"));

			// pegando o primeiro filho que possui as informa��es do tempo
			tempo = obj.getJSONArray("weather");

			// pegando o segundo filho que possui as informa��es da regi�o
			// (cidade , pais)
			infoRegiao = obj.getJSONArray("request");

			// pegando o terceiro filho que possui as informa��es do tempo atual
			// (agora)
			tempoAtual = obj.getJSONArray("current_condition");

		} catch (Exception e) {
			e.printStackTrace(); // mostra o motivo da falha caso aconte�a
		}
	}

	public boolean validar() {
		// metodo respons�vel por exibir na tela a mensagem de erro
		if (jsonObject == null) {
			Toast.makeText(context, "Serviço Indispon�vel...",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	private void getResultado() {

		if (validar()) {// se o Jsonobject foi criado ent�o prossiga

			/*
			 * se existir informa��es de tempo no JsonArray ent�o prossiga
			 */
			if (tempo != null) {
				/*
				 * em um JsonArray existe varios Previsoes de tempo para seram
				 * exibidas ser� criado um Tempo para cada item encontrado no
				 * JsonArray e adicionado em uma lista de Tempo
				 */
				List<Tempo> tmp = TempoDaoConverte.getTempo(tempo);

				if (tmp != null) {// se a lista de tempo foi realmente criada,
									// entao prossiga

					Tempo tempoResultado = tmp.get(0);/*
													 * para o exemplo, estamos
													 * pegando apenas o primeiro
													 * da lista
													 */
					tempoResultado.setRegiao(TempoDaoConverte
							.getRegiao(infoRegiao));
					// pegando o arquivo xml para preparar a tela
					LayoutInflater inflater = (LayoutInflater) this.context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					/*
					 * depois de pegar o xml passamos o contrele dela para uma
					 * view
					 */
					View view = inflater.inflate(R.layout.exibir_tempo, null);

					/*
					 * pegamos agora todos os componetes da tela que ser�
					 * respons�vel por mostra ainforma��o
					 */
					TextView regiao = (TextView) view
							.findViewById(R.id.regiaotxt);
					TextView data = (TextView) view
							.findViewById(R.id.valorDatatxt);
					TextView resumoTempo = (TextView) view
							.findViewById(R.id.resumoTempotxt);
					TextView maxima = (TextView) view
							.findViewById(R.id.maximaGraustxt);
					TextView minima = (TextView) view
							.findViewById(R.id.minimaGraustxt);
					TextView ventodirecao = (TextView) view
							.findViewById(R.id.direcaoVentotxt);
					TextView ventoVelocidade = (TextView) view
							.findViewById(R.id.velocidadeVentotxt);
					ImageView imagem = (ImageView) view
							.findViewById(R.id.imageTempo);

					/*
					 * Toda a informa��o sobre a previs�o foi retirada do
					 * arquivo json e colocado em um objeto da classe Tempo
					 */
					regiao.setText(tempoResultado.getRegiao().toString());
					data.setText(tempoResultado.getData().toString());

					maxima.setText(String.valueOf(tempoResultado.getTempMaxC()
							+ "%"));
					minima.setText(String.valueOf(tempoResultado.getTempMinC()
							+ "%"));
					ventoVelocidade.setText(String.valueOf(tempoResultado
							.getWindspeedKmph() + "km/h"));
					ventodirecao.setText(tempoResultado.getWinddirection()
							.toString());
					resumoTempo.setText(tempoResultado.getDescricaoTempo()
							.toString());

					// pegando a imagem pela url fornecida pela api
					try {
						InputStream is = (InputStream) new URL(
								tempoResultado.getUrlImagem()).getContent();
						Drawable img = Drawable
								.createFromStream(is, "img_temp");
						imagem.setImageDrawable(img);
					} catch (Exception e) {
					}

					// se alguma consulta ja foi feita, ent�o precisamos limpar
					// as informa��es
					if (painel != null)
						painel.removeAllViews();

					// por fim adicionando as novas informa��os da consulta
					this.painel.addView(view);

				}
			}
		}
	}

}
