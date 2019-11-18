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
    ByteBuffer read_buffer = ByteBuffer.allocate(1024);
    read_buffer.clear();
    int length;
    int read;

    read = ch.read(read_buffer);

    if (read == -1) {
      System.out.println("Nothing was there to be read, closing connection");
      ch.close();
      key.cancel();
      return false;
    }

    /* Flip buffer to read. */
    long data_length = 0;
    read_buffer.flip();
    /* Get message length */
    data_length = read_buffer.getLong(); 
    
    /* Get actual data. */
    byte[] data = new byte[(int)data_length];
    read_buffer.get(data, 0, (int)data_length);
    
    String[] res = new String(data).split("::");

    switch (Integer.parseInt(res[0])) {
      case -10: {
        System.out.println("Invalid command.");
        break;
      }
      case -1: { 
        System.out.println("You lost, nerd.");
        String word = res[1];
        System.out.println(String.format("The word was %s.", word));
        System.out.println("-New Word-");
        System.out.println(res[2]);
        break;
      }
      case 0: {
        int attempts = Integer.parseInt(res[1]); 
        System.out.println("You guessed incorrectly.");
        System.out.println(String.format("You have %d attempts left", attempts));
        break;
      }
      case 1: {
        String word = res[1];  
        System.out.println(word);
        break; 
      }
      case 2: {
        System.out.println("You won!");
        
        String word = res[1];
        System.out.println(String.format("The word was %s.", word));
        System.out.println("-New Word-");
        word = res[2]; 
        System.out.println(word);
        break;
      }
    }
    
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
