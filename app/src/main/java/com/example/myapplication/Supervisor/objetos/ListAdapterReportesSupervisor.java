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
public class ListAdapterReportesSupervisor extends RecyclerView.Adapter<ListAdapterReportesSupervisor.ViewHolder>{

    private List<ListElementReportes> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterReportesSupervisor.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementReportes item);
    }

    public ListAdapterReportesSupervisor(List<ListElementReportes> itemList, Context context, ListAdapterReportesSupervisor.OnItemClickListener listener) {
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
    public ListAdapterReportesSupervisor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.supervisor_list_reportes, parent, false);
        return new ListAdapterReportesSupervisor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterReportesSupervisor.ViewHolder holder, final int position){
        holder.bindDataSite(nData.get(position));
    }

    public void setItems(List<ListElementReportes> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, fechacreacion, sitio;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageViewSite);
            name = itemView.findViewById(R.id.reportesTextView);
            fechacreacion = itemView.findViewById(R.id.fechaTextView);
            sitio = itemView.findViewById(R.id.sitio10TextView);
        }

        void bindDataSite(final ListElementReportes item){
            name.setText(item.getNombre_reporte());
            fechacreacion.setText(item.getFecha_creacion());
            sitio.setText(item.getSitio());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }
}
