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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.List;

public class UserProfileFragment extends Fragment
{
    List<Car> data;
    Car car;
    User user;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        user = UserProfileFragmentArgs.fromBundle(getArguments()).getUser();
        ListView list=view.findViewById(R.id.userprofilefragment_listv);
        data = Model.instance.getAllCars();
        UserProfileFragment.MyAdapter adapter = new UserProfileFragment.MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                car=data.get(position);
                if(car.car_username.equals(user.username))
                {
                    UserProfileFragmentDirections.ActionUserProfileFragmentToCarEditFragment action = UserProfileFragmentDirections.actionUserProfileFragmentToCarEditFragment(car,user);
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });


        setHasOptionsMenu(true);
        return view;
    }

    class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return data.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(data.get(position).car_username.equals(user.username))
            {
                if(convertView == null)
                {
                    LayoutInflater inflater =getLayoutInflater();
                    convertView = inflater.inflate(R.layout.cars_list_row,null);
                }
                TextView model = convertView.findViewById(R.id.carlistrow_text_v1);
                model.setText(data.get(position).model);
                TextView year = convertView.findViewById(R.id.carlistrow_text_v2);
                year.setText(data.get(position).year);
                TextView price = convertView.findViewById(R.id.carlistrow_text_v3);
                price.setText(data.get(position).price);
                TextView description = convertView.findViewById(R.id.carlistrow_text_v4);
                description.setText(data.get(position).description);
                return convertView;
            }
            else
            {
                if(convertView == null)
                {
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = inflater.inflate(R.layout.empty_list_row, null);
                }
                return convertView;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.homepage_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_home:
                Navigation.findNavController(view).popBackStack();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}