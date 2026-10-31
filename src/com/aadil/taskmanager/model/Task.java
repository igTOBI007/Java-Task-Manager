package com.aadil.taskmanager.model;

public class Task {

    private int id;
    private String title;
    private Priority priority;
    private Status status;

    public Task(String title, Priority priority) {
        this.title = title;
        this.priority = priority;
        this.status = Status.PENDING;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return String.format("[%d] %-25s | %-6s | %s", id, title, priority, status);
    }
}