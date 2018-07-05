package com.intinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Date;
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
        private String company;
        private String gender;
        private Date birthday;


        private boolean enable;

        @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @JoinColumn(name = "developer_id")
        private Set<Specialization> specializations;

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
                    + ", company='" + company + '\''
                    + ", enable=" + enable
                    + ", email='" + email + '\''
                    + '}';
        }
}
