package com.aadil.taskmanager.service;

import com.aadil.taskmanager.exception.TaskNotFoundException;
import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.model.Task;
import com.aadil.taskmanager.repository.TaskRepository;

import java.util.List;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(String title, Priority priority) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        return repository.save(new Task(title.trim(), priority));
    }

    public Task getTask(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public List<Task> getTasksByStatus(Status status) {
        return repository.findAll()
                .stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public Task updateStatus(int id, Status status) {
        Task task = getTask(id);
        task.setStatus(status);
        return repository.save(task);
    }

    public void deleteTask(int id) {
        if (!repository.deleteById(id)) {
            throw new TaskNotFoundException(id);
        }
    }

    public long countCompleted() {
        return repository.findAll()
                .stream()
                .filter(task -> task.getStatus() == Status.DONE)
                .count();
    }
}