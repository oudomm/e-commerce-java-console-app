package view;

import controller.CartController;
import controller.OrderController;
import controller.ProductController;
import controller.UserController;
import model.dto.ProductResponseDto;
import model.dto.ProductResponseDto2;
import model.dto.UserCreateDto;
import model.dto.UserloginDto;
import model.entity.CartItem;
import model.entity.Product;
import model.handelException.UserAlreadyExistsException;
import model.repository.ProductRepository;

import java.util.*;

public class UI {
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
        orderController.processOrder(userUuid, productUuidLists,0,100);
    }
    public static void ui() throws UserAlreadyExistsException {
        ProductRepository productRepo = new ProductRepository();
        ProductController productController = new ProductController();
        ProductView productView = new ProductView();
        CartController cartController = new CartController();
        CartView cartView = new CartView();
        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);
        List<Product> products = productRepo.findAll(0,100);
//        System.out.print("[+] products ");
//        products.forEach(System.out::println);
        boolean authorize = false;
        while (true) {
            MenuView.showMenu();
            System.out.print(Ansi.CYAN+"Choose option: "+Ansi.RESET);
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
                        String uuid = userController.login(new UserloginDto(email,password,false));
                        if (uuid != null) {
                            authorize = true;
                        }

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
                    authorize = true;
                    // Print result
                    System.out.println("✅ User created: " + user);
                    break;
                }
                case "3":
                    System.out.println(authorize);
                    if (authorize) {
                        System.out.print("Enter product stating row : ");
                        int rowNumber = new Scanner(System.in).nextInt();
                        System.out.print("Enter number of products : ");
                        int numberOfProducts = new Scanner(System.in).nextInt();
                        products = productRepo.findAll(rowNumber,numberOfProducts);
                        productView.showProducts(products);

                    }else {
                        System.out.println("Please login first");
                    }

                    break;
                case "4":
                    if (authorize) {
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
                    }else {
                        System.out.println("Please login first");
                    }
                    break;

                case "5":
                    if (authorize) {
                        cartView.showCart(cartController.getCartItems());
                    }else {
                        System.out.println("Please login first");
                    }
                    break;
                case "6":
                    if (authorize) {
                        processOrder(cartController.getCartItems());
                    }else {
                        System.out.println("Please login first");
                    }
                    break;
                case "7":
                    if (authorize) {
                        productController.insertProductAsBatch(10000000);
                        System.out.println("10 Million products inserted.");
                    }else {
                        System.out.println("Please login first");
                    }
                    break;
                case "8":
                    if (authorize) {
                        TableUI<ProductResponseDto2> tableUI = new TableUI<>();
                        for (int i = 1; i <= 11; i++) {
                            System.out.println("🔄 Reading batch " + i + " (products " + ((i - 1) * 1_000_000 + 1) + " to " + (i * 1_000_000) + ")");
                            List<ProductResponseDto2> batch = productController.readProductAsBatch(1_000_000, i); //store as batch
                            for(int j = 0; j < 10; j++) {
                                int startIndex = j * 100000;
                                int endIndex = (j+1) * 100000;
                                List<ProductResponseDto2> sample = batch.subList(startIndex, Math.min(endIndex, batch.size()));
                                String[] productArray = {"Id", "Product UUID", "Category", "Product Name", "Price", "Quantity"};
                                tableUI.getTableDisplay(sample, productArray, productArray.length);

                            }
                        }

                        System.out.println("10000000 products read successfully.");
                    }else {
                        System.out.println("Please login first");
                    }


                    break;
                case "9":
                    if (authorize) {
                        System.out.print("Enter product uuid : ");
                        String productUuid = scanner.nextLine();
                        productController.deleteProduct(productUuid,products);
                    }
                    break;
                case "10":
                    if (authorize) {
                        System.out.print("Enter product uuid : ");
                        String productUuid = scanner.nextLine();
                        ProductResponseDto productResponseDto = productController.getProductResponseDto(products,productUuid);
                        TableUI<ProductResponseDto> tableUI = new TableUI<>();
                        String[] productColumns = {
                                "Product UUID",
                                "Product Name",
                                "Price",
                                "Quantity"
                        };
                        tableUI.getTableDisplay(Collections.singletonList(productResponseDto),productColumns,productColumns.length);

                    }else {
                        System.out.println("Please login first");
                    }
                    break;
                case "11":
                    if (authorize) {
                        authorize = false;
                        System.out.println("Logout from the system");
                    }else {
                        System.out.println("You have not logged in yet");
                    }
                    break;
                case "12":
                    userController.autoLogin();
                    break;
                case "0":
                    if (authorize) {
                        System.out.println("Goodbye!");

                    }
                    return;
                default:
                    System.out.println("Invalid option");
            }

        }
    }
}
