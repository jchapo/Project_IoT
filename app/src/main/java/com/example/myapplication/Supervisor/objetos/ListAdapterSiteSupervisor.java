package com.example.myapplication.Supervisor.objetos;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Admin.items.ListElementSite;
import com.example.myapplication.R;

import java.util.List;
public class ListAdapterSiteSupervisor extends RecyclerView.Adapter<ListAdapterSiteSupervisor.ViewHolder>{

    private List<ListElementSite> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterSiteSupervisor.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementSite item);
    }

    public ListAdapterSiteSupervisor(List<ListElementSite> itemList, Context context, ListAdapterSiteSupervisor.OnItemClickListener listener) {
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
    public ListAdapterSiteSupervisor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_list_sitios, parent, false);
        return new ListAdapterSiteSupervisor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterSiteSupervisor.ViewHolder holder, final int position){
        holder.bindDataSite(nData.get(position));
    }

    public void setItems(List<ListElementSite> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, direction,tipositio;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewSite);
            name = itemView.findViewById(R.id.nameTextViewSite);
            direction = itemView.findViewById(R.id.directionTextViewSite);
            tipositio = itemView.findViewById(R.id.tipoSitioTextViewSite);
        }

        void bindDataSite(final ListElementSite item){
            String fullDirection = item.getDepartment() + " " + item.getProvince() + " " + item.getDistrict();
            direction.setText(fullDirection);
            name.setText(item.getName());
            tipositio.setText(item.getSitetype());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}
