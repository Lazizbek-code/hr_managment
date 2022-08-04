package lazizbek.uz.hr_management.service;


import lazizbek.uz.hr_management.entity.User;
import lazizbek.uz.hr_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> get() {
        return userRepository.findAll();
    }
}
