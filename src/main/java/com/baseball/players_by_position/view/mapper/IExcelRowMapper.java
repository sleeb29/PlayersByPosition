package com.baseball.players_by_position.view.mapper;

import org.apache.poi.ss.usermodel.Row;

public interface IExcelRowMapper<T> {

    void mapHeaderRow(Row row);

    void mapRow(Row row, T t);

}
