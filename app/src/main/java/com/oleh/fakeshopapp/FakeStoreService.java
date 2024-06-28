package com.oleh.fakeshopapp;

import android.os.Handler;
import android.os.Looper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class FakeStoreService {

    public interface OnAsyncFinished<T> {
        void onFinish(T result);
    }

    private final NetworkService networkService;

    public FakeStoreService(NetworkService networkService) {
        this.networkService = networkService;
    }

    public void getProductsAsync(OnAsyncFinished<List<Product>> callback) {
        new Thread(() -> {

            List<Product> products = getProducts();

            // Call the callback function back on the UI Thread
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.onFinish(products);
            });
        }).start();
    }

    public List<Product> getProducts() {
        String url = "https://fakestoreapi.com/products";
        return getProductsByURL(url);
    }

    public void getProductsByCategoryAsync(String category, OnAsyncFinished<List<Product>> callback) {
        new Thread(() -> {
            List<Product> products = getProductsByCategory(category);
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.onFinish(products);
            });
        }).start();
    }

    public List<Product> getProductsByCategory(String category) {
        String url = "https://fakestoreapi.com/products/category/" + category;
        return getProductsByURL(url);
    }

    public void getCategoriesAsync(OnAsyncFinished<List<String>> callback) {
        new Thread(() -> {
            List<String> categories = getCategories();
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.onFinish(categories);
            });
        }).start();
    }

    public List<String> getCategories() {
        String result = networkService.getJSON("https://fakestoreapi.com/products/categories");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<String> categories = new ArrayList<>();
            JsonNode rootNode = mapper.readTree(result);
            for (JsonNode stringNode : rootNode) {
                categories.add(stringNode.asText());
            }
            return categories;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> getProductsByURL(String url) {
        String result = networkService.getJSON(url);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(result);
            List<Product> products = new ArrayList<>();
            for (JsonNode productNode : rootNode) {
                Product product = new Product(
                        productNode.get("id").asLong(),
                        productNode.get("title").asText(),
                        productNode.get("price").asDouble(),
                        productNode.get("description").asText(),
                        productNode.get("category").asText(),
                        productNode.get("image").asText(),
                        productNode.get("rating").get("rate").asDouble(),
                        productNode.get("rating").get("count").asInt()
                );
                products.add(product);
            }
            return products;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}