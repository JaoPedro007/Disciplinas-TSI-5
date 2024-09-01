package com.example.testeroom;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {Categoria.class, Contato.class}, version=3 )
@TypeConverters({DateConverter.class})
public abstract class Banco extends RoomDatabase {
    public abstract CategoriaDAO getCategoriaDAO();
    public abstract ContatoDAO getContatoDAO();
}
