package com.example.digitoolsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.digitoolsapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context){
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
    }
    private void  randowmwindowtext(Marker marker ,View view){
        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

       if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }
    @Override
    public View getInfoContents(Marker marker) {
        randowmwindowtext(marker , mWindow);
        return mWindow;
    }

    @Nullable
    @Override
    public View getInfoWindow(Marker marker) {
        randowmwindowtext(marker , mWindow);

        return mWindow;
    }
}
