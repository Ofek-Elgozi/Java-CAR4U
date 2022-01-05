package com.example.car4u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CarLoginFragment extends Fragment
{
    User u;
    View view;
    EditText emailEt;
    EditText passwordEt;
    ProgressBar login_progressBar;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_car_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        emailEt = view.findViewById(R.id.login_email);
        passwordEt = view.findViewById(R.id.login_password);
        login_progressBar = view.findViewById(R.id.login_progressBar);
        login_progressBar.setVisibility(View.GONE);
        Button sign_inBtn = view.findViewById(R.id.signin_btn);
        Button registerBtn = view.findViewById(R.id.register_btn);
        sign_inBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                registerBtn.setEnabled(false);
                passwordEt.setEnabled(false);
                emailEt.setEnabled(false);
                login_progressBar.setVisibility(View.VISIBLE);
                if(validate()==false)
                {
                    Toast.makeText(getActivity(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                validateUser(v);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Navigation.findNavController(v).navigate(R.id.carRegisterFragment);
            }
        });
        return view;
    }

    private boolean validate()
    {
        return (emailEt.getText().length() > 2 && passwordEt.getText().length() > 5);
    }

    private void validateUser(View v)
    {
        mAuth.signInWithEmailAndPassword(emailEt.getText().toString(), passwordEt.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser userAuth = mAuth.getCurrentUser();
                            Model.instance.getUserByEmail(userAuth.getEmail(), new Model.getUserByEmailListener()
                            {
                                @Override
                                public void onComplete(User user)
                                {
                                    u = user;
                                    Toast.makeText(getActivity(), "Welcome To CAR4U!", Toast.LENGTH_SHORT).show();
                                    CarLoginFragmentDirections.ActionCarLoginFragmentToCarsListFragment action = CarLoginFragmentDirections.actionCarLoginFragmentToCarsListFragment(u.getEmail());
                                    Navigation.findNavController(v).navigate(action);
                                }
                            });
                        }
                        else
                            {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                            login_progressBar.setVisibility(View.GONE);
                            }
                    }
                });
    }
}

