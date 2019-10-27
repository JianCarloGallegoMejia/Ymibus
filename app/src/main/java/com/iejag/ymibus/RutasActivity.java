package com.iejag.ymibus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RutasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);
    }

    public void onClickedButton(View view) {
        Intent intent = new Intent(RutasActivity.this, MapsActivity.class);
        switch (view.getId()) {
            case R.id.btnAranjuez:
                intent.putExtra("ruta", 1);
                break;
            case R.id.btnIntegrado:
                intent.putExtra("ruta", 2);
                break;
        }
        startActivity(intent);
    }
}
