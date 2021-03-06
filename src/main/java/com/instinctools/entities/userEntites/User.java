package com.instinctools.entities.userEntites;

import com.instinctools.entities.devEntities.Developer;
import com.instinctools.entities.empEntites.Employee;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;

@Entity
@Data
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
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
                return roles;
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

    public boolean isDeveloperRole() {
                return roles.contains(Role.DEVELOPER);
        }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                "username=" + username +
                '}';
    }
}
