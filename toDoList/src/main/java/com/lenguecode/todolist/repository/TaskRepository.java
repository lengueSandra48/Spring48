package com.lenguecode.todolist.repository;

import com.lenguecode.todolist.model.StatusToDo;
import com.lenguecode.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByStatus(StatusToDo status);
}
