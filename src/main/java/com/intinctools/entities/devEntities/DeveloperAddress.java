package com.intinctools.entities.devEntities;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DeveloperAddress {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


        private String country;
        private String city;
        private String zipPostalCode;
        private String telephone;

        @OneToOne
        private Developer developer;

}
