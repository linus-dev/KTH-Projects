import java.net.*;
import java.io.*;
import java.util.*;
import Recv.*;

public class Client {
  public static void main(String[] args) {
    try {
      Socket socket = new Socket();
      InetAddress srv_address = InetAddress.getByName("127.0.0.1");
      SocketAddress socket_address = new InetSocketAddress(srv_address, 8888);

      /* BOOM, CONNECT! */
      socket.connect(socket_address);
      
      Scanner in = new Scanner(System.in);   
      
      /* Receiving thread. */
      Thread recv = new Thread(new Recv(socket));
      recv.start();

      /* If client is connected, start server request. */
      if (socket != null && socket.isConnected()) {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        String cmd = "";
        
        while (!socket.isClosed()) {
          cmd = in.nextLine();
          if (cmd.equals("quit")) {
            socket.close();
          } else {
            output.writeUTF(cmd);
            output.flush();
          }
        }
      }
    } catch (Exception e) {
      /* I can't be bothered. */
    }

  }
}
