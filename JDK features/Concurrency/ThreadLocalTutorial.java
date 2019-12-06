import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
* ThreadLocal is used to store thread specific information locally for each thread.
*
*    Use cases:
* 1. They can be used to provide some uniform information to all tasks
*    in a thread safe manner. Alternatively one can create seperate objects for
*    each task which leads to memory inefficiency. Otherwise one can use a
*    global variable but that has to be accessed in a thread safe manner though
*    synchronization which compromises performance.
*
* 2.  They can be used to share data among many services running on the same thread
*     in a thread safe manner and without passing the data among the services.
*/

//The following code demonstrates the both use cases
class UserContextHolder {

    // For first use case: Each thread gets a local copy of recordType
    public static ThreadLocal<String> recordType = ThreadLocal.withInitial(() -> "User");

    // For second use case: Each thread can store a different userName
    // depending upon the user it is currently processing.
    public static ThreadLocal<String> userName = new ThreadLocal<>();
}

class User {
    private String name;
    private int id;
    private String password;

    User(String name, int id) {
        this.name = name;
        this.id = id;

        // set ThreadLocal value for current Thread
        UserContextHolder.userName.set(name);

        // call subsequent services
        password = new PasswordGenerator().process();

        // remove() ThreadLocal value after current thread finishes operations
        UserContextHolder.userName.remove();
    }

    @Override
    public String toString() {
        return UserContextHolder.recordType.get() + ": " + id + " " + name + ": " + password;
    }
}

class PasswordGenerator {
    // Service 1
    public String process() {

        // can get the username for the current thread without any parameters
        // being passed or any synchronized global object
        return new NameInitialGenerator().process() + (int) (Math.random() * UserContextHolder.userName.get().length());
    }
}

class NameInitialGenerator {
    // Service 2
    public String process() {

        // can get the username for the current thread without any parameters
        // being passed or any synchronized global object
        String firstLetter = UserContextHolder.userName.get().substring(0, 1);
        int spacePosition = UserContextHolder.userName.get().indexOf(" ");
        String secondLetter = spacePosition > 0 && spacePosition < UserContextHolder.userName.get().length() - 1
                ? UserContextHolder.userName.get().substring(spacePosition + 1, spacePosition + 2)
                : "";

        return firstLetter + secondLetter;
    }
}

public class ThreadLocalTutorial {
    public static void main(String args[]) throws InterruptedException {

        String names[] = { "Reshav Paul", "Kuntal Saha", "Aviroop Chatterjee", "Anirban Saha", "Aniruddha Chanda",
                "Ankush Dutta" };
        int ids[] = { 1, 2, 3, 4, 5, 6 };

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 6; i++) {
            final String name = names[i];
            final int id = ids[i];
            threadPool.submit(() -> {
                User user = new User(name, id);
                System.out.println(user);
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.MINUTES);
    }
}