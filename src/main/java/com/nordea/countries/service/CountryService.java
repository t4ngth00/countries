package com.nordea.countries.service;

import com.nordea.countries.dto.response.CountryCapitalDTO;
import io.reactivex.Single;

import java.util.List;

public interface CountryService {
    Single<List<String>> getCountryCodes();
    Single<CountryCapitalDTO> getCountryCapitalByCode(String countryCode);
}
