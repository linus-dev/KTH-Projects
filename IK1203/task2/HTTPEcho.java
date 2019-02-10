import java.net.*;
import java.io.*;

public class HTTPEcho {
  public static void main( String[] args) throws IOException {
    ServerSocket srv = new ServerSocket(args.length < 1 ? 8888 : Integer.parseInt(args[0]));
    Socket s = srv.accept();
    /* Loop and accept sockets! */
    while (true) {
      Thread tr = new Thread(new ConnectionHandle(s));
      tr.start();
      s = srv.accept();
    }
  }
}

class ConnectionHandle implements Runnable {
  private Socket socket = null;

  ConnectionHandle(Socket connection) throws IOException {
    this.socket = connection; 
    this.socket.setSoTimeout(500);
  }

  public void run(){
    try {
      this.Echo();
    } catch (Exception E) {
      /* WELL I GUESS WE JUST CRASH AND BURN. */
    }
  }
  
  private static StringBuilder BuildResponse(StringBuilder input) {
    StringBuilder output = new StringBuilder();
    
    output.append("HTTP/1.1 200 OK\r\n");
    output.append("Content-Length: " + input.length());
    output.append("\r\n");
    output.append("Server: Matterhorn\r\n");
    output.append("Content-Type: text/plain\r\n");
    output.append("Connection: Closed\r\n");
    output.append("\r\n");
    output.append(input.toString());
    return output;
  }
  
  private void Echo() throws IOException {
    /* Input buffer for the socket. */
    byte[] buffer_input = new byte[this.socket.getReceiveBufferSize()];
    int msg_size = 0;

    /* Client input. */
    StringBuilder input_string = new StringBuilder();
    InputStream input_stream = this.socket.getInputStream();
    int buffer_reads = 0;
        
    while (msg_size != -1 && buffer_reads < 10) {
      try {
        /* Read, if server refuses to close catch timeout exception. */
        msg_size = input_stream.read(buffer_input);
        input_string.append(new String(buffer_input, 0, msg_size)); 
        buffer_reads++;
      } catch (Exception e) {
        /* Timed out, end read loop. */
        msg_size = -1;
      }
    }
    
    StringBuilder output = BuildResponse(input_string);
    this.socket.getOutputStream().write(output.toString().getBytes(), 0, output.length());
    this.socket.getOutputStream().write('\n');
    this.socket.close();
  }
}
