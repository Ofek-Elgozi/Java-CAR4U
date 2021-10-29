package com.example.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        Log.d("Success","Button Disabled");
        Button button = (Button) v;
        button.setText("Disabled");
    }


}