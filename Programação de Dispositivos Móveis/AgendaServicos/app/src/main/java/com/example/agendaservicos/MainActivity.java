package com.example.agendaservicos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    public void cadastrarServicos(MenuItem mi) {
        Intent it = new Intent(this, TelaServicos.class);
        startActivity(it);
    }

    public void sair(MenuItem mi) {
        finish();
    }

    public void agendamentos(View v) {
        Intent it = new Intent(this, TelaAgendamento.class);
        startActivity(it);
    }
}
