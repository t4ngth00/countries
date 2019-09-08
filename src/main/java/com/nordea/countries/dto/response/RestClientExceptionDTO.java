package com.nordea.countries.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestClientExceptionDTO {
    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("status_text")
    private String statusText;
}

