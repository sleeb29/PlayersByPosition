package com.baseball.players_by_position.external.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpServiceParams {

    @Value("${external_api.auth_choice}")
    private String externalApiAuthChoice;
    @Value("${external_api.scheme}")
    private String externalApiScheme;
    @Value("${external_api.host}")
    private String externalApiHost;
    @Value("${external_api.resource}")
    private String externalApiResource;
    @Value("${external_api.content_type}")
    private String externalApiContentType;
    @Value("${external_api.api_key_property_name}")
    private String externalApiKeyPropertyName;
    @Value("${external_api.uri}")
    private String externalApiUri;
    @Value("${external_api.http_method}")
    private String externalHttpMethod;

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
