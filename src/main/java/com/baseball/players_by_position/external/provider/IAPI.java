package com.baseball.players_by_position.external.provider;

import org.springframework.http.HttpMethod;

public interface IAPI {

    String getProtocol();

    void setProtocol(String protocol);

    String getHostName();

    void setHostName(String hostName);

    String getResource();

    void setResource(String resource);

    String getContentType();

    void setContentType(String contentType);

    String getURI();

    void setURI(String URI);

    HttpMethod getHttpMethod();

    void setHttpMethod(HttpMethod httpMethod);

}
