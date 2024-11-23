package com.example.digitoolsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitoolsapp.CheckStatusPage;
import com.example.digitoolsapp.Data.StatusData;
import com.example.digitoolsapp.R;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<StatusData> status;
    private OnItemClickListner mListener;

    public interface OnItemClickListner {
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListner listener) {
        mListener = listener;
    }


    public StatusAdapter (Context ctx, List<StatusData> status){
        this.inflater = LayoutInflater.from(ctx);
        this.status = status;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemstatus,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // bind the data
        holder.Queue.setText(status.get(position).getNumque());
        holder.DateS.setText(status.get(position).getSd());
        holder.DateE.setText(status.get(position).getEd());
        holder.st.setText(status.get(position).getStatus());




    }

    @Override
    public int getItemCount() {
        return status.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView Queue,DateS,DateE,st;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Queue = itemView.findViewById(R.id.Quenumber);
            DateS = itemView.findViewById(R.id.sdate);
            DateE = itemView.findViewById(R.id.edate);
             st = itemView.findViewById(R.id.status);
            // handle onClick

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
