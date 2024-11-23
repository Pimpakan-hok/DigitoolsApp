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
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Adapter.HomeAdapter;
import com.example.digitoolsapp.Adapter.PostAdapter;
import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.Data.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends Fragment implements HomeAdapter.OnItemClickListener{
    public static final String UID = "uid";


    RecyclerView recyclerView;
    List<Tools> tool;
    HomeAdapter adapter;
    private RequestQueue mRequestQueue;
    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/getLatLon.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_page, container, false);
        recyclerView = v.findViewById(R.id.toolList);

        tool = new ArrayList<>();
        extractHome();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        return v;
    }
    private void extractHome() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, response -> {

                for (int i = 0; i < response.length(); i++) {
                try {
                        JSONObject ToolsObject = response.getJSONObject(i);
                        JSONArray toollist = ToolsObject.getJSONArray("list");
                        JSONObject ListObject = toollist.getJSONObject(0);

                        Tools tools = new Tools();
                        tools.setUid(ToolsObject.getString("UID"));
                        tools.setSdate(ToolsObject.getString("sdate"));
//                        tools.setUsername(ToolsObject.getString("username"));
//                        tools.setEmail(ToolsObject.getString("list"));
                        tool.add(tools);
                } catch (JSONException e)

                {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new HomeAdapter(getActivity(),tool);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(HomePage.this);


            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });

        queue.add(jsonArrayRequest);

    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(getActivity(), DetailsListPage.class);
        Tools clickedItem = tool.get(position);
        detailIntent.putExtra(UID, clickedItem.getUid());

        startActivity(detailIntent);
    }
}