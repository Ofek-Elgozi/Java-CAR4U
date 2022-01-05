package com.example.car4u;

import static android.app.Activity.RESULT_CANCELED;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.LinkedList;
import java.util.List;


public class AddCarDescriptionFragment extends Fragment
{
    View view;
    User user;
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
        view = inflater.inflate(R.layout.fragment_add_car_description, container, false);
        temp_owner= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempOwner();
        temp_model= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempModel();
        temp_year= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempYear();
        temp_price= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempPrice();
        temp_location= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempLocation();
        temp_phone= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempPhone();
        temp_url = AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getUrl();
        user= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getUser();
        progressBar = view.findViewById(R.id.addcar_des_progressbar);
        progressBar.setVisibility(View.GONE);
        EditText description = view.findViewById(R.id.addcar_des_description_et);
        Button saveBtn= view.findViewById(R.id.addcar_des_save_btn);
        Button cancelBtn= view.findViewById(R.id.addcar_des_cancel_btn);
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                cancelBtn.setEnabled(false);
                description.setEnabled(false);
                Car car = new Car();
                car.setOwner(temp_owner);
                car.setModel(temp_model);
                car.setYear(temp_year);
                car.setPrice(temp_price);
                car.setLocation(temp_location);
                car.setPhone(temp_phone);
                car.setCar_username(user.name);
                car.setDescription(description.getText().toString());
                if(temp_url != "")
                {
                    car.setAvatarUrl(temp_url);
                    Model.instance.addCar(car,()->
                    {
                        Toast.makeText(getActivity(), "New Car Added.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_addCarDescriptionFragment_pop);
                    });
                }
                else
                {
                    Model.instance.addCar(car,()->
                    {
                        Toast.makeText(getActivity(), "New Car Added.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_addCarDescriptionFragment_pop);
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

