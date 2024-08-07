package com.zohair.taskedapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.zohair.taskedapp.Auth.LoginFragment;
import com.zohair.taskedapp.databinding.ActivityAuthScreenBinding;
import com.zohair.taskedapp.databinding.ActivitySplashscreenBinding;

public class AuthScreen extends AppCompatActivity {
FrameLayout container;
ActivityAuthScreenBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = ActivityAuthScreenBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_auth_screen);

        container = binding.container1;
        loadFragment(new LoginFragment());
    }

    public void loadFragment(Fragment fragment) {
        // Create a new FragmentTransaction and replace the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container1, fragment);
        fragmentTransaction.commit();
    }
}