package com.ncsoft.line.gam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncsoft.line.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SecondAct extends AppCompatActivity {
    private CountDownTimer countDownTimerMed;
    Random randomMed = new Random();
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView timeMed = findViewById(R.id.timeMed);
        ImageView one = findViewById(R.id.one);
        ImageView two = findViewById(R.id.two);
        LinearLayout gamerLayout = findViewById(R.id.game_layout);
        Intent i = new Intent(getApplicationContext(), ThirdActivity.class);


        one.setOnClickListener(v -> {
            int counter = 0;
            ++counter;
        });
        two.setOnClickListener(v -> {
            int counter = 0;
            ++counter;
        });
        CountDownTimer countDownTimerMed = new CountDownTimer(16 * 1000, 1000) {

            @Override
            public void onTick(long millsUntilFinished) {
                timeMed.setText("" + millsUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                startActivity(i);
                finish();
            }
        };
        countDownTimerMed.start();

        Timer timerOne = new Timer();
        timerOne.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(() -> two.animate()
                        .x(randomMed.nextFloat() * (gamerLayout.getWidth()-one.getWidth()))
                        .y(randomMed.nextFloat() * (gamerLayout.getHeight()-one.getHeight()))
                        .setDuration(150)
                        .start());
            }
        },0,300);

        Timer timerTwo = new Timer();
        timerTwo.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(() -> one.animate()
                        .x(randomMed.nextFloat() * (gamerLayout.getWidth()-two.getWidth()))
                        .y(randomMed.nextFloat() * (gamerLayout.getHeight()-two.getHeight()))
                        .setDuration(150)
                        .start());
            }
        },0,300);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ScarletGame.class));
        countDownTimerMed.cancel();
        finish();
    }
}