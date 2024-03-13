package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Animal> cad;

    RadioGroup grupoRadio;
    EditText edNome, edRaca, edIdade, edNomeTutor, edTelefoneTutor;
    ListView listaAnimais;

    ArrayAdapter<Animal> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grupoRadio = (RadioGroup) findViewById(R.id.grpTipo);
        edNome = (EditText) findViewById(R.id.edNome);
        edIdade = (EditText) findViewById(R.id.edIdade);
        edRaca = (EditText) findViewById(R.id.edRaca);
        edNomeTutor = (EditText) findViewById(R.id.edNomeTutor);
        edTelefoneTutor = (EditText) findViewById(R.id.edTelefoneTutor);
        listaAnimais = (ListView) findViewById(R.id.listaAnimais);
        cad = new LinkedList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cad);
        listaAnimais.setAdapter(adapter);
    }

    public void confirmar(View v){
        int tipo=0;
        switch (grupoRadio.getCheckedRadioButtonId()){
            case R.id.rbCao:
                tipo=0; break;
            case R.id.rbGato:
                tipo=1; break;
            case R.id.rbPassaro:
                tipo=2;break;
            case R.id.rbReptil:
                tipo=3;break;
        }

        Animal novo = new Animal(tipo,
                Integer.parseInt(edIdade.getText().toString()),
                edNome.getText().toString(), edRaca.getText().toString(),
                edNomeTutor.getText().toString(), edTelefoneTutor.getText().toString());
        cad.add(novo);
        adapter.notifyDataSetChanged();
    }
}