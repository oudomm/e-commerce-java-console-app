package view;


import model.entity.Product;

import java.util.*;

public class ProductView {
    private Map<String, Integer> uuidToProductId = new HashMap<>();

    public void showProducts(List<Product> products) {
        uuidToProductId.clear();

        System.out.println("\n=== PRODUCTS LIST ===");
        for (Product p : products) {
            String uuid = p.getPUuid();
            uuidToProductId.put(uuid, p.getId());

            System.out.printf("UUID: %s | Name: %s | Price: %.2f | Qty: %d\n",
                    uuid, p.getPName(), p.getPrice(), p.getQty());
        }
    }

    public Map<String, Integer> getUuidToProductIdMap() {
        return uuidToProductId;
    }
}
