import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

class Employee {

    String name;
    String designation;
    double salary;
    int experience;

    Employee(String name, String designation, double salary, int experience) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.experience = experience;
    }

    @Override
    public String toString() {
        return name + ", " + salary + ", " + designation + ", " + experience;
    }
}

public class FunctionalProgramming {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Some simple problems solved using functional programming
        // prime checking
        int num1 = 31, num2 = 63;
        System.out.println(num1 + " is Prime? " + isPrime(num1));
        System.out.println(num2 + " is Prime? " + isPrime(num2));

        // sum of all numbers
        Integer sum = numbers.stream().reduce(Integer::sum).get();
        System.out.println("Sum of all numbers: " + sum);

        // sum of all even numbers
        Integer evenSum = numbers.stream().filter(e -> e % 2 == 0).reduce(Integer::sum).get();
        System.out.println("Sum of all even numbers: " + evenSum + "\n");

        // Some operations involvong the employee class using functional programming
        List<Employee> employees = getEmployees();
        employees.forEach(System.out::println);

        //finding employees whose names start with 'A'
        System.out.println("\nEmployees whose names start with \'A\'");
        employees.stream().filter(e -> e.name.charAt(0) == 'A').forEach(System.out::println);

        //finding employees whose salaries are greater than 20000
        System.out.println("\nEmployees with salary greater than 20000");
        employees.stream().filter(e -> e.salary > 20000).forEach(System.out::println);

        //Incrementing salaries of all employees by 10%
        System.out.println("\nSalary increment of 10% for each employee");
        employees.stream().map(e -> e.salary = e.salary * 1.1).close();
        employees.forEach(System.out::println);

        //finding out the most experienced employees
        System.out.println("\nDisplaying the names of most experienced employees");
        int maxExperience = employees.stream().max((a, b) -> Integer.compare(a.experience, b.experience))
                .get().experience;
        employees.stream().filter(e -> e.experience == maxExperience)
                .forEach(e -> System.out.println(e.name + ", experience: " + e.experience + " years"));
    }

    public static boolean isPrime(Integer number) {
        IntPredicate isDivisible = e -> number % e == 0;
        return IntStream.range(2, number - 1).noneMatch(isDivisible);
    }

    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<Employee>();

        List<String> names = Arrays.asList("Reshav", "Aviroop", "Kuntal", "Anirban", "Aniruddha");
        List<String> designations = Arrays.asList("Developer", "Designer", "Marketing");

        for (int i = 0; i < 5; i++) {
            double growth = Math.random();
            Employee employee = new Employee(names.get(i), designations.get(i % 3), growth * 20000 + 15000,
                    (int) (growth * 5));
            employees.add(employee);
        }
        return employees;
    }
}