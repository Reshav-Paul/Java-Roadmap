import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class QueueTutorial {
    public static void main(String[] args) {
        Queue<String> names = new ArrayDeque<>();
        populate(names);

        // add() and remove() throw exceptions when the queue is full or empty
        // respectively
        System.out.println("Current Queue State: " + names);
        names.add("Ankush");
        System.out.println("Updated Queue State: " + names);

        System.out.println("\nRemoving 2 elements");
        names.remove();
        names.remove();
        System.out.println("Updated Queue State: " + names);

        // offer() and poll() are equivalent to add() and remove() but dont throw
        // exceptions
        names.offer("Reshav");
        names.offer("Kuntal");
        System.out.println("\nAfter adding two values using offer(): " + names);
        System.out.println("Removed " + names.poll() + " from the queue using poll()");
        // if queue is empty poll returns null
        // if queue is full offer() returns false

        // Some other functions of queues are
        // peek() retrieves but does not remove the head, returns null if queue is empty
        System.out.println("Current Queue head: " + names.peek());

        // element() retrieves does the same as peek(), but throws an exception if queue
        // is empty
        System.out.println("Current Queue head: " + names.element());

        System.out.println("Size: " + names.size());
        System.out.println("Contains Reshav? " + names.contains("Reshav"));
        System.out.println("Contains {Reshav, Anirban}? " + names.containsAll(Arrays.asList("Reshav", "Anirban")));

        //removing all names starting with an 'A'
        names.removeIf(e -> e.charAt(0) == 'A');
        System.out.println("After removing all names starting with \'A\': " + names);
    }

    public static void populate(Queue<String> inputQueue) {
        inputQueue.add("Reshav");
        inputQueue.add("Kuntal");
        inputQueue.add("Anirban");
        inputQueue.add("Aviroop");
        inputQueue.add("Aniruddha");
    }
}