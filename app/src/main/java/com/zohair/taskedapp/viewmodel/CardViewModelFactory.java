package com.zohair.taskedapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zohair.taskedapp.repository.TodoDao;

// Factory for creating instances of CardViewModel
public class CardViewModelFactory implements ViewModelProvider.Factory {
    private final TodoDao todoDao;

    public CardViewModelFactory(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CardViewModel.class)) {
            return (T) new CardViewModel(todoDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

