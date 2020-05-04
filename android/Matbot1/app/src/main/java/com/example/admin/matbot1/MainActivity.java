package com.example.admin.matbot1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                    Intent i = new Intent(getApplication(), Home.class);
                    startActivity(i);
                    finish();
                }catch (Exception e){

                }
            }
        };t.start();





        }









}
