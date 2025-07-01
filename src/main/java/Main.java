import controller.CartController;
import controller.OrderController;
import controller.ProductController;
import controller.UserController;
import lombok.RequiredArgsConstructor;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.CartItem;
import model.entity.Product;
import model.handelException.UserAlreadyExistsException;
import model.repository.ProductRepository;
import view.CartView;
import view.ProductView;

import java.util.*;

public class Main {
    public static void processOrder(List<CartItem> cartItems){
//        cartItems.forEach(System.out::println);
        OrderController orderController = new OrderController();
        System.out.print("[+] Insert User Uuid: ");
        String userUuid = new Scanner(System.in).nextLine();
        System.out.print("[+] Click to order ....!");
        new Scanner(System.in).nextLine();
        List<String> productUuidLists = new ArrayList<>();
        if (!cartItems.isEmpty()){
            for (CartItem cartItem : cartItems) {
                productUuidLists.add(cartItem.getProduct().getPUuid());
            }
        }else {
            System.out.println("No cart items found");
        }

        try{
            Thread.sleep(100);
            System.out.println("Making order...");
        }catch (Exception ignore){}
//        productUuidLists.forEach(System.out::println);
        orderController.processOrder(userUuid, productUuidLists);
    }
    public static void main(String[] args) throws UserAlreadyExistsException {
        ProductRepository productRepo = new ProductRepository();
        ProductView productView = new ProductView();
        CartController cartController = new CartController();
        CartView cartView = new CartView();
        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);
        List<Product> products = productRepo.findAll();
//        System.out.print("[+] products ");
//        products.forEach(System.out::println);

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Show Products");
            System.out.println("4. Add Product to Cart");
            System.out.println("5. Show Cart");
            System.out.println("6. order");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" :{
                    String email = null;
                    String password = null;
                    Boolean status = null;

                    try {
                        // Input email
                        System.out.print("Enter email: ");
                        email = scanner.nextLine();
                        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                            throw new IllegalArgumentException("Invalid email format.");
                        }

                        // Input password
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();
                        if (password.length() < 6) {
                            throw new IllegalArgumentException("Password must be at least 6 characters.");
                        }

                        // Input status
                        userController.login(new UserloginDto(email,password,false));

                    } catch (InputMismatchException e) {
                        System.out.println("❌ Invalid input type.");
                    } catch (IllegalArgumentException e) {
//                        System.out.println("❌ " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("❌ Unexpected error: " + e.getMessage());
                    }
                    break;
                }
                case "2" :{
                    String name, email, password;

                    // Input name
                    System.out.print("Enter name: ");
                    name = scanner.nextLine();

                    // Input email with basic format validation
                    while (true) {
                        System.out.print("Enter email: ");
                        email = scanner.nextLine();
                        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                            System.out.println("❌ Invalid email format. Try again.");
                        } else {
                            break;
                        }
                    }

                    // Input password with basic validation
                    while (true) {
                        System.out.print("Enter password (at least 6 characters): ");
                        password = scanner.nextLine();
                        if (password.length() < 6) {
                            System.out.println("❌ Password too short. Try again.");
                        } else {
                            break;
                        }
                    }

                    // Create the DTO
                    UserCreateDto user = new UserCreateDto(name, email, password);
                    userController.signUp(user);

                    // Print result
                    System.out.println("✅ User created: " + user);
                    break;
                }
                case "3":
                    products = productRepo.findAll();
                    productView.showProducts(products);
                    break;
                case "4":
                    productView.showProducts(products);
                    Map<String, Integer> cartProducts = productView.getUuidToProductIdMap(products);
                    System.out.print("Enter product UUID: ");
                    String inputUuid = scanner.nextLine().trim().toLowerCase();

                    Integer productId = cartProducts.get(inputUuid);

                    if (productId == null) {
                        System.out.println("❌ Invalid UUID - product not found.");
                    } else {
                        cartController.addToCart(productId);
                    }
                    break;

                case "5":
                    cartView.showCart(cartController.getCartItems());
                    break;
                case "6":
                    processOrder(cartController.getCartItems());
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
