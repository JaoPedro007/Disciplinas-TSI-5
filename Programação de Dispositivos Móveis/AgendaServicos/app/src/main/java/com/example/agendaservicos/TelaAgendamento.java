package com.example.agendaservicos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.agendaservicos.banco.Banco;
import com.example.agendaservicos.dao.AgendamentoDAO;
import com.example.agendaservicos.dao.ItemAgendamentoDAO;
import com.example.agendaservicos.dao.ServicoDAO;
import com.example.agendaservicos.modelo.Agendamento;
import com.example.agendaservicos.modelo.ItemAgendamento;
import com.example.agendaservicos.modelo.Servico;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaAgendamento extends AppCompatActivity {
    public static SimpleDateFormat dtFormater = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat fmtDiaMes = new SimpleDateFormat("dd/MM");

    ArrayAdapter<ItemAgendamento> adapter;
    ArrayList<ItemAgendamento> itemAgendamentos;

    Banco bd;
    AgendamentoDAO dao;
    ServicoDAO servicoDao;
    ItemAgendamentoDAO itemDao;

    ListView lista;
    EditText edCliente, edEndereco, edQuantidade;
    TextView txtDataHora, txtUnidade, txtValor;
    Spinner opcoes_servico;
    Servico servicoSelecionado;


    Date dataAgendamento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_agendamento);
        lista = (ListView) findViewById(R.id.lista_servico);
        edCliente = (EditText) findViewById(R.id.ed_nome_cliente);
        edEndereco = (EditText) findViewById(R.id.ed_endereco);
        edQuantidade = (EditText) findViewById(R.id.ed_quantidade);
        txtDataHora = (TextView) findViewById(R.id.data_hora);
        txtUnidade = (TextView) findViewById(R.id.txt_unidade);
        txtValor = (TextView) findViewById(R.id.txt_valor);
        opcoes_servico = (Spinner) findViewById(R.id.opcoes_servico);

        itemAgendamentos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice,
                itemAgendamentos);
        lista.setAdapter( adapter );

        edQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                atualizarValorTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        opcoes_servico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                servicoSelecionado = (Servico) parent.getItemAtPosition(position);
                if (servicoSelecionado != null) {
                    txtUnidade.setText(servicoSelecionado.getUnidadeMedida());
                    txtValor.setText(String.valueOf(servicoSelecionado.getValor()));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

     }

     @Override
    public void onStart(){
        super.onStart();
        bd = Room.databaseBuilder(getApplicationContext(), Banco.class, "banco_agendamento").build();
         dao = bd.getAgendamentoDAO();
         servicoDao = bd.getServicoDAO();
         itemDao = bd.getItemAgendamentoDAO();
         carregarServicos();

     }

     public void onStop(){
        bd.close();
        super.onStop();
     }

    public void lerData(View view) {
        DatePickerDialog dlg = new DatePickerDialog(this);
        dlg.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                dataAgendamento = new Date(ano-1900, mes, dia);
                txtDataHora.setText(dtFormater.format(dataAgendamento));
            }
        });
        dlg.show();
    }

    public void adicionar(View v) {
        if (servicoSelecionado != null && !edQuantidade.getText().toString().isEmpty()) {
            double quantidade = Double.parseDouble(edQuantidade.getText().toString());
            double valorUnitario = servicoSelecionado.getValor();
            double valorTotal = quantidade * valorUnitario;

            ItemAgendamento itemAgendamento = new ItemAgendamento();
            itemAgendamento.setId_servico(servicoSelecionado.getId());
            itemAgendamento.setQuantidade(quantidade);
            itemAgendamento.setValorItem(valorTotal);
            itemAgendamento.setServico(servicoSelecionado);

            itemAgendamentos.add(itemAgendamento);

            adapter.notifyDataSetChanged();
            edQuantidade.setText("");
        }
    }


    public void confirmar(View v) {
        if (dataAgendamento == null) {
            return;
        }

        double valorTotal = 0;
        for (ItemAgendamento item : itemAgendamentos) {
            valorTotal += item.getValorItem();
        }

        Agendamento ag = new Agendamento();
        ag.setNomeCliente(edCliente.getText().toString());
        ag.setEndereco(edEndereco.getText().toString());
        ag.setDataHora(dataAgendamento);
        ag.setValorTotal(valorTotal);

        new Thread() {
            public void run() {
                Looper.prepare();
                long idAgendamento = dao.inserir(ag);

                for (ItemAgendamento itemAgendamento : itemAgendamentos) {
                    itemAgendamento.setId_agendamento(idAgendamento);
                    itemDao.inserir(itemAgendamento);
                }

                Looper.loop();
            }
        }.start();

        limparCampos();
    }


    private void carregarServicos() {
        servicoDao.listar().observe(this, new Observer<List<Servico>>() {
            @Override
            public void onChanged(List<Servico> servicos) {
                ArrayAdapter<Servico> servicosAdapter = new ArrayAdapter<Servico>(TelaAgendamento.this,
                        android.R.layout.simple_spinner_item, servicos) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        TextView label = (TextView) super.getView(position, convertView, parent);
                        label.setText(getItem(position).getDescricao());
                        return label;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
                        label.setText(getItem(position).getDescricao());
                        return label;
                    }
                };
                servicosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                opcoes_servico.setAdapter(servicosAdapter);


            }
        });
    }

    private void atualizarValorTotal(){
        if (servicoSelecionado != null) {
            String quantidadeStr = edQuantidade.getText().toString();
            if (!quantidadeStr.isEmpty()) {
                try {
                    double quantidade = Double.parseDouble(quantidadeStr);
                    double valorUnitario = servicoSelecionado.getValor();
                    double valorTotal = quantidade * valorUnitario;
                    txtValor.setText(String.format("%.2f", valorTotal));
                } catch (NumberFormatException e) {
                    txtValor.setText("0.00");
                }
            } else {
                txtValor.setText("0.00");
            }
        }
    }




    class ObservadorItemAgendamento implements Observer<List<ItemAgendamento>> {
        @Override
        public void onChanged(List<ItemAgendamento> itemAgendamentos) {
            TelaAgendamento.this.itemAgendamentos.clear();
            TelaAgendamento.this.itemAgendamentos.addAll( itemAgendamentos );
            adapter.notifyDataSetChanged();
        }
    }
    private void limparCampos(){
        edCliente.setText("");
        edEndereco.setText("");
        edQuantidade.setText("");
        txtValor.setText("");
    }


}