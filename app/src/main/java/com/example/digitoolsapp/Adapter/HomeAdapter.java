package com.example.digitoolsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.Data.Tools;
import com.example.digitoolsapp.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    LayoutInflater inflater;
    List<Tools> tool;
    private  OnItemClickListener mListener1;

    public interface OnItemClickListener  {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener1 = listener;
    }

    public  HomeAdapter(Context ctx, List tool){
        this.inflater = LayoutInflater.from(ctx);
        this.tool = tool;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.homelist,parent,false);
        return new HomeAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        // bind the data
        holder.Title.setText(tool.get(position).getUid());
        holder.time.setText(tool.get(position).getSdate());



    }

    @Override
    public int getItemCount() {
        return tool.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView Title,postDate,postDesc ,time;
        ImageView postCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.nametools);
            time = itemView.findViewById(R.id.time);


            // handle onClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener1.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}