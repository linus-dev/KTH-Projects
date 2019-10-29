import java.net.*;
import java.io.*;
import java.util.*; 
import Connection.*;

public class HTTPAsk {
  public static void main( String[] args) throws IOException {

    ServerSocket srv = new ServerSocket(args.length < 1 ? 8888 : Integer.parseInt(args[0]));
    /* Loop and accept sockets! */
    while (true) {
      Socket s = srv.accept();
      Thread tr = new Thread(new Connection(s));
      tr.start();
    }
  }
}

