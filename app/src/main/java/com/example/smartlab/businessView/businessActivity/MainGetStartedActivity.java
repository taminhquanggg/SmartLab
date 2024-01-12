package com.example.smartlab.businessView.businessActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.smartlab.R;

public class MainGetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_get_started);

        ImageView imageView = findViewById(R.id.pic_imgGetStarted);
        Glide.with(this).asGif().load(R.drawable.cover_get_started).into(imageView);

        final Button btnPatient = findViewById(R.id.btn_Patient);

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainGetStartedActivity.this, PatientGetOTPActivity.class));
            }
        });
    }
}