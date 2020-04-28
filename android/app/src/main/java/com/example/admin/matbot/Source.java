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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Admin on 25-04-2020.
 */
public class Source extends AppCompatActivity {

    Spinner source, dest;
    Button call, send;
    String source_ptn, dest_ptn;
    boolean free_bot = false;

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
                source_ptn = source.getSelectedItem().toString();
                dest_ptn = dest.getSelectedItem().toString();
                Toast.makeText(Source.this, source_ptn+" -> "+dest_ptn, Toast.LENGTH_SHORT).show();
                new MyAsyncTasksw1().execute();
                if(free_bot == true){
                    send.setVisibility(View.VISIBLE);
                }else{
                    //Toast.makeText(Source.this, "Bot is busy!!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source_ptn = source.getSelectedItem().toString();
                dest_ptn = dest.getSelectedItem().toString();
                Toast.makeText(Source.this, source_ptn + " -> " + dest_ptn, Toast.LENGTH_SHORT).show();
                new MyAsyncTasksw2().execute();

            }
        });


    }


    private class MyAsyncTasksw1 extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressDialog = new ProgressDialog(Source.this);

        protected void onPreExecute()
        {
            progressDialog.setMessage("Please Wait");
            progressDialog.show();


        }
        public void postData(String source_ptn,String dest_ptn)
        {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.170/phpmyfiles/call_api.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("f1", usn));
                nameValuePairs.add(new BasicNameValuePair("source", source_ptn));
                nameValuePairs.add(new BasicNameValuePair("dest", dest_ptn));




                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.d("nameValuePairs", "" + nameValuePairs);
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();


                // If the response does not enclose an entity, there is no need
                if (entity != null) {
                    InputStream instream = entity.getContent();

                    String result;
                    result = convertStreamToString(instream);
                    Log.d("respo", "" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("success");
                    String state = jsonObject.getString("free_bot");
                    Toast.makeText(Source.this, "result " + result + "\nstate " + state, Toast.LENGTH_SHORT).show();

                    if(state == "true"){
                        free_bot = true;
                    }

                    Log.d("status", "" + status);

                    Toast.makeText(Source.this, "result " + result + "\nstatus " + status, Toast.LENGTH_SHORT).show();




                }


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground (String...params)
        {
            postData(source_ptn, dest_ptn);
            progressDialog.dismiss();
            return null;
        }
        private  String convertStreamToString(InputStream is)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }


    private class MyAsyncTasksw2 extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressDialog = new ProgressDialog(Source.this);

        protected void onPreExecute()
        {
            progressDialog.setMessage("Please Wait");
            progressDialog.show();


        }
        public void postData(String source_ptn,String dest_ptn)
        {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.170/phpmyfiles/send_api.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("f1", usn));
                nameValuePairs.add(new BasicNameValuePair("source", source_ptn));
                nameValuePairs.add(new BasicNameValuePair("destination", dest_ptn));




                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                Log.d("nameValuePairs", "" + nameValuePairs);
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();


                // If the response does not enclose an entity, there is no need
                if (entity != null) {
                    InputStream instream = entity.getContent();

                    String result;
                    result = convertStreamToString(instream);
                    Log.d("respo", "" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("success");

                    Log.d("status", "" + status);

                    Toast.makeText(Source.this, "result " + result + "\nstatus " + status, Toast.LENGTH_SHORT).show();




                }


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground (String...params)
        {
            postData(source_ptn, dest_ptn);
            progressDialog.dismiss();
            return null;
        }
        private  String convertStreamToString(InputStream is)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            try {
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }




}
