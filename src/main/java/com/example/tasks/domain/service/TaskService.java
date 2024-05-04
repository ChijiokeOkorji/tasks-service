package com.example.tasks.domain.service;

import com.example.tasks.application.dto.Task;
import com.example.tasks.infrastructure.repository.TaskRepository;
import com.example.tasks.application.dto.TaskRequest;
import com.example.tasks.infrastructure.entity.TaskEntity;
import com.example.tasks.infrastructure.mapper.TaskDtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskDtoMapper taskDtoMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskDtoMapper taskDtoMapper) {
        this.taskRepository = taskRepository;
        this.taskDtoMapper = taskDtoMapper;
    }

    public Task createTask(TaskRequest taskRequest) {
        TaskEntity taskEntity = taskDtoMapper.toEntityModel(taskRequest);
        TaskEntity taskEntityResponse = taskRepository.save(taskEntity);
        return taskDtoMapper.fromEntityModel(taskEntityResponse);
    }

    public List<Task> getAllTasks() {
        List<TaskEntity> taskEntityResponse = taskRepository.findAll();
        return taskEntityResponse.stream()
                .map(taskDtoMapper::fromEntityModel)
                .toList();
    }

    public Task getTaskById(String taskId) {
        TaskEntity taskEntityResponse = taskRepository.findById(UUID.fromString(taskId)).orElse(null);

        if (Objects.nonNull(taskEntityResponse)) {
            return taskDtoMapper.fromEntityModel(taskEntityResponse);
        } else {
            return null;
        }
    }

    public Task updateTask(String taskId, TaskRequest taskRequest) {
        TaskEntity existingTask = taskRepository.findById(UUID.fromString(taskId))
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        TaskEntity taskEntity = taskDtoMapper.toEntityUpdateModel(existingTask, taskRequest);
        TaskEntity taskEntityResponse = taskRepository.save(taskEntity);
        return taskDtoMapper.fromEntityModel(taskEntityResponse);
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteById(UUID.fromString(taskId));
    }
}
