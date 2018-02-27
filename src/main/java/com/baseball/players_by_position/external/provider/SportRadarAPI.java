package com.baseball.players_by_position.external.provider;

import com.baseball.players_by_position.external.provider.IAPI;
import org.springframework.http.HttpMethod;

public class SportRadarAPI implements IAPI {

    private final static String API_KEY_ENVIRONMENT_VARIABLE_NAME = "SPORT_RADAR_API_KEY";

    private String protocol;
    private String hostName;
    private String resource;
    private String contentType;
    private String URI;
    private HttpMethod httpMethod;

    public SportRadarAPI(String protocol, String hostName, String resource, String contentType, HttpMethod httpMethod){

        this.protocol = protocol;
        this.hostName = hostName;
        this.resource = resource;
        this.URI = protocol + "://" + hostName + resource + "?api_key=" + System.getenv(API_KEY_ENVIRONMENT_VARIABLE_NAME);

        this.contentType = contentType;
        this.httpMethod = httpMethod;

    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getHostName() {
        return hostName;
    }

    @Override
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String getURI() {
        return URI;
    }

    @Override
    public void setURI(String URI) {
        this.URI = URI;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

}
