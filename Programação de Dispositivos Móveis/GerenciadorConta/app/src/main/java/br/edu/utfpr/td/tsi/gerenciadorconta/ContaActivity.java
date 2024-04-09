package br.edu.utfpr.td.tsi.gerenciadorconta;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ContaActivity extends AppCompatActivity {
    ContaAdapter adapter;
    EditText descricaoConta;
    EditText valorConta;
    EditText vencimentoConta;
    ListView listaContas;
    ArrayList<Conta> cadastro;
    Date dataSelecionada;
    Categoria categoria;
    int selecionado = -1;
    boolean editando = false;
    boolean contaExistente = false;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    class ContaAdapter extends ArrayAdapter<Conta> {
        public ContaAdapter() {
            super(ContaActivity.this, android.R.layout.simple_list_item_1, cadastro);
        }

        @Override
        public View getView(int position, View reciclada, ViewGroup grupo) {
            if (reciclada == null) {
                reciclada = getLayoutInflater().inflate(R.layout.item_lista_contas, null);
            }
            Conta conta = cadastro.get(position);

            TextView descricaoConta = reciclada.findViewById(R.id.descricao_conta);
            TextView dataVencimentoConta = reciclada.findViewById(R.id.data_vencimento_conta);
            TextView valorConta = reciclada.findViewById(R.id.valor_conta);

            String dataFormatada = dateFormat.format(conta.getVencimentoConta());

            descricaoConta.setText(conta.getDescricaoConta());
            dataVencimentoConta.setText(dataFormatada);
            valorConta.setText("R$ " + conta.getValorConta());


            if (position == selecionado) {
                reciclada.setBackgroundColor(Color.LTGRAY);
            } else {
                reciclada.setBackgroundColor(Color.WHITE);
            }
            return reciclada;
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);

        categoria = (Categoria) getIntent().getSerializableExtra("categoria");
        ((TextView) findViewById(R.id.descricaoCategoria)).setText(categoria.getDescricao());

        cadastro = new ArrayList<>();
        cadastro.addAll(categoria.getContas());
        if (savedInstanceState != null) {
            cadastro = (ArrayList<Conta>) savedInstanceState.getSerializable("LISTA_CONTAS");
            selecionado = savedInstanceState.getInt("SELECIONADO", -1);
        }
        descricaoConta = (EditText) findViewById(R.id.descricaoConta);
        valorConta = (EditText) findViewById(R.id.valorConta);
        vencimentoConta = (EditText) findViewById(R.id.vencimentoConta);

        listaContas = (ListView) findViewById(R.id.listaContas);

        adapter = new ContaAdapter();
        listaContas.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listaContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecionado = position;
                adapter.notifyDataSetChanged();
            }
        });
        vencimentoConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_principal, m);
        return true;
    }

    public void onSaveInstanceState(Bundle dados) {
        super.onSaveInstanceState(dados);
        dados.putSerializable("LISTA_CONTAS", cadastro);
        dados.putInt("SELECIONADO", selecionado);
    }

    public void confirmar(MenuItem mi) {
        confirmar((View) null);
    }

    public void confirmar(View view) {
        String descricao = descricaoConta.getText().toString();
        String valorString = valorConta.getText().toString();

        if (editando) {
            try {
                cadastro.get(selecionado).setDescricaoConta(descricao);
                cadastro.get(selecionado).setValorConta(Double.parseDouble(valorString));
                cadastro.get(selecionado).setVencimentoConta(dateFormat.parse(vencimentoConta.getText().toString()));
            } catch (ParseException e) {
                Toast.makeText(ContaActivity.this, R.string.erro_parse_numero, Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
            editando = false;
            Toast.makeText(ContaActivity.this, R.string.conta_atualizada, Toast.LENGTH_SHORT).show();
        } else {
            if (descricao.isEmpty() || valorString.isEmpty() || dataSelecionada == null) {
                Toast.makeText(ContaActivity.this, R.string.preencha_todos_campos, Toast.LENGTH_LONG).show();

            } else if (contaExistente(descricao)) {
                Toast.makeText(ContaActivity.this, R.string.conta_existente, Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    Double valor = Double.parseDouble(valorString);
                    Conta novaConta = new Conta(descricao, valor, dataSelecionada, categoria);
                    cadastro.add(novaConta);
                    adapter.notifyDataSetChanged();
                } catch (NumberFormatException e) {
                    Toast.makeText(ContaActivity.this, R.string.erro_parse_numero, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }


        Intent itResult = new Intent();
        itResult.putExtra("lista_contas", cadastro);

        setResult(RESULT_OK, itResult);

        limparCampos();
    }

    public void editar(MenuItem mi) {
        editar((View) null);
    }

    public void editar(View view) {
        if (selecionado != -1) {
            descricaoConta.setText(cadastro.get(selecionado).getDescricaoConta());
            valorConta.setText(String.valueOf(cadastro.get(selecionado).getValorConta()));
            vencimentoConta.setText(dateFormat.format(cadastro.get(selecionado).getVencimentoConta()));

            editando = true;
        } else {
            Toast.makeText(ContaActivity.this, R.string.nenhuma_conta_selecionada_editar, Toast.LENGTH_LONG).show();
        }
    }

    public void remover(MenuItem mi) {
        remover((View) null);
    }

    public void remover(View view) {
        if (selecionado != -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ContaActivity.this);
            builder.setTitle("Confirmar Remoção");
            builder.setMessage("Tem certeza de que deseja remover esta conta?");
            builder.setPositiveButton("Sim", (dialog, which) -> {
                cadastro.remove(selecionado);
                adapter.notifyDataSetChanged();
                limparCampos();
                selecionado = -1;
                Toast.makeText(ContaActivity.this, R.string.conta_removida, Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Cancelar", null);
            builder.show();
        } else {
            Toast.makeText(ContaActivity.this, R.string.nenhuma_conta_selecionada_remover, Toast.LENGTH_LONG).show();
        }
    }


    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ContaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                dataSelecionada = calendar.getTime();
                vencimentoConta.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
    }

    private void limparCampos() {
        descricaoConta.setText("");
        valorConta.setText("");
        vencimentoConta.setText("");
    }

    private boolean contaExistente(String descricao) {
        for (Conta conta : cadastro) {
            if (conta.getDescricaoConta().equals(descricao)) {
                return true;
            }
        }
        return false;
    }

}
