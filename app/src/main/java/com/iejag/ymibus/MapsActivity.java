package com.iejag.ymibus;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private GoogleMap mMap;
    private String TAG = "Maps activity";
    private int ruta;
    private FirebaseAuth mAuth;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ruta = getIntent().getIntExtra("ruta", 0);
        Log.d("ruta", String.valueOf(ruta));
        // user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadMyPosition();

        loadRoute(ruta);
        loadDriverPosition();
    }

    private void loadDriverPosition() {

        switch (ruta) {
            case 1:
                loadAranjuez();
                break;
        }
    }

    private void loadAranjuez() {

        //Falta borrar marker cuando se inactiva el driver

        myRef = database.getReference("rutas").child("aranjuez");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot driverSnapshot : dataSnapshot.getChildren()) {
                    Ruta ruta = driverSnapshot.getValue(Ruta.class);
                    addMarker(ruta);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void addMarker(Ruta ruta) {
        LatLng driver = new LatLng(Double.parseDouble(ruta.getLatitud()), Double.parseDouble(ruta.getLongitud()));
        mMap.addMarker(new MarkerOptions()
                .position(driver)
                .title("Driver")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logomarker)));
    }

    private void loadRoute(int ruta) {
        switch (ruta) {
            case 1:
                Polyline polyline1 = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .color(Color.GREEN)
                        .add(
                                new LatLng(6.287263, -75.552701),
                                new LatLng(6.286819, -75.552701),
                                new LatLng(6.286039, -75.552685),
                                new LatLng(6.285067, -75.552698),
                                new LatLng(6.284253, -75.552837),
                                new LatLng(6.284450, -75.553623),
                                new LatLng(6.284955, -75.553628),
                                new LatLng(6.285660, -75.553596),
                                new LatLng(6.285646, -75.554210),
                                new LatLng(6.283228, -75.554333),
                                new LatLng(6.283170, -75.553757),
                                new LatLng(6.282167, -75.553768),
                                new LatLng(6.282156, -75.555604),
                                new LatLng(6.285753, -75.555426),
                                new LatLng(6.285859, -75.557236),
                                new LatLng(6.283326, -75.557350),
                                new LatLng(6.282055, -75.563543),
                                new LatLng(6.280119, -75.563477),
                                new LatLng(6.277632, -75.563873),
                                new LatLng(6.272435, -75.564978),
                                new LatLng(6.256245, -75.567783),
                                new LatLng(6.254092, -75.563077),
                                new LatLng(6.253818, -75.563071)));
                break;
        }
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
        mMap.setMyLocationEnabled(true);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            LatLng userPosition = new LatLng(location.getLatitude(), location.getLongitude());
                            Log.d(TAG, String.valueOf(location.getLatitude())+" , "+String.valueOf(location.getLongitude()));
                            mMap.addMarker(new MarkerOptions()
                                    .position(userPosition)
                                    .title("Me")
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 15));
                        }
                    }
                });
    }
}

