/*
Code adapted from: https://github.com/aboullaite/SpringBoot-Excel-Csv/
 */


package com.baseball.players_by_position.view;

import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.view.mapper.IExcelRowMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractXlsView {

    private IExcelRowMapper excelRowMapper;

    public ExcelView(IExcelRowMapper excelRowMapper) {
        this.excelRowMapper = excelRowMapper;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        response.setHeader("Content-Disposition", "attachment; filename=\"starters_by_position\"");
        response.setHeader("Content-Type", "application/octet-stream");

        Map<String, ArrayList<Player>> positionToPlayersMap = (Map<String, ArrayList<Player>>) model.get("positionToStartingPlayersMap");

        for (Map.Entry<String, ArrayList<Player>> entry : positionToPlayersMap.entrySet()) {

            String position = entry.getKey();
            List<Player> players = entry.getValue();

            Sheet sheet = workbook.createSheet(position);
            sheet.setDefaultColumnWidth(30);

            writeSheetRows(sheet, players);

        }

    }

    private void writeSheetRows(Sheet sheet, List<Player> players) {

        Row header = sheet.createRow(0);
        excelRowMapper.mapHeaderRow(header);

        int rowCount = 1;

        for(Player player : players){
            Row row = sheet.createRow(rowCount);
            excelRowMapper.mapRow(row, player);
            rowCount++;
        }

    }

}