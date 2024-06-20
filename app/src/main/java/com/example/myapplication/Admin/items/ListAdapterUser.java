package com.example.myapplication.Admin.items;

import android.content.Context;
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

public class ListAdapterUser extends RecyclerView.Adapter<ListAdapterUser.ViewHolder> {
    private List<ListElementUser> nData;
    private List<ListElementUser> nDataFull; // Lista original sin filtrar
    private LayoutInflater nInflater;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListElementUser item);
    }

    public ListAdapterUser(List<ListElementUser> itemList, Context context, OnItemClickListener listener) {
        this.nInflater = LayoutInflater.from(context);
        this.context = context;
        this.nData = itemList;
        this.nDataFull = new ArrayList<>(itemList); // Inicializar la lista completa
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = nInflater.inflate(R.layout.admin_list_usuarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(nData.get(position));
    }

    @Override
    public int getItemCount() {
        return nData.size();
    }

    public void setItems(List<ListElementUser> items) {
        Collections.sort(items, Comparator.comparing(ListElementUser::getName)); // Ordenar alfabéticamente
        nData = items;
        nDataFull = new ArrayList<>(items); // Actualizar la lista completa al cambiar los datos
        notifyDataSetChanged();
    }

    public void filter(String text) {
        nData.clear();
        if (text.isEmpty()) {
            nData.addAll(nDataFull);
        } else {
            text = text.toLowerCase();
            for (ListElementUser item : nDataFull) {
                if (item.getName().toLowerCase().contains(text) || item.getLastname().toLowerCase().contains(text)) {
                    nData.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, user, status;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTextView);
            user = itemView.findViewById(R.id.userTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }

        void bindData(final ListElementUser item) {
            String fullName = item.getName() + " " + item.getLastname();
            name.setText(fullName);
            user.setText(item.getUser());
            status.setText(item.getStatus());
            itemView.setOnClickListener(view -> listener.onItemClick(item));
        }
    }
}
