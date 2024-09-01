package com.example.testeroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.InvalidationTracker;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Contatos extends AppCompatActivity {
    public static SimpleDateFormat dtFormater = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat fmtDiaMes = new SimpleDateFormat("dd/MM");
    Categoria categoria;
    ArrayAdapter<Contato> adapter;
    ArrayList<Contato> contatos;

    Banco bd;
    ContatoDAO dao;

    ListView lista;
    EditText edNome, edTelefone, edEmail;
    TextView txtCategoria;
    TextView txtDataNascimento;

    Date dataNascimento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);
        categoria = (Categoria) getIntent().getSerializableExtra("Categoria");
        lista = (ListView) findViewById(R.id.lista_contatos);
        edNome = (EditText) findViewById(R.id.ed_nome);
        edTelefone = (EditText) findViewById(R.id.ed_fone);
        edEmail = (EditText) findViewById(R.id.ed_email);
        txtCategoria = (TextView) findViewById(R.id.txt_categoria);
        txtDataNascimento = (TextView) findViewById(R.id.txt_dt_nascimento);
        txtCategoria.setText(categoria.getNome());
        contatos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,
                contatos);
        lista.setAdapter( adapter );
    }

    @Override
    public void onStart() {
        super.onStart();
        bd = Room.databaseBuilder(getApplicationContext(), Banco.class, "banco_exemplo").build();
        dao = bd.getContatoDAO();
        dao.buscarPorCategoria( categoria.getId() ).observeForever( new ObservadorContato() );
    }

    public void onStop() {
        bd.close();
        super.onStop();
    }

    public void lerData(View view) {
        DatePickerDialog dlg = new DatePickerDialog(this);
        dlg.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                dataNascimento = new Date(ano-1900, mes, dia);
                txtDataNascimento.setText(dtFormater.format(dataNascimento));
            }
        });
        dlg.show();
    }

    public void confirmar(View v) {
        Contato c = new Contato();
        c.setNome(edNome.getText().toString());
        c.setTelefone(edTelefone.getText().toString());
        c.setEmail(edEmail.getText().toString());
        c.setId_categoria( categoria.getId() );
        c.setDataNascimento( dataNascimento );
        new Thread() {
            public void run() {
                Looper.prepare();
                dao.inserir(c);
                Looper.loop();
            }
        }.start();
        edNome.setText("");
        edTelefone.setText("");
        edEmail.setText("");
    }

    class ObservadorContato implements Observer<List<Contato>> {
        @Override
        public void onChanged(List<Contato> contatos) {
            Contatos.this.contatos.clear();
            Contatos.this.contatos.addAll( contatos );
            adapter.notifyDataSetChanged();
        }
    }
}