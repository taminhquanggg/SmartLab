package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.smartlab.R;

public class MainStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainStartActivity.this, MainGetStartedActivity.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        }, 3000);
    }
}