package ConnectionSink;
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ConnectionSink implements Runnable {
  private Selector sel;
  private SocketChannel ch;

  public ConnectionSink(SocketChannel channel, Selector selecter) throws Exception {
    this.sel = selecter;
    this.ch = channel;
  }
  
  public void run() {
    try { 
      while (true) {
        this.sel.select();
        Iterator<SelectionKey> keys = this.sel.selectedKeys().iterator();
        
        while (keys.hasNext()) {
          SelectionKey key = keys.next();
          keys.remove();
          
          if (!key.isValid()) {
            continue;
          }
          
          /* If client is connecting to the server. */
          if (key.isConnectable()) {
            System.out.println("Connecting to server.");
            this.ConnectHdl(key);
          }
          
          /* If recv from server. */
          if (key.isReadable()) {
            System.out.println("Reading from server.");
            this.ReadHdl(key);
          }
        }
      }
    } catch (Exception E) {
      System.out.println(E);
    }
  }
  
  /* Handle reading */
  private boolean ReadHdl(SelectionKey key) throws Exception {
    SocketChannel ch = (SocketChannel) key.channel();
    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    readBuffer.clear();
    int length;

    try {
      length = ch.read(readBuffer);
    } catch (IOException e) {
      System.out.println("Reading problem, closing connection");
      try {
        key.cancel();
        ch.close();
      } catch (Exception er) {
        System.out.println(er);
      }
      return true;
    }
    if (length == -1) {
      System.out.println("Nothing was read from server");
      try {
        key.cancel();
        ch.close();
      } catch (Exception er) {
       System.out.println(er);
      }
      return true;
    }
    readBuffer.flip();
    byte[] buff = new byte[1024];
    readBuffer.get(buff, 0, length);
    System.out.println("Server said: " + new String(buff));

    return true; 
  }

  private boolean ConnectHdl(SelectionKey key) throws Exception {
    SocketChannel ch = (SocketChannel) key.channel();
    if (ch.isConnectionPending()) {
      ch.finishConnect();
    }
    System.out.println("Connected to server. Awaiting MOTD");
    ch.register(sel, SelectionKey.OP_READ);
    return true;
  }
}
