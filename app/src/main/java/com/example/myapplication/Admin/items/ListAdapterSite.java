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

public class ListAdapterSite extends RecyclerView.Adapter<ListAdapterSite.ViewHolder> {
    private List<ListElementSite> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterSite.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementSite item);
    }

    public ListAdapterSite(List<ListElementSite> itemList, Context context, ListAdapterSite.OnItemClickListener listener) {
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
    public ListAdapterSite.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.lista_sitios_admin, null);
        return new ListAdapterSite.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterSite.ViewHolder holder, final int position){
        holder.bindDataSite(nData.get(position));
    }

    public void setItems(List<ListElementSite> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, direction, status;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewSite);
            name = itemView.findViewById(R.id.nameTextViewSite);
            direction = itemView.findViewById(R.id.directionTextViewSite);
            status = itemView.findViewById(R.id.statusTextViewSite);

        }

        void bindDataSite(final ListElementSite item){
            String fullDirection = item.getDepartment() + " " + item.getProvince() + " " + item.getDistrict();
            direction.setText(fullDirection);
            name.setText(item.getName());
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
