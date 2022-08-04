package lazizbek.uz.hr_management.controller;


import lazizbek.uz.hr_management.entity.Task;
import lazizbek.uz.hr_management.payload.ApiResponse;
import lazizbek.uz.hr_management.payload.TaskDto;
import lazizbek.uz.hr_management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;


    /**
     * ADD TASK
     *
     * @param taskDto Name,
     *                Description,
     *                Deadline,
     *                UserId
     * @return ApiResponse in ResponseEntity
     */
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody TaskDto taskDto) {
        ApiResponse apiResponse = taskService.add(taskDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }


    /**
     * CONFIRM TASK
     *
     * @param taskCode String
     * @return ApiResponse in ResponseEntity
     */
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmTask(@RequestParam String taskCode) {
        ApiResponse apiResponse = taskService.confirmTask(taskCode);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * COMPLETE TASK
     *
     * @return ApiResponse in ResponseEntity
     */
    @PutMapping("/complete")
    public ResponseEntity<?> complete() {
        ApiResponse apiResponse = taskService.complete();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    /**
     * GET ALL USER TASKS BY SECURITY CONTEXT HOLDER
     *
     * @return ApiResponse in ResponseEntity
     */
    @GetMapping
    public ResponseEntity<?> get() {
        List<Task> tasks = taskService.get();
        return ResponseEntity.status(tasks != null ? 200 : 409).body(tasks);
    }
}
