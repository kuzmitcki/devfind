package com.intinctools.entities.devEntities;

import com.intinctools.entities.userEntites.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "desiredJob_id")
        private DesiredJob desiredJob;

        @OneToOne
        private User user;

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
