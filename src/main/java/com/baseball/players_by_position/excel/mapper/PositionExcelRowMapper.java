package com.baseball.players_by_position.excel.mapper;

import com.baseball.players_by_position.model.Player;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

@Service
public class PositionExcelRowMapper implements IExcelRowMapper<Player> {

    static final String FIRST_NAME = "Player Name";
    static final String TEAM = "Team";


    public void mapHeaderRow(Row row){

        row.createCell(0).setCellValue(FIRST_NAME);
        row.createCell(1).setCellValue(TEAM);

    }

    public void mapRow(Row row, Player player){

        row.createCell(0).setCellValue(player.getFirstName() + " " + player.getLastName());
        row.createCell(1).setCellValue(player.getTeam());

    }

}
