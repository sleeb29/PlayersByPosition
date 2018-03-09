package com.baseball.players_by_position.controller;

import com.baseball.players_by_position.external.provider.service.GetPlayerService;
import com.baseball.players_by_position.model.Player;
import com.baseball.players_by_position.service.service.PlayerService;
import com.baseball.players_by_position.view.ExcelView;
import com.baseball.players_by_position.view.mapper.IExcelRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

@RestController
public class GetPlayersByPositionWorkbook {

    @Autowired
    PlayerService playerService;

    @Autowired
    IExcelRowMapper excelRowMapper;

    @Autowired
    GetPlayerService getPlayerService;
    
    @RequestMapping(value = "/getStartersByPositionWorkbook", method = RequestMethod.GET)
    @Cacheable("positionToStartingPlayersWorkbook")
    public ModelAndView getStartersByPositionWorkbook(Model model) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, JAXBException, XMLStreamException {

        Map<String, List<Player>> positionToStartingPlayersMap = getPlayerService.getPositionToStartingPlayersMap();
        return new ModelAndView(new ExcelView(excelRowMapper), "positionToStartingPlayersMap", positionToStartingPlayersMap);

    }

}