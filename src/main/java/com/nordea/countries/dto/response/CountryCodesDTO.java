package com.nordea.countries.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryCodesDTO {
    @JsonProperty("country_codes")
    private List<String> countryCodes;
}
