package Wave;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface Chat extends Remote {
  public void Send(String msg) throws RemoteException;
}

