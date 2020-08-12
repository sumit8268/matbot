package com.example.asus.matbot;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CommandBot extends AppCompatActivity {


    Spinner source, dest;
    Button call_bot, send_bot;
    ArrayList<String> spinner_data = new ArrayList<String>();
    InputStream is;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commandbot);

        source = (Spinner) findViewById(R.id.spn_source);
        dest = (Spinner) findViewById(R.id.spn_dest);
        call_bot = (Button) findViewById(R.id.btn_call_bot);
        send_bot = (Button) findViewById(R.id.btn_send_bot);

        new GetPointsApiCall().execute();


        call_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mylist = new ArrayList<String>();
                mylist.add(String.valueOf(source.getSelectedItem().toString()));
                mylist.add(String.valueOf(dest.getSelectedItem().toString()));

                new CallBotApiCall().execute(mylist);
            }
        });

        send_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mylist = new ArrayList<String>();
                mylist.add(String.valueOf(source.getSelectedItem().toString()));
                mylist.add(String.valueOf(dest.getSelectedItem().toString()));

                new SendBotApiCall().execute(mylist);
            }
        });

    }


    public class GetPointsApiCall extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(CommandBot.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {

            //get passed arraylist


            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.103:5000/get_points_api");


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")); // UTF-8  support multi language
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONArray json_data = new JSONArray(result);

                try {
                    Toast.makeText(getApplication(), json_data.get(0).toString(), Toast.LENGTH_SHORT).show();
                    for (int i = 1; i < json_data.length(); i++) {
                        spinner_data.add(json_data.get(i).toString());

                    }


                    ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(getBaseContext(), R.layout.support_simple_spinner_dropdown_item, spinner_data);
                    source.setAdapter(spinneradapter);
                    dest.setAdapter(spinneradapter);

                } catch (Exception e) {

                }


            } catch (Exception e) {
            }
        }

    }


    public class CallBotApiCall extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(CommandBot.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {

            //get passed arraylist
            ArrayList<String> passed = alldata[0];
            String source1 = passed.get(0);
            String dest1 = passed.get(1);


            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.103:5000/call_bot_api");
                nameValuePairs.add(new BasicNameValuePair("source", source1));
                nameValuePairs.add(new BasicNameValuePair("destination", dest1));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")); // UTF-8  support multi language
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONObject json_data = new JSONObject(result);

                try {
                    Toast.makeText(getApplication(), json_data.get("success").toString(), Toast.LENGTH_SHORT).show();
                    Boolean free_bot = json_data.getBoolean("free_bot");
                    Toast.makeText(getApplication(), free_bot.toString(), Toast.LENGTH_SHORT).show();

                    if (free_bot != false) {
                        send_bot.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplication(), "Bot will reach here please wait!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplication(), "Bot is busy try again later!!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                }


            } catch (Exception e) {
            }
        }

    }


    public class SendBotApiCall extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(CommandBot.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {

            //get passed arraylist
            ArrayList<String> passed = alldata[0];
            String source1 = passed.get(0);
            String dest1 = passed.get(1);


            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.103:5000/send_bot_api");
                nameValuePairs.add(new BasicNameValuePair("source", source1));
                nameValuePairs.add(new BasicNameValuePair("destination", dest1));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8")); // UTF-8  support multi language
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                result = sb.toString();
            } catch (Exception e) {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            try {
                JSONObject json_data = new JSONObject(result);

                try {
                    Toast.makeText(getApplication(), json_data.get("success").toString(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }


            } catch (Exception e) {
            }
        }

    }
}