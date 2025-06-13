package view;

import controller.UserController;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.User;
import model.handelException.InvalidCredentialsException;
import model.handelException.UserAlreadyExistsException;
import model.service.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private final UserController userController;
    private final Scanner scanner;
    private String currentUserUuid;

    public UserView() {
        this.userController = new UserController(new UserServiceImpl());
        this.scanner = new Scanner(System.in);
        this.currentUserUuid = null;
    }

    public void start() {
        System.out.println("=== User Management System ===");

        while (true) {
            displayMenu();
            int choice = getChoice();

            try {
                switch (choice) {
                    case 1 -> signUp();
                    case 2 -> login();
                    case 3 -> autoLogin();
                    case 4 -> showAllUsers();
                    case 5 -> showCurrentUser();
                    case 6 -> logout();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("Current Status: " + (currentUserUuid != null ? "Logged in" : "Not logged in"));
        System.out.println("=".repeat(40));
        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.println("3. Auto Login");
        System.out.println("4. Show All Users");
        System.out.println("5. Show Current User Details");
        System.out.println("6. Logout");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private int getChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void signUp() {
        System.out.println("\n--- Sign Up ---");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            UserCreateDto createDto = new UserCreateDto(name, email, password);
            String result = userController.signUp(createDto);
            System.out.println("✓ Sign up successful! User created with email: " + result);
        } catch (UserAlreadyExistsException e) {
            System.out.println("✗ Sign up failed: " + e.getMessage());
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            UserloginDto loginDto = new UserloginDto(email, password, false);
            String uuid = userController.login(loginDto);
            currentUserUuid = uuid;
            System.out.println("✓ Login successful! Welcome back!");
            System.out.println("Your session UUID: " + uuid);
        } catch (InvalidCredentialsException e) {
            System.out.println("✗ Login failed: " + e.getMessage());
        }
    }

    private void autoLogin() {
        System.out.println("\n--- Auto Login ---");

        try {
            UserloginDto autoLoginDto = userController.autoLogin();
            if (autoLoginDto != null && autoLoginDto.status()) {
                System.out.println("✓ Auto login successful!");
                System.out.println("Email: " + autoLoginDto.email());
                System.out.println("Status: " + (autoLoginDto.status() ? "Active" : "Inactive"));

                // You might want to get the UUID here if needed
                System.out.println("Note: Auto login completed, but UUID not retrieved in this demo.");
            } else {
                System.out.println("✗ No active session found or auto login failed.");
            }
        } catch (Exception e) {
            System.out.println("✗ Auto login failed: " + e.getMessage());
        }
    }

    private void showAllUsers() {
        System.out.println("\n--- All Users ---");

        try {
            List<UserResponseDto> users = userController.showAllUser();

            if (users.isEmpty()) {
                System.out.println("No users found.");
                return;
            }

            System.out.println("Total users: " + users.size());
            System.out.println("-".repeat(60));

            for (int i = 0; i < users.size(); i++) {
                UserResponseDto user = users.get(i);
                System.out.printf("%d. Name: %s | Email: %s%n",
                        i + 1, user.name(), user.email());
            }

        } catch (Exception e) {
            System.out.println("✗ Failed to retrieve users: " + e.getMessage());
        }
    }

    private void showCurrentUser() {
        if (currentUserUuid == null) {
            System.out.println("✗ You are not logged in. Please login first.");
            return;
        }

        System.out.println("\n--- Current User Details ---");

        try {
            User user = userController.findUserByUuid(currentUserUuid);
            if (user != null) {
                System.out.println("ID: " + user.getId());
                System.out.println("UUID: " + user.getUUuid());
                System.out.println("Name: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Status: " + (user.getStatus() ? "Active" : "Inactive"));
            } else {
                System.out.println("✗ User not found with UUID: " + currentUserUuid);
            }
        } catch (Exception e) {
            System.out.println("✗ Failed to retrieve user details: " + e.getMessage());
        }
    }

    private void logout() {
        if (currentUserUuid == null) {
            System.out.println("✗ You are not logged in.");
            return;
        }

        currentUserUuid = null;
        System.out.println("✓ You have been logged out successfully.");
    }

    // Main method for testing
    public static void main(String[] args) {
        UserView userView = new UserView();
        userView.start();
    }
}