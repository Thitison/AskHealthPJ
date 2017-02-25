package com.example.administrator.askhealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Report extends Activity {


    EditText editTextAddress, editTextSubject, editTextMessage;
    Button buttonIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        final EditText editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextAddress.setText("manghong.nice@gmail.com");


        editTextSubject = (EditText)findViewById(R.id.editTextSubject);
        editTextMessage = (EditText)findViewById(R.id.editTextMessage);

        Button buttonIntent = (Button)findViewById(R.id.buttonIntent);
        buttonIntent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");
                intent.putExtra(Intent.EXTRA_EMAIL
                        , new String[] {editTextAddress.getText().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT
                        , editTextSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT
                        , editTextMessage.getText().toString());
                startActivity(Intent.createChooser(intent, "Send email with"));

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
