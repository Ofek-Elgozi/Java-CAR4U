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

import java.util.List;


public class CarRegisterFragment extends Fragment
{
    View view;
    ProgressBar progressBar;
    EditText nameET;
    EditText passwordEt;
    EditText emailEt;
    EditText phoneEt;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_car_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        emailEt = view.findViewById(R.id.email_register);
        passwordEt = view.findViewById(R.id.password_register);
        nameET = view.findViewById(R.id.name_register);
        phoneEt = view.findViewById(R.id.phone_register);
        progressBar = view.findViewById(R.id.progressbar_register);
        progressBar.setVisibility(View.GONE);
        Button cancelBtn = view.findViewById(R.id.register_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Navigation.findNavController(v).popBackStack();
            }
        });
        Button signupBtn = view.findViewById(R.id.signup_btn);
        signupBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(validate()==false)
                {
                    Toast.makeText(getActivity(), "Register Failed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailEt.getText().toString(), passwordEt.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    progressBar.setVisibility(View.VISIBLE);
                                    User user = new User();
                                    user.setName(nameET.getText().toString());
                                    user.setPassword(passwordEt.getText().toString());
                                    user.setEmail(emailEt.getText().toString());
                                    user.setPhone(phoneEt.getText().toString());
                                    nameET.setEnabled(false);
                                    passwordEt.setEnabled(false);
                                    emailEt.setEnabled(false);
                                    phoneEt.setEnabled(false);
                                    cancelBtn.setEnabled(false);
                                    // Sign up success, update UI with the signed-in user's information
                                    Model.instance.addUser(user,()->
                                    {
                                        Navigation.findNavController(v).popBackStack();
                                        Toast.makeText(getActivity(), "User " + user.name + " Added Successfully!", Toast.LENGTH_SHORT).show();
                                    });
                                } else {
                                    // If sign up fails, display a message to the user.
                                    Toast.makeText(getActivity(), "Register Failed!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
        return view;
    }

    private boolean validate()
    {
        return (nameET.getText().length() > 2 && passwordEt.getText().length() > 5 && emailEt.getText().length() > 2);
    }
}

