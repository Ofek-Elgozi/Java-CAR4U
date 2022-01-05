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

import java.util.LinkedList;
import java.util.List;


public class CarEditDescriptionFragment extends Fragment
{
    View view;
    Car car;
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    public String temp_url;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_car_edit_description, container, false);
        temp_owner= CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getOwner();
        temp_model= CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getModel();
        temp_year= CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getYear();
        temp_price=CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getPrice();
        temp_location=CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getLocation();
        temp_phone=CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getPhone();
        temp_url = CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getUrl();
        car=CarEditDescriptionFragmentArgs.fromBundle(getArguments()).getCar();
        progressBar = view.findViewById(R.id.car_edit_des_progressBar);
        progressBar.setVisibility(View.GONE);
        EditText description = view.findViewById(R.id.car_edit_des);
        description.setText(car.description);

        Button saveBtn= view.findViewById(R.id.car_edit_des_savebtn);
        Button cancelBtn= view.findViewById(R.id.car_edit_des_cancelbtn);
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                description.setEnabled(false);
                cancelBtn.setEnabled(false);
                car.owner=temp_owner;
                car.model=temp_model;
                car.year=temp_year;
                car.price=temp_price;
                car.location=temp_location;
                car.phone=temp_phone;
                car.description =description.getText().toString();
                if(temp_url != "")
                {
                    car.setAvatarUrl(temp_url);
                    Model.instance.addCar(car,()->
                    {
                        Toast.makeText(getActivity(), "Car Details successfully Edited", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_carEditDescriptionFragment_pop);
                    });
                }
                else
                {
                    Model.instance.addCar(car,()->
                    {
                        Toast.makeText(getActivity(), "Car Details successfully Edited", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_carEditDescriptionFragment_pop);
                    });
                }
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