package com.intinctools.entities.userEntites;

import com.intinctools.entities.devEntities.Developer;
import com.intinctools.entities.empEntites.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;



@Entity
@Getter
@Setter
public class User implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String password;
        private String username;
        private boolean enable;



        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "developer_id")
        private Developer developer;


        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "employee_id")
        private Employee employee;


        @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
        @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
        @Enumerated(EnumType.STRING)
        private Set<Role> roles;



        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getRoles();
        }


        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return isEnable();
    }


}
