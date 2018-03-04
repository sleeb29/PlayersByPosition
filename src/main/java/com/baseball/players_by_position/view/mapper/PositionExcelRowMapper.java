package com.baseball.players_by_position.view.mapper;

import com.baseball.players_by_position.model.Player;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class PositionExcelRowMapper implements IExcelRowMapper<Player> {

    static final String FIRST_NAME = "Player Name";
    static final String TEAM = "Team";
    static final String STATUS = "Status";
    static final String RANK = "Rank";

    public void mapHeaderRow(Row row){

        row.createCell(0).setCellValue(FIRST_NAME);
        row.createCell(1).setCellValue(TEAM);
        row.createCell(2).setCellValue(STATUS);
        row.createCell(3).setCellValue(RANK);
    }

    public void mapRow(Row row, Player player){

        row.createCell(0).setCellValue(player.getFirstName() + " " + player.getLastName());
        row.createCell(1).setCellValue(player.getTeam());
        row.createCell(2).setCellValue(player.getStatus());
        row.createCell(3).setCellValue(player.getRank());

    }

}
