package com.aadil.taskmanager.service;

import com.aadil.taskmanager.exception.TaskNotFoundException;
import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.model.Task;
import com.aadil.taskmanager.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService(new InMemoryTaskRepository());
    }

    @Test
    void createTask_trimsTitleAndAssignsId() {
        Task task = service.createTask("  Learn testing  ", Priority.HIGH);

        assertEquals("Learn testing", task.getTitle());
        assertEquals(1, task.getId());
        assertEquals(Status.PENDING, task.getStatus());
    }

    @Test
    void createTask_blankTitle_throwsAndStoresNothing() {
        assertThrows(IllegalArgumentException.class,
                () -> service.createTask("   ", Priority.LOW));

        assertEquals(0, service.getAllTasks().size());
    }

    @Test
    void getTask_missingId_throwsNotFound() {
        assertThrows(TaskNotFoundException.class, () -> service.getTask(99));
    }

    @Test
    void updateStatus_changesTheStatus() {
        Task task = service.createTask("Write tests", Priority.MEDIUM);

        service.updateStatus(task.getId(), Status.DONE);

        assertEquals(Status.DONE, service.getTask(task.getId()).getStatus());
    }

    @Test
    void deleteTask_missingId_throwsNotFound() {
        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(5));
    }

    @Test
    void countCompleted_countsOnlyDoneTasks() {
        Task a = service.createTask("A", Priority.LOW);
        service.createTask("B", Priority.LOW);
        Task c = service.createTask("C", Priority.LOW);

        service.updateStatus(a.getId(), Status.DONE);
        service.updateStatus(c.getId(), Status.DONE);

        assertEquals(2, service.countCompleted());
    }
}