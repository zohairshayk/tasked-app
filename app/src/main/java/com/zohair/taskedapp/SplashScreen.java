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

import com.zohair.taskedapp.databinding.ActivitySplashscreenBinding;

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
            // Delay transition to the next activity
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(SplashScreen.this, AuthScreen.class);
                startActivity(intent);
                finish();
            }, 5000);
        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    }
}

