package mapper;

import lombok.RequiredArgsConstructor;
import model.dto.OrderResponseDto;
import model.dto.ProductResponseDto;
import model.dto.UserResponseDto;
import model.entity.Order;
import model.entity.Product;
import model.entity.User;
import model.repository.ProductRepository;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderMapper {
    private static final UserRepository userRepository = new UserRepository();
    private static final ProductRepository productRepository  = new ProductRepository();
    public static OrderResponseDto MapperFromOrdertoOrderResponseDto(Order order) {
        List<Product> products = new ArrayList<>();
        User user = userRepository.findUserById(order.getUserId());
        UserResponseDto userResponseDto1 = UserMapper.mapFromUserToUserResponseDto(user);//note
        List<ProductResponseDto> productResponseDtos = new ArrayList<>(); //note
        order.getProductIds().forEach(productId -> {products.add(productRepository.findProductById(productId));});
        products.forEach(product -> productResponseDtos.add(ProductMapper.mapFromProductToProductResponseDto(product)));

        return new OrderResponseDto(null,userResponseDto1,productResponseDtos);
    }
}
