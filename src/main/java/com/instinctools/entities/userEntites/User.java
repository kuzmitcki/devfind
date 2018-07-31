package com.instinctools.entities.userEntites;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.empEntites.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
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

        private String activationCode;

        @Email
        private String  email;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "developer_id")
        private Developer developer;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "employee_id")
        private Employee employee;

        @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
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

        public boolean isDeveloperRole(){
                return roles.contains(Role.DEVELOPER);
        }

}
