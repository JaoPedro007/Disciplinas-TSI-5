package com.example.longproccess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.LinkedList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinkedList<Integer> numeros;
    ArrayAdapter adapter;

    class Ouvinte extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
                Integer valor = intent.getIntExtra("numero", 0);
                numeros.add(valor);
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numeros = new LinkedList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, numeros);
        ListView lista = (ListView) findViewById(R.id.lista);
        lista.setAdapter(adapter);
        registerReceiver(new Ouvinte(), new IntentFilter("NUMERO_GERADO"));
    }

    public void iniciar(View view){
        Intent it = new Intent(this, NumberService.class);
        it.putExtra("quantidade", 20);
        startService( it );
    }
}