package view;


import model.entity.CartItem;

import java.util.List;

public class CartView {

    public void showCart(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            System.out.println("\n🛒 Your cart is empty.");
            return;
        }

        System.out.println("\n=== 🛒 Cart Items ===");
        System.out.printf("%-20s %-10s %-10s\n", "Product Name", "Qty", "Price");
        System.out.println("-------------------------------------------");

        for (CartItem item : cartItems) {
            System.out.printf("%-20s %-10d $%-10.2f\n",
                    item.getProduct().getPName(),
                    item.getQuantity(),
                    item.getProduct().getPrice());
        }

        System.out.println("-------------------------------------------\n");
    }
}

