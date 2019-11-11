import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import ConnectionSink.*;

public class Client {
  public static void main(String[] args) {
    try {
      SocketChannel ch = SocketChannel.open();
      Selector sel = Selector.open();
      
      InetAddress srv_address = InetAddress.getByName("127.0.0.1");
      SocketAddress inet_address = new InetSocketAddress(srv_address, 8888);
      
      ch.configureBlocking(false);
      ch.register(sel, SelectionKey.OP_CONNECT);
      ch.connect(inet_address);
      
      /* 
       * Thread for receiving (sink).
       * Governed by Selector.
       */
      Thread tr = new Thread(new ConnectionSink(ch, sel));
      tr.start();

      while(!ch.isConnected()) {
        System.out.println("Awaiting connection");
        Thread.sleep(1000);
      }

      Scanner in = new Scanner(System.in);   
      String cmd = "";

      while (true) {
        cmd = in.nextLine();
        /* TODO: If write returns 0 then ask for OP_WRITE. */
        ch.write(ByteBuffer.wrap(cmd.getBytes()));
      }
    } catch (Exception e) {
      System.out.println(e); 
    }

  }
}
