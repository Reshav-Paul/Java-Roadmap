import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamAPITutorial {

    public static void doubleValue(Integer i) {
        System.out.print(i * 2 + " | ");
    }

    public static void main(String[] args) {
        List<Integer> naturalNumbers = Arrays.asList(1, 2, 3, 4, 5);

        // Different methods for Iterating over the List
        // 1. Anonymous class technique
        System.out.println();
        Consumer<Integer> c1 = new Consumer<Integer>() {
            public void accept(Integer i) {
                System.out.print(i + " | ");
            }
        };
        naturalNumbers.forEach(c1);

        // 2. Lambda initialization of object
        Consumer<Integer> c2 = i -> System.out.print(i + " | ");
        naturalNumbers.forEach(c2);

        // 3. Lamda Passing without object
        naturalNumbers.forEach(i -> System.out.print(i + " | "));

        // 4. Passing a method reference
        naturalNumbers.forEach(System.out::print);
        System.out.print(" | ");
        naturalNumbers.forEach(StreamAPITutorial::doubleValue);

        // Using Streams
        // Demonstrating map() and reduce()
        // Complete breakdown implementation to calculate the sum of triple
        // of all the elements of the stream

        // get the stream
        Stream<Integer> valueStream = naturalNumbers.stream();

        // Create a function to triple any integer value
        Function<Integer, Integer> tripleValue = new Function<Integer, Integer>() {
            public Integer apply(Integer i) {
                return i * 3;
            }
        };
        // Get the tripled values into a different stream
        Stream<Integer> tripleStream = valueStream.map(tripleValue);

        // Create a BinaryOperator to reduce the stream to its sum
        BinaryOperator<Integer> add = new BinaryOperator<Integer>() {
            public Integer apply(Integer i, Integer j) {
                return i + j;
            }
        };
        // reduce the stream and store the sum into result
        Integer result = tripleStream.reduce(0, add);
        System.out.println("\n\ntriple sum using breakdown method: " + result);

        // condensed implementation of the same map() logic using lambdas
        System.out.println("triple sum using condensed method using lambdas: "
                + naturalNumbers.stream().map(i -> i * 3).reduce(0, (c, e) -> c + e));
        System.out.println("...condensed method with function: "
                + naturalNumbers.stream().map(i -> i * 3).reduce(0, (c, e) -> Integer.sum(c, e)));
        System.out.println("...condensed method with function reference: "
                + naturalNumbers.stream().map(i -> i * 3).reduce(0, Integer::sum));

        // Demonstrating filter()
        List<Integer> values = Arrays.asList(25, 32, 49, 55, 64, 80);

        // using map() and filter() to perform the same function
        // adding numbers that are multiples of 5
        // using map()
        System.out.println(
                "sum of multiples of 5: " + values.stream().map(i -> i % 5 == 0 ? i : 0).reduce(0, (c, e) -> c + e));

        // using filter()
        // Complete breakdown implementation of using filter()
        Predicate<Integer> isMulOfFive = new Predicate<Integer>() {
            public boolean test(Integer val) {
                return val % 5 == 0;
            }
        };
        System.out.println(
                "...using breakdown method: " + values.stream().filter(isMulOfFive).reduce(0, (c, e) -> c + e));

        // condensed implementation of the same filter() logic using lambdas
        System.out.println("...using lambdas: " + values.stream().filter(i -> i % 5 == 0).reduce(0, (c, e) -> c + e));

        // combining map(), filter() and reduce()
        // increment values by 1 and then check divisibility by 5
        System.out.println("More use of lambdas: "
                + values.stream().map(i -> i + 1).filter(i -> i % 5 == 0).reduce(0, (c, e) -> c + e));

        // print only the first value in the stream. If the stream is empty print 0
        System.out.println("Demonstrating orElse and printing only first value if present: "
                + values.stream().map(i -> i + 1).filter(i -> i % 5 == 0).findFirst().orElse(0));
    }
}