package com.example.myapplication.Admin.items;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapter.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElement item);
    }

    public ListAdapter(List<ListElement> itemList, Context context, ListAdapter.OnItemClickListener listener) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return nData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.lista_usuasios_admin, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(nData.get(position));
    }

    public void setItems(List<ListElement> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, user, status, dni;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            user = itemView.findViewById(R.id.userTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }

        void bindData(final ListElement item){
            name.setText(item.getName());
            user.setText(item.getUser());
            status.setText(item.getStatus());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }

}
