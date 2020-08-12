package com.example.asus.matbot;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
                    Intent i = new Intent(getApplication(), Home1.class);
                    startActivity(i);
                    finish();
                }catch (Exception e){

                }
            }
        };t.start();





    }



}
