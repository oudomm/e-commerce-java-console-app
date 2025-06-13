package controller;
import model.entity.CartItem;
import model.entity.Product;
import model.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;


public class CartController {
    private List<CartItem> cart = new ArrayList<>();
    private ProductRepository productRepo = new ProductRepository();

    public void addToCart(int productId) {
        Product product = productRepo.findProductById(productId);
        if (product == null) {
            System.out.println(" Product not found.");
            return;
        }
        cart.add(new CartItem(product));
        System.out.println(" Added '" + product.getPName() + "' to cart (quantity=1).");
    }

//    public void showCart() {
//        System.out.println("\n--- Cart Items ---");
//        for (CartItem item : cart) {
//            System.out.printf("%s | Qty: %d | Price: %.2f\n",
//                    item.getProduct().getPName(),
//                    item.getQuantity(),
//                    item.getProduct().getPrice());
//        }
//        System.out.println("------------------\n");
//    }
    public List<CartItem> getCartItems() {
        return cart;
    }
}
