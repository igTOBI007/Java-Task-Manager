package com.aadil.taskmanager.repository;

import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.model.Task;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileTaskRepository implements TaskRepository {

    private final Path filePath;
    private final Map<Integer, Task> storage = new LinkedHashMap<>();
    private int nextId = 1;

    public FileTaskRepository(String fileName) {
        this.filePath = Path.of(fileName);
        load();
    }

    private void load() {
        if (!Files.exists(filePath)) {
            return;
        }
        try {
            for (String line : Files.readAllLines(filePath)) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|", 4);
                int id = Integer.parseInt(parts[0]);
                Priority priority = Priority.valueOf(parts[1]);
                Status status = Status.valueOf(parts[2]);

                Task task = new Task(parts[3], priority);
                task.setId(id);
                task.setStatus(status);

                storage.put(id, task);
                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read " + filePath, e);
        }
    }

    private void persist() {
        List<String> lines = new ArrayList<>();
        for (Task task : storage.values()) {
            lines.add(task.getId() + "|" + task.getPriority() + "|"
                    + task.getStatus() + "|" + task.getTitle());
        }
        try {
            Files.write(filePath, lines);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not write " + filePath, e);
        }
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == 0) {
            task.setId(nextId++);
        }
        storage.put(task.getId(), task);
        persist();
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
        boolean removed = storage.remove(id) != null;
        if (removed) {
            persist();
        }
        return removed;
    }
}