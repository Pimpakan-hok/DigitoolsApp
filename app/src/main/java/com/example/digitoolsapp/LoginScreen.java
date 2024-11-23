package com.example.digitoolsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Data.PostData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {
    Button loginButton;
    private EditText etUsername, etPassword;
    private String uid, password;
    TextView la;
    private static int MY_FINE_LOCATION_REQUEST = 99;
    private static int MY_BACKGROUND_LOCATION_REQUEST = 100;

    LocationService mLocationService = new LocationService();
    Intent mServiceIntent;
    private String URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/Login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        uid= password = "";
        startservice();

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
//        loginButton = findViewById(R.id.loginButton);
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void login(View view) {
        uid = etUsername.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if(!uid.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("status");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("res", response);
                        if (result.equals("success")) {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject Object = jsonArray.getJSONObject(i);
                                String name = Object.getString("username");
                                String email = Object.getString("email");
                                String phone = Object.getString("phonenum");
                                String uid = Object.getString("UID");

                                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                                intent.putExtra("username" , name);
                                intent.putExtra("email" , email);
                                intent.putExtra("phonenum" , phone);
                                intent.putExtra("UID" , uid);

                                startActivity(intent);

                                Intent serviceintent = new Intent(LoginScreen.this , LocationService.class);
                                serviceintent.putExtra("UID" , uid);
                                startService(serviceintent);

                                finish();
                                Toast.makeText(LoginScreen.this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();


                            }
                        } else if (result.equals("error")) {
                            Toast.makeText(LoginScreen.this, "กรุณากรอกให้ถูกต้อง", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginScreen.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> data = new HashMap<>();
                    data.put("UID", uid);
                    data.put("password", password);
                    return data;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }
    }
    public void startservice(){
        if (ActivityCompat.checkSelfPermission(LoginScreen.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                if (ActivityCompat.checkSelfPermission(LoginScreen.this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {


                    AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen.this).create();
                    alertDialog.setTitle("อนุญาติให้เข้าถึงตำแหน่งที่ตั้งของคุณ");
//                    alertDialog.setMessage(getString(R.string.background_location_permission_message));

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "อนุญาติ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    starServiceFunc();
                                    requestBackgroundLocationPermission();
                                    dialog.dismiss();
                                }
                            });

//                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Grant background Permission",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    requestBackgroundLocationPermission();
//                                    dialog.dismiss();
//                                }
//                            });

                    alertDialog.show();


                }else if (ActivityCompat.checkSelfPermission(LoginScreen.this, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        == PackageManager.PERMISSION_GRANTED){
                    starServiceFunc();
                }
            }else{
                starServiceFunc();
            }

        }else if (ActivityCompat.checkSelfPermission(LoginScreen.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginScreen.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                AlertDialog alertDialog = new AlertDialog.Builder(LoginScreen.this).create();
                alertDialog.setTitle("ACCESS_FINE_LOCATION");
                alertDialog.setMessage("Location permission required");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                requestFineLocationPermission();
                                dialog.dismiss();
                            }
                        });


                alertDialog.show();

            } else {
                requestFineLocationPermission();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        Toast.makeText(this, Integer.toString(requestCode), Toast.LENGTH_LONG).show();

        if ( requestCode == MY_FINE_LOCATION_REQUEST){

            if (grantResults.length !=0 /*grantResults.isNotEmpty()*/ && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    requestBackgroundLocationPermission();
                }

            } else {
                Toast.makeText(this, "ACCESS_FINE_LOCATION permission denied", Toast.LENGTH_LONG).show();
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                 /*   startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", this.getPackageName(), null),),);*/

                    startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:com.trickyworld.locationupdates")
                    ));

                }
            }
            return;

        }else if (requestCode == MY_BACKGROUND_LOCATION_REQUEST){

            if (grantResults.length!=0 /*grantResults.isNotEmpty()*/ && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(this, "Background location Permission Granted", Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(this, "Background location permission denied", Toast.LENGTH_LONG).show();
            }
            return;
        }

    }

    private void requestBackgroundLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                MY_BACKGROUND_LOCATION_REQUEST);
    }

    private void requestFineLocationPermission() {
        ActivityCompat.requestPermissions(this,  new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, MY_FINE_LOCATION_REQUEST);

    }

    private void starServiceFunc() {
        mLocationService = new LocationService();
        mServiceIntent = new Intent(this, mLocationService.getClass());
        if (!Util.isMyServiceRunning(mLocationService.getClass(), this)) {
            startService(mServiceIntent);
//            Toast.makeText(this, "Suceess" , Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Run", Toast.LENGTH_SHORT).show();
        }
    }

}