package com.zohair.taskedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zohair.taskedapp.Dashboard.MainActivity;
import com.zohair.taskedapp.databinding.ActivitySplashscreenBinding;
import com.zohair.taskedapp.repository.AppDatabase;
import com.zohair.taskedapp.repository.Todo;
import com.zohair.taskedapp.repository.TodoDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    ActivitySplashscreenBinding binding;
    Animation slide_in_right;
    TextView txt;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadViews();

        logo = binding.logo;

        slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);

        MyAnimationListener listener = new MyAnimationListener();
        listener.setImage(logo);

        logo.startAnimation(slide_in_right);
        slide_in_right.setAnimationListener(listener);

    }

    private void loadViews() {

    }

    public class MyAnimationListener implements Animation.AnimationListener {

        ImageView img;
        public void setImage(ImageView view) {
            this.img = img;
        }

        public void onAnimationEnd(Animation animation) {
            // Do whatever you want
            // Get the database instance
            AppDatabase database = AppDatabase.getDatabase(SplashScreen.this);

            // Example usage: Perform database operations or initialization here
            TodoDao todoDao = database.todoDao();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            String formattedDate = sdf.format(date);
            // Optionally, perform some operations
            new Thread(() -> {
                // Perform database operations in a background thread
//                Todo todo = new Todo();
//                todo.setId(1);
//                todo.setTitle("Job Appointment");
//                todo.setDescription("I have a job interview at Systems Limited on Monday.");
//                todo.setCreatedAt(formattedDate);
//                todo.setImageString("N/A");
//
//                Todo todo1 = new Todo();
//                todo1.setId(2);
//                todo1.setTitle("Family Gathering");
//                todo1.setDescription("Family gathering on occasion of Ahmed's Nikkah Ceremmony");
//                todo1.setCreatedAt(formattedDate);
//                todo1.setImageString("N/A");
//
//                todoDao.insertTodo(todo);
//                todoDao.insertTodo(todo1);
                // After operations are done, navigate to the main activity
                // Delay transition to the next activity
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }, 5000);
            }).start();

        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    }
}

