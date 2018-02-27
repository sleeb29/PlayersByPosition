package com.baseball.players_by_position.excel.formatter;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

@Service
public class PositionExcelFormatter implements IExcelFormatter {

    private CellStyle headerStyle;

    @Override
    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    @Override
    public void setHeaderStyle(CellStyle headerStyle) {
        this.headerStyle = headerStyle;
    }

    public void updateHeaderStyle(Font font){

        font.setFontName("Arial");
        headerStyle.setFillForegroundColor(HSSFColor.BLUE.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);
        headerStyle.setFont(font);

    }

}
