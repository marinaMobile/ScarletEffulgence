package com.ncsoft.line.gam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncsoft.line.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class FirstAct extends AppCompatActivity {
    private CountDownTimer countDownTimer;
    private TextView time;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        time = (TextView) findViewById(R.id.time);
        ImageView dragon = findViewById(R.id.my_button);
        LinearLayout ll_wasp = findViewById(R.id.ll_game);
        Intent i = new Intent(getApplicationContext(), SecondAct.class);



        dragon.setOnClickListener(new View.OnClickListener() {
            int counter = 0;
            @Override
            public void onClick(View v) {
                ++counter;
            }
        });
        countDownTimer = new CountDownTimer(16 * 1000, 1000) {

            @Override
            public void onTick(long millsUntilFinished) {
                time.setText("" + millsUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                startActivity(i);
            }
        };
        countDownTimer.start();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(() -> dragon.animate()
                        .x(random.nextFloat() * ll_wasp.getWidth()-dragon.getWidth())
                        .y(random.nextFloat() * ll_wasp.getHeight()-dragon.getHeight())
                        .setDuration(300)
                        .start());
            }
        },0,300);


    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ScarletGame.class));
        countDownTimer.cancel();
        finish();

    }

}