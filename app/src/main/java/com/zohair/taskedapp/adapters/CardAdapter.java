package com.zohair.taskedapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zohair.taskedapp.Dashboard.EditFragment;
import com.zohair.taskedapp.R;
import com.zohair.taskedapp.repository.AppDatabase;
import com.zohair.taskedapp.repository.Todo;

import java.util.List;
import java.util.concurrent.Executors;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Todo> todoList;

    AppDatabase db;

    public CardAdapter(List<Todo> todoList,AppDatabase db) {
        this.db =db;
        this.todoList = todoList;
    }

    // Method to get the current todo list
    public List<Todo> getTodoList() {
        return todoList;
    }


    public void updateData(List<Todo> newTodoList) {
        todoList.clear();
        todoList.addAll(newTodoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Todo todo = todoList.get(position);
        holder.titleTextView.setText(todo.getTitle());
        holder.descriptionTextView.setText(todo.getDescription());
        holder.dateTextView.setText("Created on: " + todo.getCreatedAt());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Edit", Toast.LENGTH_LONG).show();
                // Add your edit logic here
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Todo todo1 = db.todoDao().selectTodoById(todo.getId());
                        EditFragment editFragment = new EditFragment(db,todo1,position);
                        FragmentActivity activity = (FragmentActivity) view.getContext();
                        editFragment.show(activity.getSupportFragmentManager(), "EditFragment");
                    }
                });
                // Use the current activity to switch fragments
                notifyItemChanged(position);

            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(view.getContext(), "Delete", Toast.LENGTH_LONG).show();
                FragmentActivity activity = (FragmentActivity) view.getContext();
                showCustomAlertDialog(activity,view.getContext(),todo);



            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

//    public void updateData(List<Todo> newTodoList) {
//        this.todoList = newTodoList;
//        notifyDataSetChanged();
//    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        ImageView editBtn, deleteBtn;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            dateTextView = itemView.findViewById(R.id.date);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    // Create and show custom AlertDialog
    private void showCustomAlertDialog(Activity activity,Context context,Todo todo) {
        // Inflate the custom dialog layout
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom_alert, null);

        // Set dialog background to transparent to avoid white corners
        dialogView.setBackground(new ColorDrawable(Color.TRANSPARENT));

        // Find views in the custom layout
        TextView dialogText = dialogView.findViewById(R.id.dialog_text);
        Button positiveButton = dialogView.findViewById(R.id.positive_button);
        Button negativeButton = dialogView.findViewById(R.id.negative_button);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView); // Use the custom view

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();

        // Set button click listeners
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle positive button click
                //Toast.makeText(context, "Confirmed!", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Close the dialog
                deleteTodo(todo,context);
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle negative button click
                //Toast.makeText(context, "Cancelled!", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Close the dialog
            }
        });

        // Show the dialog
        dialog.show();
    }

    private void deleteTodo(Todo todo,Context context) {

        // Run the database operation on a background thread
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                db.todoDao().deleteTodoById(todo.getId()); // Use the correct ID of the Todo item
            }
        });

        Toast.makeText(context,"Successfully Deleted.",Toast.LENGTH_SHORT).show();
        // Optionally, you may want to update the UI on the main thread after deletion
        todoList.remove(todo.getId());
        notifyItemRemoved(todo.getId());
    }

}
