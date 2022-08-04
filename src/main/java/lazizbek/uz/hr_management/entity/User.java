package lazizbek.uz.hr_management.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String emailCode;

    private Double salary;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToMany
    private Set<Role> roles;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;

    //----------------METHODS OF USER DETAILS----------------//

    // return user roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    // return user unique email
    @Override
    public String getUsername() {
        return this.email;
    }

    // return boolean - user's account is expired or not
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    // return boolean - user's account is locked or not
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    // return boolean - user's credentials is expired or not
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    // return boolean - user's account is enable or not
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public User() {
    }

    public User(UUID id, String firstName, String lastName, String email, String emailCode, Double salary, String password, Timestamp createdAt, Timestamp updatedAt, Set<Role> roles, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.emailCode = emailCode;
        this.salary = salary;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
