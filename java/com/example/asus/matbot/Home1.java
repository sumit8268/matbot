package com.example.asus.matbot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Home1 extends AppCompatActivity {

    Button cmd_bot, new_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        cmd_bot = (Button) findViewById(R.id.btn_cmd_bot);
        new_route = (Button) findViewById(R.id.btn_new_route);

        cmd_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), CommandBot.class);
                startActivity(i);
            }
        });

        new_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), AddPoint.class);
                startActivity(i);
            }
        });

    }
}
