package lazizbek.uz.hr_management.repository;


import lazizbek.uz.hr_management.entity.Task;
import lazizbek.uz.hr_management.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTaskCode(String taskCode);

    Optional<Task> findByTaskStatusAndUserId(TaskStatus taskStatus, UUID user_id);

    List<Task> findAllByUserId(UUID user_id);

    Optional<Task> findByCreatedAtBetweenAndUserId(Timestamp createdAt, Timestamp createdAt2, UUID user_id);
}