package com.zohair.taskedapp.Auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zohair.taskedapp.AuthScreen;
import com.zohair.taskedapp.R;
import com.zohair.taskedapp.databinding.FragmentLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentLoginBinding binding;
    EditText email,pwd;

    Button signIn;
    TextView signUp,forgotPwd;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        loadViews();
        return binding.getRoot();
    }

    private void loadViews() {
            email = binding.email;
            pwd = binding.password;
            signIn = binding.signIn;
            signUp= binding.signUp;
            forgotPwd = binding.forgotPassword;

            forgotPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(requireContext(),"Clicked Forgot Password",Toast.LENGTH_LONG).show();
                }
            });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthScreen authScreen = new AuthScreen();
                    loadFragment(new RegisterFragment());
                }
            });
    }

    private void loadFragment(Fragment fragment) {
        // Create a new FragmentTransaction and replace the fragment
        FragmentManager fragmentManager = getParentFragmentManager(); // Or getActivity().getSupportFragmentManager() if not using nested fragments
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container1, fragment);
        transaction.addToBackStack(null); // Optional: Add to back stack to allow back navigation
        transaction.commit();
    }
}