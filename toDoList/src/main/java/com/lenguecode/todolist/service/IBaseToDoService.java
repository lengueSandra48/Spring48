package com.lenguecode.todolist.service;

import com.lenguecode.todolist.model.StatusToDo;
import com.lenguecode.todolist.model.Task;

import java.util.List;
import java.util.Optional;

public interface IBaseToDoService {

    Integer createTask(Task task);
    List<Task> readAllTasks();
    List<Task> filterByStatus(StatusToDo status);
    Optional<Task> updateTaskStatus(Integer taskId, StatusToDo status);
    Optional<Task> updateTaskTitle(Integer taskId, String title);
    Optional<Task> updateTaskDescription(Integer taskId, String description);
    void deleteTask(Integer taskId);
}
