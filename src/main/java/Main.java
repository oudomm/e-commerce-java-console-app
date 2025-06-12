import java.util.List;
import java.util.Map;

import controller.ProductController;
import model.dto.ProductResponseDto;
public class Main {
    public static void main(String[] args) {
        ProductController controller = new ProductController();

        System.out.println("=== All Products ===");
        for (ProductResponseDto p : controller.getAllProducts()) {
            System.out.println(p.category() + " - " + p.productName() + " ($" + p.price() + ")");
        }

        System.out.println("\n=== Grouped by Category ===");
        Map<String, List<ProductResponseDto>> grouped = controller.getProductsGroupedByCategory();

        for (String category : grouped.keySet()) {
            System.out.println("Category: " + category);
            for (ProductResponseDto p : grouped.get(category)) {
                System.out.println("  - " + p.productName());
            }
        }
    }
}
