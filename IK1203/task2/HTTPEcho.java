import java.net.*;
import java.io.*;
import java.util.Formatter;

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

/* Class for HTTP response.  TODO: Make pretty. */
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
    
    fmt.format("HTTP/1.1 %d %s\r\n", this.status, (this.status == 200 ? "OK" : "ERROR"));
    fmt.format("Content-Length: %d\r\n", this.body.length());
    fmt.format("Server: %s\r\n", this.server);
    output.append("Content-Type: text/plain\r\n");
    output.append("Connection: Closed\r\n");
    output.append("\r\n");
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

/* Thread class for each connection. */
class ConnectionHandle implements Runnable {
  private Socket socket = null;

  ConnectionHandle(Socket connection) throws IOException {
    this.socket = connection; 
    this.socket.setSoTimeout(500);
  }

  public void run(){
    try {
      this.Response();
    } catch (Exception E) {
      /* WELL I GUESS WE JUST CRASH AND BURN. */
    }
  }

  private void Response() throws IOException {
    /* Input buffer for the socket. */
    byte[] buffer_input = new byte[this.socket.getReceiveBufferSize()];
    int msg_size = 0;

    /* Client input. */
    Response response = new Response(200);
    InputStream input_stream = this.socket.getInputStream();
    int buffer_reads = 0;
        
    while (msg_size != -1 && buffer_reads < 10) {
      try {
        /* Read, if server refuses to close catch timeout exception. */
        msg_size = input_stream.read(buffer_input);
        response.AppendToBody(new String(buffer_input, 0, msg_size)); 
        buffer_reads++;
      } catch (Exception e) {
        /* Timed out, end read loop. */
        msg_size = -1;
      }
    }
    
    String output = response.GetResponse();
    this.socket.getOutputStream().write(output.getBytes(), 0, output.length());
    this.socket.getOutputStream().write('\n');
    this.socket.close();
  }
}
