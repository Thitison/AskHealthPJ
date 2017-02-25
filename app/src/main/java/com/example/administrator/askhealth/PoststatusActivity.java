package com.example.administrator.askhealth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.administrator.googleplacesapitutorial.CheckinActivity;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;


public class PoststatusActivity extends AppCompatActivity {
    public String category = null;
    String txtMessage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poststatus);

        Button Checkin = (Button)findViewById(R.id.Checkin);
        Checkin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(),CheckinActivity.class);
                startActivity(intent);
            }
        });

        Intent bundleID = getIntent();
        category = bundleID.getStringExtra("category");

        Bundle data = getIntent().getExtras();
        txtMessage = data.getString("txtMessage");

        final EditText txtMessage_A = (EditText)findViewById(R.id.txtMessage);

        txtMessage_A.setText(txtMessage);

        Button editClub = (Button)findViewById(R.id.btnpost);
        editClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMessage = txtMessage_A.getText().toString();

                new post().execute(txtMessage, category);

            }
        });


    }

    public class post extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = new FormBody.Builder()
                    .add("txtMessage_A",params[0])
                    .add("category_A", params[1])
                    .build();

            OkHttpClient oktest = new OkHttpClient();
            Request request = new Request.Builder().url("http://sporthealth.esy.es/api/AddPost.php").post(body).build();

            try {
                Response res = oktest.newCall(request).execute();
                Log.d("AAA",res.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            BackToHome();
        }

    }

    public void BackToHome (){
        Intent intent = new Intent(getBaseContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }






}
