package lazizbek.uz.hr_management.payload;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

public class TaskDto {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Date deadline;

    @NotNull
    private UUID userId;

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
