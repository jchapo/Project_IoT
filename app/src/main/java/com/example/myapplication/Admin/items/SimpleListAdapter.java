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

public class SimpleListAdapter extends RecyclerView.Adapter<SimpleListAdapter.ViewHolder> {
    private List<ListElementUser> mData;
    private LayoutInflater mInflater;
    private List<ListElementUser> mSelectedItems = new ArrayList<>();

    public SimpleListAdapter(Context context, List<ListElementUser> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.admin_list_usuarios_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListElementUser item = mData.get(position);
        holder.name.setText(item.getName() + " " + item.getLastname());
        holder.user.setText(item.getUser());
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
        TextView name, user, status;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            user = itemView.findViewById(R.id.userTextView);
            status = itemView.findViewById(R.id.statusTextView);
            checkBox = itemView.findViewById(R.id.checkBox); // Inicializa CheckBox

        }
    }


}
