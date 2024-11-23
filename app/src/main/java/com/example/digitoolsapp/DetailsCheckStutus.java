package com.example.digitoolsapp;

import static com.example.digitoolsapp.CheckStatusPage.UID;
import static com.example.digitoolsapp.CheckStatusPage.QID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Adapter.StatusAdapter;
import com.example.digitoolsapp.Adapter.ToolsAdapter;
import com.example.digitoolsapp.Adapter.ToolsST;
import com.example.digitoolsapp.Data.ListToolSt;
import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.Data.StatusData;
import com.example.digitoolsapp.Data.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsCheckStutus extends AppCompatActivity {

    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/getStatDetail.php";
    String  state , qid;
    RecyclerView recyclerView;
    List<ListToolSt> listtoolsel;
    ToolsST adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_check_stutus);
        recyclerView = findViewById(R.id.ToolSTList);
        listtoolsel = new ArrayList<>();

        Intent intent = getIntent();
        state = intent.getStringExtra(UID);
        qid = intent.getStringExtra(QID);
        extraDetailStatus();
        ImageButton back = findViewById(R.id.backpagelist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DetailsCheckStutus.this,MainActivity.class);
                startActivity(back);            }
        });
    }

    private void extraDetailStatus() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest jsonArrayRequest = new  StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>()  {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject StatusObject =new JSONObject(response);
                    JSONArray statelist = StatusObject.getJSONArray("list");
                    StatusData status = new StatusData();
                    status.setQue_owner_UID(StatusObject.getString("UID"));

                    TextView id = findViewById(R.id.Titlename);
                    String di = StatusObject.getString("UID");
                    id.setText(di);

                    TextView sday = findViewById(R.id.timeb);
                    String sd = StatusObject.getString("sdate");
                    sday.setText(sd);

                    TextView eday = findViewById(R.id.timeg);
                    String ed = StatusObject.getString("edate");
                    eday.setText(ed);

                    TextView des = findViewById(R.id.desstat);
                    String d = StatusObject.getString("que_desc");
                    des.setText(d);
//                    id.setText(v);
//                    tools.setEmail(ToolsObject.getString("list"));
                    for(int i = 0; i < statelist.length(); i++){

                        JSONObject ListObject = statelist.getJSONObject(i);
                        ListToolSt listtoolss = new ListToolSt();

                        listtoolss.setToolidst(ListObject.getString("name"));
                        listtoolsel.add(listtoolss);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new ToolsST(getApplicationContext(),listtoolsel);
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
                data.put("UID",  state);
                data.put("queID",  qid);

                return data;

            }

        };


        queue.add(jsonArrayRequest);

    }

}