package com.example.car4u;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.List;


public class AddCarDescriptionFragment extends Fragment {
    View view;
    List<Car> data;
    User user;
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    public String temp_username=" ";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_car_description, container, false);
        data = Model.instance.getAllCars();
        temp_owner= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempOwner();
        temp_model= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempModel();
        temp_year= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempYear();
        temp_price= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempPrice();
        temp_location= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempLocation();
        temp_phone= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempPhone();
        user= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getUser();

        EditText description = view.findViewById(R.id.addcar_des_description_et);
        Button saveBtn= view.findViewById(R.id.addcar_des_save_btn);
        Button cancelBtn= view.findViewById(R.id.addcar_des_cancel_btn);
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Car car = new Car();
                car.owner=temp_owner;
                car.model=temp_model;
                car.year=temp_year;
                car.price=temp_price;
                car.location=temp_location;
                car.phone=temp_phone;
                car.car_username=user.username;
                user.car_amount++;
                car.description =description.getText().toString();
                Model.instance.addCar(car);
                Toast.makeText(getActivity(), car.car_username + " Added New Car!!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigate(R.id.action_addCarDescriptionFragment_pop);
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