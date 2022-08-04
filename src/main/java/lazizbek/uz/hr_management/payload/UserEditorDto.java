package lazizbek.uz.hr_management.payload;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEditorDto {

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName; // user firstName

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName; // user lastName

    @NotNull
    @Size(min = 8)
    private String password; // user password

    private Double salary;

    private Integer roleId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
