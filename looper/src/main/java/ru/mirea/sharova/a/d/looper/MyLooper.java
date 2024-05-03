package ru.mirea.sharova.a.d.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }

    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                String age = msg.getData().getString("AGE");
                String prof = msg.getData().getString("PROF");
                int years = Integer.parseInt(age);
                Log.d("MyLooper", String.format("Age: %s, Profession: %s", age, prof));

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", String.format("Age: %s, Profession: %s", age, prof));
                message.setData(bundle);
                mainHandler.sendMessageDelayed(message, years * 1000);
            }
        };
        Looper.loop();
    }
}

