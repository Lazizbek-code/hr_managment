package lazizbek.uz.hr_management.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String firstName; // user firstName

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName; // user lastName

    @NotNull
    @Email
    private String email; // user email

    @NotNull
    @Size(min = 8)
    private String password; // user password

    @NotNull
    private Double salary; // user salary

    private Integer roleId; // user role

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
