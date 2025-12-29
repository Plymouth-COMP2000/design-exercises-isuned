package com.example.hqrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.VH> {

    public interface OnDeleteClick {
        void onDelete(ReservationsModel model);
    }

    private final List<ReservationsModel> list;
    private final OnDeleteClick onDeleteClick;

    public ReservationsAdapter(List<ReservationsModel> list, OnDeleteClick onDeleteClick) {
        this.list = list;
        this.onDeleteClick = onDeleteClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        ReservationsModel m = list.get(position);
        h.txtMain.setText(m.username + " - " + m.time);
        h.txtSub.setText("Date: " + m.date + "   Guests: " + m.guests);

        h.btnDelete.setOnClickListener(v -> {
            if (onDeleteClick != null) onDeleteClick.onDelete(m);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtMain, txtSub;
        Button btnDelete;

        VH(@NonNull View itemView) {
            super(itemView);
            txtMain = itemView.findViewById(R.id.txtMain);
            txtSub = itemView.findViewById(R.id.txtSub);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}


