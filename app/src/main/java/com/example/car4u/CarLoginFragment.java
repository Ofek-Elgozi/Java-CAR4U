package com.example.car4u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.List;

public class CarLoginFragment extends Fragment
{
    List<User> data;
    User user;
    View view;
    public String temp_username=" ";
    public String temp_password=" ";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_car_login, container, false);
        data = Model.instance.getAllUsers();
        EditText usernameEt = view.findViewById(R.id.login_username);
        EditText passwordEt = view.findViewById(R.id.login_password);


        Button sign_inBtn = view.findViewById(R.id.signin_btn);
        sign_inBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                temp_username=usernameEt.getText().toString();
                temp_password=passwordEt.getText().toString();
                user=Model.instance.getUserByUsername(temp_username);
                if(Model.instance.UserIsExist(temp_username, temp_password)==true)
                {
                    CarLoginFragmentDirections.ActionCarLoginFragmentToCarsListFragment action = CarLoginFragmentDirections.actionCarLoginFragmentToCarsListFragment(user);
                    Navigation.findNavController(v).navigate(action);
                }
                else
                {
                    Toast.makeText(getActivity(), "Invaild User, Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button registerBtn = view.findViewById(R.id.register_btn);
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
}

