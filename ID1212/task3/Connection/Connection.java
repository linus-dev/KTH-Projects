package Connection;
import java.net.*;
import java.io.*;
import java.util.*; 
import Logic.*;

public class Connection implements Runnable {
  private Socket socket = null;
  private Game session_ = new Game();

  public Connection(Socket connection) throws IOException {
    this.socket = connection; 
    this.socket.setSoTimeout(500);
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

  private static String[] ParseCMD(StringBuilder request) {
    String[] full_str = request.toString().replace("\r", "").replace("\n", "").split(" ");
    String cmd = full_str[0];
    String arg = full_str[1];

    return new String[]{cmd, arg};
  }
 
  private void Loop() throws IOException {
    /* Input buffer for the socket. */
    byte[] buffer_input = new byte[this.socket.getReceiveBufferSize()];
    int msg_size = 0;
    InputStream input_stream = this.socket.getInputStream();
    DataOutputStream output = new DataOutputStream(this.socket.getOutputStream());
    
    /* Client input. */
    while (true) {
      StringBuilder input_string = new StringBuilder();

      while (input_stream.available() > 0) {
        msg_size = input_stream.read(buffer_input);
        input_string.append(new String(buffer_input, 0, msg_size));
      }
      
      if ((input_string.toString().split(" ")).length == 2) {
        String[] player_cmd = this.ParseCMD(input_string);
        String cmd = player_cmd[0];
        String arg = player_cmd[1];
        
        if (cmd.equals("guess")) {
          /* Result of command. */
          int result = this.session_.Guess(arg);
          switch (result) {
            case -1: {
              /* Player lost, output full word and request new one. */
              output.writeInt(-1); 
              output.writeChars(new String(this.session_.GetWord()));
              this.session_.NewWord();
              output.writeChars(new String(this.session_.GetWord())); 
              break;
            }
            case 0: {
              /* Player guessed incorrectly. */
              output.writeInt(this.session_.GetAttempts()); 
              break;
            }
            case 1: {
              /* Player guessed a letter correctly. Output unmasked letters. */
              output.writeChars(new String(this.session_.GetWord())); 
              break;
            }
            case 2: {
              /* Player won, output full word and request new one. */
              output.writeInt(2);
              output.writeChars(new String(this.session_.GetWord()));
              this.session_.NewWord();
              output.writeChars(new String(this.session_.GetWord())); 
              break;
            }
          }
        }
      }
    }
    //this.socket.getOutputStream().write('\n');
    //this.socket.close();
  }
}
