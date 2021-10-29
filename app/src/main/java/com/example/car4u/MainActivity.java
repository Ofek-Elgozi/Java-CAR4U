package com.example.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","MainActivity On Create");
    }

    public void disable(View v){
        v.setEnabled(false);
    }
    /*
    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("TAG","MainActivity On Start");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("TAG","MainActivity On Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("TAG","MainActivity On Pause");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("TAG","MainActivity On Pause");
    }
    */

}