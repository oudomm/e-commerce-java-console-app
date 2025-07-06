package model.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mapper.OrderMapper;
import model.dto.OrderResponseDto;
import model.dto.ProductResponseDto;
import model.entity.Order;
import model.entity.Product;
import model.entity.User;
import model.repository.OrderRepository;
import model.repository.ProductRepository;
import model.repository.UserRepository;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@NoArgsConstructor
public class OrderServiceImp {
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final UserRepository userRepository = new UserRepository();
    public void writeRecordToFile(OrderResponseDto orderResponseDto) {
        String filename = "Receipt.txt";
        StringBuilder receipt = new StringBuilder();

        receipt.append("================================================\n");
        receipt.append("                       RECEIPT                  \n");
        receipt.append("================================================\n");
        receipt.append(String.format("%-5s %-25s %10s\n", "No.", "Product Name", "Qty"));
        receipt.append("------------------------------------------------\n");

        List<ProductResponseDto> products = orderResponseDto.productResponseDtoList();
        int index = 1;

        for (ProductResponseDto product : products) {
            receipt.append(String.format("%-5d %-25s %10d\n",
                    index++, product.pName(), product.qty()));
        }

        receipt.append("================================================\n");
        receipt.append("          Thank you for your purchase!          \n");
        receipt.append("================================================\n");

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename))) {
            out.write(receipt.toString().getBytes(StandardCharsets.UTF_8));
            System.out.println("✅ Receipt written to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error writing receipt: " + e.getMessage());
        }
    }


    public OrderResponseDto makeOrder(String userUuid,List<String> productUuids,int roleNumber,int numberOfProducts) {
//        System.out.println("product in service");
//        productUuids.forEach(System.out::println);
        try {
            List<Product> products = new ArrayList<>();
            List<Integer> productsIds = new ArrayList<>();
            Integer userId;
            if (userUuid != null) {
                User user = userRepository.findAll(roleNumber,numberOfProducts).stream().filter(U->U.getUUuid().equals(userUuid)).findFirst().get();
                User user1 = userRepository.findUserById(user.getId());
                userId = user1.getId();
            } else {
                userId = 0;
            }
            if (productUuids != null) {
                productUuids.forEach(productUuid->products.add(productRepository.findAll(roleNumber,numberOfProducts).stream().filter(product->product.getPUuid().equals(productUuid)).findFirst().get()));
                products.forEach(product -> productsIds.add(product.getId()));
            }
            productsIds.forEach(System.out::println);
            orderRepository.save(new Order(userId,productsIds));
            writeRecordToFile(OrderMapper.MapperFromOrdertoOrderResponseDto(new Order(userId,productsIds)));

            return OrderMapper.MapperFromOrdertoOrderResponseDto(new Order(userId,productsIds));

        }catch (NoSuchElementException exception){
            System.out.println("No such product or order found"+ exception.getMessage());
        }
        return null;
    }
}
