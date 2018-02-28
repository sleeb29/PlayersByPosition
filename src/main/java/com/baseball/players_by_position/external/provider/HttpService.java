package com.baseball.players_by_position.external.provider;

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

    public ResponseEntity getHTTPResponse(HttpServiceParams httpServiceParams) {

        RestTemplate template = new RestTemplate();

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(params, headers);
        HttpMethod httpMethod = HttpMethod.valueOf(httpServiceParams.getExternalHttpMethod());
        String externalAPIKeyPropertyValue = System.getenv(httpServiceParams.getExternalApiKeyPropertyName());
        String updatedUri = httpServiceParams.getExternalApiUri();

        if (httpServiceParams.getExternalApiAuthChoice().equals(AuthChoice.API_KEY_AUTHORIZATION.toString())) {
            updatedUri = httpServiceParams.getExternalApiUri().replace(httpServiceParams.getExternalApiKeyPropertyName(), externalAPIKeyPropertyValue);
        }

        ResponseEntity<String> httpResponse = template.exchange(updatedUri, httpMethod, request, String.class, params);
        return httpResponse;

    }

    enum AuthChoice {
        API_KEY_AUTHORIZATION {
            public String toString() {
                return "API_KEY_AUTHORIZATION";
            }
        }

    }

}
