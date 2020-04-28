package com.example.admin.matbot;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Admin on 25-04-2020.
 */
public class Route extends AppCompatActivity {

    EditText source, dest, color1, color2, color3, color4, color5, color6, color7, color8;
    Button addcolor, addroute;
    int cur = 1;
    String source1, dest1, col1 = null, col2 = null, col3 = null, col4 = null, col5 = null, col6 = null, col7 = null, col8 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route);
        source = (EditText)findViewById(R.id.et_route_source);
        dest = (EditText)findViewById(R.id.et_route_dest);
        color1 = (EditText)findViewById(R.id.et_route_color1);
        color2 = (EditText)findViewById(R.id.et_route_color2);
        color3 = (EditText)findViewById(R.id.et_route_color3);
        color4 = (EditText)findViewById(R.id.et_route_color4);
        color5 = (EditText)findViewById(R.id.et_route_color5);
        color6 = (EditText)findViewById(R.id.et_route_color6);
        color7 = (EditText)findViewById(R.id.et_route_color7);
        color8 = (EditText)findViewById(R.id.et_route_color8);
        addcolor = (Button)findViewById(R.id.btn_route_addcolor);
        addroute = (Button)findViewById(R.id.btn_route_addroute);




        addcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (cur){
                    case 1: color2.setVisibility(View.VISIBLE);
                        break;
                    case 2: color3.setVisibility(View.VISIBLE);
                        break;
                    case 3: color4.setVisibility(View.VISIBLE);
                        break;
                    case 4: color5.setVisibility(View.VISIBLE);
                        break;
                    case 5: color6.setVisibility(View.VISIBLE);
                        break;
                    case 6: color7.setVisibility(View.VISIBLE);
                        break;
                    case 7: color8.setVisibility(View.VISIBLE);
                        break;
                    default:
                        Toast.makeText(Route.this, "No More Colors Can Be Added!!!", Toast.LENGTH_SHORT).show();
                }
                cur++;
            }
        });


        addroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source1 = source.getText().toString();
                dest1 = dest.getText().toString();
                col1 = color1.getText().toString();
                col2 = color2.getText().toString();
                col3 = color3.getText().toString();
                col4 = color4.getText().toString();
                col5 = color5.getText().toString();
                col6 = color6.getText().toString();
                col7 = color7.getText().toString();
                col8 = color8.getText().toString();

                new MyAsyncTasksw1().execute();
                Toast.makeText(Route.this, source.getText()+"\n"+dest.getText()+"\n"+color1.getText()+"\n"+color2.getText()+"\n"+color3.getText(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private class MyAsyncTasksw1 extends AsyncTask<String, Void, String>
    {
        private ProgressDialog progressDialog = new ProgressDialog(Route.this);

        protected void onPreExecute()
        {
            progressDialog.setMessage("Please Wait");
            progressDialog.show();


        }
        public void postData(String source1,String dest1,String col1,String col2,String col3,String col4,String col5,String col6,String col7,String col8)
        {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.43.170/phpmyfiles/insert_api.php");

            try {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("f1", usn));
                nameValuePairs.add(new BasicNameValuePair("source1", source1));
                nameValuePairs.add(new BasicNameValuePair("dest1", dest1));
                nameValuePairs.add(new BasicNameValuePair("color1", col1));
                nameValuePairs.add(new BasicNameValuePair("color2", col2));
                nameValuePairs.add(new BasicNameValuePair("color3", col3));
                nameValuePairs.add(new BasicNameValuePair("color4", col4));
                nameValuePairs.add(new BasicNameValuePair("color5", col5));
                nameValuePairs.add(new BasicNameValuePair("color6", col6));
                nameValuePairs.add(new BasicNameValuePair("color7", col7));
                nameValuePairs.add(new BasicNameValuePair("color8", col8));



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

                    Toast.makeText(Route.this, "result "+result+"\nstatus "+status, Toast.LENGTH_SHORT).show();




                }


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground (String...params)
        {
            postData(source1, dest1, col1,col2,col3,col4,col5,col6,col7,col8);
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
