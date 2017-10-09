package ru.mipt.feofanova.timerapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    static final String KEY_TIMER_VALUE_ = "TIMER";
    long MILLI_SECONDS = 2000;

    static CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPause() {
        super.onPause();

        timer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();

        timer = new CountDownTimer(MILLI_SECONDS, 10) {

            public void onTick(long milliSeconds) {
                MILLI_SECONDS = milliSeconds;
            }

            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        MILLI_SECONDS = savedInstanceState.getLong(KEY_TIMER_VALUE_);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(KEY_TIMER_VALUE_, MILLI_SECONDS);
    }

}
