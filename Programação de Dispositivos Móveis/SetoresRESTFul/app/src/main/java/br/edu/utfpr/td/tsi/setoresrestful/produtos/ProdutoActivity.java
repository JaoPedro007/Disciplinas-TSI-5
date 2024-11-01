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
            String action = intent.getAction();

            if (ProdutoService.RESULTADO_LISTA_PRODUTOS.equals(action)) {
                Produto[] prods = (Produto[]) intent.getSerializableExtra("produtos");
                Produto produto = (Produto) intent.getSerializableExtra("produto");

                produtos.clear();
                if (prods != null && prods.length > 0) {
                    produtos.addAll(Arrays.asList(prods));
                }
                else if (produto != null) {
                    produtos.add(produto);
                }
                adapter.notifyDataSetChanged();
            }
            else if (SetorService.RESULTADO_LISTA_SETORES.equals(action)) {
                setores = (Setor[]) intent.getSerializableExtra("setores");
                if (setores != null) {
                    Setor setorDefault = new Setor("");

                    List<Setor> listaSetores = new ArrayList<>(Arrays.asList(setores));
                    listaSetores.add(0, setorDefault);

                    ArrayAdapter<Setor> setorAdapter = new ArrayAdapter<>(ProdutoActivity.this,
                            android.R.layout.simple_spinner_item, listaSetores);
                    setorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    Spinner spinnerSetor = findViewById(R.id.spinner_setor_produto);
                    spinnerSetor.setAdapter(setorAdapter);

                    if (produtoSelecionado != null && produtoSelecionado.getSetor() != null) {
                        setorSelecionado = produtoSelecionado.getSetor();
                        spinnerSetor.setSelection(setorAdapter.getPosition(setorSelecionado));
                    } else {
                        spinnerSetor.setSelection(setorAdapter.getPosition(setorDefault));
                    }
                }
            }
        }
    }


    Setor[] setores;
    LinkedList<Produto> produtos;
    EditText edDescricao, estoque, preco;
    Spinner spinnerSetor;
    ListView lista;
    ArrayAdapter<Produto> adapter;
    Boolean editando = false;
    Produto produtoSelecionado;
    Setor setorSelecionado;

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
            spinnerSetor.setSelection(((ArrayAdapter<Setor>) spinnerSetor.getAdapter()).getPosition(produtoSelecionado.getSetor()));
            editando=true;
        });

        lista.setOnItemLongClickListener((parent, view, position, id) -> {
            produtoSelecionado = produtos.get(position);
            new AlertDialog.Builder(ProdutoActivity.this)
                    .setTitle(R.string.deletar)
                    .setMessage(getString(R.string.certeza_deletar_produto) + produtoSelecionado.getDescricao() + "?")
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

    public void listarProdutoPorId(View v) {
        EditText editTextId = findViewById(R.id.txt_id_produto);
        String idText = editTextId.getText().toString();
        if (!idText.isEmpty()) {
            try {
                int produtoId = Integer.parseInt(idText);

                Intent it = new Intent(this, ProdutoService.class);
                it.setAction(ProdutoService.ACTION_LISTAR_PRODUTO);
                it.putExtra("produto_id", produtoId);
                startService(it);
            } catch (NumberFormatException e) {
                alerta(getString(R.string.erro), getString(R.string.id_produto_invalido));
            }
        } else {
            buscarProdutos();
        }
    }

    public void confirmar(View view) {
        Produto produto;
        Intent it = new Intent(this, ProdutoService.class);

        if (!camposValidos()) {
            alerta(getString(R.string.erro), getString(R.string.preencha_todos_campos));
            return;
        }

        if (produtoSelecionado != null && editando) {
            produto = produtoSelecionado;
            it.setAction(ProdutoService.ACTION_EDITAR);
        } else {
            produto = new Produto();
            it.setAction(ProdutoService.ACTION_CADASTRAR);
        }

        produto.setDescricao(edDescricao.getText().toString());
        produto.setEstoque(Float.parseFloat(estoque.getText().toString()));
        produto.setPreco(Double.parseDouble(preco.getText().toString()));

        Setor setor = (Setor) spinnerSetor.getSelectedItem();

        if (!setor.toString().trim().isEmpty()) {
            produto.setSetor(setor);
        }

        it.putExtra("produto", produto);
        startService(it);

        if(editando) {
            alerta(getString(R.string.edicao), getString(R.string.o_produto) + ": " + produto.getDescricao() + " " + getString(R.string.editado_sucesso));
            editando=false;
        }
        else
            alerta(getString(R.string.cadastro), getString(R.string.o_produto) + ": " + produto.getDescricao() + " " + getString(R.string.cadastrado_sucesso));

        limparCampos();
        buscarProdutos();
    }

    private void alerta(String title, String message){
        new AlertDialog.Builder(ProdutoActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void limparCampos(){
            edDescricao.setText("");
            estoque.setText("");
            preco.setText("");
            spinnerSetor.setSelection(0);
    }

    private boolean camposValidos(){
        return !(edDescricao.getText().toString().trim().isEmpty()
                || estoque.getText().toString().trim().isEmpty()
                || preco.getText().toString().trim().isEmpty());
    }

    private void carregarSetores() {
        Intent it = new Intent(this, SetorService.class);
        it.setAction(SetorService.ACTION_LISTAR);
        startService(it);
    }

}