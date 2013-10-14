package com.br.previsaodotempo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.br.previsaodotempo.iternet.ClienteWebTempo;
import com.br.previsaodotempo.iternet.PrevisaoDoTempoGet;
/**
 *
 * @author Irlei
 */
public class TelaInicial extends Activity {
    /*componetes que representam os componetes de tela botão enviar, Spinner de escolha de cidade e painel de exibição*/

    private Button botaoEnviar;
    private Spinner spinnerCidades;
    private LinearLayout painelDoTempo;
    // cidade que aparecerão no spinner
    private final static String[] cidades = new String[]{"Porto Seguro", "Arraial D'Ajuda",
        "Santa Cruz Cabralia", "Ilheus", "Salvador", "Mucuri", "Nova Viçosa"};

    /* metodo que é chamado quando quando a app é exucutada
     * tudo parte daqui*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tela_inicial);// pega o xml que representa a tela principal
        // pega os componetes da tela e passa o controle deles para o seus representantes na classe
        botaoEnviar = (Button) findViewById(R.id.botaoEnviar);
        spinnerCidades = (Spinner) findViewById(R.id.spinnerCidades);
        painelDoTempo = (LinearLayout) findViewById(R.id.painelDoTempo);

        // adapta a lista de cidade para aparecer no spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, cidades);

        spinnerCidades.setAdapter(adapter);// passa o adptador para o spinner

        //colocando o titulo no spinner
        spinnerCidades.setPrompt("Cidades");

        // metodo de acção para o botão enviar
        botaoEnviar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                previsaoDotempo();// depois do click/toutch será chamado o metodo previsaoDotempo();
            }
        });
    }

    /*cria um menu quando o botão menu for acionado*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tela_inicial, menu);
        return true;
    }

    //metodo que chamará o serviço com a internet
    public void previsaoDotempo() {
        // cidade recebe o nome da que foi escolida no spinner com o espaço entre as palavras trocado por %20 para que a url seja válida
        String cidade = spinnerCidades.getSelectedItem().toString().replaceAll(" ", "%20");
        /*cria um objeto que fará a conexão com o servço da api passando como parametro a cidade 
        aquantidade de dias e o tipo de resposta*/
        PrevisaoDoTempoGet tempo = new PrevisaoDoTempoGet(ClienteWebTempo.getUrl(cidade, 1, "json"), this, painelDoTempo);
        // processo será executado em backgronud(Segundo plano) para não travar a aplicação enquanto espera resposta
        tempo.execute();
    }
}
