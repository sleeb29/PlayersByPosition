package com.baseball.players_by_position.external.provider;

import org.springframework.stereotype.Service;

@Service
public class HttpServiceParams {

    private String externalApiAuthChoice;
    private String externalApiScheme;
    private String externalApiHost;
    private String externalApiResource;
    private String externalApiContentType;
    private String externalApiKeyPropertyName;
    private String externalApiUri;
    private String externalHttpMethod;

    public void setExternalApiAuthChoice(String externalApiAuthChoice) {
        this.externalApiAuthChoice = externalApiAuthChoice;
    }

    public void setExternalApiScheme(String externalApiScheme) {
        this.externalApiScheme = externalApiScheme;
    }

    public void setExternalApiHost(String externalApiHost) {
        this.externalApiHost = externalApiHost;
    }

    public void setExternalApiResource(String externalApiResource) {
        this.externalApiResource = externalApiResource;
    }

    public void setExternalApiContentType(String externalApiContentType) {
        this.externalApiContentType = externalApiContentType;
    }

    public void setExternalApiKeyPropertyName(String externalApiKeyPropertyName) {
        this.externalApiKeyPropertyName = externalApiKeyPropertyName;
    }

    public void setExternalApiUri(String externalApiUri) {
        this.externalApiUri = externalApiUri;
    }

    public void setExternalHttpMethod(String externalHttpMethod) {
        this.externalHttpMethod = externalHttpMethod;
    }

    public String getExternalApiAuthChoice() {
        return externalApiAuthChoice;
    }

    public String getExternalApiScheme() {
        return externalApiScheme;
    }

    public String getExternalApiHost() {
        return externalApiHost;
    }

    public String getExternalApiResource() {
        return externalApiResource;
    }

    public String getExternalApiContentType() {
        return externalApiContentType;
    }

    public String getExternalApiKeyPropertyName() {
        return externalApiKeyPropertyName;
    }

    public String getExternalApiUri() {
        return externalApiUri;
    }

    public String getExternalHttpMethod() {
        return externalHttpMethod;
    }
}
