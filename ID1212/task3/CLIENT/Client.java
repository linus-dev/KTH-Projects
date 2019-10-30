import java.net.*;
import java.io.*;
import java.util.*;
class Recv implements Runnable {
  DataInputStream input;

  public Recv(Socket socket) {
    try {
      this.input = new DataInputStream(socket.getInputStream());
    
    } catch (Exception e) {

    }
  }
  public void run() {
    try {
    System.out.println(input.readUTF()); 
    System.out.println(input.readUTF()); 
    while (true) {
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
public class Client {
  public static void main(String[] args) {
    try {
    Socket socket = new Socket();
    InetAddress srv_address = InetAddress.getByName("127.0.0.1");
    SocketAddress socket_address = new InetSocketAddress(srv_address, 8888);

    /* BOOM, CONNECT! */
    socket.connect(socket_address);
    
    Scanner in = new Scanner(System.in);   
    
    Thread recv = new Thread(new Recv(socket));
    recv.start();

    /* If client is connected, start server request. */
    if (socket != null && socket.isConnected()) {
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());
      String cmd = "";
      
      while (true) {
        cmd = in.nextLine();
        output.writeUTF(cmd);
        output.flush();
      }
    }

    } catch (Exception e) {

    }

  }
}
