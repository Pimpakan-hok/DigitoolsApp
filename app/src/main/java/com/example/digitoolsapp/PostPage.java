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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.digitoolsapp.Adapter.PostAdapter;
import com.example.digitoolsapp.Data.PostData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostPage extends Fragment implements PostAdapter.OnItemClickListener {
    public static final String TITLE = "title";
    public static final String TIME = "time";
    public static final String DESC = "description";
    public static final String pic = "picurl";

    RecyclerView recyclerView;
    List<PostData> post;
    PostAdapter adapter;
    private RequestQueue mRequestQueue;
    private static String JSON_URL = "https://digiproj.sut.ac.th/dgtprj65_05/android/getPost.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post_page, container, false);
        recyclerView = v.findViewById(R.id.postList);
        post = new ArrayList<>();
        extractPost();
        mRequestQueue = Volley.newRequestQueue(getActivity());
        return v;
    }
    private void extractPost() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject PostObject = response.getJSONObject(i);

                    PostData posts = new PostData();
                    posts.setPost_title(PostObject.getString("post_title").toString());
                    posts.setPost_desc(PostObject.getString("post_desc").toString());
                    posts.setPost_time(PostObject.getString("post_time").toString());
                    posts.setPost_pic_path(PostObject.getString("post_pic_path"));

                    post.add(posts);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new PostAdapter(getActivity(),post);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(PostPage.this);


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
        Intent detailIntent = new Intent(getActivity(), DetailsPostActivity.class);
        PostData clickedItem = post.get(position);

        detailIntent.putExtra(TITLE, clickedItem.getPost_title());
        detailIntent.putExtra(TIME, clickedItem.getPost_time());
        detailIntent.putExtra(DESC, clickedItem.getPost_desc());


        startActivity(detailIntent);
    }
}