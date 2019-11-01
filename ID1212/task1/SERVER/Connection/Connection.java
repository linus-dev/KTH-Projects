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
      
      String[] player_cmd = this.ParseCMD(input_string);
      String cmd = player_cmd[0];
      System.out.println(cmd);
      
      if (cmd.equals("guess")) {
        /* Result of command. */

        /* Get sent argument. */
        String arg = player_cmd[1];

        int result = this.session_.Guess(arg);
        System.out.println("Client guessing: " + arg);
        
        switch (result) {
          case -1: {
            /* Player lost, output full word and request new one. */
            output.writeInt(-1); 
            output.writeUTF(this.session_.RevealWord());
            this.session_.NewWord();
            output.writeUTF(new String(this.session_.GetWord())); 
            break;
          }
          case 0: {
            /* Player guessed incorrectly. */
            output.writeInt(0); 
            output.writeInt(this.session_.GetAttempts()); 
            break;
          }
          case 1: {
            output.writeInt(1); 
            /* Player guessed a letter correctly. Output unmasked letters. */
            output.writeUTF(new String(this.session_.GetWord())); 
            break;
          }
          case 2: {
            /* Player won, output full word and request new one. */
            output.writeInt(2);
            output.writeUTF(new String(this.session_.GetWord()));
            this.session_.NewWord();
            output.writeUTF(new String(this.session_.GetWord())); 
            break;
          }
        }
      } else if (cmd.equals("quit")) { 
        /* Client requesting a quit. */
        this.socket.close();
        System.out.println("Socket closed!");
      } else {
        /* Invalid command. */
        output.writeInt(-10);
      }
    }
  }
}
