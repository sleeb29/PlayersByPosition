package com.baseball.players_by_position.configuration.marshaller;

import com.baseball.players_by_position.model.PlayerRank;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class Jax2BConfig {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(PlayerRank.class);
        return marshaller;
    }

}
