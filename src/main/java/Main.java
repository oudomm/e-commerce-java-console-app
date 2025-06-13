import controller.CartController;
import model.entity.Product;
import model.repository.ProductRepository;
import view.CartView;
import view.ProductView;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepo = new ProductRepository();
        ProductView productView = new ProductView();
        CartController cartController = new CartController();
        CartView cartView = new CartView();

        Scanner scanner = new Scanner(System.in);
        List<Product> products = productRepo.findAll();

        while (true) {
            System.out.println("\n1. Show Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Show Cart");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    products = productRepo.findAll();
                    productView.showProducts(products);
                    break;

                case "2":
                    productView.showProducts(products);
                    System.out.print("Enter product UUID to add to cart: ");
                    String inputUuid = scanner.nextLine();

                    Map<String, Integer> uuidMap = productView.getUuidToProductIdMap();
                    Integer productId = uuidMap.get(inputUuid);

                    if (productId == null) {
                        System.out.println("❌ Invalid UUID - product not found.");
                    } else {
                        cartController.addToCart(productId);
                    }
                    break;

                case "3":
                    cartView.showCart(cartController.getCartItems());
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
