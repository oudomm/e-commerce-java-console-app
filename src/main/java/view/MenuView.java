package view;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

public class MenuView {

    public static void showMenu() {
        Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        CellStyle style = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Header
        table.addCell("\u001B[36mOption\u001B[0m", style);  // Cyan
        table.addCell("\u001B[35mDescription\u001B[0m", style);  // Purple

        // Menu options
        table.addCell("1", style); table.addCell("Login", style);
        table.addCell("2", style); table.addCell("Register", style);
        table.addCell("3", style); table.addCell("Show Products", style);
        table.addCell("4", style); table.addCell("Add Product to Cart", style);
        table.addCell("5", style); table.addCell("Show Cart", style);
        table.addCell("6", style); table.addCell("Order", style);
        table.addCell("7", style); table.addCell("Insert Million Product", style);
        table.addCell("8", style); table.addCell("Read Million Products", style);
        table.addCell("9", style); table.addCell("Logout", style);


        table.addCell("0", style); table.addCell("Exit", style);

        // Column widths
        table.setColumnWidth(0, 20, 60);
        table.setColumnWidth(1, 20, 60);

        // Display menu
        System.out.println("\n🧭 MAIN MENU:");
        System.out.println(table.render());
    }
}

