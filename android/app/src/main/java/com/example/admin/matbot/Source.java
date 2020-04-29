package com.example.admin.matbot;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by Admin on 25-04-2020.
 */
public class Source extends AppCompatActivity {

    Spinner source, dest;
    Button call, send;
    String source_ptn, dest_ptn;
    boolean free_bot = false;
    String status11;

    private InputStream is=null;
    private String result=null;
    private String line=null;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.source);

        source = (Spinner)findViewById(R.id.spn_source);
        dest = (Spinner)findViewById(R.id.spn_dest);
        call = (Button)findViewById(R.id.btn_call_bot);
        send = (Button)findViewById(R.id.btn_send_bot);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mylist = new ArrayList<String>();

                mylist.add(String.valueOf(source.getSelectedItem().toString()));
                mylist.add(String.valueOf(dest.getSelectedItem().toString()));

                Toast.makeText(Source.this, mylist.get(0) + " -> " + mylist.get(1), Toast.LENGTH_SHORT).show();
                new sendUserDetailTOServer().execute(mylist);

            }
        });








        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source_ptn = source.getSelectedItem().toString();
                dest_ptn = dest.getSelectedItem().toString();
                Toast.makeText(Source.this, source_ptn + " -> " + dest_ptn, Toast.LENGTH_SHORT).show();

            }
        });


    }


    public class sendUserDetailTOServer extends AsyncTask< ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(Source.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {

            ArrayList<String> passed = alldata[0]; //get passed arraylist
            String source11 = passed.get(0);
            String dest11 = passed.get(1);


            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("source", source11 ));
                nameValuePairs.add(new BasicNameValuePair("dest", dest11));

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.170/phpmyfiles/call_api.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs ,"UTF-8")); // UTF-8  support multi language
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            }
            catch(Exception e)
            {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try
            {
                JSONObject json_data = new JSONObject(result);

                    try {
                        String success =json_data.getString("success");
                        free_bot = json_data.getBoolean("free_bot");


                        if (free_bot) {
                            send.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(Source.this, "Bot is busy!!", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(Source.this, success, Toast.LENGTH_SHORT).show();


                    }catch (Exception e){

                    }



            }
            catch(Exception e)
            {
            }
        }

    }




}
