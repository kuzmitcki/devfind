package com.intinctools.entities.empEntites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


        private String country;
        private String state;
        private String street;
        private String zipPostalCode;
        private String telephone;



        public Address(String country, String state, String street, String zipPostalCode, String telephone) {
                this.country = country;
                this.state = state;
                this.street = street;
                this.zipPostalCode = zipPostalCode;
                this.telephone = telephone;
        }
}
