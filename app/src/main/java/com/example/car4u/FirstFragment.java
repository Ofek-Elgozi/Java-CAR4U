package com.example.car4u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class FirstFragment extends Fragment
{
    User u;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_first, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Button loginBtn = view.findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (user != null)
                {
                    Toast.makeText(getActivity(), "Welcome To CAR4U!", Toast.LENGTH_SHORT).show();
                    FirstFragmentDirections.ActionFirstFragmentToCarsListFragment2 action = FirstFragmentDirections.actionFirstFragmentToCarsListFragment2(user.getEmail());
                    Navigation.findNavController(v).navigate(action);
                } else {
                    Navigation.findNavController(v).navigate(R.id.carLoginFragment);
                }
            }
        });
        return view;
    }
}


