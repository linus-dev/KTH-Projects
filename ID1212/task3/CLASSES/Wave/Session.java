package Wave;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Wave.Chat;

public interface Session extends Remote {
  public boolean Upload(String name, int size) throws RemoteException;
}
