package com.example.agendaservicos.banco;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.agendaservicos.helper.DateConverter;
import com.example.agendaservicos.modelo.Agendamento;
import com.example.agendaservicos.modelo.ItemAgendamento;
import com.example.agendaservicos.modelo.Servico;
import com.example.agendaservicos.dao.AgendamentoDAO;
import com.example.agendaservicos.dao.ItemAgendamentoDAO;
import com.example.agendaservicos.dao.ServicoDAO;

@Database(entities = {Servico.class, Agendamento.class, ItemAgendamento.class}, version = 4)
@TypeConverters({DateConverter.class})
public abstract class Banco extends RoomDatabase {
    public abstract AgendamentoDAO getAgendamentoDAO();
    public abstract ServicoDAO getServicoDAO();
    public abstract ItemAgendamentoDAO getItemAgendamentoDAO();

}
