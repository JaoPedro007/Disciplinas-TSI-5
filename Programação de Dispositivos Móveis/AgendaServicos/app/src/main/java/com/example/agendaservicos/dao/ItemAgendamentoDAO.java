package com.example.agendaservicos.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.agendaservicos.modelo.ItemAgendamento;

import java.util.List;

@Dao
public interface ItemAgendamentoDAO {

    @Insert
    public void inserir(ItemAgendamento item);

    @Update
    public void alterar(ItemAgendamento item);

    @Delete
    public void remover(ItemAgendamento item);

    @Query("select * from itemagendamento")
    public LiveData<List<ItemAgendamento>> listar();
}
