package Wave;

import java.rmi.Remote;
import java.rmi.RemoteException;
import Wave.Chat;
public interface RMIInterface extends Remote {
  public boolean Register(String user, String pass) throws RemoteException;
  public Session Login(String name, String pass, Chat ch) throws RemoteException;
}

