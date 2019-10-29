import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  public static void main(String[] args) {
    try {
    Socket socket = new Socket();
    InetAddress srv_address = InetAddress.getByName("127.0.0.1");
    SocketAddress socket_address = new InetSocketAddress(srv_address, 8888);
    Scanner in = new Scanner(System.in);   
    
    /* If the server does not close the connection, wait 1.5 seconds then read. */
    socket.setSoTimeout(1500);
    /* BOOM, CONNECT! */
    socket.connect(socket_address);
    String cmd = "";

    /* If client is connected, start server request. */
    if (socket != null && socket.isConnected()) {
      /* Message length */
      int msg_size = 0;
      /* Input buffer for the socket. */
      byte[] buffer_input = new byte[socket.getReceiveBufferSize()];
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());
      
      cmd = in.nextLine();
      output.writeUTF(cmd);
      output.flush();
      System.out.println("Wrote" + cmd);
      /* Server input. */
      StringBuilder input_string = new StringBuilder();
      InputStream input_stream = socket.getInputStream();
      while (true) {
        while (input_stream.available() > 0) {
          msg_size = input_stream.read(buffer_input);
          input_string.append(new String(buffer_input, 0, msg_size));
        }
        if (input_string.length() > 0) {
          System.out.println(input_string.toString());
        }
      }
    }

    } catch (Exception e) {

    }

  }
}
