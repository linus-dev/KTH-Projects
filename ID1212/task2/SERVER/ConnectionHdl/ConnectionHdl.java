package ConnectionHdl;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import Logic.*;

public class ConnectionHdl {
  private Map<SocketChannel, Game> game_tracking = new HashMap<>();
  private ServerSocketChannel srv_ch;
  private Selector sel;
 
  /* Init nio. */
  public ConnectionHdl() throws Exception {

    this.srv_ch = ServerSocketChannel.open();
    this.srv_ch.configureBlocking(false);
    this.srv_ch.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
    this.sel = Selector.open();
    
    /* Accept reg to accpet connections. */
    this.srv_ch.register(this.sel, SelectionKey.OP_ACCEPT);

    /* Accept loop. */
    this.Loop();
  }
  
  /* Handle read operations. */
  private void ReadHdl(SelectionKey key) throws Exception {
    SocketChannel channel = (SocketChannel) key.channel();
    Game session = game_tracking.get(channel);
    
    ByteBuffer read_buffer = ByteBuffer.allocate(1024);

    /* Set buffer to write (put). */
    read_buffer.clear();
    
    int read;
    /* Read msg length */
    read = channel.read(read_buffer);
    
    if (read == -1) {
      System.out.println("Nothing was there to be read, closing connection");
      channel.close();
      key.cancel();
      return;
    }
     
    /* Flip buffer to read. */
    long data_length = 0;
    read_buffer.flip();
    /* Get message length */
    data_length = read_buffer.getLong(); 
    
    /* Get actual data. */
    byte[] data = new byte[(int)data_length];
    read_buffer.get(data, 0, (int)data_length);
    
    System.out.println("Received: " + new String(data));
    System.out.println("Length: " + new String(data).length());
    this.Response(channel, session, data);
  }

  private void Response(SocketChannel ch, Game session, byte[] data) throws Exception {
    long result = session.Process(new String(data));
    ByteBuffer response = ByteBuffer.allocate(8 + 1024);

    switch (result) {
      case -1: {
        /* Player lost, output full word and request new one. */
        break;
      }
      case 0: {
        /* Player guessed incorrectly. */
        break;
      }
      case 1: {
        /* Player guessed a letter correctly. Output unmasked letters. */
        break;
      }
      case 2: {
        /* Player won, output full word and request new one. */
        break;
      }
    }
  }

  private void Loop() throws Exception {
    /* Loop and accept sockets! */
    while (true) {
      this.sel.select();
      System.out.println("Selecting");
      Iterator<SelectionKey> keys = this.sel.selectedKeys().iterator();

      while (keys.hasNext()) {
        
        SelectionKey key = keys.next();
        keys.remove();

        if (!key.isValid()) {
          continue;
        }
        
        /* If channel is requesting to Connect */
        if (key.isAcceptable()) {
          SocketChannel ch = this.srv_ch.accept();
          ch.configureBlocking(false);

          /* Req to write */
          ch.register(this.sel, SelectionKey.OP_READ);
          System.out.println("Connected client."); 
          
          /* New game for channel. */
          game_tracking.put(ch, new Game());
        }

        if (key.isReadable()) {
          this.ReadHdl(key);
        }
      }
    }
  }
}
