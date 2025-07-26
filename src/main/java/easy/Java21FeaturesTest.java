package easy;

import java.util.List;

public class Java21FeaturesTest {

    public static void main(String[] args) throws InterruptedException {
        // Test 1: String Templates (Preview feature)
        String name = "World";
        System.out.println(STR."Hello \{name}!");

        // Test 2: Record Patterns
        record Point(int x, int y) {}
        Object obj = new Point(1, 2);
        if (obj instanceof Point(int x, int y)) {
            System.out.println(STR."Coordinates: (\{x}, \{y})");
        }

        // Test 3: Pattern Matching in switch
        Object test = "Hello";
        String result = switch(test) {
            case String s when s.length() > 3 -> "Long String: " + s;  // Added when clause to differentiate cases
            case String s -> "Short String: " + s;
            case Integer i -> "Number: " + i;
            default -> "Something else";
        };
        System.out.println(result);

        // Test 4: Virtual Threads
        Thread.startVirtualThread(() -> {
            System.out.println("Running in a virtual thread: " + Thread.currentThread());
        }).join();  // Added join() to wait for the virtual thread

        // Test 5: Sequenced Collection
        var list = List.of(1, 2, 3, 4, 5);
        System.out.println("First element: " + list.getFirst());
        System.out.println("Last element: " + list.getLast());
    }
}