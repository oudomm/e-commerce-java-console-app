import model.entity.Product;
import model.dto.ProductResponseDto;
import model.repository.ProductRepository;
import mapper.ProductMapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create repository instance
        ProductRepository productRepository = new ProductRepository();

        // Step 2: Get product list from database
        List<Product> products = productRepository.findAll();

        // Step 3: Convert products to DTOs
        List<ProductResponseDto> dtos = products.stream()
                .map(ProductMapper::MapFromProductToProductResponseDto)
                .toList();

        // Step 4: Print all products
        System.out.println("All Products:");
        for (ProductResponseDto dto : dtos) {
            System.out.println(dto.category() + " - " + dto.productName() +
                    " (Price: $" + dto.price() + ", Qty: " + dto.qty() + ")");
        }
        System.out.println();

        // Step 5: Group by category and print
        Map<String, List<ProductResponseDto>> grouped = dtos.stream()
                .collect(Collectors.groupingBy(ProductResponseDto::category));

        grouped.forEach((category, list) -> {
            System.out.println("Category: " + category);
            list.forEach(p ->
                    System.out.println(" - " + p.productName() +
                            " (Price: $" + p.price() + ", Qty: " + p.qty() + ")"));
            System.out.println();
        });
    }
}
