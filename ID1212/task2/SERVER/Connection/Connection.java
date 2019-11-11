package Connection;
import java.net.*;
import java.io.*;
import java.util.*; 
import Logic.*;

public class Connection implements Runnable {
  private Socket socket = null;
  private Game session_ = new Game();
  private static final String WELCOME = "Welcom to Hangman, to play write guess # or guess word";
  public Connection(Socket connection) throws IOException {
    this.socket = connection; 
  }
   
  public void run() {
    try {
      this.Loop();
    } catch (Exception e) {
      /* WELL I GUESS WE JUST CRASH AND BURN. */
      try {
        this.socket.close();
      } catch (Exception socket_exception) {
      }
    }
  }

  private static String[] ParseCMD(String request) {
    String[] full_str = request.replace("\r", "").replace("\n", "").split(" ");

    return full_str;
  }
 
  private void Loop() throws IOException {
    DataInputStream input = new DataInputStream(this.socket.getInputStream());
    DataOutputStream output = new DataOutputStream(this.socket.getOutputStream());
    
    output.writeUTF(WELCOME);
    output.writeUTF(new String(this.session_.GetWord()));

    /* Client input. */
    while (!socket.isClosed() && socket.isConnected()) {
      String input_string;
       
      input_string = input.readUTF(); 
      
    }
  }
}
