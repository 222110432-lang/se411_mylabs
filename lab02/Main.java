import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Exercise 1
        System.out.println("Exercise 1:");

        String[] names = {"Ali", "Sara", "Ahmed"};

        PrintableList<String> list = new PrintableList<>(names);

        list.printItems();


        // Exercise 2
        System.out.println("\nExercise 2:");

        NumberBox<Integer> intBox = new NumberBox<>();
        intBox.setItem(10);
        System.out.println("Integer item: " + intBox.getItem());

        NumberBox<Double> doubleBox = new NumberBox<>();
        doubleBox.setItem(5.5);
        System.out.println("Double item: " + doubleBox.getItem());

        List<Integer> intNumbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("Integer sum: " + intBox.sumNumbers(intNumbers));

        List<Double> doubleNumbers = Arrays.asList(1.5, 2.5, 3.5);
        System.out.println("Double sum: " + doubleBox.sumNumbers(doubleNumbers));


        // Exercise 3
        System.out.println("\nExercise 3:");

        Pipeline<String, String> pipeline = new Pipeline<>();

        // String -> String
        Pipeline<String, String> upperPipeline =
                pipeline.addTransformer(input -> input.toUpperCase());

        // String -> Integer
        Pipeline<String, Integer> lengthPipeline =
                upperPipeline.addTransformer(input -> input.length());

        int result = lengthPipeline.execute("hello");

        System.out.println("Pipeline result: " + result);


        // Exercise 4
        System.out.println("\nExercise 4:");

        List<String> words = Arrays.asList("Java", "Generics", "Wildcard");
        printList(words);

        List<Integer> numbers = Arrays.asList(10, 20, 30);
        System.out.println("Sum: " + sumNumbers(numbers));
    }


    // Exercise 4 methods

    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;

        for (Number number : numbers) {
            sum += number.doubleValue();
        }

        return sum;
    }
}