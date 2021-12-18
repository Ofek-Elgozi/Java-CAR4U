package com.example.car4u;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;

import java.util.LinkedList;
import java.util.List;


public class CarsListFragment extends Fragment
{
    List<Car> data= new LinkedList<Car>();
    Car car;
    User user;
    View view;
    MyAdapter adapter;
    ProgressBar carlist_progressBar;
    SwipeRefreshLayout swipeRefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_cars_list, container, false);
        user=CarsListFragmentArgs.fromBundle(getArguments()).getUser();
        carlist_progressBar = view.findViewById(R.id.carlist_progressBar);
        carlist_progressBar.setVisibility(View.VISIBLE);
        RecyclerView list= view.findViewById(R.id.carslistfragment_listv);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, View v)
            {
                car=data.get(position);
                CarsListFragmentDirections.ActionCarsListFragmentToCarDetailsFragment action = CarsListFragmentDirections.actionCarsListFragmentToCarDetailsFragment(car);
                Navigation.findNavController(v).navigate(action);
            }
        });
        setHasOptionsMenu(true);
        refreshData();
        return view;
    }

    public void refreshData()
    {
        Model.instance.getAllCars(new Model.getAllCarsListener()
        {
            @Override
            public void onComplete(List<Car> car_data)
            {
                data = car_data;
                carlist_progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView model;
        TextView year;
        TextView price;
        TextView description;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener)
        {
            super(itemView);
            model= itemView.findViewById(R.id.carlistrow_text_v1);
            year= itemView.findViewById(R.id.carlistrow_text_v2);
            price= itemView.findViewById(R.id.carlistrow_text_v3);
            description= itemView.findViewById(R.id.carlistrow_text_v4);
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if (listener != null)
                    {
                        listener.onItemClick(pos,v);
                    }
                }
            });
        }
    }

    interface OnItemClickListener
    {
        void onItemClick(int position, View v);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener)
        {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View v = getLayoutInflater().inflate(R.layout.cars_list_row,parent,false);
            MyViewHolder holder = new MyViewHolder(v,listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
        {
            holder.model.setText(data.get(position).model);
            holder.year.setText(data.get(position).year);
            holder.price.setText(data.get(position).price);
            holder.description.setText(data.get(position).description);
        }

        @Override
        public int getItemCount()
        {
            return data.size();
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
                CarsListFragmentDirections.ActionCarsListFragmentToAddCarFragment action = CarsListFragmentDirections.actionCarsListFragmentToAddCarFragment(user);
                Navigation.findNavController(view).navigate(action);
                break;
            case R.id.menu_profile:
                CarsListFragmentDirections.ActionCarsListFragmentToUserProfileFragment action2 = CarsListFragmentDirections.actionCarsListFragmentToUserProfileFragment(user);
                Navigation.findNavController(view).navigate(action2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

