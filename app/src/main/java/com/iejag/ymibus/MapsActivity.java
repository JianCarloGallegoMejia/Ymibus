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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    Marker mPreviousMarker;
    private GoogleMap mMap;
    private String TAG = "Maps activity";
    private int ruta;
    private FirebaseAuth mAuth;
    private FusedLocationProviderClient fusedLocationClient;
    private BitmapDescriptor bd;

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
        loadRoute();
        loadDriverPosition();
    }

    private void loadDriverPosition() {

        switch (ruta) {
            case 1:
                loadDrivers("aranjuez");
                break;
            case 2:
                loadDrivers("integrado");
                break;
        }
    }

    private void loadDrivers(String bus) {

        //Falta borrar marker cuando se inactiva el driver

        myRef = database.getReference("rutas").child(bus);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();
                loadRoute();
                for (DataSnapshot driverSnapshot : dataSnapshot.getChildren()) {
                    Ruta ruta = driverSnapshot.getValue(Ruta.class);
                    Log.d(TAG, ruta.getLatitud() + " , " + ruta.getLongitud());
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
        mPreviousMarker = mMap.addMarker(new MarkerOptions()
                .position(driver)
                .title("Driver")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.logomarker)));
    }

    private void loadRoute() {
        Polyline polyline;
        switch (ruta) {
            case 1:
                polyline = mMap.addPolyline(new PolylineOptions()
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
            case 2:
                polyline = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .color(Color.GREEN)
                        .add(
                                new LatLng(6.287008, -75.552697),
                                new LatLng(6.284264, -75.552819),
                                new LatLng(6.284469, -75.553629),
                                new LatLng(6.285679, -75.553643),
                                new LatLng(6.285814, -75.555811),
                                new LatLng(6.285967, -75.559896),
                                new LatLng(6.286127, -75.562720),
                                new LatLng(6.287388, -75.562656),
                                new LatLng(6.287442, -75.563407),
                                new LatLng(6.288777, -75.563402),
                                new LatLng(6.289391, -75.562908),
                                new LatLng(6.289289, -75.562490),
                                new LatLng(6.289404, -75.562368),
                                new LatLng(6.290420, -75.562723),
                                new LatLng(6.291163, -75.562388),
                                new LatLng(6.291171, -75.562527),
                                new LatLng(6.290833, -75.562892)));

                LatLng inicio = new LatLng(6.287008, -75.552697);

                mMap.addMarker(new MarkerOptions()
                        .position(inicio)
                        .title("Inicio"));

                LatLng fin = new LatLng(6.290833, -75.562892);

                mMap.addMarker(new MarkerOptions()
                        .position(fin)
                        .title("Fin"));
                break;

        /*        LatLng inicio = new LatLng(6.287008, -75.552697);
                bd = BitmapDescriptorFactory.fromResource(R.drawable.ic_flag_green_24dp);
                mMap.addMarker(new MarkerOptions()
                        .position(inicio)
                        .title("Inicio")
                .icon(bd));

                LatLng fin = new LatLng(6.290833, -75.562892);
                bd = BitmapDescriptorFactory.fromResource(R.drawable.ic_flag_red_24dp);
                mMap.addMarker(new MarkerOptions()
                        .position(fin)
                        .title("Fin")
                        .icon(bd));
                break;*/
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
                            Log.d(TAG, location.getLatitude() + " , " + location.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(userPosition)
                                    .title("Me"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPosition, 15));
                        }
                    }
                });
    }
}


