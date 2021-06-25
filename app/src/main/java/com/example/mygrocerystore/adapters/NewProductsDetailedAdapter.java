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
import com.example.mygrocerystore.models.NewProductsDetailedModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewProductsDetailedAdapter extends RecyclerView.Adapter<NewProductsDetailedAdapter.ViewHolder> {

    Context context;
    List<NewProductsDetailedModel> newProductsDetailedModelList;

    OnClickListener onClickListener;

    public void setOnClickListener (OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onPlusClick(TextView textView, int position);
        void onMinusClick(TextView textView, int position);
        void onBuy(TextView textView,int position);
    }

    public NewProductsDetailedAdapter(Context context, List<NewProductsDetailedModel> newProductsDetailedModelList) {
        this.context = context;
        this.newProductsDetailedModelList = newProductsDetailedModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_newproducts_detailed_item, parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        int totalQuantity;

        Glide.with(context).load(newProductsDetailedModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(newProductsDetailedModelList.get(position).getName());
        holder.price.setText(newProductsDetailedModelList.get(position).getPrice());

        totalQuantity = Integer.parseInt(holder.quantity.getText().toString());
//        holder.addItem.setOnClickListener(v -> {
//            if(totalQuantity<10){
//                totalQuantity = totalQuantity+1;
//                holder.quantity.setText(String.valueOf(totalQuantity));
//            }
//        });
//
//        holder.removeItem.setOnClickListener(v -> {
//            if(totalQuantity>1){
//                totalQuantity = totalQuantity-1;
//                holder.quantity.setText(String.valueOf(totalQuantity));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return newProductsDetailedModelList.size();
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

            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            onClickListener.onPlusClick(quantity,position);
                        }
                    }
                }
            });
            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            onClickListener.onMinusClick(quantity,position);
                        }
                    }
                }
            });
            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            onClickListener.onBuy(quantity,position);
                        }
                    }
                }
            });

        }
    }
}
