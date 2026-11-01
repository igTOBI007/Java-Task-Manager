package com.aadil.taskmanager.ui;

import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.model.Task;
import com.aadil.taskmanager.service.TaskService;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private final TaskService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleApp(TaskService service) {
        this.service = service;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addTask();
                    case "2" -> listAll();
                    case "3" -> listByStatus();
                    case "4" -> changeStatus();
                    case "5" -> deleteTask();
                    case "6" -> showStats();
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Goodbye.");
    }

    private void printMenu() {
        System.out.println("""

                ===== TASK MANAGER =====
                1. Add task
                2. List all tasks
                3. List by status
                4. Change task status
                5. Delete task
                6. Stats
                0. Exit
                ------------------------""");
        System.out.print("Choose: ");
    }

    private void addTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        Priority priority = readEnum(Priority.class, "Priority (LOW/MEDIUM/HIGH): ");
        Task task = service.createTask(title, priority);
        System.out.println("Created -> " + task);
    }

    private void listAll() {
        printTasks(service.getAllTasks());
    }

    private void listByStatus() {
        Status status = readEnum(Status.class, "Status (PENDING/IN_PROGRESS/DONE): ");
        printTasks(service.getTasksByStatus(status));
    }

    private void changeStatus() {
        int id = readInt("Task id: ");
        Status status = readEnum(Status.class, "New status (PENDING/IN_PROGRESS/DONE): ");
        System.out.println("Updated -> " + service.updateStatus(id, status));
    }

    private void deleteTask() {
        int id = readInt("Task id: ");
        service.deleteTask(id);
        System.out.println("Deleted task " + id);
    }

    private void showStats() {
        System.out.println("Total: " + service.getAllTasks().size()
                + " | Completed: " + service.countCompleted());
    }

    private void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        tasks.forEach(System.out::println);
    }

    private int readInt(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please enter a valid number");
        }
    }

    private <E extends Enum<E>> E readEnum(Class<E> type, String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim().toUpperCase();
        try {
            return Enum.valueOf(type, input);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value: " + input);
        }
    }
}