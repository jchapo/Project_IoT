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

public class ListAdapterEquiposNuevo extends RecyclerView.Adapter<ListAdapterEquiposNuevo.ViewHolder> {

    private List<ListElementEquiposNuevo> nData;
    private LayoutInflater nInflater;
    private Context context;
    final ListAdapterEquiposNuevo.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListElementEquiposNuevo item);
    }

    public ListAdapterEquiposNuevo(List<ListElementEquiposNuevo> itemList, Context context, ListAdapterEquiposNuevo.OnItemClickListener listener) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    @Override
    public ListAdapterEquiposNuevo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.supervisor_list_devices, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterEquiposNuevo.ViewHolder holder, final int position) {
        holder.bindData(nData.get(position));
    }

    public void setItems(List<ListElementEquiposNuevo> items) {
        nData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, description, tipo;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextViewDevice);
            description = itemView.findViewById(R.id.desciptionDevice);
            tipo = itemView.findViewById(R.id.tipoSitioDevice);
        }

        void bindData(final ListElementEquiposNuevo item) {
            String fullDescription = item.getMarca() + " / " + item.getModelo() + " / " + item.getSku();
            name.setText(item.getNameEquipo());
            description.setText(fullDescription);
            tipo.setText(item.getTipoEquipo());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
