/*
Code from: https://github.com/aboullaite/SpringBoot-Excel-Csv/
 */

package com.baseball.players_by_position.resolver;

import com.baseball.players_by_position.view.ExcelView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

public class ExcelViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        ExcelView view = new ExcelView();
        return view;
    }
}