import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import ConnectionSink.*;
import Wave.*;

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
      int written = 0;
      while (true) {
        cmd = in.nextLine();
        Wave.Message wave_msg = new Wave.Message(cmd.getBytes());
        /* TODO: If write returns 0 then ask for OP_WRITE. */
        System.out.println("Writing to server...");
        written = ch.write(wave_msg.GetMessage());
        System.out.println(wave_msg.GetMessage());
      }
    } catch (Exception e) {
      System.out.println(e); 
    }

  }
}
