package br.edu.utfpr.td.tsi.gerenciadorconta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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

    class ContaAdapter extends ArrayAdapter<Conta>{
        public ContaAdapter(){
            super(ContaActivity.this, android.R.layout.simple_list_item_1, cadastro);
        }

        @Override
        public View getView(int position, View reciclada, ViewGroup grupo){
            if(reciclada == null){
                reciclada = getLayoutInflater().inflate(
                        R.layout.item_lista_contas, null);
            }
            Conta conta = cadastro.get(position);

            TextView descricaoConta = reciclada.findViewById(R.id.descricao_conta);
            TextView dataValorConta = reciclada.findViewById(R.id.data_valor_conta);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dataFormatada = dateFormat.format(conta.getVencimentoConta());

            descricaoConta.setText(conta.getDescricaoConta());
            dataValorConta.setText(dataFormatada + " - " + conta.getValorConta());

            if (position == selecionado){
                reciclada.setBackgroundColor(Color.LTGRAY);
            }else{
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
        if(savedInstanceState != null){
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

        vencimentoConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


    }
    public void onSaveInstanceState(Bundle dados){
        super.onSaveInstanceState(dados);
        dados.putSerializable("LISTA_CONTAS", cadastro);
        dados.putInt("SELECIONADO", selecionado);
    }
    public void adicionar(View view){
        Conta novaConta = new Conta(
                descricaoConta.getText().toString(),
                Double.parseDouble(valorConta.getText().toString()),
                dataSelecionada,
                categoria);


        categoria.adicionarConta(novaConta);
        cadastro.add(novaConta);
        adapter.notifyDataSetChanged();

        Intent itResult = new Intent();
        itResult.putExtra("conta", novaConta);
        setResult(RESULT_OK, itResult);
        limparCampos();

    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(ContaActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        dataSelecionada = calendar.getTime();
                        vencimentoConta.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void limparCampos(){
        descricaoConta.setText("");
        valorConta.setText("");
        vencimentoConta.setText("");
    }
}
