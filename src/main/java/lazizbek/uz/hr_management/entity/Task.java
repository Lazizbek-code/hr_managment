package lazizbek.uz.hr_management.entity;

import lazizbek.uz.hr_management.entity.enums.TaskStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date deadline;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus = TaskStatus.NEW;

    private String taskCode;

    @CreatedBy
    private UUID createdBy;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp completedAt;

    public Task() {
    }

    public Task(Integer id, String name, String description, Date deadline, User user, TaskStatus taskStatus, String taskCode, UUID createdBy, Timestamp createdAt, Timestamp completedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.user = user;
        this.taskStatus = taskStatus;
        this.taskCode = taskCode;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Timestamp completedAt) {
        this.completedAt = completedAt;
    }
}
