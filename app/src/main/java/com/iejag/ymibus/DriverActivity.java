package com.iejag.ymibus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class DriverActivity extends AppCompatActivity {
    Switch activoinactivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        activoinactivo = findViewById(R.id.switch1);
        activoinactivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    activoinactivo.setText("Activo");
                }
                else{
                    activoinactivo.setText("Inactivo");
                }
            }
        });
    }
}
