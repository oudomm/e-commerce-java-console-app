package view;

import model.dto.ProductResponseDto;
import model.dto.ProductResponseDto2;
import model.dto.UserResponseDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class TableUI<T> {

    public void getTableDisplay(List<T> tList, String[] columns, int numberColumn) {
        if (tList == null || tList.isEmpty()) {
            System.out.println("No data to display.");
            return;
        }

        Table table = new Table(numberColumn, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);

        // Add table headers
        for (String columnName : columns) {
            table.addCell(columnName, cellStyle);
        }

        // Add table rows based on data type
        T first = tList.getFirst();
        if (first instanceof ProductResponseDto) {
            for (T p : tList) {
                ProductResponseDto dto = (ProductResponseDto) p;
                table.addCell(Ansi.BLUE + dto.pUuid() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.GREEN + dto.pName() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.YELLOW + dto.price() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.CYAN + dto.qty() + Ansi.RESET, cellStyle);

            }
        } else if (first instanceof UserResponseDto) {
            for (T p : tList) {
                UserResponseDto dto = (UserResponseDto) p;
                table.addCell(Ansi.BLUE + dto.uuid() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.GREEN + dto.name() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.CYAN + dto.email() + Ansi.RESET, cellStyle);

            }
        } else if (first instanceof ProductResponseDto2) {
            for (T p : tList) {
                ProductResponseDto2 dto = (ProductResponseDto2) p;
                table.addCell(Ansi.PURPLE + dto.id() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.BLUE + dto.pUuid() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.GREEN + dto.category() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.GREEN + dto.pName() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.YELLOW + dto.price() + Ansi.RESET, cellStyle);
                table.addCell(Ansi.CYAN + dto.qty() + Ansi.RESET, cellStyle);

            }
        }
        for (String columnName : columns) {
            table.addCell(columnName, cellStyle);
        }
        // Set consistent column width for ALL columns
        for (int i = 0; i < numberColumn; i++) {
            table.setColumnWidth(i, 40, 60);
        }


        // Render the table
        System.out.println(table.render());
    }
}
