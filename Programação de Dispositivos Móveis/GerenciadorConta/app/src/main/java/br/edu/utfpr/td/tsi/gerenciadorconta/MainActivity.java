package br.edu.utfpr.td.tsi.gerenciadorconta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText descricaoCategoria;
    ListView listaCategorias;
    ArrayList<Categoria> cadastro;
    CategoriaAdapter adapter;
    boolean editando = false;
    int selecionado = -1;

    class CategoriaAdapter extends ArrayAdapter<Categoria> {
        public CategoriaAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, cadastro);
        }

        @Override
        public View getView(int position, View reciclada, ViewGroup grupo) {
            if (reciclada == null) {
                reciclada = getLayoutInflater().inflate(
                        R.layout.item_lista, null);
            }
            Categoria categoria = cadastro.get(position);


            ((TextView) reciclada.findViewById(R.id.item_descricao_categoria))
                    .setText(categoria.getDescricao());
            ((TextView) reciclada.findViewById(R.id.item_valor_contas))
                    .setText("R$ " + categoria.getValorTotal());
            ((TextView) reciclada.findViewById(R.id.item_quantidade_contas))
                    .setText(String.valueOf(categoria.getQuantidadeContas()));
            if (position == selecionado) {
                reciclada.setBackgroundColor(Color.LTGRAY);
            } else {
                reciclada.setBackgroundColor(Color.WHITE);
            }
            return reciclada;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descricaoCategoria = (EditText) findViewById(R.id.descricaoCategoria);
        listaCategorias = (ListView) findViewById(R.id.listaCategorias);

        cadastro = new ArrayList<>();
        if (savedInstanceState != null) {
            cadastro = (ArrayList<Categoria>) savedInstanceState.getSerializable("LISTA_CATEGORIAS");
            selecionado = savedInstanceState.getInt("SELECIONADO", -1);
        }
        adapter = new CategoriaAdapter();
        listaCategorias.setAdapter(adapter);
        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selecionado = position;
                adapter.notifyDataSetChanged();
            }
        });
        listaCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Categoria categoria = cadastro.get(position);
                try {
                    Intent it = new Intent(MainActivity.this, ContaActivity.class);
                    it.putExtra("categoria", categoria);
                    startActivityForResult(it, 1234);
                    return true;
                } catch (Exception ex) {
                }
                return false;
            }
        });
    }

    public void onSaveInstanceState(Bundle dados) {
        super.onSaveInstanceState(dados);
        dados.putSerializable("LISTA_CATEGORIAS", cadastro);
        dados.putInt("SELECIONADO", selecionado);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_principal, m);
        return true;
    }

    public void confirmar(MenuItem mi){
        confirmar((View) null);
    }
    public void confirmar(View view) {
        if (editando) {
            String novaDescricao = descricaoCategoria.getText().toString();
            cadastro.get(selecionado).setDescricao(novaDescricao);
            adapter.notifyDataSetChanged();
            editando = false;
            Toast.makeText(MainActivity.this, "Descrição da categoria atualizada com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Categoria novaCategoria = new Categoria(descricaoCategoria.getText().toString());
            if (novaCategoria.getDescricao().isEmpty()) {
                Toast.makeText(MainActivity.this, "Informe uma descrição", Toast.LENGTH_SHORT).show();
            } else {
                cadastro.add(novaCategoria);
                adapter.notifyDataSetChanged();
            }
        }
        descricaoCategoria.setText("");

    }
    public void editar(MenuItem mi){
        editar((View) null);
    }
    public void editar(View view) {
        if (selecionado != -1) {
            descricaoCategoria.setText(cadastro.get(selecionado).getDescricao());
            editando = true;
        } else {
            Toast.makeText(MainActivity.this, "Nenhuma categoria selecionada para editar", Toast.LENGTH_SHORT).show();
        }
    }

    public void remover(MenuItem mi){
        remover((View) null);
    }
    public void remover(View view) {
        if (selecionado != -1) {
            cadastro.remove(cadastro.get(selecionado));
            adapter.notifyDataSetChanged();
            descricaoCategoria.setText("");
            selecionado = -1;
            Toast.makeText(MainActivity.this, "Categoria removida com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Nenhuma categoria selecionada para remover", Toast.LENGTH_SHORT).show();
        }
    }


    public void onActivityResult(int requisicao, int resposta, Intent dados) {
        super.onActivityResult(requisicao, resposta, dados);
        if (requisicao == 1234 && resposta == RESULT_OK) {
            ArrayList<Conta> contasCategoria = (ArrayList<Conta>) dados.getSerializableExtra("lista_contas");

            if (!contasCategoria.isEmpty()) {
                for (Categoria categoria : cadastro) {
                    if (categoria.getDescricao().equals(contasCategoria.get(0).getCategoria().getDescricao())) {
                        categoria.getContas().clear();

                        for (Conta conta : contasCategoria) {
                            categoria.adicionarConta(conta);
                        }
                        double valorTotal = 0.0;
                        for (Conta conta : categoria.getContas()) {
                            valorTotal += conta.getValorConta();
                        }
                        categoria.setValorTotal(valorTotal);

                        break;
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }


}

