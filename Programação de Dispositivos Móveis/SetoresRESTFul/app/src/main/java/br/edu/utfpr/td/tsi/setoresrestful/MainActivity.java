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

import br.edu.utfpr.td.tsi.setoresrestful.produtos.ProdutoActivity;

public class MainActivity extends AppCompatActivity {

    class SetorServiceObserver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(SetorService.RESULTADO_LISTA_SETORES)){
                Setor[] sets = (Setor[]) intent.getSerializableExtra("setores");
                Setor setor = (Setor) intent.getSerializableExtra("setor");

                setores.clear();
                if(sets != null && sets.length > 0){
                    setores.addAll(Arrays.asList(sets));
                }
                else if (setor != null) {
                    setores.add(setor);
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

        lista.setOnItemLongClickListener((parent, view, position, id) -> {
            setorSelecionado = setores.get(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.deletar)
                    .setMessage(getString(R.string.certeza_deletar_setor) + "\n" + setorSelecionado.getDescricao() + "?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        setores.remove(position);
                        adapter.notifyDataSetChanged();

                        Intent it = new Intent(MainActivity.this, SetorService.class);
                        it.setAction(SetorService.ACTION_DELETAR);
                        it.putExtra("setor", setorSelecionado);
                        startService(it);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
            return true;
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

    public void listarSetorPorId(View v) {
        EditText editTextId = findViewById(R.id.txt_id_setor);
        String idText = editTextId.getText().toString();
        if (!idText.isEmpty()) {
            try {
                int setorId = Integer.parseInt(idText);

                Intent it = new Intent(this, SetorService.class);
                it.setAction(SetorService.ACTION_LISTAR_SETOR);
                it.putExtra("setor_id", setorId);
                startService(it);
            } catch (NumberFormatException e) {
                alerta(getString(R.string.erro), getString(R.string.id_produto_invalido));
            }
        } else {
            buscarSetores();
        }
    }

    public void confirmar(View view){
        Setor setor;
        Intent it = new Intent(this, SetorService.class);

        if(!camposValidos()){
            alerta(getString(R.string.erro), getString(R.string.preencha_todos_campos));
            return;
        }
        if(setorSelecionado != null && editando){
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
        if(editando) {
            alerta(getString(R.string.edicao), getString(R.string.o_setor) + ": " + setor.getDescricao() + " " + getString(R.string.editado_sucesso));
            editando=false;
        }
        else
            alerta(getString(R.string.cadastro), getString(R.string.o_setor) + ": " + setor.getDescricao() + " " + getString(R.string.cadastrado_sucesso));

        limparCampos();
        buscarSetores();

    }

    private void alerta(String title, String message){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void limparCampos(){
        edDescricao.setText("");
        edMargem.setText("");
    }

    private boolean camposValidos(){
        return !(edDescricao.getText().toString().trim().isEmpty()
                || edMargem.getText().toString().trim().isEmpty());
    }
}