package com.example.digitoolsapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Adapter.HomeAdapter;
import com.example.digitoolsapp.Data.Tools;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service  {
    public static ArrayList<LatLng> locationArrayList = new ArrayList<LatLng>();
    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/addlocation.php";

    FusedLocationProviderClient fusedLocationClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    private String uid , latitude , longitude;
    private TimerTask datatask;
    private Timer datatimer;

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());

    }

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Notification();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) createNotificationChanel() ;
        else startForeground(
                1,
                new Notification()
        );

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setMaxWaitTime(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Location location =  locationResult.getLastLocation();
//                Toast.makeText(getApplicationContext(),
//                        "Lat: "+Double.toString(location.getLatitude()) + '\n' +
//                                "Long: " + Double.toString(location.getLongitude()), Toast.LENGTH_LONG).show();
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
                Log.d("TEST" ,latitude + longitude + uid);

                //locationArrayList.add(new LatLng(location.getLatitude(), location.getLongitude()));

               /* for (Location location : locationResult.getLocations()) {
                  location
                }*/

                datatask = new TimerTask() {
                    @Override
                    public void run() {
                        Extragps(uid,latitude,longitude);
                    }
                };
                datatimer = new Timer();
                datatimer.scheduleAtFixedRate(datatask , 0 , 10000);
            }
        };
        startLocationUpdates();

    }

    private void Extragps(String uid,String latitude,String longitude) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonArrayRequest = new  StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>()  {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if(error) {

                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<>();
                data.put("UID", uid);
                data.put("latitude", latitude);
                data.put("longitude", longitude);

                return data;

            }

        };


        queue.add(jsonArrayRequest);

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChanel() {
        String notificationChannelId = "Location channel id";
        String channelName = "Background Service";

        NotificationChannel chan = new NotificationChannel(
                notificationChannelId,
                channelName,
                NotificationManager.IMPORTANCE_NONE
        );
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = getSystemService(NotificationManager.class);

        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, notificationChannelId);

        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Location updates:")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        uid = intent.getStringExtra("UID");


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (datatask != null) {
            datatask.cancel();
        }
        if(datatimer !=null ){
            datatimer.cancel();
        }
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
