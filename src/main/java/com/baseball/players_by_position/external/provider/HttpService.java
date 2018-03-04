package com.baseball.players_by_position.external.provider;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@Service
public class HttpService {

    private static final Logger logger = Logger.getLogger(HttpService.class.getName());

    @Value("${client.key_store_location}")
    private String clientKeyStoreLocation;
    @Value("${client.key_store_password_env_variable_name}")
    private String clientKeyStorePasswordEnvVariableName;

    @Async
    public Future<ResponseEntity> getHTTPResponse(IHttpServiceParams httpServiceParams) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        logger.info("in getHTTPResponse for service: " + httpServiceParams.getExternalApiHost());

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(createHttpClient());
        RestTemplate restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Object> request = new HttpEntity<>(params, headers);
        HttpMethod httpMethod = HttpMethod.valueOf(httpServiceParams.getExternalHttpMethod());
        String updatedUri = httpServiceParams.getExternalApiUri();

        if (httpServiceParams.getExternalApiAuthChoice().equals(AuthChoice.API_KEY_AUTHORIZATION.toString())) {
            String externalAPIKeyPropertyValue = System.getenv(httpServiceParams.getExternalApiKeyPropertyName());
            updatedUri = httpServiceParams.getExternalApiUri().replace(httpServiceParams.getExternalApiKeyPropertyName(), externalAPIKeyPropertyValue);
        }

        ResponseEntity<String> httpResponse = restTemplate.exchange(updatedUri, httpMethod, request, String.class, params);
        return new AsyncResult<>(httpResponse);

    }

    private HttpClient createHttpClient() throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, CertificateException {

        String keyPassphrase = System.getenv(clientKeyStorePasswordEnvVariableName);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(clientKeyStoreLocation), keyPassphrase.toCharArray());

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, keyPassphrase.toCharArray())
                .build();

        return HttpClients.custom()
                .setSSLContext(sslContext)
                .build();
    }

    enum AuthChoice {
        API_KEY_AUTHORIZATION {
            public String toString() {
                return "API_KEY_AUTHORIZATION";
            }
        }

    }

}
