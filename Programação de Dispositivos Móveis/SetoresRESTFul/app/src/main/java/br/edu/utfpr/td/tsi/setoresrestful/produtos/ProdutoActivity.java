package br.edu.utfpr.td.tsi.setoresrestful.produtos;

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
import android.widget.Spinner;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.edu.utfpr.td.tsi.setoresrestful.MainActivity;
import br.edu.utfpr.td.tsi.setoresrestful.R;

import br.edu.utfpr.td.tsi.setoresrestful.Setor;
import br.edu.utfpr.td.tsi.setoresrestful.SetorService;


public class ProdutoActivity extends AppCompatActivity {

    class ProdutoServiceObserver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(ProdutoService.RESULTADO_LISTA_PRODUTOS)){
                Produto[] prods = (Produto[]) intent.getSerializableExtra("produtos");
                produtos.clear();
                if(prods != null && prods.length > 0){
                    produtos.addAll(Arrays.asList(prods));
                }
                adapter.notifyDataSetChanged();
            }
            else if (intent.getAction().equals(SetorService.RESULTADO_LISTA_SETORES)) {
                Setor[] setores = (Setor[]) intent.getSerializableExtra("setores");
                if (setores != null) {
                    ArrayAdapter<Setor> setorAdapter = new ArrayAdapter<>(ProdutoActivity.this,
                            android.R.layout.simple_spinner_item, setores);
                    setorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    Spinner spinnerSetor = findViewById(R.id.spinner_setor_produto);
                    spinnerSetor.setAdapter(setorAdapter);
                }
            }
        }
    }

    LinkedList<Produto> produtos;
    EditText edDescricao, estoque, preco;
    Spinner spinnerSetor;
    Setor setorSelecionado;
    ListView lista;
    ArrayAdapter<Produto> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        produtos = new LinkedList<>();
        edDescricao = findViewById(R.id.txt_descr_produto);
        estoque = findViewById(R.id.txt_estoque_produto);
        preco = findViewById(R.id.txt_preco_produto);
        spinnerSetor = findViewById(R.id.spinner_setor_produto);
        lista = findViewById(R.id.lista_produtos);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, produtos);
        lista.setAdapter(adapter);

        ProdutoServiceObserver observer = new ProdutoServiceObserver();
        registerReceiver(observer, new IntentFilter(ProdutoService.RESULTADO_LISTA_PRODUTOS));
        registerReceiver(observer, new IntentFilter(SetorService.RESULTADO_LISTA_SETORES));

        buscarProdutos();
        carregarSetores();
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

    public void buscarProdutos(){
        Intent it = new Intent(this, ProdutoService.class);
        it.setAction(ProdutoService.ACTION_LISTAR);
        startService(it);
    }

    public void confirmar(View view) {
        Produto produto = new Produto();
        produto.setDescricao(edDescricao.getText().toString());
        produto.setEstoque(Float.parseFloat(estoque.getText().toString()));
        produto.setPreco(Double.parseDouble(preco.getText().toString()));

        spinnerSetor = findViewById(R.id.spinner_setor_produto);
        setorSelecionado = (Setor) spinnerSetor.getSelectedItem();
        produto.setSetor(setorSelecionado);

        Intent it = new Intent(this, ProdutoService.class);
        it.setAction(ProdutoService.ACTION_CADASTRAR);
        it.putExtra("produto", produto);
        startService(it);

        it = new Intent(this, ProdutoService.class);
        it.setAction(ProdutoService.ACTION_LISTAR);
        startService(it);
    }


    public void carregarSetores() {
        Intent it = new Intent(this, SetorService.class);
        it.setAction(SetorService.ACTION_LISTAR);
        startService(it);
    }

}