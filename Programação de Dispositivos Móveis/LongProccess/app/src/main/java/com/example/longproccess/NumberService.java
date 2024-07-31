package com.example.longproccess;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.Random;

public class NumberService extends IntentService {

    public NumberService() {
        super("NumberService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        int quantidade = intent.getIntExtra("quantidade", 5);
        Random rand = new Random(System.currentTimeMillis());
        while (quantidade > 0){
            int numero = rand.nextInt(1000);
            try {
                Thread.sleep(1000);
            }catch (Throwable t){ }
            Intent reposta = new Intent("NUMERO_GERADO");
            reposta.putExtra("numero", numero);
            sendBroadcast( reposta );
            quantidade--;
        }

    }
}