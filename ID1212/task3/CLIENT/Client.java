package CLIENT;

import java.io.*;
import java.util.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import Wave.RMIInterface;
import Wave.Session;

/* Remote object for server to send US messages. */
class Chat extends UnicastRemoteObject implements Wave.Chat {
  protected Chat() throws RemoteException {
    super();
  }
  @Override
  public void Send(String msg) throws RemoteException {
    System.out.println(msg);
  }
}

public class Client {

	private static RMIInterface look_up;
  
  private static void PrintHelp() {
    System.out.println("1 Login");
    System.out.println("2 Register");
    System.out.println("3 Get File");
    System.out.println("4 Upload File");
    System.out.println("5 Logout");
    System.out.println("q Quit");
  }

  public static void main(String[] args) 
		throws MalformedURLException, RemoteException, NotBoundException {
		
		look_up = (RMIInterface) Naming.lookup("//localhost/MyServer");
    Scanner in = new Scanner(System.in);   
    String input = "";
    Client.PrintHelp();
    
    /* If server != null means we are logged in. */
	  Session server = null;

    while ((input = in.nextLine()) != "q") {
      if (input.equals("help")) {
        Client.PrintHelp();
      }
      
      /* Login */
      if (input.equals("1") && server == null) {
        System.out.println("--- Login ---");

        System.out.print("Username: ");
        String user = in.nextLine();
        System.out.print("Password: ");
        String pass = in.nextLine();
	      
        server = look_up.Login(user, pass, new Chat());
        if (server != null) {
          System.out.println("Login success!");  
        }
      }
     
      /* Register */
      if (input.equals("2") && server == null) {
        System.out.println("--- Register ---");
        System.out.print("Username: ");
        String user = in.nextLine();
        System.out.print("Password: ");
        String pass = in.nextLine();

        if (look_up.Register(user, pass)) {
          System.out.println("Register success!");  
          server = look_up.Login(user, pass, new Chat());
        } else {
          System.out.println("Register failed, user already exists!");
        }

      }

      /* Get File */
      if (input.equals("3") && server != null) {
        System.out.println("--- Get File ---");
        System.out.print("Filename: ");
        String filename = in.nextLine();
        Map<String, String> rs = server.GetFile(filename);
        if (rs != null) {
          System.out.println("--- File Info ---"); 
          System.out.print("Filename: "); 
          System.out.println(rs.get("filename")); 
          System.out.print("Size: "); 
          System.out.println(rs.get("size")); 
          System.out.print("Owner: "); 
          System.out.println(rs.get("owner")); 
        } else {
          System.out.println("Invalid filename.");
        }
      }
      /* Upload File */
      if (input.equals("4") && server != null) {
        System.out.println("--- Upload File ---");
        System.out.print("Filename: ");
        String filename = in.nextLine();
        server.Upload(filename, (int)(Math.random() * 1000.0));
      }
      
      /* Upload File */
      if (input.equals("5") && server != null) {
        System.out.println("Logging out");
        server.LogOut();
        server = null;
      }
    }
  }

}
