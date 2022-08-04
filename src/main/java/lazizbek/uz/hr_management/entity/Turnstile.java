package lazizbek.uz.hr_management.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Turnstile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp loginTime;

    @UpdateTimestamp
    private Timestamp logoutTime;

    public Turnstile() {
    }

    public Turnstile(Integer id, Boolean active, User user, Timestamp loginTime, Timestamp logoutTime) {
        this.id = id;
        this.active = active;
        this.user = user;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }
}
