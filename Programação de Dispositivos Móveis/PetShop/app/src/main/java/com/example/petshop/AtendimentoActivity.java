package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AtendimentoActivity extends AppCompatActivity {

    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendimento);
        Intent origem = getIntent();
        String nome = origem.getStringExtra("nome");
        ((TextView) findViewById(R.id.txt_nome_animal)).setText( nome );
        ((TextView) findViewById(R.id.txt_data)).setText(sdf.format(new Date()));
    }

    public void registrar(View v) {
        String nomeAtendente = ((EditText) findViewById(R.id.ed_nome_profissional))
                .getText().toString();
        String registro = ((EditText) findViewById(R.id.ed_registro))
                .getText().toString();
        if (nomeAtendente.trim().isEmpty() || registro.trim().isEmpty()) {
            Toast.makeText(this,"Informe todos os dados", Toast.LENGTH_SHORT).show();
        } else {
            Atendimento at = new Atendimento();
            at.setDataHora( new Date() );
            at.setNomeVeterinario( nomeAtendente );
            at.setRegistro( registro );
            Intent itResult = new Intent();
            itResult.putExtra("atendimento", at);
            setResult(RESULT_OK, itResult);
            finish();
        }
    }
}