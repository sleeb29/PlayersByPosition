package com.baseball.players_by_position.external.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {

    @Value("${external_api.auth}")
    private String externalAPIAuth;
    @Value("${external_api.scheme}")
    private String externalApiScheme;
    @Value("${external_api.host}")
    private String externalApiHost;
    @Value("${external_api.resource}")
    private String externalApiResource;
    @Value("${external_api.content_type}")
    private String externalApiContentType;
    @Value("${external_api.content_type}")
    private String externalAPI;
    @Value("${external_api.api_key_property_name}")
    private String externalAPIKeyPropertyName;
    @Value("${external_api.uri}")
    private String externalAPIURI;
    @Value("${external_api.http_method}")
    private String externalHttpMethod;

    @Bean
    public ResponseEntity getHTTPResponse() {

        RestTemplate template = new RestTemplate();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(params, headers);
        HttpMethod httpMethod = HttpMethod.valueOf(externalHttpMethod);
        String externalAPIKeyPropertyValue = System.getenv(externalAPIKeyPropertyName);
        String updatedURI = externalAPIURI;

        if (externalAPIAuth == "API_KEY") {
            updatedURI = externalAPIURI.replace(externalAPIKeyPropertyName, externalAPIKeyPropertyValue);
        }

        ResponseEntity<String> httpResponse = template.exchange(updatedURI, httpMethod, request, String.class, params);

        return httpResponse;

    }

}
