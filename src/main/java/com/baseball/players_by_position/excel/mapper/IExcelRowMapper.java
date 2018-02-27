package com.baseball.players_by_position.excel.mapper;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

public interface IExcelRowMapper<T> {

    public void mapHeaderRow(Row row);
    public void mapRow(Row row, T t);

}
