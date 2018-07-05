package com.intinctools.entities.devEntities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Specialization {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String skills;


        public Specialization(String skills) {
            this.skills = skills;
        }

        @Override
        public String toString() {
            return skills;
        }
}
