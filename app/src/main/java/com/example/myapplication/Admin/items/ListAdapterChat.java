package com.example.myapplication.Admin.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListAdapterChat extends RecyclerView.Adapter<ListAdapterChat.ViewHolder> {
    private List<ListElementChat> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterChat.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementChat item);
    }

    public ListAdapterChat(List<ListElementChat> itemList, Context context, ListAdapterChat.OnItemClickListener listener) {
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
    public ListAdapterChat.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.admin_lista_chats, null);
        return new ListAdapterChat.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterChat.ViewHolder holder, final int position){
        holder.bindDataChat(nData.get(position));
    }

    public void setItems(List<ListElementChat> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, role;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.usuarioTextView);
            role = itemView.findViewById(R.id.roleTextView);
        }

        void bindDataChat(final ListElementChat item){
            name.setText(item.getUsuarioChatName());
            role.setText(item.getRole());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }

}