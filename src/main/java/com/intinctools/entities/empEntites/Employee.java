package com.intinctools.entities.empEntites;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Employee(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
                return "Employee{" +
                        "id=" + id +
                        ", email='" + email + '\'' +
                        '}';
        }
}
