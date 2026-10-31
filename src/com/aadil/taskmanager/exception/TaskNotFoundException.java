package com.aadil.taskmanager.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(int id) {
        super("No task found with id: " + id);
    }
}