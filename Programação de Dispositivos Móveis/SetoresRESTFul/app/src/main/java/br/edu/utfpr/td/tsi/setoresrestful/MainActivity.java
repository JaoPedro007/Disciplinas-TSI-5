package br.edu.utfpr.td.tsi.setoresrestful;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.Arrays;
import java.util.LinkedList;

import br.edu.utfpr.td.tsi.setoresrestful.produtos.Produto;
import br.edu.utfpr.td.tsi.setoresrestful.produtos.ProdutoActivity;
import br.edu.utfpr.td.tsi.setoresrestful.produtos.ProdutoService;

public class MainActivity extends AppCompatActivity {

    class SetorServiceObserver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(SetorService.RESULTADO_LISTA_SETORES)){
                Setor[] sets = (Setor[]) intent.getSerializableExtra("setores");
                setores.clear();
                if(sets != null && sets.length > 0){
                    setores.addAll(Arrays.asList(sets));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    LinkedList<Setor> setores;
    EditText edDescricao, edMargem;
    ListView lista;
    ArrayAdapter<Setor> adapter;
    Setor setorSelecionado;
    Boolean editando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setores = new LinkedList<>();
        edDescricao = (EditText) findViewById(R.id.txt_descr_setor);
        edMargem = (EditText) findViewById(R.id.txt_margem_setor);
        lista = (ListView) findViewById(R.id.lista_setores);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, setores);
        lista.setAdapter( adapter );
        registerReceiver(new SetorServiceObserver(), new IntentFilter(SetorService.RESULTADO_LISTA_SETORES));

        lista.setOnItemClickListener((parent, view, position, id) -> {
            setorSelecionado = setores.get(position);
            edDescricao.setText(setorSelecionado.getDescricao());
            edMargem.setText(String.valueOf(setorSelecionado.getMargem()));
            editando=true;
        });

        buscarSetores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        long id = item.getItemId();
        if(id == R.id.menu_setores){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.menu_produtos) {
            Intent intent = new Intent(this, ProdutoActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    public void buscarSetores(){
        Intent it = new Intent(this, SetorService.class);
        it.setAction(SetorService.ACTION_LISTAR);
        startService(it);
    }

    public void confirmar(View view){
        Setor setor;
        Intent it = new Intent(this, SetorService.class);

        if(setorSelecionado != null && editando == true){
            setor = setorSelecionado;
            it.setAction(SetorService.ACTION_EDITAR);
        }else {
            setor = new Setor();
            it.setAction(SetorService.ACTION_CADASTRAR);
        }
        setor.setDescricao(edDescricao.getText().toString());
        setor.setMargem(Double.parseDouble(edMargem.getText().toString()));

        it.putExtra("setor", setor);
        startService(it);
        buscarSetores();

    }
}