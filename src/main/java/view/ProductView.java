package view;


import mapper.ProductMapper;
import model.dto.ProductResponseDto;
import model.entity.Product;

import java.util.*;
import java.util.stream.Collectors;

public class ProductView {
    private Map<String, Integer> uuidToProductId = new HashMap<>();
    private TableUI<ProductResponseDto> tableUI = new TableUI<>();
    public void showProducts(List<Product> products) {
        uuidToProductId.clear();

        System.out.println("\n=== PRODUCTS LIST ===");
//        for (Product p : products) {
//            String uuid = p.getPUuid();
//            uuidToProductId.put(uuid, p.getId());
//
//            System.out.printf("UUID: %s | Name: %s | Price: %.2f | Qty: %d\n",
//                    uuid, p.getPName(), p.getPrice(), p.getQty());
//        }

        List<ProductResponseDto> productResponseDto = products.stream().map(ProductMapper::mapFromProductToProductResponseDto).toList();
        String[] productColumns = {
                "Product UUID",
                "Product Name",
                "Price",
                "Quantity"
        };
        tableUI.getTableDisplay(productResponseDto,productColumns,productColumns.length);

    }

    public Map<String, Integer> getUuidToProductIdMap(List<Product> products) {
        uuidToProductId.clear(); // Clear old data

        for (Product p : products) {
            String uuid = p.getPUuid();
            if (uuid != null) {
                uuidToProductId.put(uuid.trim().toLowerCase(), p.getId());
            }
        }

        return uuidToProductId;
    }

}
