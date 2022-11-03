package com.ncsoft.line.gam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ncsoft.line.R;

public class WinAct extends AppCompatActivity {
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Button restart = findViewById(R.id.restartBtn);
        restart.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ScarletGame.class)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ScarletGame.class));
    }
}