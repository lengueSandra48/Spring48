package com.lenguecode.todolist.controller;

import com.lenguecode.todolist.model.StatusToDo;
import com.lenguecode.todolist.model.Task;
import com.lenguecode.todolist.service.IBaseToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
public class ToDoListController {
    private final IBaseToDoService service;

    //creer une tache
    @PostMapping("/task")
    public ResponseEntity<Integer> createTask(@RequestBody Task task) {
        Integer taskId = service.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskId);
    }


    // Lire toutes les tâches
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(service.readAllTasks());
    }

    // Filtrer les tâches par statut
    @GetMapping("/tasks/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable StatusToDo status) {
        return ResponseEntity.ok(service.filterByStatus(status));
    }

    // Mettre à jour le statut d'une tâche
    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Integer taskId, @RequestBody StatusToDo status) {
        return service.updateTaskStatus(taskId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour le titre d'une tâche
    @PatchMapping("/tasks/{taskId}/title")
    public ResponseEntity<Task> updateTaskTitle(@PathVariable Integer taskId, @RequestBody String title) {
        return service.updateTaskTitle(taskId, title)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Mettre à jour la description d'une tâche
    @PatchMapping("/tasks/{taskId}/description")
    public ResponseEntity<Task> updateTaskDescription(@PathVariable Integer taskId, @RequestBody String description) {
        return service.updateTaskDescription(taskId, description)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une tâche
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer taskId) {
        service.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
