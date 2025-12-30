package com.example.hqrestaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StaffMenuAdapter extends RecyclerView.Adapter<StaffMenuAdapter.VH> {

    public interface OnDeleteClick {
        void onDelete(MenuItem item);
    }

    private final List<MenuItem> items;
    private final OnDeleteClick onDeleteClick;

    public StaffMenuAdapter(List<MenuItem> items, OnDeleteClick onDeleteClick) {
        this.items = items;
        this.onDeleteClick = onDeleteClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_staff_menu_row, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        MenuItem item = items.get(position);

        h.name.setText(item.getName());
        h.price.setText(item.getPrice());

        // show drawable name like [burger]
        String img = item.getImageName();
        h.image.setText(img == null || img.isEmpty() ? "[IMG]" : "[" + img + "]");

        h.delete.setOnClickListener(v -> {
            if (onDeleteClick != null) onDeleteClick.onDelete(item);
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, price, image, delete;

        VH(@NonNull View itemView) {
            super(itemView);
            // ✅ MUST match your item_staff_menu_row.xml ids
            name   = itemView.findViewById(R.id.staffItemName);
            price  = itemView.findViewById(R.id.staffItemPrice);
            image  = itemView.findViewById(R.id.staffItemImage);
            delete = itemView.findViewById(R.id.staffDelete);
        }
    }
}
