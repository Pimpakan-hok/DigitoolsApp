package com.example.digitoolsapp;

import static com.example.digitoolsapp.HomePage.UID;
import static com.example.digitoolsapp.PostPage.TITLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
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
import com.example.digitoolsapp.Adapter.ToolsAdapter;
import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.Data.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsListPage extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Listtools> listtoolsel;

    ToolsAdapter adapter;
    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/getIndivUB.php";
    String topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list_page);
        recyclerView = findViewById(R.id.ToolList);
        listtoolsel = new ArrayList<>();

        Intent intent = getIntent();
        topic = intent.getStringExtra(UID);
        extraDetailHome();
        ImageButton back = findViewById(R.id.backpagelists);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DetailsListPage.this,MainActivity.class);
                startActivity(back);            }
        });
    }

    private void extraDetailHome() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonArrayRequest = new  StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>()  {


            @Override
            public void onResponse(String response) {

                try {


                    JSONObject ToolsObject =new JSONObject(response);
                    JSONArray toollist = ToolsObject.getJSONArray("list");
                    Tools tools = new Tools();
                    tools.setUid(ToolsObject.getString("UID"));

                    TextView uid = findViewById(R.id.Titlename);
                    String b = ToolsObject.getString("UID");
                    uid.setText(b);

                    TextView user = findViewById(R.id.username);
                    String name = ToolsObject.getString("username");
                    user.setText(name);

                    TextView ph = findViewById(R.id.phoneh);
                    String phe = ToolsObject.getString("phonenum");
                    ph.setText(phe);

                    TextView sd = findViewById(R.id.timebh);
                    String sday = ToolsObject.getString("sdate");
                    sd.setText(sday);

                    TextView ed = findViewById(R.id.timegh);
                    String eday = ToolsObject.getString("edate");
                    ed.setText(eday);

//                  tools.setEmail(ToolsObject.getString("list"));
                    for(int i = 0; i < toollist.length(); i++){
                        JSONObject ListObject = toollist.getJSONObject(i);
                        Listtools listtoolss = new Listtools();


                        listtoolss.setToolid(ListObject.getString("name"));

                        listtoolsel.add(listtoolss);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new ToolsAdapter(getApplicationContext(),listtoolsel);
                recyclerView.setAdapter(adapter);
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
                data.put("UID", topic);
                return data;

            }

        };


        queue.add(jsonArrayRequest);

    }

}