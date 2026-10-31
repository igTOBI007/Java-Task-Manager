import com.aadil.taskmanager.exception.TaskNotFoundException;
import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.repository.InMemoryTaskRepository;
import com.aadil.taskmanager.repository.TaskRepository;
import com.aadil.taskmanager.service.TaskService;

public class Main {

    public static void main(String[] args) {

        TaskRepository repo = new InMemoryTaskRepository();
        TaskService service = new TaskService(repo);

        service.createTask("Learn services", Priority.HIGH);
        service.createTask("Write tests", Priority.LOW);
        service.createTask("Push to GitHub", Priority.MEDIUM);

        service.updateStatus(1, Status.DONE);
        service.updateStatus(3, Status.DONE);

        System.out.println("DONE tasks:");
        service.getTasksByStatus(Status.DONE).forEach(System.out::println);

        System.out.println("\nCompleted count: " + service.countCompleted());

        service.deleteTask(2);
        System.out.println("\nAfter deleting task 2:");
        service.getAllTasks().forEach(System.out::println);

        try {
            service.deleteTask(2);
        } catch (TaskNotFoundException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
}