package ru.mipt.feofanova.timerapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Math.pow;

public class TimerActivity extends AppCompatActivity {

    private static final SparseArray<String> WRITTEN_NUMBERS =  new SparseArray<String>() {
        {
            append(1, "один");
            append(2, "два");
            append(3, "три");
            append(4, "четыре");
            append(5, "пять");
            append(6, "шесть");
            append(7, "семь");
            append(8, "восемь");
            append(9, "девять");
            append(10, "десять");

            append(11, "одиннадцать");
            append(12, "двенадцать");
            append(13, "тринадцать");
            append(14, "четырнадцать");
            append(15, "пятнадцать");
            append(16, "шестнадцать");
            append(17, "семнадцать");
            append(18, "восемнадцать");
            append(19, "девятнадцать");

            append(20, "двадцать");
            append(30, "тридцать");
            append(40, "сорок");
            append(50, "пятьдесят");
            append(60, "шестьдесят");
            append(70, "семьдесят");
            append(80, "восемьдесят");
            append(90, "девяносто");

            append(100, "сто");
            append(200, "двести");
            append(300, "триста");
            append(400, "четыреста");
            append(500, "пятьсот");
            append(600, "шестьсот");
            append(700, "семьсот");
            append(800, "восемьсот");
            append(900, "девятьсот");
            append(1000, "тысяча");
        }
    };

    private static final String KEY_SECONDS_ = "SECONDS";
    private static final String KEY_BUTTON_ = "BUTTON";
    private static final String KEY_TEXT_ = "TEXT";

    static final int TIMER_VALUE = 1001;
    static final int TIMER_INTERVAL = 1000;

    enum State {
        START, STOP
    }
    State buttonState = State.START;

    int seconds = 0;
    CountDownTimer timer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        final String buttonStart = getString(R.string.start);
        final String buttonStop = getString(R.string.stop);

        final Button button = (Button)findViewById(R.id.button);

        button.setText(buttonStart);
        buttonState = State.START;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonState == State.START) {
                    button.setText(buttonStop);
                    buttonState = State.STOP;
                    timer.start();

                } else if (buttonState == State.STOP) {
                    button.setText(buttonStart);
                    buttonState = State.START;
                    timer.cancel();
                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();

        final String buttonStart = getString(R.string.start);

        final TextView textView = ((TextView)findViewById(R.id.text));
        final Button button = (Button)findViewById(R.id.button);

        long allTime = (TIMER_VALUE - seconds)*1000;
        timer = new CountDownTimer(allTime, TIMER_INTERVAL) {

            StringBuilder tmpString = new StringBuilder();
            @Override
            public void onTick(long milliSec) {
                seconds++;
                if (seconds > TIMER_VALUE) {
                    this.onFinish();
                    this.cancel();
                }

                tmpString.append(WRITTEN_NUMBERS.get(seconds, ""));
                if (tmpString.length() == 0) {
                    parseNumber(tmpString);
                }

                textView.setText(tmpString.toString());
                tmpString.delete(0, tmpString.length());
            }

            @Override
            public void onFinish() {
                button.setText(buttonStart);
                buttonState = State.START;

                tmpString.delete(0, tmpString.length());
                seconds = 0;
            }
        };

        if (buttonState == State.STOP) {
            timer.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        timer.cancel();
    }

    public void parseNumber(StringBuilder buff) {
        int currentNumber = seconds;
        for (int i = 0; currentNumber > 0; i++) {
            int numeral = currentNumber % 10;
            currentNumber = currentNumber / 10;

            if (numeral == 0) {
                continue;
            }

            if (i == 0 && currentNumber % 10 == 1) {
                buff.insert(0, WRITTEN_NUMBERS.get(numeral + 10) + " ");
                currentNumber = currentNumber / 10;
                i++;
            } else {
                buff.insert(0, WRITTEN_NUMBERS.get(numeral*(int)pow(10, i)) + " ");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_SECONDS_, seconds);
        outState.putString(KEY_BUTTON_, ((Button)(findViewById(R.id.button))).getText().toString());
        outState.putString(KEY_TEXT_, ((TextView)(findViewById(R.id.text))).getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        final String buttonStart = getString(R.string.start);

        seconds = savedInstanceState.getInt(KEY_SECONDS_);

        String textString = savedInstanceState.getString(KEY_TEXT_, "");
        String buttonString = savedInstanceState.getString(KEY_BUTTON_);
        buttonState = buttonString.equals(buttonStart) ? State.START : State.STOP;

        ((TextView)findViewById(R.id.text)).setText(textString);
        ((Button)findViewById(R.id.button)).setText(buttonString);
    }
}
