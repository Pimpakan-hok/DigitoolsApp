package com.example.digitoolsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitoolsapp.Data.ListToolSt;
import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.R;

import java.util.List;

public class ToolsST extends RecyclerView.Adapter<ToolsST.ViewHolder>  {
    LayoutInflater inflater;
   List<ListToolSt> listtoolst;

    public ToolsST (Context ctx, List<ListToolSt> listtoolst){
        this.inflater = LayoutInflater.from(ctx);
        this.listtoolst = listtoolst;
    }




    @NonNull
    @Override
    public ToolsST.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemtoollsitstatus,parent,false);
        return new ToolsST.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolsST.ViewHolder holder, int position) {
        // bind the data
        holder.Toolst.setText(listtoolst.get(position).getToolidst());
//        holder.Toolstate.setText(listtools.get(position).getName());
//        holder.Toolh.setText(listtools.get(position).getName());

//        holder.st.setText(status.get(position).getStatus());




    }

    @Override
    public int getItemCount() {
        return listtoolst.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView Toolst,Toolstate,Toolh;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Toolst = itemView.findViewById(R.id.ToolsSt);
//            Toolstate = itemView.findViewById(R.id.ToolsSt);
//            Toolh = itemView.findViewById(R.id.Toolsh);
//             st = itemView.findViewById(R.id.status);
            // handle onClick

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "Do Something With this Click", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
}
