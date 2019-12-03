package SERVER;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Wave.RMIInterface;
import Wave.Session;
import Wave.Chat;

import FS.FileSystem;
import java.util.*;

class USession extends UnicastRemoteObject implements Wave.Session {
  private String username;

  /* For user messaging... or something... */
  private static Map<String, Chat> participants = Collections.synchronizedMap(new HashMap<>());

  protected USession(String user, Chat ch) throws RemoteException {
    super();
    this.username = user;
    /* Add our new session. */
    USession.participants.put(user, ch);
  }
  @Override
  public String GetFile(String name) throws RemoteException {
    FileSystem fs = FileSystem.GetFS();
    try {
      return fs.AddFile(name, size, this.username);
    } catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }

  @Override
  public boolean Upload(String name, int size) throws RemoteException {
    FileSystem fs = FileSystem.GetFS();
    try {
      return fs.AddFile(name, size, this.username);
    } catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }
}

public class Server extends UnicastRemoteObject implements Wave.RMIInterface{
  protected Server() throws RemoteException {
    super();
  }

  @Override
  public boolean Register(String user, String pass) throws RemoteException {
    /* FS register */
    FileSystem fs = FileSystem.GetFS();
    try {
      return fs.Register(user, pass);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Session Login(String user, String pass, Chat ch) throws RemoteException {
    /* If user matches */
    FileSystem fs = FileSystem.GetFS();
    try {
      return (fs.Login(user, pass) ? new USession(user, ch) : null);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public static void main(String[] args){
    try {
      Naming.rebind("//localhost/MyServer", new Server());            
      System.err.println("Server ready");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
