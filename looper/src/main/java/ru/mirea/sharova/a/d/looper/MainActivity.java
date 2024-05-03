package ru.mirea.sharova.a.d.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import ru.mirea.sharova.a.d.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("result"));
            }
        };

        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = binding.editTextAge.getText().toString();
                String prof = binding.editTextProf.getText().toString();

                if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(prof)) {
                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    int years = Integer.parseInt(age);
                    bundle.putString("PROF", prof);
                    bundle.putString("AGE", age);
                    msg.setData(bundle);
                    myLooper.mHandler.sendMessageDelayed(msg, years * 1000);
                } else {
                    Log.d(MainActivity.class.getSimpleName(), "Error: Age or profession field is empty");
                }
            }
        });
    }
}
