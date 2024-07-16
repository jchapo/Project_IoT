package com.example.myapplication.Admin.items;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListAdapterSite extends RecyclerView.Adapter<ListAdapterSite.ViewHolder> {
    private List<ListElementSite> nData;
    private List<ListElementSite> nDataFull; // Lista original sin filtrar
    private LayoutInflater nInflater;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListElementSite item);
    }

    public ListAdapterSite(List<ListElementSite> itemList, Context context, OnItemClickListener listener) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
        this.nDataFull = new ArrayList<>(itemList); // Inicializar la lista completa
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Verifica que el layout admin_lista_sitios existe en res/layout
        View view = nInflater.inflate(R.layout.admin_lista_sitios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindDataSite(nData.get(position));
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public void setItems(List<ListElementSite> items) {
        Log.d("ListAdapterSite", "setItems called with items: " + items.size());
        // Ordenar la lista antes de establecer los ítems
        Collections.sort(items, Comparator.comparing(ListElementSite::getName));
        nData = items;
        nDataFull = new ArrayList<>(items); // Actualizar la lista completa al cambiar los datos
        notifyDataSetChanged();
    }

    public void filter(String text) {
        Log.d("ListAdapterSite", "filter called with text: " + text);
        nData.clear();
        if (text.isEmpty()) {
            nData.addAll(nDataFull);
        } else {
            text = text.toLowerCase();
            for (ListElementSite item : nDataFull) {
                if (item.getName().toLowerCase().contains(text) || item.getDepartment().toLowerCase().contains(text) || item.getProvince().toLowerCase().contains(text) || item.getDistrict().toLowerCase().contains(text)) {
                    nData.add(item);
                }
            }
        }
        Log.d("ListAdapterSite", "filter result size: " + nData.size());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, direction, status;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextViewSite);
            direction = itemView.findViewById(R.id.directionTextViewSite);
            status = itemView.findViewById(R.id.statusTextViewSite);
        }

        void bindDataSite(final ListElementSite item) {
            String fullDirection = item.getDepartment() + " " + item.getProvince() + " " + item.getDistrict();
            direction.setText(fullDirection);
            name.setText(item.getName());
            status.setText(item.getStatus());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }
}
