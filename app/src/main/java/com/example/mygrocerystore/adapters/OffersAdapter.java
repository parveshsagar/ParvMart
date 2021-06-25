package com.example.mygrocerystore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.models.OffersModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    Context context;
    List<OffersModel> offersModelList;

    int totalQuantity;

    public OffersAdapter(Context context, List<OffersModel> offersModelList) {
        this.context = context;
        this.offersModelList = offersModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_item, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(offersModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(offersModelList.get(position).getName());
        holder.price.setText(offersModelList.get(position).getPrice());

        totalQuantity = Integer.parseInt(holder.quantity.getText().toString());
        holder.addItem.setOnClickListener(v -> {
            if(totalQuantity<10){
                totalQuantity = totalQuantity+1;
                holder.quantity.setText(String.valueOf(totalQuantity));
            }
        });

        holder.removeItem.setOnClickListener(v -> {
            if(totalQuantity>1){
                totalQuantity = totalQuantity-1;
                holder.quantity.setText(String.valueOf(totalQuantity));
            }
        });

    }

    @Override
    public int getItemCount() {
        return offersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,addItem,removeItem;
        TextView name,price,quantity;
        Button buyNow;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.prod_nav_img);
            name = itemView.findViewById(R.id.nav_prod_name);
            price = itemView.findViewById(R.id.price);

            addItem = itemView.findViewById(R.id.add_item);
            removeItem = itemView.findViewById(R.id.remove_item);
            quantity = itemView.findViewById(R.id.quantity);
            buyNow = itemView.findViewById(R.id.buynow);

        }
    }
}
