package com.baseball.players_by_position.external.provider;

public interface IHttpServiceParams {
    String getExternalApiAuthChoice();

    void setExternalApiAuthChoice(String externalApiAuthChoice);

    String getExternalApiScheme();

    void setExternalApiScheme(String externalApiScheme);

    String getExternalApiHost();

    void setExternalApiHost(String externalApiHost);

    String getExternalApiResource();

    void setExternalApiResource(String externalApiResource);

    String getExternalApiContentType();

    void setExternalApiContentType(String externalApiContentType);

    String getExternalApiKeyPropertyName();

    void setExternalApiKeyPropertyName(String externalApiKeyPropertyName);

    String getExternalApiUri();

    void setExternalApiUri(String externalApiUri);

    String getExternalHttpMethod();

    void setExternalHttpMethod(String externalHttpMethod);
}
