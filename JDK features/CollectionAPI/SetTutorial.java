import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SetTutorial {
    public static void main(String[] args) {

        // A set is a list of unique elements

        // HashSet does not preserve any well defined order among the elements
        Set<String> names = new HashSet<>();
        // populate the set with names
        populate(names);

        // Adds an element which exists already
        // Duplicate element will not be added in a set
        // whereas a list can contain duplicate elements
        names.add("Reshav");
        System.out.println("HashSet: " + names);

        // add a new name
        names.add("Ankush");
        System.out.println("HashSet: " + names);

        // TreeSet maintains the natural order among the elements
        SortedSet<String> sortedNames = new TreeSet<String>();
        populate(sortedNames);
        System.out.println("TreeSet: " + sortedNames);

        // LinkedHashSet preserves the order in which the elements were entered
        names = new LinkedHashSet<>();
        populate(names);
        System.out.println("LinkedHashSet: " + names);

        // Some other set operations
        // Displaying size
        System.out.println("\nSet size: " + names.size());
        // Searching for a value
        System.out.println("Contains Reshav? " + names.contains("Reshav"));
        // Searching for a set of values
        System.out
                .println("Contains {Anirban, Aniruddha}? " + names.containsAll(Arrays.asList("Anirban", "Aniruddha")));

        // removing all elements with 'a' as the second last character
        names.removeIf(e -> e.charAt(e.length() - 2) == 'a');
        System.out.println("\nAfter removing all names with \'a\' as the second last character: ");
        System.out.println(names);
    }

    public static void populate(Set<String> inputSet) {

        inputSet.add("Reshav");
        inputSet.add("Kuntal");
        inputSet.add("Anirban");
        inputSet.add("Aviroop");
        inputSet.add("Aniruddha");
    }
}