import com.aadil.taskmanager.repository.FileTaskRepository;
import com.aadil.taskmanager.repository.TaskRepository;
import com.aadil.taskmanager.service.TaskService;
import com.aadil.taskmanager.ui.ConsoleApp;

public class Main {

    public static void main(String[] args) {
        TaskRepository repository = new FileTaskRepository("tasks.txt");
        TaskService service = new TaskService(repository);
        new ConsoleApp(service).run();
    }
}