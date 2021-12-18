package com.example.car4u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.LinkedList;
import java.util.List;


public class CarEditFragment extends Fragment
{
    List<Car> data = new LinkedList<Car>();
    Car car;
    View view;
    User user;
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_car_edit, container, false);
        Model.instance.getAllCars(new Model.getAllCarsListener()
        {
            @Override
            public void onComplete(List<Car> car_data)
            {
                data = car_data;
            }
        });
        car=CarEditFragmentArgs.fromBundle(getArguments()).getCar();
        user=CarEditFragmentArgs.fromBundle(getArguments()).getUser();
        progressBar = view.findViewById(R.id.car_edit_progressBar);
        progressBar.setVisibility(View.GONE);
        EditText edit_owner = view.findViewById(R.id.car_edit_owner);
        edit_owner.setText(car.owner);

        EditText edit_model = view.findViewById(R.id.car_edit_model);
        edit_model.setText(car.model);

        EditText edit_year = view.findViewById(R.id.car_edit_year);
        edit_year.setText(car.year);

        EditText edit_price = view.findViewById(R.id.car_edit_price);
        edit_price.setText(car.price);

        EditText edit_location = view.findViewById(R.id.car_edit_location);
        edit_location.setText(car.location);

        EditText edit_phone = view.findViewById(R.id.car_edit_phone);
        edit_phone.setText(car.phone);


        Button continueBtn= view.findViewById(R.id.car_edit_continuebtn);
        Button cancelBtn= view.findViewById(R.id.car_edit_cancelbtn);
        Button deleteBtn= view.findViewById(R.id.car_edit_deletebtn);
        continueBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                temp_owner=edit_owner.getText().toString();
                temp_model=edit_model.getText().toString();
                temp_year=edit_year.getText().toString();
                temp_price=edit_price.getText().toString();
                temp_location=edit_location.getText().toString();
                temp_phone=edit_phone.getText().toString();
                CarEditFragmentDirections.ActionCarEditFragmentToCarEditDescriptionFragment action = CarEditFragmentDirections.actionCarEditFragmentToCarEditDescriptionFragment(car,temp_owner,temp_model,temp_year,temp_price,temp_location,temp_phone);
                Navigation.findNavController(v).navigate(action);
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
        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                cancelBtn.setEnabled(false);
                continueBtn.setEnabled(false);
                edit_owner.setEnabled(false);
                edit_model.setEnabled(false);
                edit_year.setEnabled(false);
                edit_price.setEnabled(false);
                edit_location.setEnabled(false);
                edit_phone.setEnabled(false);
                Model.instance.removeCar(car,()->
                {
                    Navigation.findNavController(v).popBackStack();
                });
            }
        });
        return view;
    }
}
