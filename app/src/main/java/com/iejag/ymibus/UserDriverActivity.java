package com.iejag.ymibus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UserDriverActivity extends AppCompatActivity {
    ImageButton user;
    ImageButton driver;
    ImageButton information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_driver);
        user = findViewById(R.id.userimage);
        driver = findViewById(R.id.busimage);
        information = findViewById(R.id.imbinfo);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDriverActivity.this,RutasActivity.class);
                startActivity(intent);
            }
        });

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (UserDriverActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (UserDriverActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });


    }
}
