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
    Response response = new Response(200);
    Map<String, String> queries = this.ParseQuery(input_string);
    
    if (queries.get("host") != null && queries.get("port") != null) {
      try {
        response.AppendToBody(TCPClient.AskServer(queries.get("host"),
                                            Integer.parseInt(queries.get("port")),
                                            queries.get("string")));
      } catch (Exception ask_error) {
        response.AppendToBody("400 BAD REQUEST");
        response.SetStatus(400);
      }
    } else {
      response.AppendToBody("400 BAD REQUEST");
      response.SetStatus(400);
    }
    
    String output = response.GetResponse();
    this.socket.getOutputStream().write(output.getBytes(), 0, output.length());
    this.socket.getOutputStream().write('\n');
    this.socket.close();
  }
}
