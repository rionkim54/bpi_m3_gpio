package com.zerowin.test11;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestLED extends Thread {

    private String TAG = "Test-LED";

    private Gpio gpio_PC4 = new Gpio("PC4");
    private Gpio gpio_PC7 = new Gpio("PC7");
    private Gpio gpio_PC17 = new Gpio("PC17");

    Activity activity;
    TestLED(Activity activity) {

        this.activity = activity;

        gpio_PC4.setcfg(Gpio.GPIO_CFG_WRITE);
        gpio_PC7.setcfg(Gpio.GPIO_CFG_WRITE);
        gpio_PC17.setcfg(Gpio.GPIO_CFG_WRITE);
    }

    boolean bLedOn = false;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Runtime command = Runtime.getRuntime();
            Process proc;
            DataOutputStream opt;
            proc = command.exec("su");
            opt = new DataOutputStream(proc.getOutputStream());

            Log.d(TAG, "LED Testing start.");
            while (true) {
                /*
                int value = gpio.input();
                Log.d(TAG, "value="+value);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } */

                LEDRun(opt);

                bLedOn = !bLedOn;
                gpio_PC4.output(bLedOn ? 1 : 0);
                gpio_PC7.output(bLedOn ? 1 : 0);
                gpio_PC17.output(bLedOn ? 1 : 0);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void LEDRun(DataOutputStream opt) {

        try {

            opt.writeBytes("echo 3000 > /sys/bus/platform/devices/reg-81x-cs-gpio0ldo/max_microvolts\n");
            opt.writeBytes("echo 3000 > /sys/bus/platform/devices/reg-81x-cs-gpio1ldo/max_microvolts\n");
            Log.d(TAG, "LED ON.");

            //gpio5.output(0);
            //gpio6.output(1);

            Thread.sleep(2000);

            opt.writeBytes("echo 0 > /sys/bus/platform/devices/reg-81x-cs-gpio0ldo/max_microvolts\n");
            opt.writeBytes("echo 0 > /sys/bus/platform/devices/reg-81x-cs-gpio1ldo/max_microvolts\n");

            Log.d(TAG, "LED OFF.");
            //gpio5.output(1);
            //gpio6.output(0);

            Thread.sleep(2000);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}