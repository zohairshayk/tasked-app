package com.zohair.taskedapp.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao  {
     @Insert
     public void insertTodo(Todo td);

     @Query("Select * from Todo")
     public LiveData<List<Todo>> getTodo();

    @Query("DELETE FROM Todo WHERE id = :id")
    void deleteTodoById(Integer id);

    @Update
    void updateTodo(Todo todo);

    @Query("DELETE FROM Todo")
    void deleteAllTodos();
}
