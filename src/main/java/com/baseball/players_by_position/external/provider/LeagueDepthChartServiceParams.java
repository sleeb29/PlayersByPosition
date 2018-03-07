package com.baseball.players_by_position.external.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LeagueDepthChartServiceParams implements IHttpServiceParams {

    @Value("${depth_chart.auth_choice}")
    private String externalApiAuthChoice;
    @Value("${depth_chart.scheme}")
    private String externalApiScheme;
    @Value("${depth_chart.host}")
    private String externalApiHost;
    @Value("${depth_chart.resource}")
    private String externalApiResource;
    @Value("${depth_chart.content_type}")
    private String externalApiContentType;
    @Value("${depth_chart.api_key_property_name}")
    private String externalApiKeyPropertyName;
    @Value("${depth_chart.uri}")
    private String externalApiUri;
    @Value("${depth_chart.http_method}")
    private String externalHttpMethod;

    public LeagueDepthChartServiceParams() {

    }

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
}
