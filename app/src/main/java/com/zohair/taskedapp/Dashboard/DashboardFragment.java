package com.zohair.taskedapp.Dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zohair.taskedapp.R;
import com.zohair.taskedapp.adapters.CardAdapter;
import com.zohair.taskedapp.repository.AppDatabase;
import com.zohair.taskedapp.repository.Todo;
import com.zohair.taskedapp.repository.TodoDao;
import com.zohair.taskedapp.retrofit.ApiClient;
import com.zohair.taskedapp.retrofit.QuoteResponse;
import com.zohair.taskedapp.retrofit.WebCalls;
import com.zohair.taskedapp.viewmodel.CardViewModel;
import com.zohair.taskedapp.viewmodel.CardViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements EditFragment.OnTodoUpdatedListener {

    private RecyclerView recyclerView;
    private TextView noRecordTextView;
    private CardAdapter cardAdapter;
    private CardViewModel cardViewModel;
    private ImageView settingsIcon;

    private ApiClient client;
    private WebCalls webCalls;
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        settingsIcon = view.findViewById(R.id.settings_icon);
        recyclerView = view.findViewById(R.id.recyclerView);
        noRecordTextView = view.findViewById(R.id.noRecord);
        fab = view.findViewById(R.id.add);

        getQuotes();

        AppDatabase database = AppDatabase.getDatabase(getContext()); // Get the database instance
        TodoDao todoDao = database.todoDao(); // Get the DAO

        // Set up the Toolbar
        toolbar.setTitle(""); // Clear default title if needed
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardAdapter = new CardAdapter(new ArrayList<>(),database);
        recyclerView.setAdapter(cardAdapter);

        // Set up the click listener for the settings icon
        settingsIcon.setOnClickListener(v -> {
            // Handle settings icon click
            Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
            // You can start a new activity or open a dialog here
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTodoFragment bottomSheet = new AddTodoFragment(database);
                bottomSheet.show(getActivity().getSupportFragmentManager(), "addTodo Fragment");
              //  Toast.makeText(getContext(), "Add Note", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the ViewModel
        // Initialize the ViewModel

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

    private void getQuotes() {

        Log.d("TAG","getQuotes()");

        webCalls = ApiClient.getRetrofit().create(WebCalls.class);
        Call<QuoteResponse> call = webCalls.getQuotes();
        call.enqueue(new Callback<QuoteResponse>() {
            @Override
            public void onResponse(Call<QuoteResponse> call, Response<QuoteResponse> response) {
                Log.d("RES","Res: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<QuoteResponse> call, Throwable t) {

            }
        });


        }



    @Override
    public void onTodoUpdated(Todo updatedTodo, int position) {
        // Get the current list from the adapter
        List<Todo> todoList = cardAdapter.getTodoList();

        // Update the item at the given position
        todoList.set(position, updatedTodo);

        // Notify the adapter that the item has changed
        cardAdapter.notifyItemChanged(position);
    }
}
