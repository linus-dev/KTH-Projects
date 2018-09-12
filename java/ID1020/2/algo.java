/*
 * This implements a stack (linked list) and uses it to read one character
 * at a time to then pop it off the stack in reverse order.
*/
import java.util.*;
import edu.princeton.cs.algs4.StdIn;

class algo {
  public static void main(String[] args) {
    Stack<Character> st = new Stack<Character>();
    ReadC(st);
  }

  private static void ReadC(Stack<Character> stack) {
    Character input = StdIn.readChar();
    if (input != '\n') {
      stack.push(input);
      ReadC(stack);
      System.out.print(stack.pop());
    }
  }
}

