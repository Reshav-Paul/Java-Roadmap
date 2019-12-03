import java.util.*;

public class ArrayListTutorial {
    public static void main(String[] args) {
        long startTime, endTime;

        // An ArrayList maintains an array internally
        //An array provides O(1) random access time
        //Elements are stored in consecutive memory locations
        List<Integer> arrayList = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++)
            arrayList.add((int) (Math.random() * 100));
        System.out.println();

        // Insertion at the end is very fast
        startTime = System.currentTimeMillis();
        for(long i = 0; i < 100000; i++)
            arrayList.add((int)(Math.random() * 100));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion at the end(ms): " + (endTime - startTime));

        //Insertion at the front is slow. Insertion in the middle is slow as well
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            arrayList.add(0, (int)(Math.random() * 100));
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for insertion at the front(ms): " + (endTime - startTime));

        //Deletion at the end is very fast
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            arrayList.remove(arrayList.size() - 1);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for deletion in the end(ms): " + (endTime - startTime));

        //Deletion at the front is slow. Deletion from the middle is slow as well
        startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++)
            arrayList.remove(0);
        endTime = System.currentTimeMillis();
        System.out.println("Time taken for deletion in the front(ms): " + (endTime - startTime));

        //Insertion and deletion in the middle or near the middle is slower.
        //This is because internally the elements are shifted to fill the vacancy
        //when an element is deleted, which takes time
        //Similarly, when an element is to be inserted at any position not near
        //the end, a lot of elements have to be shifted to make space for the new element

        //Performance considerations become important when there are really large lists
        //with frequent insertion at positions not near the end.
        
        //Some other functions provided by ArrayList are
        arrayList.clear();

        //Adds all elements in another collection
        arrayList.addAll(Arrays.asList(1, 2, 3, 5, 4, 8, 6, 7));
        System.out.println();

        System.out.println("Original list: " + arrayList);
        System.out.println("Is the ArrayList Empty? " + (arrayList.isEmpty()? "Yes" : "No"));

        //Searching for a single element and for a list of elements
        System.out.println("Contains 6? " + arrayList.contains(6)); 
        System.out.println("Contains 1, 3 and 4? " + arrayList.containsAll(Arrays.asList(1, 3, 4)));

        //Setting elements in particular location
        arrayList.set(5, 7);
        arrayList.set(7, 8);
        System.out.println("Swapping elements 8 and 7, we get: " + arrayList);

        //Getting elements from particular location
        System.out.println("Element at position 4: " + arrayList.get(3));

        //Getting a sublist from the original list
        System.out.println("sublist from index 2 to 5: " + arrayList.subList(2, 6));

        //Deleting elements using a condition
        arrayList.removeIf(e -> e % 2 == 0);
        System.out.println("After removing all even elements: " + arrayList);

        //and many more functions are also available...
    }
}