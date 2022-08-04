package lazizbek.uz.hr_management.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne(optional = false)
    private User user;

    public Salary() {
    }

    public Salary(Integer id, Double salary, Timestamp createdAt, User user) {
        this.id = id;
        this.salary = salary;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
