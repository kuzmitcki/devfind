package com.instinctools.entities.devEntities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter
@AllArgsConstructor
public class DeveloperDTO {
    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String zipPostalCode;

    private String telephone;

    public DeveloperDTO(String country, String city, String zipPostalCode, String telephone) {
        this.country = country;
        this.city = city;
        this.zipPostalCode = zipPostalCode;
        this.telephone = telephone;
    }
}
