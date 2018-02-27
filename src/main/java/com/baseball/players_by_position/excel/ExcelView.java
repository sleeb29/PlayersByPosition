package com.baseball.players_by_position.excel;

import com.baseball.players_by_position.excel.formatter.IExcelFormatter;
import com.baseball.players_by_position.excel.mapper.IExcelRowMapper;
import com.baseball.players_by_position.model.Player;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class ExcelView extends AbstractXlsView {

    private IExcelFormatter excelFormatter;
    private IExcelRowMapper excelRowMapper;

    @Autowired void setExcelFormatter(IExcelFormatter excelFormatter){
        this.excelFormatter = excelFormatter;
    }

    @Autowired void setExcelRowMapper(IExcelRowMapper<Player> excelRowMapper){
        this.excelRowMapper = excelRowMapper;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        initWorkbookSettings(workbook);

        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"starters_by_position.xlsx\"");

        this.excelFormatter.updateHeaderStyle(workbook.createFont());

        @SuppressWarnings("unchecked")
        Map<String, List<Player>> positionToPlayersMap = (Map<String, List<Player>>) model.get("positionsToStartingPlayersMap");

        for(Map.Entry<String, List<Player>> entry : positionToPlayersMap.entrySet()){

            String position = entry.getKey();
            List<Player> players = entry.getValue();

            Sheet sheet = workbook.createSheet(position);
            sheet.setDefaultColumnWidth(30);

            writeSheetRows(sheet, players);

        }

    }

    private void initWorkbookSettings(Workbook workbook){

        this.excelFormatter.updateHeaderStyle(workbook.createFont());

    }

    private void writeSheetRows(Sheet sheet, List<Player> players){

        // create header row
        Row header = sheet.createRow(0);
        excelRowMapper.mapHeaderRow(header);
        header.setRowStyle(excelFormatter.getHeaderStyle());

        int rowCount = 1;

        for(Player player : players){
            Row row = sheet.createRow(rowCount);
            excelRowMapper.mapRow(row, player);
            rowCount++;
        }

    }

}