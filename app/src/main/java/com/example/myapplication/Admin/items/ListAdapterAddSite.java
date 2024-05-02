package com.example.myapplication.Admin.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterAddSite extends RecyclerView.Adapter<ListAdapterAddSite.ViewHolder> {
    private List<ListElementSite> mData;
    private LayoutInflater mInflater;
    private List<ListElementSite> mSelectedItems = new ArrayList<>();

    public ListAdapterAddSite(Context context, List<ListElementSite> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.admin_list_sitios_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListElementSite item = mData.get(position);
        holder.name.setText(item.getName());
        holder.location.setText(item.getAddress());
        holder.status.setText(item.getStatus());

        // Asigna un listener al CheckBox para manejar la selección
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Si se marca, añade el elemento a la lista de seleccionados
                mSelectedItems.add(item);
            } else {
                // Si se desmarca, elimina el elemento de la lista de seleccionados
                mSelectedItems.remove(item);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView name, location, status;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            location = itemView.findViewById(R.id.locationTextView);
            status = itemView.findViewById(R.id.statusTextView);
            checkBox = itemView.findViewById(R.id.checkBoxSiteSelect); // Inicializa CheckBox

        }
    }


}
