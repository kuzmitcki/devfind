package com.intinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Developer {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String firstName;
        private String lastName;
        private boolean enable;
        private boolean jobExperience;
        private String country;
        private String city;
        private String summary;
        private String zipPostalCode;
        private String telephone;
        private String additionalInformation;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "developer_id")
        private Set<Specialization> specializations;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "developer_id")
        private Set<Education> education;


        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "developer_id")
        private Set<WorkExperience> workExperiences;



        @Email
        private String  email;

        public Developer(@Email String email) {
        this.email = email;
        }


        @Override
        public String toString() {
            return "Developer{"
                    + "id=" + id
                    + ", firstName='" + firstName + '\''
                    + ", lastName='" + lastName + '\''
                    + ", enable=" + enable
                    + ", email='" + email + '\''
                    + '}';
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return id == developer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
