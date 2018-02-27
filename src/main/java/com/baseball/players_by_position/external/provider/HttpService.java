package com.baseball.players_by_position.external.provider;

import com.baseball.players_by_position.external.provider.IAPI;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpService {

    private IAPI api;

    public IAPI getApi() {
        return api;
    }

    public void setApi(IAPI api) {
        this.api = api;
    }

    public ResponseEntity getHTTPResponse(IAPI api) {

        RestTemplate template = new RestTemplate();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<Object>(params, headers);
        String uri = api.getURI();
        ResponseEntity<String> httpResponse = template.exchange(uri, api.getHttpMethod(), request, String.class, params);

        return httpResponse;

    }

}
