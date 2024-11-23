package com.example.digitoolsapp;

import static com.example.digitoolsapp.HomePage.UID;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Adapter.CustomInfoWindowAdapter;
import com.example.digitoolsapp.Data.ListItem;
import com.example.digitoolsapp.Data.ListItemMap;
import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.Data.Marker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.digitoolsapp.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;
    Gson gson;
    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;
    Marker[] markers;
    Listtools[] listtools;
    ListItem[] listitems;
    private FusedLocationProviderClient fusedLocationProviderClient;
    String topic ;

    private static final int REQUEST_CODE = 101;
    Location currentLocation;

    LatLng alorsetar;
    private String url = "https://digiproj.sut.ac.th/dgtprj65_05/android/getLatLon.php";
    RequestQueue requestQueue;
    double lati,lont ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        topic = intent.getStringExtra(UID);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gson = new GsonBuilder().create();
        markerOptions = new Vector<>();
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
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        // Add a marker in Sydney and move the camera
        //mMap.addMarker(marker);
//
        for (MarkerOptions mark : markerOptions){
            mMap.addMarker(mark);
        }
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alorsetar,8));
        // adding on click listener to marker of google maps.
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(@NonNull com.google.android.gms.maps.model.Marker marker) {
//                // on marker click we are getting the title of our marker
//                // which is clicked and displaying it in a toast message.
//                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                ListItem itemMap = (ListItem) marker.getTag();
//               String ltool = itemMap.toolid;
//               String ntool = itemMap.name;
//               String qtool = itemMap.quantity;
//                String m = marker.getTag().toString();
//                builder.setIcon(R.drawable.logo);
//                builder.setTitle(m);
//                builder.setMessage(m);
//
//                // set the neutral button to do some actions
//                builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        Toast.makeText(MapsActivity.this,  topic, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//                alertDialog.getWindow().setGravity(Gravity.CENTER);
//
//
//                return false;
//            }
//        });

        enableMyLocation();
        sendRequest();
    }
    private void enableMyLocation() {

        String perms[] = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_NETWORK_STATE"};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                Log.d("DigitoolsApp", "permission granted");
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission

            Log.d("DigitoolsApp", "permission denied");
            ActivityCompat.requestPermissions(this, perms, 200);

        }
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(5000);
        LocationCallback locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
//                Toast.makeText(getApplicationContext(),"Result:" + locationResult,Toast.LENGTH_LONG).show();

                if(locationResult == null){
                    Toast.makeText(getApplicationContext(),"Null",Toast.LENGTH_LONG).show();
                    return;

                }
                for(Location location:locationResult.getLocations()){
                    if(locationResult == null){
                        Toast.makeText(getApplicationContext(),"Current:" + location.getLongitude(),Toast.LENGTH_LONG).show();
                        return;

                    }
                }

            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lati = location.getLatitude();
                    lont = location.getLongitude();
                    LatLng latLng =new LatLng(lati, lont);
                    String Nameuser;
                    Intent b = getIntent();
                    Nameuser = b.getStringExtra(UID);


                    MarkerOptions markerf = new MarkerOptions()
                            .position(latLng)
                            .title(Nameuser);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    mMap.addMarker(markerf);
                    mMap.clear();
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }
    public void sendRequest() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, onSuccess, onError);
        requestQueue.add(stringRequest);

    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            markers = gson.fromJson(response,  Marker[].class);
            Log.d("Marker" , "point" + markers.length);

            if(markers.length <1){
                Toast.makeText(getApplicationContext(),"ไม่มีใครยืมเครื่องมือ" , Toast.LENGTH_LONG).show();
                return;
            }

            for (Marker info: markers){
                String id = info.uniqueId;
                Double lat = Double.parseDouble(info.latitude);
                Double lon = Double.parseDouble(info.longitude);
                String email = info.email;
                String username = info.username;
                String phonenum = info.phonenum;
                List<ListItem> List = info.list;
                StringBuilder stringBuilder = new StringBuilder();

                for (ListItem item: List){

                    String toolid = item.toolid;
                    String name = item.name;
                    String quantity = item.quantity;
                    stringBuilder.append(name).append("/");

                }
                String Tooldigi = stringBuilder.toString();
                MarkerOptions marker = new MarkerOptions()
                        .position(new LatLng(lat,lon))
                        .title(id)
                        .snippet("ชื่อ : " + username +"\n"+"อีเมล : " + email + "\n"+"เบอร์มือถือ : " + phonenum)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                mMap.addMarker(marker);

            }


        }


    };
    public Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_LONG).show();
        }

    };


}