package com.example.car4u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG","MainActivity On Create");
    }

    public void handleText(View v){
        TextView t= findViewById(R.id.Source);
        String input = t.getText().toString();
        ((TextView)findViewById(R.id.Output)).setText(input);
        Log.d("info", input);
    }


}