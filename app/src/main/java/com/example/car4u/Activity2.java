package com.example.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Log.d("TAG","Activity2 On Create");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("TAG","Activity2 On Start");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("TAG","Activity2 On Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("TAG","Activity2 On Pause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("TAG","Activity2 On Pause");
    }
}