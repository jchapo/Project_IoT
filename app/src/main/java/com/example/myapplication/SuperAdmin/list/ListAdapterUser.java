package com.example.myapplication.SuperAdmin.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListAdapterUser extends RecyclerView.Adapter<ListAdapterUser.ViewHolder> {
    private List<ListElementUser> nData;
    private LayoutInflater nInflater;
    private Context context;
    final OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementUser item);
    }

    public ListAdapterUser(List<ListElementUser> itemList, Context context, OnItemClickListener listener) {
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
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.superadmin_lista_admin, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.bindData(nData.get(position));
    }

    public void setItems(List<ListElementUser> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, user, status;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            user = itemView.findViewById(R.id.userTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }

        void bindData(final ListElementUser item){
            String fullName = item.getName() + " " + item.getLastname();
            name.setText(fullName);
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
