package com.baseball.players_by_position.external.provider.service;

import com.baseball.players_by_position.external.provider.params.PlayerRankingServiceParams;
import com.baseball.players_by_position.model.PlayerRankList;
import com.baseball.players_by_position.model.RankPlayerStageList;
import com.baseball.players_by_position.service.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Service
public class PlayerRankingService extends HttpService {

    @Autowired
    PlayerRankingServiceParams playerRankingServiceParams;

    @Autowired
    PlayerService playerService;

    public void put() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, XMLStreamException, JAXBException {

        String updatedUriForPlayerRankCall = playerRankingServiceParams.getExternalApiUri() + playerRankingServiceParams.getRankingResource();
        ResponseEntity playerRankingServiceRankingResponse = this.getHTTPResponse(playerRankingServiceParams, updatedUriForPlayerRankCall);
        String playerRankingServiceRankingResponseBody = playerRankingServiceRankingResponse.getBody().toString();

        XMLStreamReader xmlStreamReader = getXMLStreamReader(playerRankingServiceRankingResponseBody);
        PlayerRankList playerRankList = transformPayloadToPlayerRankingList(xmlStreamReader);
        playerService.populatePlayerRankStagingTable(playerRankList.getPlayerRankings());

        String updatedUriForRankCall = playerRankingServiceParams.getExternalApiUri() + playerRankingServiceParams.getPlayerResource();
        ResponseEntity rankingServicePlayerResponse = this.getHTTPResponse(playerRankingServiceParams, updatedUriForRankCall);
        String rankingServicePlayerResponseBody = rankingServicePlayerResponse.getBody().toString();

        XMLStreamReader rankPlayerXmlStreamReader = getXMLStreamReader(rankingServicePlayerResponseBody);
        RankPlayerStageList rankPlayerStagingList = transformPayloadToRankingPlayerList(rankPlayerXmlStreamReader);
        playerService.populateRankPlayerStagingTable(rankPlayerStagingList.getPlayerRankings());

        playerService.aggregatePlayerRankTables();

    }

    private XMLStreamReader getXMLStreamReader(String xml) throws XMLStreamException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xml));

        return xmlStreamReader;

    }

    private PlayerRankList transformPayloadToPlayerRankingList(XMLStreamReader xmlStreamReader) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(PlayerRankList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlayerRankList playerRankList = (PlayerRankList) jaxbUnmarshaller.unmarshal(xmlStreamReader);

        return playerRankList;

    }

    private RankPlayerStageList transformPayloadToRankingPlayerList(XMLStreamReader xmlStreamReader) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(RankPlayerStageList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        RankPlayerStageList rankPlayerStageList = (RankPlayerStageList) jaxbUnmarshaller.unmarshal(xmlStreamReader);

        return rankPlayerStageList;

    }

}
