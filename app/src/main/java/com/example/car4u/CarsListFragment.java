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

import java.util.List;


public class CarsListFragment extends Fragment
{
    List<Car> data;
    Car car;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_cars_list, container, false);
        ListView list=view.findViewById(R.id.carslistfragment_listv);
        data = Model.instance.getAllCars();
        MyAdapter adapter = new MyAdapter();
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                car=data.get(position);
                //action = CarsListFragmentDirections.actionCarsListFragmentToCarDetailsFragment(car);
                //Navigation.findNavController(view).navigate(action);
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
            if(convertView == null)
            {
                LayoutInflater inflater =getLayoutInflater();
                convertView = inflater.inflate(R.layout.cars_list_row,null);
            }
            TextView model= convertView.findViewById(R.id.carlistrow_text_v1);
            model.setText(data.get(position).model);
            TextView year= convertView.findViewById(R.id.carlistrow_text_v2);
            year.setText(data.get(position).year);
            TextView price= convertView.findViewById(R.id.carlistrow_text_v3);
            price.setText(data.get(position).price);
            TextView description= convertView.findViewById(R.id.carlistrow_text_v4);
            description.setText(data.get(position).Description);
            return convertView;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.carslist_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                Navigation.findNavController(view).navigate(R.id.addCarFragment);
                break;
            case R.id.menu_profile:
                Navigation.findNavController(view).navigate(R.id.userProfileFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}