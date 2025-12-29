package com.example.hqrestaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {

    private final List<MenuItem> items;

    public MenuAdapter(List<MenuItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MenuItem item = items.get(position);

        holder.name.setText(item.name);
        holder.price.setText(item.price);

        // ✅ This is the key line for different images
        holder.image.setImageResource(item.imageResId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImage);
            name  = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
        }
    }
}

