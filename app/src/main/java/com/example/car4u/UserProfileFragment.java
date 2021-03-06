package com.example.car4u;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserProfileFragment extends Fragment
{
    UserProfileViewModel viewModel;
    Car car;
    User user;
    View view;
    MyAdapter adapter;
    ProgressBar userprofile_progressBar;
    SwipeRefreshLayout userprofile_swipeRefresh;
    private FirebaseAuth mAuth;


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        viewModel=new ViewModelProvider(this).get(UserProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        user = UserProfileFragmentArgs.fromBundle(getArguments()).getUser();
        viewModel.setUser(user);
        userprofile_progressBar = view.findViewById(R.id.user_profile_progressBar);
        userprofile_progressBar.setVisibility(View.VISIBLE);
        RecyclerView list=view.findViewById(R.id.userprofilefragment_listv);
        list.setHasFixedSize(true);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(LayoutManager);
        adapter = new MyAdapter();
        DividerItemDecoration DividerList = new DividerItemDecoration(list.getContext(),LayoutManager.getOrientation());
        DividerList.setDrawable(getResources().getDrawable(R.drawable.divider));
        list.addItemDecoration(DividerList);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position, View v)
            {
                car=viewModel.getData().getValue().get(position);
                UserProfileFragmentDirections.ActionUserProfileFragmentToCarEditFragment action = UserProfileFragmentDirections.actionUserProfileFragmentToCarEditFragment(car,user);
                Navigation.findNavController(v).navigate(action);
            }
        });
        userprofile_swipeRefresh = view.findViewById(R.id.userprofile_SwipeRefresh);
        userprofile_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                userprofile_swipeRefresh.setRefreshing(false);
            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Car>>()
        {
            @Override
            public void onChanged(List<Car> cars)
            {
                adapter.notifyDataSetChanged();
                userprofile_swipeRefresh.setRefreshing(false);
                userprofile_progressBar.setVisibility(View.GONE);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView model;
        TextView year;
        TextView price;
        ImageView avatarImg;
        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener)
        {
            super(itemView);
            model= itemView.findViewById(R.id.carlistrow_text_v1);
            year= itemView.findViewById(R.id.carlistrow_text_v2);
            price= itemView.findViewById(R.id.carlistrow_text_v3);
            avatarImg = itemView.findViewById(R.id.carlistrow_imagev);
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
        public void bind(Car car)
        {
            model.setText(car.getModel());
            year.setText(car.getYear());
            price.setText(car.getPrice());
            avatarImg.setImageResource(R.drawable.avatar);
            if(car.getAvatarUrl() != null)
            {
                Picasso.get().load(car.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImg);
            }
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
            Car c2 = viewModel.getData().getValue().get(position);
            holder.bind(c2);
        }

        @Override
        public int getItemCount()
        {
            if(viewModel.getData() == null || viewModel.getData().getValue()==null)
                return 0;
            return viewModel.getData().getValue().size();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.userprofile_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_home:
                Navigation.findNavController(view).popBackStack();
                break;
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.action_userProfileFragment_to_carLoginFragment2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}



