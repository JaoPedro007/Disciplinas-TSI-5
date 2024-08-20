package br.edu.utfpr.td.tsi.setoresrestful.produtos;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;

import br.edu.utfpr.td.tsi.setoresrestful.Setor;

public class ProdutoService extends IntentService {
    public static final String ACTION_LISTAR    = "br.edu.utfpr.td.tsi.setoresrestful.produtos.action.LISTAR";
    public static final String ACTION_CADASTRAR = "br.edu.utfpr.td.tsi.setoresrestful.produtos.action.CADASTRAR";
    public static final String ACTION_EDITAR = "br.edu.utfpr.td.tsi.setoresrestful.produtos.action.EDITAR";
    public static final String ACTION_DELETAR = "br.edu.utfpr.td.tsi.setoresrestful.produtos.action.DELETAR";
    public static final String RESULTADO_LISTA_PRODUTOS = "br.edu.utfpr.td.tsi.setoresrestful.produtos.RESULTADO_LISTA_SETORES";
    static final String URL_WS = "http://argo.td.utfpr.edu.br/clients/ws/produto";
    Gson gson;
    public ProdutoService() {
        super("ProdutoService");
        gson = new GsonBuilder().create();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent == null)
            return;
        switch (intent.getAction()){
            case ACTION_CADASTRAR:
                cadastrar(intent);
                break;
            case ACTION_LISTAR:
                listar(intent);
                break;
            case ACTION_EDITAR:
                editar(intent);
                break;
            case ACTION_DELETAR: deletar(intent);
                break;
        }
    }


    private void cadastrar(Intent intent) {
        try {
            Produto prod = (Produto) intent.getSerializableExtra("produto");
            String strProduto = gson.toJson(prod);

            URL url = new URL(URL_WS);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type","application/json");
            con.connect();
            PrintWriter writer = new PrintWriter(con.getOutputStream());
            writer.println(strProduto);
            writer.flush();
            if (con.getResponseCode() == 200) {
                Log.d("POST","Produto cadastrado com sucesso.");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    private void listar(Intent intent) {
        try {
            URL url = new URL(URL_WS);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            if (con.getResponseCode() == 200) {
                BufferedReader ent = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                StringBuilder bld = new StringBuilder(1000);
                String linha;
                do {
                    linha = ent.readLine();
                    if (linha != null) {
                        bld.append(linha);
                    }
                } while (linha != null);
                Produto[] produtos = gson.fromJson(bld.toString(), Produto[].class);

                Arrays.sort(produtos, new Comparator<Produto>() {
                    @Override
                    public int compare(Produto p1, Produto p2) {
                        return Long.compare(p1.getId(), p2.getId());
                    }
                });
                Intent it = new Intent(RESULTADO_LISTA_PRODUTOS);
                it.putExtra("produtos", produtos);
                sendBroadcast(it);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editar(Intent intent) {
        try {
            Produto prod = (Produto) intent.getSerializableExtra("produto");
            String strProduto = gson.toJson(prod);

            URL url = new URL(URL_WS + "/" + prod.getId());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("content-type","application/json");
            con.connect();
            PrintWriter writer = new PrintWriter(con.getOutputStream());
            writer.println(strProduto);
            writer.flush();
            if (con.getResponseCode() == 200) {
                Log.d("PUT","Produto atualizado com sucesso.");
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void deletar(Intent intent) {
        try {
            Produto produto = (Produto) intent.getSerializableExtra("produto");

            URL url = new URL(URL_WS + "/" + produto.getId());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("DELETE");
            con.connect();

            if (con.getResponseCode() == 200) {
                Log.d("DELETE", "Produto deletado com sucesso.");
            } else {
                Log.d("DELETE", "Falha ao deletar o produto. Código: " + con.getResponseCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}