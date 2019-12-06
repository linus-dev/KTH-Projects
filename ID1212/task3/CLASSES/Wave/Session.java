package Wave;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Wave.Chat;
import java.util.*;

public interface Session extends Remote {
  public boolean Upload(String name, int size) throws RemoteException;
  public Map<String, String> GetFile(String name) throws RemoteException;
}
