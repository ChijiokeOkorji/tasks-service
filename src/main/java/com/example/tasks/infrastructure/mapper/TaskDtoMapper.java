package com.example.tasks.infrastructure.mapper;

import com.example.tasks.application.dto.Task;
import com.example.tasks.application.dto.TaskRequest;
import com.example.tasks.infrastructure.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoMapper {
    public TaskEntity toEntityModel(TaskRequest taskRequest) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(taskRequest.getTaskName());
        taskEntity.setStatus(taskRequest.getStatus());
        return taskEntity;
    }
    public Task fromEntityModel(TaskEntity taskEntity){
        Task task = new Task();
        task.setId(taskEntity.getId().toString());
        task.setTaskName(taskEntity.getTaskName());
        task.setStatus(taskEntity.getStatus());
        return task;
    }
    public TaskEntity toEntityUpdateModel(TaskEntity taskEntity, TaskRequest taskRequest){
        taskEntity.setTaskName(taskRequest.getTaskName());
        taskEntity.setStatus(taskRequest.getStatus());
        return taskEntity;
    }
}
