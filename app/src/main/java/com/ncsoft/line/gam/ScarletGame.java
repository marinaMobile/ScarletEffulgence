package com.ncsoft.line.gam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.ncsoft.line.R;

public class ScarletGame extends AppCompatActivity {

    Button startBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scarlet_game);

        startBtn = (Button) findViewById(R.id.startBtn);


        startBtn.setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(),
                        FirstAct.class)));


    }
}