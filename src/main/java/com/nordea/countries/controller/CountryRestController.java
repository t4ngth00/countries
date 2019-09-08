package com.nordea.countries.controller;

import com.nordea.countries.dto.response.CountryCapitalDTO;
import com.nordea.countries.dto.response.CountryCodesDTO;
import com.nordea.countries.service.CountryService;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.web.bind.annotation.*;

@RestController
public class CountryRestController {
    private CountryService countryService;

    public CountryRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "/country_codes", method = RequestMethod.GET)
    public Single<CountryCodesDTO> getCountryCodes() {
        return countryService.getCountryCodes()
            .subscribeOn(Schedulers.io())
            .map(countryCodes -> new CountryCodesDTO(countryCodes));
    }

    @RequestMapping(value = "capital/{countryCode}", method = RequestMethod.GET)
    public Single<CountryCapitalDTO> getCountryCapitalByCode(@PathVariable(value = "countryCode") String countryCode) {
        return countryService.getCountryCapitalByCode(countryCode)
            .subscribeOn(Schedulers.io());
    }
}
