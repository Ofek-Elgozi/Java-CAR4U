package com.example.car4u;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.car4u.Model.AppLocalDB;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.LinkedList;
import java.util.List;

public class CarLoginFragment extends Fragment
{
    List<User> data = new LinkedList<User>();
    User user;
    View view;
    public String temp_username=" ";
    public String temp_password=" ";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.fragment_car_login, container, false);
        Model.instance.getAllUsers(new Model.getAllUsersListener()
        {
            @Override
            public void onComplete(List<User> user_data)
            {
                data = user_data;
            }
        });
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
                Model.instance.getUserByUsername(temp_username, (user) ->
                {
                    CarLoginFragment.this.user=user;
                    if(CarLoginFragment.this.user!=null)
                    {
                        Toast.makeText(getActivity(), "Welcome " + user.username +"!", Toast.LENGTH_SHORT).show();
                        CarLoginFragmentDirections.ActionCarLoginFragmentToCarsListFragment action = CarLoginFragmentDirections.actionCarLoginFragmentToCarsListFragment(user);
                        Navigation.findNavController(v).navigate(action);
                    }
                    else if(CarLoginFragment.this.user==null)
                    {
                        Toast.makeText(getActivity(), "Invaild User, Please Try Again.", Toast.LENGTH_SHORT).show();
                    }
                });
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

