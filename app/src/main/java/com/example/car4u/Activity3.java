package com.example.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        Log.d("TAG","Activity3 On Create");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("TAG","Activity3 On Start");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("TAG","Activity3 On Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("TAG","Activity3 On Pause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("TAG","Activity3 On Pause");
    }
}