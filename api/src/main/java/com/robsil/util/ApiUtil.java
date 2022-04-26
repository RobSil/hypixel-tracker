package com.robsil.util;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Log4j2
@NoArgsConstructor
public class ApiUtil {

    public HttpResponse<String> sendRequest(URI uri) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest =
                HttpRequest
                        .newBuilder(uri)
                        .GET()
                        .build();

        try {

            return httpClient.send(httpRequest,
                                   HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            log.warn("ApiUtil: exception occured while sending request.");
            e.printStackTrace();
        }

        return null;
    }
}
