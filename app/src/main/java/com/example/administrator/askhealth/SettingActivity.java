package com.example.administrator.askhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button Logout = (Button)findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button about = (Button)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AboutAskHealth.class);
                startActivity(intent);
            }
        });


        Button report = (Button)findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Report.class);
                startActivity(intent);
            }
        });

        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.homebar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent go = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(go);
            }
        });
    }
}
