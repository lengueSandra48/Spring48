package com.lenguecode.todolist.service;

import com.lenguecode.todolist.model.StatusToDo;
import com.lenguecode.todolist.model.Task;
import com.lenguecode.todolist.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseToDoServiceImpl implements IBaseToDoService{

    private final TaskRepository taskRepository;

    @Override
    public Integer createTask(Task task) {
        Task currentTask = Task.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .duedate(task.getDuedate())
                .status(task.getStatus())
                .build();

        Task savedTask = taskRepository.save(currentTask);
        return savedTask.getId();
    }


    @Override
    public List<Task> readAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> filterByStatus(StatusToDo status) {
        return taskRepository.findAllByStatus(status);
    }

    @Override
    public Optional<Task> updateTaskStatus(Integer taskId, StatusToDo status) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setStatus(status);
                    return taskRepository.save(task);
                });
    }


    @Override
    public Optional<Task> updateTaskTitle(Integer taskId, String title) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setTitle(title);
                    return taskRepository.save(task);
                });
    }


    @Override
    public Optional<Task> updateTaskDescription(Integer taskId, String description) {
        return taskRepository.findById(taskId)
                .map(task -> {
                    task.setDescription(description);
                    return taskRepository.save(task);
                });
    }


    @Override
    public void deleteTask(Integer taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found with ID " + taskId);
        }
        taskRepository.deleteById(taskId);
    }

}
