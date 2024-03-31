package br.edu.utfpr.td.tsi.gerenciadorconta;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalhesCategoriaActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_categoria);

        String descricao = getIntent().getStringExtra("descricao");

        TextView descricaoCategoria = findViewById(R.id.descricaoCategoria);
        descricaoCategoria.setText(descricao);
    }
}
