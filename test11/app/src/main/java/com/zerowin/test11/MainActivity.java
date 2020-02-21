package com.zerowin.test11;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Gpio gpio_PC4 = new Gpio("PH25");
    private Gpio gpio_PH27 = new Gpio("PH27");
    private Gpio gpio_PH24 = new Gpio("PH24");

    private Gpio gpio_PH26 = new Gpio("PH26");

    boolean bButtonRedPushed = false;
    boolean bButtonYellowPushed = false;
    boolean bButtonGreenPushed = false;

    private static Handler mHandler ;

    ImageView m_button_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler() ;


        //TestLED th = new TestLED(this);
        //th.start();

        // GPIO 출력으로 설정합니다.
        gpio_PC4.setcfg(Gpio.GPIO_CFG_WRITE);
        gpio_PH27.setcfg(Gpio.GPIO_CFG_WRITE);
        gpio_PH24.setcfg(Gpio.GPIO_CFG_WRITE);

        gpio_PH26.setcfg(Gpio.GPIO_CFG_READ);

        gpio_PC4.output(0);
        gpio_PH27.output(0);
        gpio_PH24.output(0);

        m_button_input = findViewById(R.id.button_input);

        // 버튼 클릭 함수를 누릅니다.
        final Button m_buRed = (Button)findViewById(R.id.button_red);
        m_buRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bButtonRedPushed = !bButtonRedPushed;

                if(bButtonRedPushed == true)
                {
                    m_buRed.setBackgroundColor(Color.rgb(255, 0, 0));
                    gpio_PC4.output(1);
                }
                else {
                    m_buRed.setBackgroundColor(Color.rgb(200, 200, 200));
                    gpio_PC4.output(0);
                }
            }
        });
        final Button m_buYellow = (Button)findViewById(R.id.button_yellow);
        m_buYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bButtonYellowPushed = !bButtonYellowPushed;

                if(bButtonYellowPushed == true)
                {
                    m_buYellow.setBackgroundColor(Color.rgb(255, 255, 0));
                    gpio_PH27.output(1);
                }
                else {
                    m_buYellow.setBackgroundColor(Color.rgb(200, 200, 200));
                    gpio_PH27.output(0);
                }
            }
        });

        final Button m_buGreen = (Button)findViewById(R.id.button_green);
        m_buGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bButtonGreenPushed = !bButtonGreenPushed;

                if(bButtonGreenPushed == true)
                {
                    m_buGreen.setBackgroundColor(Color.rgb(0, 255, 0));
                    gpio_PH24.output(1);
                }
                else {
                    m_buGreen.setBackgroundColor(Color.rgb(200, 200, 200));
                    gpio_PH24.output(0);
                }
            }
        });

        m_buRed.setBackgroundColor(Color.rgb(200, 200, 200));
        m_buYellow.setBackgroundColor(Color.rgb(200, 200, 200));
        m_buGreen.setBackgroundColor(Color.rgb(200, 200, 200));


        // 핸들러로 전달할 runnable 객체. 수신 스레드 실행.
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int value = gpio_PH26.input();

                Log.v(this.toString(), "value="+value);

                if(value == 1) m_button_input.setBackgroundResource(R.drawable.button_default);
                else m_button_input.setBackgroundResource(R.drawable.button_pushed);
            }
        } ;

        // 새로운 스레드 실행 코드. 1초 단위로 현재 시각 표시 요청.
        class NewRunnable implements Runnable {
            @Override
            public void run() {

                while (true) {



                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace() ;
                    }

                    mHandler.post(runnable) ;
                }
            }
        }

        NewRunnable nr = new NewRunnable() ;
        Thread t = new Thread(nr) ;
        t.start() ;
    }
}
