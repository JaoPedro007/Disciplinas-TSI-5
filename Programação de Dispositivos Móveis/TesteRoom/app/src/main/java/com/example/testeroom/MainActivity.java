package com.example.testeroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener {

    EditText edNome;
    ListView lista;
    ArrayAdapter<Categoria> adapter;
    LinkedList<Categoria> categorias;
    Banco banco;
    CategoriaDAO dao;

    static final Migration Migration_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("alter table contato add column data_nascimento bigint");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edNome = (EditText) findViewById(R.id.edNome);
        lista = (ListView) findViewById(R.id.lista);
        categorias = new LinkedList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, categorias);
        lista.setAdapter(adapter);
        lista.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lista.setOnItemLongClickListener( this );
        // Banco de Dados
        banco = Room.databaseBuilder(getApplicationContext(),
                        Banco.class, "banco_exemplo")
                .addMigrations(Migration_2_3)
                .build();
        dao = banco.getCategoriaDAO();
        dao.listar().observeForever( new CategoriaObserver() );
    }

    @Override
    public void onDestroy() {
        banco.close();
        super.onDestroy();
    }

    public void confirmar(View v) {
        String nome = edNome.getText().toString();
        if (nome.trim().isEmpty()) {
            Toast.makeText(this,"Informe o nome da categoria",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Categoria cat = new Categoria(nome.trim());
        new Thread() {
            public void run() {
                Looper.prepare();
                dao.inserir(cat);
                Looper.loop();
            }
        }.start();
    }

    public void remover(View v) {
        if (lista.getCheckedItemPosition() < 0) {
            Toast.makeText(this,"Selecione a categoria a remover.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Categoria cat = categorias.get( lista.getCheckedItemPosition()   );
        new Thread() {
            public void run() {
                Looper.prepare();
                dao.remover(cat);
                Looper.loop();
            }
        }.start();
        lista.clearChoices();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Categoria cat = categorias.get(pos);
        Intent it = new Intent(this, Contatos.class);
        it.putExtra("Categoria", cat);
        startActivity( it );
        return true;
    }

    class CategoriaObserver implements Observer<List<Categoria>> {
        @Override
        public void onChanged(List<Categoria> cats) {
             categorias.clear();
             categorias.addAll( cats );
             adapter.notifyDataSetChanged();
        }
    }
}