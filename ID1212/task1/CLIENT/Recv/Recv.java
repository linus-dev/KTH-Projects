package Recv;
import java.net.*;
import java.io.*;
import java.util.*;

public class Recv implements Runnable {
  Socket socket_;
  DataInputStream input;

  public Recv(Socket socket) {
    try {
      this.input = new DataInputStream(socket.getInputStream());
      this.socket_ = socket; 
    } catch (Exception e) {

    }
  }
  public void run() {
    try {
      /* MOTD */
      System.out.println(input.readUTF()); 
      System.out.println(input.readUTF());

      while (!this.socket_.isClosed()) {
        switch (input.readInt()) {
          case -10: {
            System.out.println("Invalid command.");
            break;
          }
          case -1: { 
            System.out.println("You lost, nerd.");
            String word = input.readUTF();
            System.out.println(String.format("The word was %s.", word));
            System.out.println("-New Word-");
            
            word = input.readUTF();
            System.out.println(word);
            break;
          }
          case 0: {
            int attempts = input.readInt(); 
            System.out.println("You guessed incorrectly.");
            System.out.println(String.format("You have %d attempts left", attempts));
            break;
          }
          case 1: {
            String word = input.readUTF();  
            System.out.println(word);
            break; 
          }
          case 2: {
            System.out.println("You won!");
            
            String word = input.readUTF();
            System.out.println(String.format("The word was %s.", word));
            System.out.println("-New Word-");
            
            word = input.readUTF();
            System.out.println(word);
            break;
          }
        }
      }
    } catch (Exception e) {}
  }
}
