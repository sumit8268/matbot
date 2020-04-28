package com.example.admin.matbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admin on 25-04-2020.
 */
public class Home extends AppCompatActivity {

    Button cmd_bot, new_route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        cmd_bot = (Button)findViewById(R.id.btn_cmd_bot);
        new_route = (Button)findViewById(R.id.btn_new_route);

        cmd_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Source.class);
                startActivity(i);
            }
        });

        new_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), Route.class);
                startActivity(i);
            }
        });
    }
}
