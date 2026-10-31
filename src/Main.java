import com.aadil.taskmanager.model.Priority;
import com.aadil.taskmanager.model.Status;
import com.aadil.taskmanager.model.Task;
import com.aadil.taskmanager.repository.InMemoryTaskRepository;
import com.aadil.taskmanager.repository.TaskRepository;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {

        TaskRepository repo = new InMemoryTaskRepository();

        repo.save(new Task("Finish Java project", Priority.HIGH));
        repo.save(new Task("Solve 3 LeetCode problems", Priority.MEDIUM));
        repo.save(new Task("Read about interfaces", Priority.LOW));
        System.out.println("All tasks:");
        repo.findAll().forEach(System.out::println);

        Optional<Task> found = repo.findById(2);
        System.out.println("\nfindById(2) present? " + found.isPresent());
        found.ifPresent(System.out::println);
        Optional<Task> missing = repo.findById(99);
        System.out.println("\nfindById(99) present? " + missing.isPresent());

        Task t1 = repo.findById(1).get();
        t1.setStatus(Status.DONE);
        repo.save(t1);
        System.out.println("\nAfter updating task 1:");
        repo.findAll().forEach(System.out::println);

        System.out.println("\ndeleteById(2): " + repo.deleteById(2));
        System.out.println("deleteById(2) again: " + repo.deleteById(2));

        System.out.println("\nFinal list:");
        repo.findAll().forEach(System.out::println);
    }
}