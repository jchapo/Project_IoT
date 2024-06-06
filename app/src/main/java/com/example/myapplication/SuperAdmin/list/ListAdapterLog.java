package com.example.myapplication.SuperAdmin.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListAdapterLog extends RecyclerView.Adapter<ListAdapterLog.ViewHolder> {
    private List<ListElementLog> mData;
    private List<ListElementLog> mDataFull;
    private LayoutInflater mInflater;
    private Context mContext;
    final ListAdapterLog.OnItemClickListener listener;


    public interface OnItemClickListener {
        void onItemClick(ListElementLog item);
    }

    public ListAdapterLog(List<ListElementLog> itemList, Context context, OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = itemList;
        this.mDataFull = new ArrayList<>(itemList);
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapterLog.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.superadmin_lista_log, parent, false);
        return new ListAdapterLog.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapterLog.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElementLog> items) {
        mData = items;
        mDataFull = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        mData.clear();
        if (text.isEmpty()) {
            mData.addAll(mDataFull);
        } else {
            text = text.toLowerCase();
            for (ListElementLog item : mDataFull) {
                if (item.getMessage().toLowerCase().contains(text)) {
                    mData.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageLog;
        TextView timestampTextView, userTextView, userRolTextView, logTypeTextView, messageTextView;

        ViewHolder(View itemView) {
            super(itemView);
            iconImageLog = itemView.findViewById(R.id.iconImageViewLog);
            timestampTextView = itemView.findViewById(R.id.timestampTextView);
            userTextView = itemView.findViewById(R.id.userLogTextView);
            userRolTextView = itemView.findViewById(R.id.userRolLogTextView);
            logTypeTextView = itemView.findViewById(R.id.statusTextViewLog);
            messageTextView = itemView.findViewById(R.id.nameTextViewLog);
        }

        void bindData(final ListElementLog item) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedDate = formatter.format(item.getTimestamp());
            timestampTextView.setText(formattedDate);
            userTextView.setText(item.getUser());
            userRolTextView.setText(item.getUserRol());
            logTypeTextView.setText(item.getLogType().toString());
            messageTextView.setText(item.getMessage());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
