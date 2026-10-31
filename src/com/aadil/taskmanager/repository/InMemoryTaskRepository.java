package com.aadil.taskmanager.repository;

import com.aadil.taskmanager.model.Task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Integer, Task> storage = new LinkedHashMap<>();
    private int nextId = 1;

    @Override
    public Task save(Task task) {
        if (task.getId() == 0) {
            task.setId(nextId++);
        }
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(int id) {
        return storage.remove(id) != null;
    }
}