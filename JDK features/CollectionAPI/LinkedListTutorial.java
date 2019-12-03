import java.util.*;

public class LinkedListTutorial {
    public static void main(String[] args) {
        long startTime, endTime;

        // An ArrayList maintains an array internally
        //An array provides O(1) random access time
        //Elements are stored in consecutive memory locations
        LinkedList<Integer> linkedList = new LinkedList<Integer>();

        for (int i = 0; i < 100; i++)
            linkedList.add((int) (Math.random() * 100));
        System.out.println();

        // Insertion at the end is very fast
        startTime = System.currentTimeMillis();
        for(long i = 0; i < 10000; i++)
            linkedList.add((int)(Math.random() * 100));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion at the end(ms): " + (endTime - startTime));

        //Insertion at the front is very fast
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++)
            linkedList.add(0, (int)(Math.random() * 100));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion at the front(ms): " + (endTime - startTime));

        //Deletion at the end is very fast
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            linkedList.remove(linkedList.size() - 1);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for deletion in the end(ms): " + (endTime - startTime));

        //Deletion at the front is very fast
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            linkedList.remove(0);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for deletion in the front(ms): " + (endTime - startTime));

        //Insertion at the middle is slow
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            linkedList.add(linkedList.size() / 2, (int)(Math.random() * 100));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion at the middle(ms): " + (endTime - startTime));

        //Deletion at the middle is slow
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            linkedList.remove(linkedList.size() / 2);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for deletion at the middle(ms): " + (endTime - startTime));

        //LinkedList maintains a double linked list internally and thus
        //insertion and deletion at the front and end occur in constant time
        //However in the average case the time for insertion and deletion
        //at any location is O(n).
        
        //Some other functions provided by LinkedList are
        linkedList.clear();

        //Adds all elements in another collection
        linkedList.addAll(Arrays.asList(1, 2, 3, 5, 4, 8, 6, 7));
        System.out.println();

        System.out.println("Original list: " + linkedList);
        System.out.println("Is the LinkedList Empty? " + (linkedList.isEmpty()? "Yes" : "No"));

        //Searching for a single element and for a list of elements
        System.out.println("Contains 6? " + linkedList.contains(6)); 
        System.out.println("Contains 1, 3 and 4? " + linkedList.containsAll(Arrays.asList(1, 3, 4)));

        //Setting elements in particular location
        linkedList.set(5, 7);
        linkedList.set(7, 8);
        System.out.println("Swapping elements 8 and 7, we get: " + linkedList);

        //Getting elements from particular location
        System.out.println("Element at position 4: " + linkedList.get(3));

        //Getting a sublist from the original list
        System.out.println("sublist from index 2 to 5: " + linkedList.subList(2, 6));

        //Deleting elements using a condition
        linkedList.removeIf(e -> e % 2 == 0);
        System.out.println("After removing all even elements: " + linkedList);

        //and many more functions are also available...
    }
}