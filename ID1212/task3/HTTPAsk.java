import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.*; 
import java.nio.charset.StandardCharsets; 

public class HTTPAsk {
  public static List<String> words;
  public static void main( String[] args) throws IOException {
    words = Files.readAllLines(Paths.get("../words.txt"), StandardCharsets.UTF_8); 

    ServerSocket srv = new ServerSocket(args.length < 1 ? 8888 : Integer.parseInt(args[0]));
    /* Loop and accept sockets! */
    while (true) {
      Socket s = srv.accept();
      Thread tr = new Thread(new ConnectionHandle(s));
      tr.start();
    }
  }
}

class Response {
  private int status;
  private String server = "Matterhorn";
  private StringBuilder body = new StringBuilder();
  
  public Response (int initial_status) {
    this.status = initial_status;
  }
  
  public String GetResponse() {
    StringBuilder output = new StringBuilder();
    Formatter fmt = new Formatter(output);
    output.append(this.body.toString());
    
    return output.toString();
  }
  
  public void SetStatus(int status_code) {
    this.status = status_code;
  }
  
  public void AppendToBody(String data) {
    this.body.append(data);
  }
}

class ConnectionHandle implements Runnable {
  private Socket socket = null;
  private String word = "";
  private char[] guessed;
  
  ConnectionHandle(Socket connection) throws IOException {
    this.socket = connection; 
    this.socket.setSoTimeout(500);
    this.word = HTTPAsk.words.get((int)(Math.random() * HTTPAsk.words.size()));
    this.guessed = new char[this.word.length()];
    Arrays.fill(guessed, '_');
    System.out.println(word);
  }

  public void run() {
    try {
      this.Response();
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
  
  private void Response() throws IOException {
    /* Input buffer for the socket. */
    byte[] buffer_input = new byte[this.socket.getReceiveBufferSize()];
    int msg_size = 0;
    InputStream input_stream = this.socket.getInputStream();

    /* Client input. */
    while (true) {
      StringBuilder input_string = new StringBuilder();

      while (input_stream.available() > 0) {
        msg_size = input_stream.read(buffer_input);
        input_string.append(new String(buffer_input, 0, msg_size));
      }
      
      if (input_string.length() > 2) {
        String[] player_cmd = this.ParseCMD(input_string);
        String cmd = player_cmd[0];
        String arg = player_cmd[1];
        
        if (cmd.equals("guess")) {
          boolean found_letter = false;

          if (arg.length() == 1) {
            if (this.word.indexOf(arg) != -1) {
              
              /* Find all occurances. */
              int i = this.word.indexOf(arg);
              while(i != -1) {
                this.guessed[i] = arg.charAt(0);
                i = this.word.indexOf(arg, i+1);
              }

              System.out.println(this.guessed);
              System.out.println("ITS IN THE WORD!");
              found_letter = true;
            }
          } else {
            if (this.word.equals(arg)) {
              System.out.println("You guessed right...");
              found_letter = true;
            }
          }
          if (!found_letter) {
            System.out.println("Tough luck, sucker.");
          }
        }

        /* Start building the response. */
        Response response = new Response(200);
        response.AppendToBody("BORING\n");
        
        String output = response.GetResponse();
        this.socket.getOutputStream().write(output.getBytes(), 0, output.length());
        this.socket.getOutputStream().flush();
      }
    }
    //this.socket.getOutputStream().write('\n');
    //this.socket.close();
  }
}
