package com.example.car4u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.car4u.Model.Car;
import com.squareup.picasso.Picasso;


public class CarDetailsFragment extends Fragment
{
    Car car;
    View view;
    ImageView avatarImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_car_details, container, false);
        car=CarDetailsFragmentArgs.fromBundle(getArguments()).getCar();

        avatarImg = view.findViewById(R.id.car_details_imagev);
        avatarImg.setImageResource(R.drawable.avatar);
        if(car.getAvatarUrl() != null)
        {
            Picasso.get().load(car.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImg);
        }

        TextView description= view.findViewById(R.id.car_details_description);
        description.setText(car.getDescription());

        TextView owner= view.findViewById(R.id.car_details_owner);
        owner.setText(car.owner);

        TextView model= view.findViewById(R.id.car_details_model);
        model.setText(car.model);

        TextView year= view.findViewById(R.id.car_details_year);
        year.setText(car.year);

        TextView price= view.findViewById(R.id.car_details_price);
        price.setText(car.price);

        TextView location= view.findViewById(R.id.car_details_location);
        location.setText(car.location);

        TextView phone= view.findViewById(R.id.car_details_phone);
        phone.setText(car.phone);


        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cardetails_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.cardetails_home:
                Navigation.findNavController(view).popBackStack();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}



