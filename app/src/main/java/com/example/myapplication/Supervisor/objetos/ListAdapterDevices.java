package com.example.myapplication.Supervisor.objetos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListAdapterDevices extends RecyclerView.Adapter<ListAdapterDevices.ViewHolder> {
    private List<ListElementDevices> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterDevices.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementDevices item);
    }

    public ListAdapterDevices(List<ListElementDevices> itemList, Context context, ListAdapterDevices.OnItemClickListener listener) {
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
    public ListAdapterDevices.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.lista_devices_supervisor, null);
        return new ListAdapterDevices.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterDevices.ViewHolder holder, final int position){
        holder.bindData(nData.get(position));
    }

    public void setItems(List<ListElementDevices> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, desciption, tipo;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextViewDevice);
            desciption = itemView.findViewById(R.id.desciptionDevice);
            tipo = itemView.findViewById(R.id.tipoSitioDevice);
        }

        void bindData(final ListElementDevices item){
            String fullDesciption = item.getMarca() + " / " + item.getModelo() + " / " + item.getSku();
            name.setText(item.getName());
            desciption.setText(fullDesciption);
            tipo.setText(item.getFechaIngreso());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }

}
