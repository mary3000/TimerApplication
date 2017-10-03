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

    private static final SparseArray<String> writtenNumbers =  new SparseArray<String>() {
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

            append(11, "одинадцать");
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

    final int[] seconds = {0};
    static final int TIMER_VALUE = 1001;
    static final int TIMER_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        final Button button = (Button)findViewById(R.id.button);

        if (savedInstanceState != null) { //method
            seconds[0] = savedInstanceState.getInt(KEY_SECONDS_, -1);
            String textString = savedInstanceState.getString(KEY_TEXT_, "def");
            String buttonString = savedInstanceState.getString(KEY_BUTTON_, "def");
            ((TextView)findViewById(R.id.text)).setText(textString);
            button.setText(buttonString);
        } else {
            button.setText("Start");
        }

        long allTime = (TIMER_VALUE - seconds[0])*1000;
        final CountDownTimer timer = new CountDownTimer(allTime, TIMER_INTERVAL) {

            StringBuffer buff = new StringBuffer();
            @Override
            public void onTick(long milliSec) {
                seconds[0]++;
                buff.append(writtenNumbers.get(seconds[0], ""));
                if (buff.length() == 0) {
                    int currentNumber = seconds[0];
                    for (int i = 0; currentNumber > 0; i++) {
                        int numeral = currentNumber % 10;
                        currentNumber = currentNumber / 10;
                        if (numeral == 0) {
                            continue;
                        }
                        if (i == 0 && currentNumber % 10 == 1) {
                            buff.insert(0, writtenNumbers.get(numeral + 10) + " ");
                            currentNumber = currentNumber / 10;
                            i++;
                        } else {
                            buff.insert(0, writtenNumbers.get(numeral*(int)pow(10, i)) + " ");
                        }
                    }
                }

                ((TextView)findViewById(R.id.text)).setText(buff.toString());
                buff.delete(0, buff.length());
            }

            @Override
            public void onFinish() {
                button.setText("Start");
                buff.delete(0, buff.length());
                seconds[0] = 0;
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonString = button.getText().toString();
                if (buttonString.equals("Start")) {
                    button.setText("Stop");
                    timer.start();
                } else if (buttonString.equals("Stop")) {
                    button.setText("Start");
                    timer.cancel();
                }

            }
        });

        if (savedInstanceState != null) {
            if (button.getText() == "Stop") {
                timer.start();
            } else {
                String textString = savedInstanceState.getString(KEY_TEXT_, "");
                ((TextView)findViewById(R.id.text)).setText(textString);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SECONDS_, seconds[0]);
        outState.putString(KEY_BUTTON_, ((Button)(findViewById(R.id.button))).getText().toString());
        outState.putString(KEY_TEXT_, ((TextView)(findViewById(R.id.text))).getText().toString());
    }
}

