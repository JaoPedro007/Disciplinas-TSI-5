package com.example.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    class AnimalAdapter extends ArrayAdapter<Animal> {

        public AnimalAdapter() {
            super(MainActivity.this, android.R.layout.simple_list_item_1, cad );
        }

        @Override
        public View getView(int posicao, View reciclada, ViewGroup grupo ) {
            if (reciclada == null) {
                reciclada = getLayoutInflater().inflate(
                        R.layout.item_lista, null );
            }
            ImageView imagem = (ImageView) reciclada.findViewById(R.id.item_imagem);
            Animal animal = cad.get( posicao );
            switch (animal.getTipo()) {
                case Animal.CAO : imagem.setImageResource(R.drawable.cao); break;
                case Animal.GATO: imagem.setImageResource(R.drawable.gato); break;
                case Animal.REPTIL: imagem.setImageResource(R.drawable.reptil); break;
                case Animal.PASSARO: imagem.setImageResource(R.drawable.passaro); break;
                case Animal.PEIXE : imagem.setImageResource(R.drawable.peixe); break;
            }
            ((TextView) reciclada.findViewById(R.id.item_nome))
                    .setText(animal.getNome() +
                            " (" + animal.getAtendimentos().size() + ")");
            ((TextView) reciclada.findViewById(R.id.item_raca))
                    .setText(animal.getRaca());
            ((TextView) reciclada.findViewById(R.id.item_tutor))
                    .setText(animal.getNomeDono()+" - "+animal.getTelefone());
            if (posicao == selecionado) {
                reciclada.setBackgroundColor(Color.LTGRAY);
            } else {
                reciclada.setBackgroundColor(Color.WHITE);
            }
            return reciclada;
        }

    }

    LinkedList<Animal> cad;

    RadioGroup grupoRadio;
    EditText edNome, edRaca, edIdade, edNomeTutor, edFone;

    ListView listaAnimais;
    AnimalAdapter adapter;
    int selecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grupoRadio = (RadioGroup) findViewById(R.id.grpTipo);
        edNome = (EditText) findViewById(R.id.edNome);
        edRaca = (EditText) findViewById(R.id.edRaca);
        edIdade = (EditText) findViewById(R.id.edIdade);
        edNomeTutor = (EditText) findViewById(R.id.edNomeTutor);
        edFone = (EditText) findViewById(R.id.edFone);
        listaAnimais = (ListView) findViewById(R.id.listaAnimais);
        cad = new LinkedList<>();
        if (savedInstanceState != null) {
            cad = (LinkedList<Animal>) savedInstanceState.getSerializable("LISTA_ANIMAIS");
            selecionado = savedInstanceState.getInt("SELECIONADO", -1);
        }
        adapter = new AnimalAdapter();
        listaAnimais.setAdapter( adapter );
        listaAnimais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                selecionado = pos;
                adapter.notifyDataSetChanged();
            }
        });
        listaAnimais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                Animal a = cad.get(pos);
                String fone = a.getTelefone();
                if (fone == null || fone.trim().isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Telefone não cadastrado", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Intent it = new Intent(Intent.ACTION_DIAL);
                        it.setData( Uri.parse("tel:" + fone) );
                        startActivity(it);
                    } catch(Exception ex) {
                    }
                }
                return false;
            }
        });
    }

    public void confirmar(View v) {
        int tipo = 0;
        if ( grupoRadio.getCheckedRadioButtonId() == R.id.rbCao ) tipo = 0;
        if ( grupoRadio.getCheckedRadioButtonId() == R.id.rbGato) tipo = 1;
        if ( grupoRadio.getCheckedRadioButtonId() == R.id.rbPassaro) tipo = 2;
        if ( grupoRadio.getCheckedRadioButtonId() == R.id.rbReptil) tipo = 3;
        Animal novo = new Animal(tipo,
                Integer.parseInt(edIdade.getText().toString()),
                edNome.getText().toString(), edRaca.getText().toString(),
                edNomeTutor.getText().toString(), edFone.getText().toString());
        cad.add(novo);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle dados) {
        super.onSaveInstanceState(dados);
        dados.putSerializable("LISTA_ANIMAIS", cad);
        dados.putInt("SELECIONADO", selecionado);
    }

    public void atender(View v) {
        if (selecionado < 0) {
            Toast.makeText(this, "Selecione o pet a atender",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Animal a = cad.get( selecionado );
        Intent it = new Intent(this, AtendimentoActivity.class);
        it.putExtra("nome", a.getNome());
        startActivityForResult(it, 123);
    }

    @Override
    public void onActivityResult(int requisicao, int resposta, Intent dados) {
        super.onActivityResult(requisicao, resposta, dados);
        if (requisicao == 123 && resposta == RESULT_OK) {
            Atendimento at = (Atendimento) dados.getSerializableExtra("atendimento");
            Animal a = cad.get(selecionado);
            a.adicionarAtendimento(at);
            adapter.notifyDataSetChanged();
        }
    }
}
