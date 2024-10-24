package com.zohair.taskedapp.Dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zohair.taskedapp.R;
import com.zohair.taskedapp.repository.AppDatabase;
import com.zohair.taskedapp.repository.Todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends BottomSheetDialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    EditText titleTxt,desc;
    Button editBtn;
    private AppDatabase db;

    Todo todo;
    int position;

    // Define the interface for callback
    public interface OnTodoUpdatedListener {
        void onTodoUpdated(Todo updatedTodo, int position);
    }

    private OnTodoUpdatedListener listener;

    public EditFragment(AppDatabase db, Todo todo,int position) {
        this.db = db;
        this.todo =todo;
        this.position = position;
    }


    public void setOnTodoUpdatedListener(OnTodoUpdatedListener listener) {
        this.listener = listener;
    }

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(String param1, String param2) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        renderViews(view);
        setListeners();

        return view;
    }

    private void setListeners() {  editBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = titleTxt.getText().toString();
            String description = desc.getText().toString();

            if (!title.isEmpty() && !description.isEmpty()) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                String formattedDate = sdf.format(date);

                todo.setTitle(title);
                todo.setDescription(description);
                todo.setCreatedAt(formattedDate);

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                        db.todoDao().updateTodo(todo);

                        // Optionally, you could update the UI on the main thread
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("###","In run");
                                    dismiss(); // Close the bottom sheet
                                    View decorView = getActivity().getWindow().getDecorView();
                                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                                }
                            });
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        }
    });

    }

    private void renderViews(View view) {

        titleTxt = view.findViewById(R.id.todo_title);
        desc = view.findViewById(R.id.todo_description);
        editBtn = view.findViewById(R.id.edit_todo_button);

        titleTxt.setText(todo.getTitle());
        desc.setText(todo.getDescription());

    }

}