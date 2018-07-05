package com.intinctools.entities.empEntites;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String email;


        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "employee_id")
        private Set<Address> addresses;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "employee_id")
        private Set<Job> jobs;


        public Employee(String email) {
            this.email = email;
        }
}
