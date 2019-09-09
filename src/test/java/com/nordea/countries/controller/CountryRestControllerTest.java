package com.nordea.countries.controller;

import com.nordea.countries.dto.response.CountryCapitalDTO;
import com.nordea.countries.service.CountryService;
import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestClientResponseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CountryRestController.class)
public class CountryRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @Test
    public void GetCountryCodes_Success_Return200_With_CountryCodeDTO() throws Exception {
        List<String> countryCodes = new ArrayList<>(Arrays.asList("FI", "VN"));

        when(countryService.getCountryCodes())
            .thenReturn(Single.just(countryCodes));

        MvcResult mvcResult = mockMvc.perform(get("/country_codes")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.country_codes", equalTo(countryCodes)));

        verify(countryService, times(1)).getCountryCodes();
    }

    @Test
    public void GetCountryCapitalByCode_Success_Return200_With_CountryCapitalDTO() throws Exception {
        when(countryService.getCountryCapitalByCode(anyString()))
            .thenReturn(Single.just(new CountryCapitalDTO("Finland", "Helsinki")));

        MvcResult mvcResult = mockMvc.perform(get("/capital/fi")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Finland")))
            .andExpect(jsonPath("$.capital", equalTo("Helsinki")));

        verify(countryService, times(1)).getCountryCapitalByCode(anyString());
    }

    @Test
    public void GetCountryCapitalByCode_Failed_Return400_With_RestClientExceptionDTO() throws Exception {
        int expectedStatusCode = 400;

        when(countryService.getCountryCapitalByCode(anyString()))
            .thenReturn(Single.error(new RestClientResponseException(
                "400 Exception",
                expectedStatusCode,
                null,
                null,
                null,
                null
            )));

        MvcResult mvcResult = mockMvc.perform(get("/capital/notACountryCode")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status_code", equalTo(expectedStatusCode)))
            .andExpect(jsonPath("$.status_text", equalTo(HttpStatus.valueOf(expectedStatusCode).getReasonPhrase())));

        verify(countryService, times(1)).getCountryCapitalByCode(anyString());
    }

    @Test
    public void GetCountryCapitalByCode_Failed_Return404_With_RestClientExceptionDTO() throws Exception {
        int expectedStatusCode = 404;

        when(countryService.getCountryCapitalByCode(anyString()))
            .thenReturn(Single.error(new RestClientResponseException(
                "404 Exception",
                expectedStatusCode,
                null,
                null,
                null,
                null
            )));

        MvcResult mvcResult = mockMvc.perform(get("/capital/fi")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status_code", equalTo(expectedStatusCode)))
            .andExpect(jsonPath("$.status_text", equalTo(HttpStatus.valueOf(expectedStatusCode).getReasonPhrase())));

        verify(countryService, times(1)).getCountryCapitalByCode(anyString());
    }
}
