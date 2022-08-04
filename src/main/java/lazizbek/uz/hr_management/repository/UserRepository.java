package lazizbek.uz.hr_management.repository;

import lazizbek.uz.hr_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email); // check out, this email exist or not

    Optional<User> findByEmail(String email); // find user by email

    Optional<User> findByEmailAndEmailCode(String email, String emailCode);
}
