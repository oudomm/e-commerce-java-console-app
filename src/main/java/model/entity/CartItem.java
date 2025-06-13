package model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1; // Always 1 by default
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}


