package edu.psu.se411.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

public class StackTest {

    private Stack<String> stringStack = new Stack<>();

    @Test
    public void testPushPushPop() {
        stringStack.push("Z");
        stringStack.push("A");

        assertEquals("A", stringStack.pop());
    }
    @Test
    public void pop_empty_stack() {
        NoSuchElementException thrown =
                assertThrows(NoSuchElementException.class,
                        () -> stringStack.pop(),
                        "Expected pop from empty Stack to throw, but it didn't");

        assertEquals("Stack is empty, cannot pop", thrown.getMessage());
    }
    @Test
    public void pushed_elements_are_popped_in_reverse_order() {
        stringStack.push("Z");
        stringStack.push("A");
        stringStack.push("B");

        assertEquals("B", stringStack.pop());
        assertEquals("A", stringStack.pop());
        assertEquals("Z", stringStack.pop());
    }
}