package com.oleh.fakeshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private FakeStoreService fakeStoreService;
    private List<String> categories;
    private RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        productsRecyclerView=findViewById(R.id.productsRecyclerView);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                updateRecyclerView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        fakeStoreService = new FakeStoreService(new OkHttpService());
        fakeStoreService.getCategoriesAsync(result -> {
            categories = new ArrayList<>();
            categories.add("All categories");
            categories.addAll(result);

            ArrayAdapter<String> spinerAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    categories
            );
            categorySpinner.setAdapter(spinerAdapter);
        });
    }

    private void updateRecyclerView(int categoryPosition) {
        if(categoryPosition == 0){
            fakeStoreService.getProductsAsync(result -> {
                setAdapter(result);
            });

        }

    }

    private void setAdapter(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(products);
        adapter.setOnItemClickedListener((view, position) -> {
            Product product = products.get(position);

            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("product", product);

            startActivity(intent);
        });

        productsRecyclerView.setAdapter(adapter);
    }
}

