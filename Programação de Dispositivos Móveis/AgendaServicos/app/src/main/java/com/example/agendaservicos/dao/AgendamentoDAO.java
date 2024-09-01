package com.example.agendaservicos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.agendaservicos.modelo.Agendamento;

import java.util.List;

@Dao
public interface AgendamentoDAO {

    @Insert
    public void inserir(Agendamento ag);

    @Update
    public void alterar(Agendamento ag);

    @Delete
    public void remover(Agendamento ag);

    @Query("select * from agendamento")
    public LiveData<List<Agendamento>> listar();


}
