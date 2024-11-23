package com.example.digitoolsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitoolsapp.Data.PostData;
import com.example.digitoolsapp.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<PostData> post;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public PostAdapter(Context ctx, List<PostData> post){
        this.inflater = LayoutInflater.from(ctx);
        this.post = post;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        holder.postTitle.setText(post.get(position).getPost_title());
        holder.postDate.setText(post.get(position).getPost_time());



    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView postTitle,postDate,postDesc;
        ImageView postCoverImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.nameTextView);
            postDate = itemView.findViewById(R.id.timeTextView);


            // handle onClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
