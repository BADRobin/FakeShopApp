package com.oleh.fakeshopapp;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

public class Product implements Serializable {

    private final long id;
    private final String title;
    private final double price;
    private final String description;
    private final String category;
    private final String imageURL;
    private final double ratingRate;
    private final int ratingCount;

    public Product(long id, String title, double price, String description, String category, String imageURL, double ratingRate, int ratingCount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageURL = imageURL;
        this.ratingRate = ratingRate;
        this.ratingCount = ratingCount;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public double getRatingRate() {
        return ratingRate;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public String getFormattedPrice(){
        return String.format(Locale.ENGLISH, "%.2f €", price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(price, product.price) == 0 && Double.compare(ratingRate, product.ratingRate) == 0 && ratingCount == product.ratingCount && Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(category, product.category) && Objects.equals(imageURL, product.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, description, category, imageURL, ratingRate, ratingCount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ratingRate=" + ratingRate +
                ", ratingCount=" + ratingCount +
                '}';
    }
}
