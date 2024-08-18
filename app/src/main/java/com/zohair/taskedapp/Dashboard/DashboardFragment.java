package com.zohair.taskedapp.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zohair.taskedapp.R;
import com.zohair.taskedapp.SplashScreen;
import com.zohair.taskedapp.adapters.CardAdapter;
import com.zohair.taskedapp.repository.AppDatabase;
import com.zohair.taskedapp.repository.Todo;
import com.zohair.taskedapp.repository.TodoDao;
import com.zohair.taskedapp.viewmodel.CardViewModel;
import com.zohair.taskedapp.viewmodel.CardViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView noRecordTextView;
    private CardAdapter cardAdapter;
    private CardViewModel cardViewModel;
    private ImageView settingsIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        settingsIcon = view.findViewById(R.id.settings_icon);
        recyclerView = view.findViewById(R.id.recyclerView);
        noRecordTextView = view.findViewById(R.id.noRecord);

        // Set up the Toolbar
        toolbar.setTitle(""); // Clear default title if needed
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(new ArrayList<>());
        recyclerView.setAdapter(cardAdapter);

        // Set up the click listener for the settings icon
        settingsIcon.setOnClickListener(v -> {
            // Handle settings icon click
            Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
            // You can start a new activity or open a dialog here
        });

        // Initialize the ViewModel
        // Initialize the ViewModel
        AppDatabase database = AppDatabase.getDatabase(getContext()); // Get the database instance
        TodoDao todoDao = database.todoDao(); // Get the DAO
        CardViewModelFactory factory = new CardViewModelFactory(todoDao);
        cardViewModel = new ViewModelProvider(this, factory).get(CardViewModel.class);

        // Get the ViewModel and observe data
        cardViewModel.getCards().observe(getViewLifecycleOwner(), cards -> {
            if (cards != null && !cards.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                noRecordTextView.setVisibility(View.GONE);
                cardAdapter.updateData(cards);
            } else {
                recyclerView.setVisibility(View.GONE);
                noRecordTextView.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}
