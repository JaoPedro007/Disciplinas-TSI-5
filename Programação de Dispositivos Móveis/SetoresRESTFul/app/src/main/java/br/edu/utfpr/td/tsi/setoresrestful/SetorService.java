package br.edu.utfpr.td.tsi.setoresrestful;

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

public class SetorService extends IntentService {

    public static final String ACTION_LISTAR    = "br.edu.utfpr.td.tsi.setoresrestful.action.LISTAR";
    public static final String ACTION_CADASTRAR = "br.edu.utfpr.td.tsi.setoresrestful.action.CADASTRAR";
    public static final String RESULTADO_LISTA_SETORES = "br.edu.utfpr.td.tsi.setoresrestful.RESULTADO_LISTA_SETORES";
    static final String URL_WS = "http://argo.td.utfpr.edu.br/clients/ws/setor";
    Gson gson;

    public SetorService() {
        super("SetorService");
        gson = new GsonBuilder().create();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent == null)
            return;
        switch (intent.getAction()){
            case ACTION_CADASTRAR: cadastrar(intent);
            break;
            case ACTION_LISTAR: listar(intent);
        }
    }

    private void cadastrar(Intent intent) {
        try {
            Setor set = (Setor) intent.getSerializableExtra("setor");
            String strSetor = gson.toJson(set);

            URL url = new URL(URL_WS);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type","application/json");
            con.connect();
            PrintWriter writer = new PrintWriter(con.getOutputStream());
            writer.println(strSetor);
            writer.flush();
            if (con.getResponseCode() == 200) {
                Log.d("POST","OK");
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
                Setor[] setores = gson.fromJson(bld.toString(), Setor[].class);

                Arrays.sort(setores, new Comparator<Setor>() {
                    @Override
                    public int compare(Setor s1, Setor s2) {
                        return Long.compare(s1.getId(), s2.getId());
                    }
                });

                Intent it = new Intent(RESULTADO_LISTA_SETORES);
                it.putExtra("setores", setores);
                sendBroadcast(it);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}