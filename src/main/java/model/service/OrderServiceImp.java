package model.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mapper.OrderMapper;
import model.dto.OrderResponseDto;
import model.entity.Order;
import model.entity.Product;
import model.entity.User;
import model.repository.OrderRepository;
import model.repository.ProductRepository;
import model.repository.UserRepository;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@NoArgsConstructor
public class OrderServiceImp {
    private final ProductRepository productRepository = new ProductRepository();
    private final OrderRepository orderRepository = new OrderRepository();
    private final UserRepository userRepository = new UserRepository();
    public void WriteRecordToFile(OrderResponseDto orderResponseDto) {
        String filename = "Receipt.txt";
        List<String> items = new ArrayList<>();
        String itemLine = "";
        orderResponseDto.productResponseDtoList().forEach(productResponseDto -> items.add(productResponseDto.pName()));
        for(String item : items) {
            itemLine="item : " + item+"\n";

        }
        String data =
                "==========================\n" +
                "        RECEIPT           \n" +
                "--------------------------\n" +
                itemLine +
                "--------------------------\n" +
                "Thank you for your purchase!\n" +
                "==========================\n";
        try(BufferedOutputStream bufferedOutputStream= new BufferedOutputStream(new FileOutputStream(filename))) {
            byte[] bytes = data.getBytes();
            bufferedOutputStream.write(bytes);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public OrderResponseDto makeOrder(String userUuid,List<String> productUuids) {
        try {
            List<Product> products = new ArrayList<>();
            List<Integer> productsIds = new ArrayList<>();
            Integer userId;
            if (userUuid != null) {
                User user = userRepository.findAll().stream().filter(U->U.getUUuid().equals(userUuid)).findFirst().get();
                User user1 = userRepository.findUserById(user.getId());
                userId = user1.getId();
            } else {
                userId = 0;
            }
            if (productUuids != null) {
                productUuids.forEach(productUuid->products.add(productRepository.findAll().stream().filter(product->product.getPUuid().equals(productUuid)).findFirst().get()));
                products.forEach(product -> productsIds.add(product.getId()));
            }
            productsIds.forEach(System.out::println);
            orderRepository.save(new Order(userId,productsIds));
            WriteRecordToFile(OrderMapper.MapperFromOrdertoOrderResponseDto(new Order(userId,productsIds)));

            return OrderMapper.MapperFromOrdertoOrderResponseDto(new Order(userId,productsIds));

        }catch (NoSuchElementException exception){
            System.out.println("No such product or order found"+ exception.getMessage());
        }
        return null;
    }
}
