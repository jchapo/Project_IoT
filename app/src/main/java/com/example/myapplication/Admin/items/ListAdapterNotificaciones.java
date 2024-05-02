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

public class ListAdapterNotificaciones extends RecyclerView.Adapter<ListAdapterNotificaciones.ViewHolder> {
    private List<ListElementNotificaciones> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterNotificaciones.OnItemClickListener listener;

    public interface  OnItemClickListener{
        void onItemClick(ListElementNotificaciones item);
    }

    public ListAdapterNotificaciones(List<ListElementNotificaciones> itemList, Context context, ListAdapterNotificaciones.OnItemClickListener listener) {
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
    public ListAdapterNotificaciones.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.admin_lista_noti_form, null);
        return new ListAdapterNotificaciones.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ListAdapterNotificaciones.ViewHolder holder, final int position){
        holder.bindDataChat(nData.get(position));
    }

    public void setItems(List<ListElementNotificaciones> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView accion, descripcionResumida, fecha;

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            accion = itemView.findViewById(R.id.accionTextView);
            descripcionResumida = itemView.findViewById(R.id.descripcionResumidaTextView);
            fecha = itemView.findViewById(R.id.fechaTextView);
        }

        void bindDataChat(final ListElementNotificaciones item){
            accion.setText(item.getAccion());
            descripcionResumida.setText(item.getDescripcionResumida());
            fecha.setText(item.getFecha());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });

        }
    }

}
