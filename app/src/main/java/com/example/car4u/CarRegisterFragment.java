package com.example.car4u;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.List;


public class CarRegisterFragment extends Fragment
{
    List<User> data;
    View view;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_car_register, container, false);
        EditText usernameEt = view.findViewById(R.id.username_register);
        EditText passwordEt = view.findViewById(R.id.password_register);
        EditText emailEt = view.findViewById(R.id.email_register);
        EditText phoneEt = view.findViewById(R.id.phone_register);
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
                progressBar.setVisibility(View.VISIBLE);
                User user = new User();
                user.username=usernameEt.getText().toString();
                user.password=passwordEt.getText().toString();
                user.email=emailEt.getText().toString();
                user.phone=phoneEt.getText().toString();
                usernameEt.setEnabled(false);
                passwordEt.setEnabled(false);
                emailEt.setEnabled(false);
                phoneEt.setEnabled(false);
                cancelBtn.setEnabled(false);
                Model.instance.addUser(user,()->
                {
                    Navigation.findNavController(v).popBackStack();
                });
                Toast.makeText(getActivity(), "User " + user.username + " Added Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

