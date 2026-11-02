package com.aadil.taskmanager.repository;

import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryTaskRepositoryTest {

    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTaskRepository();
    }

    @Test
    void save_assignsIncreasingIds() {
        Task first = repository.save(new Task("First", Priority.LOW));
        Task second = repository.save(new Task("Second", Priority.LOW));

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void deleteById_returnsTrueOnlyWhenSomethingWasRemoved() {
        Task task = repository.save(new Task("Temp", Priority.LOW));

        assertTrue(repository.deleteById(task.getId()));
        assertFalse(repository.deleteById(task.getId()));
    }
}