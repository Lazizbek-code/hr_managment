package lazizbek.uz.hr_management.repository;

import lazizbek.uz.hr_management.entity.Turnstile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TurnstileRepository extends JpaRepository<Turnstile, Integer> {
    Optional<Turnstile> findByUserId(UUID user_id);
}
