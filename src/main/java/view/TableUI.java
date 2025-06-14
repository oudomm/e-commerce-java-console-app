package view;

import model.dto.ProductResponseDto;
import model.dto.UserResponseDto;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

public class TableUI<T>{
    public void getTableDisplay(List<T> tList,String[] columns,int numberColumn){
        Table table = new Table(numberColumn, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
        for (String columnName : columns){
            table.addCell(columnName,cellStyle);
            table.setColumnWidth(numberColumn-1,50,70);
        }
        for(T p : tList){
            if(tList.getFirst() instanceof ProductResponseDto){
                table.addCell(((ProductResponseDto)p).pUuid(),cellStyle);
                table.addCell(((ProductResponseDto)p).pName(),cellStyle);
                table.addCell(String.valueOf(((ProductResponseDto) p).toString()), cellStyle);
            }
            if(tList.getFirst() instanceof UserResponseDto){
                table.addCell(((((UserResponseDto) p).uuid())),cellStyle);
                table.addCell(((UserResponseDto)p).name(),cellStyle);
                table.addCell(((UserResponseDto)p).email(),cellStyle);
                table.addCell(((UserResponseDto)p).toString(),cellStyle);
            }
        }
        for (int i = 0; i < tList.size(); i++) {
            if(tList.getFirst() instanceof ProductResponseDto){
                table.setColumnWidth(i,50,70);
            }
            if(tList.getFirst() instanceof UserResponseDto){
                table.setColumnWidth(i,50,70);
            }
        }
//        if(tList.getFirst() instanceof UserResponseDto){
//            table.addCell(((((UserResponseDto) p).uuid())),cellStyle);
//            table.addCell(((UserResponseDto)p).userName(),cellStyle);
//            table.addCell(((UserResponseDto)p).email(),cellStyle);
//            table.addCell(((UserResponseDto)p).createdAt().toString(),cellStyle);
//        }

        System.out.println(table.render());
    }
}
