package com.example.tasks.application.controller;

import com.example.tasks.application.dto.Task;
import com.example.tasks.application.dto.TaskRequest;
import com.example.tasks.domain.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<Task> createEntity(@Valid @RequestBody TaskRequest taskRequest) {
        Task response = taskService.createTask(taskRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<List<Task>> getAllEntities() {
        List<Task> response = taskService.getAllTasks();

        if (ObjectUtils.isEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<Task> getEntityById(@PathVariable String taskId) {
        Task response = taskService.getTaskById(taskId);

        if (ObjectUtils.isEmpty(response)) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('user', 'admin')")
    public ResponseEntity<Task> updateEntity(@PathVariable String taskId, @Valid @RequestBody TaskRequest taskRequest) {
        Task response = taskService.updateTask(taskId, taskRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Void> deleteEntity(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
