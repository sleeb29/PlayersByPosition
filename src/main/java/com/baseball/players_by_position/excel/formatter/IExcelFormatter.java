package com.baseball.players_by_position.excel.formatter;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public interface IExcelFormatter {

    CellStyle getHeaderStyle();
    void setHeaderStyle(CellStyle headerStyle);
    void updateHeaderStyle(Font font);

}
