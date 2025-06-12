package controller;

import model.entity.CartItem;
import model.entity.Product;
import model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class CartController {
    private final List<CartItem> cart = new ArrayList<>();
    private final ProductRepository productRepo = new ProductRepository();

    public boolean addToCart(String productIdStr, int quantity) {
        try {
            UUID productId = UUID.fromString(productIdStr);
            Product product = productRepo.findProductById(productId);

            if (product == null) {
                System.out.println("❌ Product not found.");
                return false;
            }

//            if (quantity > product.getStock()) {
//                System.out.println("❌ Not enough stock.");
//                return false;
//            }

            for (CartItem item : cart) {
                if (item.getProduct().getId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    System.out.println("✅ Updated quantity in cart.");
                    return true;
                }
            }

            cart.add(new CartItem(product, quantity));
            System.out.println("✅ Product added to cart.");
            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid UUID format.");
            return false;
        }
    }

    public List<CartItem> getCart() {
        return cart;
    }
}
