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
  
  public ConnectionHdl() throws Exception {
    this.srv_ch = ServerSocketChannel.open();
    this.srv_ch.configureBlocking(false);
    this.srv_ch.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
    this.sel = Selector.open();

    this.srv_ch.register(this.sel, SelectionKey.OP_ACCEPT);
    this.Loop();
  }
  
  private void ReadHdl(SelectionKey key) throws Exception {
    System.out.println("There is shit to read.");

    SocketChannel channel = (SocketChannel) key.channel();
    Game session = game_tracking.get(channel);
    
    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    readBuffer.clear();
    
    int read;
    try {
      read = channel.read(readBuffer);
    } catch (IOException e) {
      e.printStackTrace();
      key.cancel();
      channel.close();
      return;
    }

    if (read == -1) {
      System.out.println("Nothing was there to be read, closing connection");
      channel.close();
      key.cancel();
      return;
    }
    
    readBuffer.flip();
    byte[] data = new byte[1000];
    readBuffer.get(data, 0, read);
    System.out.println("Received: " + new String(data));
    
    String[] player_cmd = this.ParseCMD(new String(data));
    String cmd = player_cmd[0];
    System.out.println(cmd);
    System.out.println(player_cmd[1]); 

    if (cmd.equals("guess")) {
      /* Result of command. */

      /* Get sent argument. */
      String arg = player_cmd[1];

      int result = session.Guess(arg);
      System.out.println("Client guessing: " + arg);
      
      switch (result) {
        case -1: {
          /* Player lost, output full word and request new one. */
          session.RevealWord();
          session.NewWord();
          break;
        }
        case 0: {
          /* Player guessed incorrectly. */
          session.GetAttempts(); 
          break;
        }
        case 1: {
          /* Player guessed a letter correctly. Output unmasked letters. */
          break;
        }
        case 2: {
          /* Player won, output full word and request new one. */
          session.NewWord();
          break;
        }
      }
    } else if (cmd.equals("quit")) { 
      /* Client requesting a quit. */
      System.out.println("Socket closed!");
    } else {
      /* Invalid command. */
    }

  }

  private void Loop() throws Exception {
    /* Loop and accept sockets! */
    while (true) {
      System.out.println(sel.keys());
      sel.select();

      Iterator<SelectionKey> keys = sel.selectedKeys().iterator();

      while (keys.hasNext()) {
        
        SelectionKey key = keys.next();
        keys.remove();

        if (!key.isValid()) {
          continue;
        }
        
        /* If channel is requesting to Connect */
        if (key.isAcceptable()) {
          //ServerSocketChannel srv_ch = (ServerSocketChannel) key.channel();
          SocketChannel ch = srv_ch.accept();
          ch.configureBlocking(false);

          /* Req to write */
          ch.register(sel, SelectionKey.OP_READ);
          System.out.println("Connected client & writing MOTD."); 
          
          /* New game for channel. */
          game_tracking.put(ch, new Game());
        }

        /* If channel is requesting to write */
        if (key.isWritable()) {
           
          /* Key is writable? da fuq */
          /*SocketChannel ch = (SocketChannel) key.channel();
          byte[] data = game_tracking.get(ch);
*/
          /* Write to client. */
 /*         for (int i = 0; i < 4; i++) {
          ch.write(ByteBuffer.wrap(data));
            try {
              Thread.sleep(1000);
            } catch (Exception e) {}
          }
          key.interestOps(SelectionKey.OP_READ);
          */
        }

        if (key.isReadable()) {
          this.ReadHdl(key);
        }
      }
    }

  }


  /* Command parser. */
  private static String[] ParseCMD(String request) {
    String[] full_str = request.replace("\r", "").replace("\n", "").split(" ");

    return full_str;
  }
}
