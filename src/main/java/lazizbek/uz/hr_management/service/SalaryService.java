package lazizbek.uz.hr_management.service;


import lazizbek.uz.hr_management.entity.Salary;
import lazizbek.uz.hr_management.entity.User;
import lazizbek.uz.hr_management.payload.ApiResponse;
import lazizbek.uz.hr_management.payload.SalaryDto;
import lazizbek.uz.hr_management.repository.SalaryRepository;
import lazizbek.uz.hr_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    UserRepository userRepository;


    /**
     * ADD SALARY
     *
     * @param salaryDto Salary,
     *                  UserId
     * @return ApiResponse in ResponseEntity
     */
    public ApiResponse add(SalaryDto salaryDto) {
        Salary salary = new Salary();
        salary.setSalary(salaryDto.getSalary());

        Optional<User> optionalUser = userRepository.findById(salaryDto.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();
        salary.setUser(user);
        salaryRepository.save(salary);
        return new ApiResponse("Successfully added", true);
    }


    /**
     * GET SALARIES OF USER
     *
     * @param id UUID
     * @return ApiResponse in ResponseEntity
     */
    public List<Salary> getById(UUID id) {
        return salaryRepository.findAllByUserId(id);
    }
}