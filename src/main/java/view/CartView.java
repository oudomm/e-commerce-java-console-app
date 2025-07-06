package view;

import model.entity.CartItem;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class CartView {

    public void showCart(List<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            System.out.println(Ansi.RED + "\n\t\t\t\t\t\t\t\t 🛒 Your cart is empty." + Ansi.RESET);
            return;
        }

        System.out.println(Ansi.BLUE+"\n\t\t\t\t\t\t\t\t=== 🛒 Cart Items ==="+Ansi.RESET);

        // Create table with 3 columns and border style
        Table table = new Table(3, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);

        // Center alignment for all cells
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Add headers with optional ANSI color
        table.addCell("\u001B[35mProduct Name\u001B[0m", style); // Purple
        table.addCell("\u001B[36mQty\u001B[0m", style);          // Cyan
        table.addCell("\u001B[33mPrice\u001B[0m", style);        // Yellow

        // Add cart items
        for (CartItem item : cartItems) {
            table.addCell("\u001B[32m" + item.getProduct().getPName() + "\u001B[0m", style); // Green
            table.addCell(String.valueOf(item.getQuantity()), style);
            table.addCell(String.format("$%.2f", item.getProduct().getPrice()), style);
        }

        // Set column widths
        table.setColumnWidth(0, 30, 50); // Product Name
        table.setColumnWidth(1, 30, 50);  // Qty
        table.setColumnWidth(2, 30, 50);  // Price

        // Print the rendered table
        System.out.println(table.render());
    }
}
