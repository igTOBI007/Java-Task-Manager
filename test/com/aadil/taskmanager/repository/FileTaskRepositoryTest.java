package com.aadil.taskmanager.repository;

import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileTaskRepositoryTest {

    @TempDir
    Path tempDir;

    private String fileName;

    @BeforeEach
    void setUp() {
        fileName = tempDir.resolve("tasks.txt").toString();
    }

    @Test
    void savedTasks_surviveReload() {
        FileTaskRepository first = new FileTaskRepository(fileName);
        Task task = first.save(new Task("Persist me", Priority.HIGH));
        task.setStatus(Status.DONE);
        first.save(task);

        FileTaskRepository second = new FileTaskRepository(fileName);
        List<Task> loaded = second.findAll();

        assertEquals(1, loaded.size());
        assertEquals("Persist me", loaded.get(0).getTitle());
        assertEquals(Priority.HIGH, loaded.get(0).getPriority());
        assertEquals(Status.DONE, loaded.get(0).getStatus());
    }

    @Test
    void ids_continueAfterReload() {
        FileTaskRepository first = new FileTaskRepository(fileName);
        first.save(new Task("One", Priority.LOW));
        first.save(new Task("Two", Priority.LOW));

        FileTaskRepository second = new FileTaskRepository(fileName);
        Task third = second.save(new Task("Three", Priority.LOW));

        assertEquals(3, third.getId());
    }

    @Test
    void deletedTask_staysDeletedAfterReload() {
        FileTaskRepository first = new FileTaskRepository(fileName);
        Task keep = first.save(new Task("Keep", Priority.LOW));
        Task remove = first.save(new Task("Remove", Priority.LOW));

        first.deleteById(remove.getId());

        FileTaskRepository second = new FileTaskRepository(fileName);
        List<Task> loaded = second.findAll();

        assertEquals(1, loaded.size());
        assertEquals(keep.getId(), loaded.get(0).getId());
    }

    @Test
    void missingFile_startsEmpty() {
        FileTaskRepository repository = new FileTaskRepository(fileName);

        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void titleContainingPipe_survivesReload() {
        FileTaskRepository first = new FileTaskRepository(fileName);
        first.save(new Task("Read A|B notes", Priority.LOW));

        FileTaskRepository second = new FileTaskRepository(fileName);

        assertEquals("Read A|B notes", second.findAll().get(0).getTitle());
    }
}