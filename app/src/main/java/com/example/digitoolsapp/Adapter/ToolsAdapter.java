package com.example.digitoolsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitoolsapp.Data.Listtools;
import com.example.digitoolsapp.Data.StatusData;
import com.example.digitoolsapp.R;

import java.util.List;

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<Listtools> listtools;

    public ToolsAdapter (Context ctx, List<Listtools> listtools){
        this.inflater = LayoutInflater.from(ctx);
        this.listtools = listtools;
    }




    @NonNull
    @Override
    public ToolsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemtool,parent,false);
        return new ToolsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolsAdapter.ViewHolder holder, int position) {
        // bind the data
        holder.Tools.setText(listtools.get(position).getToolid());
//        holder.Toolstate.setText(listtools.get(position).getName());
//        holder.Toolh.setText(listtools.get(position).getName());

//        holder.st.setText(status.get(position).getStatus());




    }

    @Override
    public int getItemCount() {
        return listtools.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView Tools,Toolstate,Toolh;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Tools = itemView.findViewById(R.id.Tools);
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
