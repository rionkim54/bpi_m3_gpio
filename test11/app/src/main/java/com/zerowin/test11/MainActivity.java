package com.zerowin.test11;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Gpio gpio_PC4 = new Gpio("PC4");
    private Gpio gpio_PC7 = new Gpio("PC7");
    private Gpio gpio_PC17 = new Gpio("PC17");

    boolean bButtonRedPushed = false;
    boolean bButtonYellowPushed = false;
    boolean bButtonGreenPushed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TestLED th = new TestLED(this);
        //th.start();

        // GPIO 출력으로 설정합니다.
        gpio_PC4.setcfg(Gpio.GPIO_CFG_WRITE);
        gpio_PC7.setcfg(Gpio.GPIO_CFG_WRITE);
        gpio_PC17.setcfg(Gpio.GPIO_CFG_WRITE);

        gpio_PC4.output(0);
        gpio_PC7.output(0);
        gpio_PC17.output(0);

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
                    gpio_PC7.output(1);
                }
                else {
                    m_buYellow.setBackgroundColor(Color.rgb(200, 200, 200));
                    gpio_PC7.output(0);
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
                    gpio_PC17.output(1);
                }
                else {
                    m_buGreen.setBackgroundColor(Color.rgb(200, 200, 200));
                    gpio_PC17.output(0);
                }
            }
        });

        m_buRed.setBackgroundColor(Color.rgb(200, 200, 200));
        m_buYellow.setBackgroundColor(Color.rgb(200, 200, 200));
        m_buGreen.setBackgroundColor(Color.rgb(200, 200, 200));
    }
}
