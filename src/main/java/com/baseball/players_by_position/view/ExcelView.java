/*
Code adapted from: https://github.com/aboullaite/SpringBoot-Excel-Csv/
 */


package com.baseball.players_by_position.view;

import com.baseball.players_by_position.excel.mapper.IExcelRowMapper;
import com.baseball.players_by_position.model.Player;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Service
public class ExcelView extends AbstractXlsView {

    private IExcelRowMapper excelRowMapper;

    @Inject
    void setExcelRowMapper(IExcelRowMapper<Player> excelRowMapper) {
        this.excelRowMapper = excelRowMapper;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        response.setHeader("Content-Disposition", "attachment; filename=\"starters_by_position\"");
        response.setHeader("Content-Type", "application/octet-stream");

        Map<String, Set<Player>> positionToPlayersMap = (Map<String, Set<Player>>) model.get("positionToStartingPlayersMap");

        for(Map.Entry<String, Set<Player>> entry : positionToPlayersMap.entrySet()){

            String position = entry.getKey();
            Set<Player> players = entry.getValue();

            Sheet sheet = workbook.createSheet(position);
            sheet.setDefaultColumnWidth(30);

            writeSheetRows(sheet, players);

        }

    }

    private void writeSheetRows(Sheet sheet, Set<Player> players){

        // create header row
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