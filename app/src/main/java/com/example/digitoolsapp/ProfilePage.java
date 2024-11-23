package com.example.digitoolsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ProfilePage extends Fragment {

    SharedPreferences sharedPreferences;
    public static final String MY_PREFERENCES = "MyPrefs";
    Button bt_generate;
    ImageView iv_qr;
    Button logout;
    TextView name ,e,p , uid;
    Button btn_scan;
    String v ,toolid;
    String QRCODE , brand , model;
    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/getToolDet.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ve =  inflater.inflate(R.layout.fragment_profile_page, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        bt_generate = ve.findViewById(R.id.bt_generate);
        iv_qr = ve.findViewById(R.id.iv_qr);
        logout = ve.findViewById(R.id.Logout);
        name = ve.findViewById(R.id.prouser);
        e = ve.findViewById(R.id.email);
        p = ve.findViewById(R.id.phonepn);
        uid = ve.findViewById(R.id.uid);

        Intent intent = getActivity().getIntent();
        QRCODE = intent.getStringExtra("UID");
        String namep = intent.getStringExtra("username");
        String emai = intent.getStringExtra("email");
        String ph = intent.getStringExtra("phonenum");

//        uniqueId = UUID.randomUUID().toString();
        uid.setText(QRCODE);
        name.setText(namep);
        e.setText(emai);
        p.setText(ph);
        bt_generate.setOnClickListener(v->{
            generateQR();

        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
            }
        });
        btn_scan = ve.findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(v->
        {
            scanCode();
        });
        return ve;
    }

    private void extractScan() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest jsonArrayRequest = new  StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>()  {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ToolsScanObject = new JSONObject(response);
//                    JSONArray toollist = ToolsObject.getJSONArray("list");
//                    JSONObject ListObject = toollist.getJSONObject(0);

                      v = ToolsScanObject.getString("tool_name");
                    brand = ToolsScanObject.getString("brand_name");
                    model = ToolsScanObject.getString("tool_model");

                } catch (JSONException e)

                {
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<>();
                data.put("toolid",toolid);
                return data;
            }
        };
        queue.add(jsonArrayRequest);
    }


    private void generateQR()
    {


        MultiFormatWriter writer = new MultiFormatWriter(); //สร้างจาก UID
        try
        {
            BitMatrix matrix = writer.encode(QRCODE , BarcodeFormat.QR_CODE,400,400);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            iv_qr.setImageBitmap(bitmap);


        } catch (WriterException e)
        {
            e.printStackTrace();
        }
    }
    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        barLaucher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {

            toolid = result.getContents();
            extractScan();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("รายละเอียด");
            builder.setMessage("ชื่อ :" + v + "\n"+ "ยี่ห้อ :" +brand + "\n"+ "รุ่น :" + model);

            builder.setPositiveButton("ปิด", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.dismiss();
                }
            }).show();
//            builder.setNegativeButton("see", new DialogInterface.OnClickListener()
//            {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i)
//                {
//                    Intent intent = new Intent(getActivity(), test.class);
//                    startActivity(intent);
//                    dialogInterface.dismiss();
//                }
//            })
        }
    });
}