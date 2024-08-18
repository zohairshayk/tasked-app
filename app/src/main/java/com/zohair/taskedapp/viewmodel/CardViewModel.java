package com.zohair.taskedapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zohair.taskedapp.repository.AppDatabase;
import com.zohair.taskedapp.repository.Todo;
import com.zohair.taskedapp.repository.TodoDao;

import java.util.List;

public class CardViewModel extends ViewModel {
    private final TodoDao todoDao;
    private final LiveData<List<Todo>> todoList;

    public CardViewModel(TodoDao todoDao) {
        this.todoDao = todoDao;
        this.todoList = todoDao.getTodo(); // Replace with your actual method
    }

    public LiveData<List<Todo>> getCards() {
        return todoList;
    }
}

