package com.example.testeroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoriaDAO {
    @Insert
    public void inserir(Categoria cat);

    @Update
    public void alterar(Categoria cat);

    @Delete
    public void remover(Categoria cat);

    @Query("select * from categoria order by nome")
    public LiveData<List<Categoria>> listar();

}
