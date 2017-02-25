package com.example.administrator.askhealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.example.administrator.adapter.PostAdapter;
import com.example.administrator.model.FitnessResponseModel;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class FitnessActivity extends AppCompatActivity {
    private ListView jsonListview;
    private ArrayList<String> exData;
    private ProgressDialog progressDialog;
    private PostAdapter postAdapter;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //เป็น class model ไว้สำหรับรับ reasponse data จาก server
    private FitnessResponseModel responseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        //ปุ่มกดไปหน้าโพส
        ImageView imageView;
        imageView = (ImageView) findViewById(R.id.homebar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent go = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(go);
            }
        });

        imageView = (ImageView) findViewById(R.id.postbar);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent go = new Intent(getApplicationContext(), PoststatusActivity.class);
                go.putExtra("category","fitness");
                startActivity(go);
            }
        });
        jsonListview = (ListView) findViewById(R.id.json_listView);
        exData = new ArrayList<String>();
        //exData.add("Test1");
        //exData.add("Test2");
        //exData.add("Test3");
        //exData.add("Test4");
        //exData.add("Test5");

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(FitnessActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Downloading...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://sporthealth.esy.es/api/ViewPost.php?category=fitness");

                    URLConnection urlConnection = url.openConnection();

                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                    httpURLConnection.setAllowUserInteraction(false);
                    httpURLConnection.setInstanceFollowRedirects(true);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();

                    InputStream inputStream = null;
                    if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                        inputStream = httpURLConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();

                    /*JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray exArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < exArray.length(); i++) {
                        JSONObject jsonObj = exArray.getJSONObject(i);
                        exData.add(jsonObj.getString("name"));
                        exData.add(jsonObj.getString("description"));
                        exData.add(jsonObj.getString("likes"));
                    }*/

                    /*เปลี่ยนมาใช้ Gson ในการ map ข้อมูลแทน JSONObject */
                    responseModel = new Gson().fromJson(stringBuilder.toString(), FitnessResponseModel.class);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(FitnessActivity.this, android.R.layout.simple_list_item_2, android.R.id.text1, exData);

                initPostList(responseModel);
                progressDialog.dismiss();
            }
        }.execute();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initPostList(final FitnessResponseModel responseModel){
        //Set Custom Listview ด้วยการสร้าง Adapter ขึ้นมาเองในที่นี้สร้าง PostAdapter 
        postAdapter = new PostAdapter(FitnessActivity.this, responseModel.getData());
        jsonListview.setAdapter(postAdapter);
        postAdapter.setOnLikeClickListener(new PostAdapter.OnLikeClickListener() {
            @Override
            public void onLikeClick(int position, boolean isLiked) {
                String post_id = responseModel.getData().get(position).getPost_id();
                if (isLiked){
                    //ถ้า like ก็จะส่งไปอัพเดทที่ server โดยการส่ง post_id ไป
                }else {
                    //ถ้า unlike ก็จะส่งไปอัพเดทที่ server โดยการส่ง post_id ไปเช่นกัน
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Fitness Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.administrator.askhealth/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Fitness Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.administrator.askhealth/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

