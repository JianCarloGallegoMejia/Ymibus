package com.iejag.ymibus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//poner boton cerrar sesion

public class DriverActivity extends AppCompatActivity {
    Switch activoinactivo;
    private FirebaseAuth mAuth;
    private String latitud;
    private String longitud;
    private String TAG = "Driver Activity";
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("rutas");
        activoinactivo = findViewById(R.id.switch1);
        activoinactivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    activoinactivo.setText("Activo");
                    loadMyPosition();
                }
                else{
                    activoinactivo.setText("Inactivo");
                    myRef.child("aranjuez").child(user.getUid()).removeValue();
                }
            }
        });
    }


    private void loadMyPosition() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            updatePosition(location.getLatitude(), location.getLongitude());
                            Log.d(TAG, String.valueOf(location.getLatitude())+" , "+String.valueOf(location.getLongitude()));
                        }
                    }
                });
    }

    private void updatePosition(double latitude, double longitude) {
        Ruta rutas = new Ruta(user.getUid(), String.valueOf(latitude),String.valueOf(longitude));
        myRef.child("aranjuez").child(user.getUid()).setValue(rutas);
    }
}
