package com.example.admin.matbot1;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Admin on 03-05-2020.
 */
public class AddPoint extends AppCompatActivity {

    EditText label, type, conn, color;
    Button addpoint;
    InputStream is;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpoint);

        label = (EditText)findViewById(R.id.et_point_label);
        type = (EditText)findViewById(R.id.et_point_type);
        conn = (EditText)findViewById(R.id.et_point_connection);
        color = (EditText)findViewById(R.id.et_point_color);
        addpoint = (Button)findViewById(R.id.btn_add_point);

        addpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> mylist = new ArrayList<String>();
                mylist.add(String.valueOf(label.getText().toString()));
                mylist.add(String.valueOf(type.getText().toString()));
                mylist.add(String.valueOf(conn.getText().toString()));
                mylist.add(String.valueOf(color.getText().toString()));

                new AddPointApiCall().execute(mylist);
            }
        });

    }


    public class AddPointApiCall extends AsyncTask< ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(AddPoint.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {

            //get passed arraylist
            ArrayList<String> passed = alldata[0];
            String label1 = passed.get(0);
            String type1 = passed.get(1);
            String conn1 = passed.get(2);
            String color1 = passed.get(3);



            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.43.170:5000/call_bot_api");
                nameValuePairs.add(new BasicNameValuePair("label", label1));
                nameValuePairs.add(new BasicNameValuePair("type", type1));
                nameValuePairs.add(new BasicNameValuePair("connection", conn1));
                nameValuePairs.add(new BasicNameValuePair("color", color1));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs ,"UTF-8")); // UTF-8  support multi language
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                BufferedReader reader = new BufferedReader (new InputStreamReader(is,"iso-8859-1"),8);
                StringBuilder sb = new StringBuilder();
                String line;
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
                    Toast.makeText(getApplication(), json_data.get("success").toString(), Toast.LENGTH_SHORT).show();



                }catch (Exception e){

                }



            }
            catch(Exception e)
            {
            }
        }

    }


}
