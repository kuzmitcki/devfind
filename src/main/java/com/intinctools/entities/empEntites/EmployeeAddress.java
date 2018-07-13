package com.intinctools.entities.empEntites;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EmployeeAddress {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;


        private String country;
        private String state;
        private String street;
        private String zipPostalCode;
        private String telephone;

        @ManyToOne
        private Employee employee;


        public EmployeeAddress(String country, String state, String street, String zipPostalCode, String telephone) {
                this.country = country;
                this.state = state;
                this.street = street;
                this.zipPostalCode = zipPostalCode;
                this.telephone = telephone;
        }
}
