package com.zohair.taskedapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zohair.taskedapp.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView descriptionTextView;
    public TextView dateTextView;
    public ImageView editButton;
    public ImageView deleteButton;

    public CardViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.title);
        descriptionTextView = itemView.findViewById(R.id.description);
        dateTextView = itemView.findViewById(R.id.date);
        editButton = itemView.findViewById(R.id.editBtn);
        deleteButton = itemView.findViewById(R.id.deleteBtn);
    }
}

