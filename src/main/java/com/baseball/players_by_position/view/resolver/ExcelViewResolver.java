/*
Code from: https://github.com/aboullaite/SpringBoot-Excel-Csv/
 */

package com.baseball.players_by_position.view.resolver;

import com.baseball.players_by_position.view.ExcelView;
import com.baseball.players_by_position.view.mapper.IExcelRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

@Component
public class ExcelViewResolver implements ViewResolver {

    @Autowired
    IExcelRowMapper excelRowMapper;

    @Override
    public View resolveViewName(String s, Locale locale) {
        ExcelView view = new ExcelView(excelRowMapper);
        return view;
    }
}