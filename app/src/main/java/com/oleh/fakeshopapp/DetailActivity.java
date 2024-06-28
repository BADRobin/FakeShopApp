package com.oleh.fakeshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView fullSizeImageView = findViewById(R.id.fullSizeImageView);
        TextView detailProductName = findViewById(R.id.detailProductName);
        TextView detailProductPrice = findViewById(R.id.detailProductPrice);
        TextView detailProductDescription = findViewById(R.id.detailProductDescription);
        TextView detailProductCategory = findViewById(R.id.detailProductCategory);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        Picasso.get()
                .load(product.getImageURL())
                .into(fullSizeImageView);

        detailProductPrice.setText(product.getFormattedPrice());
        detailProductCategory.setText(product.getCategory());
        detailProductDescription.setText(product.getDescription());
        detailProductName.setText(product.getTitle());
    }
}
