package com.example.testeroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContatoDAO {
    @Insert
    public void inserir(Contato c);

    @Update
    public void alterar(Contato c);

    @Delete
    public void remover(Contato c);

    @Query("select * from contato order by nome")
    public LiveData<List<Contato>> buscarTodos();

    @Query("select * from contato where id_categoria = :cat order by nome ")
    public LiveData<List<Contato>> buscarPorCategoria(long cat);

}
