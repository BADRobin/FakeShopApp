package com.oleh.fakeshopapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private final ImageView productImageView;
    private final TextView productTitle;
    private final TextView productCategory;
    private final TextView productPrice;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productImageView = itemView.findViewById(R.id.productImageView);
        productTitle = itemView.findViewById(R.id.productTitle);
        productCategory = itemView.findViewById(R.id.productCategory);
        productPrice = itemView.findViewById(R.id.productPrice);
    }

    public ImageView getProductImageView() {
        return productImageView;
    }

    public TextView getProductTitle() {
        return productTitle;
    }

    public TextView getProductCategory() {
        return productCategory;
    }

    public TextView getProductPrice() {
        return productPrice;
    }
}
