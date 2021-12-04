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

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;


public class AddCarFragment extends Fragment
{
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    View view;
    User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_car, container, false);
        user= AddCarFragmentArgs.fromBundle(getArguments()).getUser();
        EditText owner = view.findViewById(R.id.addcar_owner_et);
        EditText model = view.findViewById(R.id.addcar_model_et);
        EditText year = view.findViewById(R.id.addcar_year_et);
        EditText price = view.findViewById(R.id.addcar_price_et);
        EditText location = view.findViewById(R.id.addcar_location_et);
        EditText phone = view.findViewById(R.id.addcar_phone_et);
        Button continueBtn= view.findViewById(R.id.addcar_continue_btn);
        Button cancelBtn= view.findViewById(R.id.addcar_cancel_btn);
        continueBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                temp_owner=owner.getText().toString();
                temp_model=model.getText().toString();
                temp_year=year.getText().toString();
                temp_price=price.getText().toString();
                temp_location=location.getText().toString();
                temp_phone=phone.getText().toString();
                AddCarFragmentDirections.ActionAddCarFragmentToAddCarDescriptionFragment action = AddCarFragmentDirections.actionAddCarFragmentToAddCarDescriptionFragment(temp_owner,temp_model,temp_year,temp_price,temp_location,temp_phone,user);
                Navigation.findNavController(view).navigate(action);
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Navigation.findNavController(v).popBackStack();
            }
        });


        return view;
    }
}