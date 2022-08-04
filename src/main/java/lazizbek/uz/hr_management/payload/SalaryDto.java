package lazizbek.uz.hr_management.payload;

import java.util.UUID;

public class SalaryDto {
    private Double salary;
    private UUID userId;

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
