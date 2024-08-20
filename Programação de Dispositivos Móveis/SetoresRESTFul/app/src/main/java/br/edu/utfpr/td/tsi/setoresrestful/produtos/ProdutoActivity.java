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


import androidx.appcompat.app.AlertDialog;
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
    Boolean editando = false;
    Produto produtoSelecionado;

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

        lista.setOnItemClickListener((parent, view, position, id) -> {
            produtoSelecionado = produtos.get(position);
            edDescricao.setText(produtoSelecionado.getDescricao());
            estoque.setText(String.valueOf(produtoSelecionado.getEstoque()));
            preco.setText(String.valueOf(produtoSelecionado.getPreco()));
            setorSelecionado = produtoSelecionado.getSetor();
            spinnerSetor.setSelection(((ArrayAdapter<Setor>) spinnerSetor.getAdapter()).getPosition(setorSelecionado));
            editando=true;
        });

        lista.setOnItemLongClickListener((parent, view, position, id) -> {
            produtoSelecionado = produtos.get(position);
            new AlertDialog.Builder(ProdutoActivity.this)
                    .setTitle("Deletar Produto")
                    .setMessage("Tem certeza que deseja deletar o produto " + produtoSelecionado.getDescricao() + "?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        produtos.remove(position);
                        adapter.notifyDataSetChanged();

                        Intent it = new Intent(ProdutoActivity.this, ProdutoService.class);
                        it.setAction(ProdutoService.ACTION_DELETAR);
                        it.putExtra("produto", produtoSelecionado);
                        startService(it);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            return true;
        });

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
        Produto produto;
        Intent it = new Intent(this, ProdutoService.class);

        if(produtoSelecionado != null && editando == true){
            produto = produtoSelecionado;
            it.setAction(ProdutoService.ACTION_EDITAR);
        }else {
            produto = new Produto();
            it.setAction(ProdutoService.ACTION_CADASTRAR);
        }
        produto.setDescricao(edDescricao.getText().toString());
        produto.setEstoque(Float.parseFloat(estoque.getText().toString()));
        produto.setPreco(Double.parseDouble(preco.getText().toString()));

        spinnerSetor = findViewById(R.id.spinner_setor_produto);
        setorSelecionado = (Setor) spinnerSetor.getSelectedItem();
        produto.setSetor(setorSelecionado);

        it.putExtra("produto", produto);
        startService(it);

        limparCampos();
        buscarProdutos();
    }


    private void limparCampos(){
            edDescricao.setText("");
            estoque.setText("");
            preco.setText("");
            spinnerSetor.setSelection(0);
    }

    private void carregarSetores() {
        Intent it = new Intent(this, SetorService.class);
        it.setAction(SetorService.ACTION_LISTAR);
        startService(it);
    }

}