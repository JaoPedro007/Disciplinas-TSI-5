package com.example.agendaservicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agendaservicos.banco.Banco;
import com.example.agendaservicos.dao.ServicoDAO;
import com.example.agendaservicos.modelo.Servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaServicos extends AppCompatActivity {

    Servico servico;
    ArrayAdapter<Servico> adapter;
    ArrayList<Servico> servicos;

    Banco bd;
    ServicoDAO dao;

    ListView lista;
    EditText edNome, edUnidade, edPreco;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_servicos);
        lista = (ListView) findViewById(R.id.lista_tipos_servico);
        edNome = (EditText) findViewById(R.id.ed_nome_servico);
        edUnidade = (EditText) findViewById(R.id.ed_unidade_medida);
        edPreco = (EditText) findViewById(R.id.ed_preco);

        servicos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, servicos);
        lista.setAdapter( adapter );
    }

    @Override
    public void onStart(){
        super.onStart();
        bd = Room.databaseBuilder(getApplicationContext(), Banco.class, "banco_agendamento").build();
        dao = bd.getServicoDAO();

        dao.listar().observe(this, new ObservadorServico());
    }

    public void onStop() {
        bd.close();
        super.onStop();
    }

    public void confirmar(View v) {
        Servico s = new Servico();
        s.setDescricao(edNome.getText().toString());
        s.setUnidadeMedida(edUnidade.getText().toString());
        s.setValor(Integer.parseInt(edPreco.getText().toString()));
        new Thread() {
            public void run() {
                Looper.prepare();
                dao.inserir(s);
                Looper.loop();
            }
        }.start();
        edNome.setText("");
        edUnidade.setText("");
        edPreco.setText("");
    }


    class ObservadorServico implements Observer<List<Servico>> {
        @Override
        public void onChanged(List<Servico> servicos) {
            TelaServicos.this.servicos.clear();
            TelaServicos.this.servicos.addAll( servicos );
            adapter.notifyDataSetChanged();
        }
    }

}