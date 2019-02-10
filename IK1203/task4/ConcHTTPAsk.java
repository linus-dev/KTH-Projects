import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.Formatter;
import java.util.HashMap;
import tcpclient.TCPClient;

public class ConcHTTPAsk {
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

  private static Map<String, String> ParseQuery(StringBuilder request) {
    String[] req = request.toString().split("\n");
    String host_string = req[1].split(" ")[1];
    String query_string = req[0].split(" ")[1];
    host_string = host_string.replaceAll("\\r", "");
    host_string = host_string.replaceAll("\\n", "");

    StringBuilder url_string = new StringBuilder();
    url_string.append("http://");
    url_string.append(host_string);
    url_string.append(query_string);
    String[] queries = new String[0];

    /* If URL is malformed, get outta here! */
    try {
      URL url = new URL(url_string.toString());
      if (url != null && url.getQuery() != null) {
        queries = url.getQuery().split("&");
      }
    } catch (Exception e) {
      System.out.println(e.toString());
    }

    /* Hashmap of key and question. */
    Map<String, String> result = new HashMap<>();

    /* Map each query key to the question. */
    for (int i = 0; i < queries.length; i++) {
      String[] query = queries[i].split("=");
      result.put(query[0], query[1]);
    }
    return result;
  }
  
  private static StringBuilder BuildResponse(int status_code, StringBuilder input) {
    StringBuilder output = new StringBuilder();
    Formatter fmt = new Formatter(output);
    
    fmt.format("HTTP/1.1 %d %s\r\n", status_code, (status_code == 200 ? "OK" : "ERROR"));
    fmt.format("Content-Length: %d\r\n", input.length());
    output.append("Server: Matterhorn\r\n");
    output.append("Content-Type: text/plain\r\n");
    output.append("Connection: Closed\r\n");
    output.append("\r\n");
    output.append(input.toString());
    return output;
  }
  
  private void Response() throws IOException {
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

    
    /* Start building the response. */
    StringBuilder response = new StringBuilder(); 
    Map<String, String> queries = this.ParseQuery(input_string);
    int status_code = 200;
    
    if (queries.get("host") != null && queries.get("port") != null) {
      try {
        response.append(TCPClient.AskServer(queries.get("host"),
                                            Integer.parseInt(queries.get("port")),
                                            queries.get("string")));
      } catch (Exception ask_error) {
        response.append("400 BAD REQUEST");
        status_code = 400;
      }
    } else {
      response.append("400 BAD REQUEST");
      status_code = 400;
    }
    
    response = BuildResponse(status_code, response);
    this.socket.getOutputStream().write(response.toString().getBytes(), 0, response.length());
    this.socket.getOutputStream().write('\n');
    this.socket.close();
  }
}
