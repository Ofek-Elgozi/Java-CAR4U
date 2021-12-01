package com.example.car4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirstFragment frag = new FirstFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        tran.replace(R.id.mainactivity_container,frag);
        tran.commit();
    }
}