package com.nordea.countries.service;

import com.nordea.countries.dto.CountryDTO;
import com.nordea.countries.dto.response.CountryCapitalDTO;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Value("${services.country.uri}")
    private String COUNTRY_SERVICE_URI;

    private final String ALL_COUNTRY_PATH = "/all";

    private final String SINGLE_COUNTRY_PATH = "/alpha/";

    private static final Logger log = LoggerFactory.getLogger(CountryServiceImpl.class);

    private final RestTemplate restTemplate;

    public CountryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Single<List<String>> getCountryCodes() {
        return this.getAllCountries()
            .flatMap(countries ->
                Observable
                    .fromIterable(countries)
                    .map(country -> country.getAlpha2Code())
                    .toList()
        );
    }

    @Override
    public Single<CountryCapitalDTO> getCountryCapitalByCode(String countryCode) {
        return Single.create(singleSubscriber -> {
            try {
                String targetURL = COUNTRY_SERVICE_URI + SINGLE_COUNTRY_PATH + countryCode;

                CountryCapitalDTO countryData = restTemplate.getForObject(
                    targetURL,
                    CountryCapitalDTO.class
                );

                singleSubscriber.onSuccess(countryData);
            } catch (HttpStatusCodeException e) {
                // Log the response body
                log.error(e.getResponseBodyAsString());

                singleSubscriber.onError(new RestClientResponseException(
                    e.getMessage(),
                    e.getStatusCode().value(),
                    null,
                    null,
                    null,
                    null
                ));
            }
        });
    }

    private Single<List<CountryDTO>> getAllCountries() {
        return Single.create(singleSubscriber -> {
            try {
                String targetURL = COUNTRY_SERVICE_URI + ALL_COUNTRY_PATH;

                ResponseEntity<List<CountryDTO>> response = restTemplate.exchange(
                    targetURL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CountryDTO>>(){}
                );

                singleSubscriber.onSuccess(response.getBody());
            } catch (HttpStatusCodeException e) {
                // Log the response body
                log.error(e.getResponseBodyAsString());

                singleSubscriber.onError(new RestClientResponseException(
                    e.getMessage(),
                    e.getStatusCode().value(),
                    null,
                    null,
                    null,
                    null
                ));
            }
        });
    }
}
