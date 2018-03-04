package com.baseball.players_by_position.external.provider.transformers;

import com.baseball.players_by_position.model.PlayerRankList;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.util.concurrent.Future;

@Service
public class PlayerRankingServiceTransformer {

    @Async
    @Cacheable("transformPayloadToPlayerRankingList")
    public Future<PlayerRankList> transformPayloadToPlayerRankingList(String playerRankingBody) throws XMLStreamException, JAXBException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(playerRankingBody));

        JAXBContext jaxbContext = JAXBContext.newInstance(PlayerRankList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        PlayerRankList playerRankList = (PlayerRankList) jaxbUnmarshaller.unmarshal(xmlStreamReader);

        return new AsyncResult<>(playerRankList);
    }

}
