package lazizbek.uz.hr_management.service;


import lazizbek.uz.hr_management.entity.Role;
import lazizbek.uz.hr_management.entity.Task;
import lazizbek.uz.hr_management.entity.User;
import lazizbek.uz.hr_management.entity.enums.RoleName;
import lazizbek.uz.hr_management.entity.enums.TaskStatus;
import lazizbek.uz.hr_management.payload.ApiResponse;
import lazizbek.uz.hr_management.payload.TaskDto;
import lazizbek.uz.hr_management.repository.RoleRepository;
import lazizbek.uz.hr_management.repository.TaskRepository;
import lazizbek.uz.hr_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender javaMailSender;


    /**
     * ADD TASK
     *
     * @param taskDto Name,
     *                Description,
     *                Deadline,
     *                UserId
     * @return ApiResponse in ResponseEntity
     */
    public ApiResponse add(TaskDto taskDto) {
        Task task = new Task();
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDeadline(taskDto.getDeadline());
        task.setTaskCode(UUID.randomUUID().toString());

        Optional<User> optionalUser = userRepository.findById(taskDto.getUserId());
        if (!optionalUser.isPresent()) {
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();


        User userInSystem = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isDirector = false;
        for (Role role : userInSystem.getRoles()) {
            if (role.getRoleName().equals(RoleName.EMPLOYEE)) {
                return new ApiResponse("EMPLOYEE can not add task", false);
            }

            if (role.getRoleName().equals(RoleName.DIRECTOR)) {
                isDirector = true;
            }

            if (role.getRoleName().equals(RoleName.HR_MANAGER)) {
                isDirector = false;
            }
        }

        if (!isDirector) {
            for (Role role : user.getRoles()) {
                if (role.getRoleName().equals(RoleName.HR_MANAGER) ||
                        role.getRoleName().equals(RoleName.DIRECTOR)) {
                    return new ApiResponse("HR_MANAGER can not add " + role.getRoleName(), false);
                }
            }

        }
        task.setUser(user);
        Task savedTask = taskRepository.save(task);

        sendEmail(savedTask.getName(), savedTask.getTaskCode());
        return new ApiResponse("Successfully added", true);
    }


    /**
     * CONFIRM TASK
     *
     * @param taskCode String
     * @return ApiResponse in ResponseEntity
     */
    public ApiResponse confirmTask(String taskCode) {
        Optional<Task> optionalTask = taskRepository.findByTaskCode(taskCode);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTaskStatus(TaskStatus.DOING);
            task.setTaskCode(null);
            taskRepository.save(task);
            return new ApiResponse("Task successfully confirmed", true);
        }
        return new ApiResponse("Task already confirmed", false);
    }


    /**
     * COMPLETE TASK
     *
     * @return ApiResponse in ResponseEntity
     */
    public ApiResponse complete() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Task> optionalTask = taskRepository.findByTaskStatusAndUserId(TaskStatus.DOING, user.getId());
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setTaskStatus(TaskStatus.DONE);
            taskRepository.save(task);

            sendEmail(user.getEmail(), task.getTaskCode());
            return new ApiResponse("Task completed", true);
        }
        return new ApiResponse("Error", false);
    }


    /**
     * GET ALL USER TASKS BY SECURITY CONTEXT HOLDER
     *
     * @return ApiResponse in ResponseEntity
     */
    public List<Task> get() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskRepository.findAllByUserId(user.getId());
    }


    // send message to email
    public void sendEmail(String sendingEmail, String taskCode) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("email.sender.hr@gmail.com");
            simpleMailMessage.setTo(sendingEmail);
            simpleMailMessage.setSubject("Confirm task");
            simpleMailMessage.setText("http://localhost:8050/api/task/confirm?taskCode=" + taskCode + "&email=" + sendingEmail);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}