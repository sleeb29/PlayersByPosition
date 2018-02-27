/*
Code adapted from: https://github.com/aboullaite/SpringBoot-Excel-Csv/
 */


package com.baseball.players_by_position.view;

import com.baseball.players_by_position.excel.formatter.IExcelFormatter;
import com.baseball.players_by_position.excel.formatter.PositionExcelFormatter;
import com.baseball.players_by_position.excel.mapper.IExcelRowMapper;
import com.baseball.players_by_position.excel.mapper.PositionExcelRowMapper;
import com.baseball.players_by_position.model.Player;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

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

    public ExcelView(){
        this.excelFormatter = new PositionExcelFormatter();
        this.excelRowMapper = new PositionExcelRowMapper();
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        initWorkbookSettings(workbook);

        response.setHeader("Content-Disposition", "attachment; filename=\"starters_by_position\"");
        response.setHeader("Content-Type", "application/octet-stream");

        excelFormatter.updateHeaderStyle(workbook.createFont());

        Map<String, Set<Player>> positionToPlayersMap = (Map<String, Set<Player>>) model.get("positionToStartingPlayersMap");

        for(Map.Entry<String, Set<Player>> entry : positionToPlayersMap.entrySet()){

            String position = entry.getKey();
            Set<Player> players = entry.getValue();

            Sheet sheet = workbook.createSheet(position);
            sheet.setDefaultColumnWidth(30);

            writeSheetRows(sheet, players);

        }

    }

    private void initWorkbookSettings(Workbook workbook){

        excelFormatter.setHeaderStyle(workbook.createCellStyle());
        excelFormatter.updateHeaderStyle(workbook.createFont());

    }

    private void writeSheetRows(Sheet sheet, Set<Player> players){

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