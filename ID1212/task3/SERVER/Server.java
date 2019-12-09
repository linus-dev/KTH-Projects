package SERVER;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Wave.RMIInterface;
import Wave.Session;
import Wave.Chat;

import FS.FileSystem;
import java.util.*;

/* User Session */
class USession extends UnicastRemoteObject implements Wave.Session {
  private String username;

  /* For user messaging... or something... */
  private static Map<String, Chat> online_users = Collections.synchronizedMap(new HashMap<>());
  private static FileSystem fs = FileSystem.GetFS();

  protected USession(String user, Chat ch) throws RemoteException {
    super();
    this.username = user;
    /* Add our new participant. */
    USession.online_users.put(user, ch);
  }

  @Override
  public Map<String, String> GetFile(String name) throws RemoteException {
    try {
      Map<String, String> ret = fs.GetFile(name);
      if (ret != null) {
        String owner = ret.get("owner");
        if (USession.online_users.containsKey(owner) &&
            !owner.equals(this.username)) {
          USession.online_users.get(owner).Send(this.username + " accessed " + ret.get("filename"));
        }
        return ret; 
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  @Override
  public boolean Upload(String name, int size) throws RemoteException {
    try {
      return fs.AddFile(name, size, this.username);
    } catch (Exception e) {
      System.out.println(e);
    }
    return false;
  }

  @Override
  public boolean LogOut() throws RemoteException {
    online_users.remove(this.username);
    return true;
  }
}

/* Auth handler */
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
