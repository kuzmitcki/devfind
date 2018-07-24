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
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String skill;

        private String experience;

        @ManyToOne
        private Developer developer;

        public Specialization(String skill, String experience) {
                this.skill = skill;
                this.experience = experience;
        }

        @Override
        public String toString() {
            return skill;
        }
}
