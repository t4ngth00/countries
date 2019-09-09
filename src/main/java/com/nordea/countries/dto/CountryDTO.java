package com.nordea.countries.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {
    private String name;
    private String alpha2Code;
    private String capital;
}

