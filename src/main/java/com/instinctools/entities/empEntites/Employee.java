package com.instinctools.entities.empEntites;


import com.instinctools.entities.userEntites.User;
import lombok.Data;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import java.util.Set;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String phone;

    private String name;

    private String company;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Set<Job> jobs;
}
