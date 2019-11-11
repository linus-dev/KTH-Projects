//import java.net.*;
//import java.io.*;
//import java.nio.*;
//import java.util.*; 
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
import ConnectionHdl.*;

public class HTTPAsk {
  public static void main( String[] args) throws IOException {
    try {
      ConnectionHdl srv = new ConnectionHdl();
    } catch (Exception e) {
      System.out.println(e);
    }
    //ServerSocket srv = new ServerSocket(args.length < 1 ? 8888 : Integer.parseInt(args[0]));
    
  }

}

