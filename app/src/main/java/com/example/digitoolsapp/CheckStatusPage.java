package com.example.digitoolsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Adapter.HomeAdapter;
import com.example.digitoolsapp.Adapter.StatusAdapter;
import com.example.digitoolsapp.Data.StatusData;
import com.example.digitoolsapp.Data.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckStatusPage extends Fragment implements StatusAdapter.OnItemClickListner{

    public static final String UID = "uid";
    public static final String QID = "queID";

    int a;
    private String uid;
    RecyclerView recyclerView;
    List<StatusData> statuse;
    StatusAdapter adapter;
    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/getStatus.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_check_status_page, container, false);
        recyclerView = v.findViewById(R.id.statusList);
        statuse = new ArrayList<>();
        extractState();
        // Inflate the layout for this fragment
        return v;
    }
    private void extractState() {
       Intent intent = getActivity().getIntent();
       String uid = intent.getStringExtra("UID");
        StringRequest jsonArrayRequest = new StringRequest(Request.Method.POST, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject statusObject = new JSONObject(response);
                    int b = 1;

                    JSONArray jsonArray = statusObject.getJSONArray("data");
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject Object = jsonArray.getJSONObject(i);
                        StatusData st = new StatusData();
                        st.setQue_owner_UID(Object.getString("UID"));
                        st.setEd(Object.getString("e_date"));
                        st.setSd(Object.getString("s_date"));
                        st.setId(Object.getString("que_ID"));
                        st.setNumque(String.valueOf(b));
                        b++;

                        st.setStatus(Object.getString("queue_status"));

                        a = Integer.parseInt(st.getStatus());


                        if (a == 1) {
                            st.setStatus("กำลังรออรุมัติ");
                        } else if (a == 2) {
                            st.setStatus("อนุมัติ");
                        } else if (a == 3) {
                            st.setStatus("ไม่ผ่าน");
                        } else if (a == 4) {
                            st.setStatus("หมดเวลา");
                        } else if (a == 5) {
                            st.setStatus("ยกเลิก");
                        } else if (a == 6) {
                            st.setStatus("กำลังใช้งาน");
                        } else if (a == 7) {
                            st.setStatus("ใช้งานสำเร็จ");
                        }


                        statuse.add(st);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new StatusAdapter(getActivity(),statuse);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(CheckStatusPage.this);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<>();
                data.put("UID", uid);
                return data;

            }

        };
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonArrayRequest);

    }
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), DetailsCheckStutus.class);
        StatusData clickedItem = statuse.get(position);
        detailIntent.putExtra(UID, clickedItem.getQue_owner_UID());
        detailIntent.putExtra(QID , clickedItem.getId());
        startActivity(detailIntent);
    }
}