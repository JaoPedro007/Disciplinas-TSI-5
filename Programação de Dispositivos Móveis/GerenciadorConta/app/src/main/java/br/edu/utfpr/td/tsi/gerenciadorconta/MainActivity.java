package br.edu.utfpr.td.tsi.gerenciadorconta;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    int selecionado = -1;

    class CategoriaAdapter extends ArrayAdapter<Categoria>{
        public CategoriaAdapter(){
            super(MainActivity.this, android.R.layout.simple_list_item_1, cadastro);
        }

        @Override
        public View getView(int position, View reciclada, ViewGroup grupo){
            if(reciclada == null){
                reciclada = getLayoutInflater().inflate(
                        R.layout.item_lista, null);
            }
            Categoria categoria = cadastro.get(position);

            String total="";
            List<Conta> contas = categoria.getContas();
            for (Conta conta : contas) {
                total = String.valueOf(conta.getValorConta());
                total += conta.getValorConta();

            }


            ((TextView) reciclada.findViewById(R.id.item_descricao_categoria))
                    .setText(categoria.getDescricao());
            ((TextView) reciclada.findViewById(R.id.item_valor_contas))
                    .setText(total);
            if (position == selecionado){
                reciclada.setBackgroundColor(Color.LTGRAY);
            }else{
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
        if(savedInstanceState != null){
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
                }catch (Exception ex){
                }
                return false;
            }
        });
    }
    public void onSaveInstanceState(Bundle dados){
        super.onSaveInstanceState(dados);
        dados.putSerializable("LISTA_CATEGORIAS", cadastro);
        dados.putInt("SELECIONADO", selecionado);
    }

    public void adicionar(View view){
        Categoria novaCategoria = new Categoria(
                descricaoCategoria.getText().toString());
        cadastro.add(novaCategoria);
        adapter.notifyDataSetChanged();
        descricaoCategoria.setText("");

    }
    @Override
    public void onActivityResult(int requisicao, int resposta, Intent dados){
        super.onActivityResult(requisicao, resposta, dados);
        if(requisicao == 1234 && resposta == RESULT_OK){
            Conta conta = (Conta) dados.getSerializableExtra("conta");
            if(selecionado >=0 && selecionado < cadastro.size()){
                Categoria categoria = cadastro.get(selecionado);
                categoria.adicionarConta(conta);
                adapter.notifyDataSetChanged();
            }

        }

    }


}