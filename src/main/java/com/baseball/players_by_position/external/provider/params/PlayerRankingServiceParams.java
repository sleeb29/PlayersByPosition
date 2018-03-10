package com.baseball.players_by_position.external.provider.params;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlayerRankingServiceParams implements IHttpServiceParams {

    @Value("${player_ranking.auth_choice}")
    private String externalApiAuthChoice;
    @Value("${player_ranking.scheme}")
    private String externalApiScheme;
    @Value("${player_ranking.host}")
    private String externalApiHost;

    private String externalApiResource;
    @Value("${player_ranking.content_type}")
    private String externalApiContentType;
    private String externalApiKeyPropertyName;
    @Value("${player_ranking.uri}")
    private String externalApiUri;
    @Value("${player_ranking.http_method}")
    private String externalHttpMethod;
    @Value("${player_ranking.player_resource}")
    private String playerResource;
    @Value("${player_ranking.ranking_resource}")
    private String rankingResource;

    @Override
    public String getExternalApiAuthChoice() {
        return externalApiAuthChoice;
    }

    @Override
    public void setExternalApiAuthChoice(String externalApiAuthChoice) {
        this.externalApiAuthChoice = externalApiAuthChoice;
    }

    @Override
    public String getExternalApiScheme() {
        return externalApiScheme;
    }

    @Override
    public void setExternalApiScheme(String externalApiScheme) {
        this.externalApiScheme = externalApiScheme;
    }

    @Override
    public String getExternalApiHost() {
        return externalApiHost;
    }

    @Override
    public void setExternalApiHost(String externalApiHost) {
        this.externalApiHost = externalApiHost;
    }

    @Override
    public String getExternalApiResource() {
        return externalApiResource;
    }

    @Override
    public void setExternalApiResource(String externalApiResource) {
        this.externalApiResource = externalApiResource;
    }

    @Override
    public String getExternalApiContentType() {
        return externalApiContentType;
    }

    @Override
    public void setExternalApiContentType(String externalApiContentType) {
        this.externalApiContentType = externalApiContentType;
    }

    @Override
    public String getExternalApiKeyPropertyName() {
        return externalApiKeyPropertyName;
    }

    @Override
    public void setExternalApiKeyPropertyName(String externalApiKeyPropertyName) {
        this.externalApiKeyPropertyName = externalApiKeyPropertyName;
    }

    @Override
    public String getExternalApiUri() {
        return externalApiUri;
    }

    @Override
    public void setExternalApiUri(String externalApiUri) {
        this.externalApiUri = externalApiUri;
    }

    @Override
    public String getExternalHttpMethod() {
        return externalHttpMethod;
    }

    @Override
    public void setExternalHttpMethod(String externalHttpMethod) {
        this.externalHttpMethod = externalHttpMethod;
    }

    public String getPlayerResource() {
        return playerResource;
    }

    public void setPlayerResource(String playerResource) {
        this.playerResource = playerResource;
    }

    public String getRankingResource() {
        return rankingResource;
    }

    public void setRankingResource(String rankingResource) {
        this.rankingResource = rankingResource;
    }
}
