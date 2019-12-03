package FS;

import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.util.*;
import java.io.*;

class DBHdl {
  private MysqlDataSource data_source;
  private Connection conn;
  
  private final String GET_FILE_STR = "SELECT * FROM FS.Files WHERE FileName = ?";
  private final String ADD_FILE_STR = "INSERT INTO FS.Files VALUES (null, ?, ?, ?)";
  
  private final String ADD_USER_STR = "INSERT INTO FS.users VALUES (null, ?, ?)";
  private final String LOGIN_STR = "SELECT * FROM FS.users WHERE username = ? AND password = ?";

  private PreparedStatement get_file_stmt = null;
  private PreparedStatement add_file_stmt = null;
  private PreparedStatement login_stmt = null;
  private PreparedStatement user_stmt = null;

  DBHdl() {
    try {
      this.data_source = new MysqlDataSource();
      this.data_source.setUser("FS");
      this.data_source.setPassword("");
      this.data_source.setServerName("localhost");
      this.conn = data_source.getConnection();

      /* Prep statements */
      this.get_file_stmt = conn.prepareStatement(GET_FILE_STR);
      this.add_file_stmt = conn.prepareStatement(ADD_FILE_STR);
      this.user_stmt = conn.prepareStatement(ADD_USER_STR);
      this.login_stmt = conn.prepareStatement(LOGIN_STR);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public boolean AddUser(String user, String password) throws Exception {
    this.user_stmt.setString(1, user);
    this.user_stmt.setString(2, password);
    return (this.user_stmt.executeUpdate() > 0);
  }

  public boolean CheckAuth(String user, String password) throws Exception {
    this.login_stmt.setString(1, user);
    this.login_stmt.setString(2, password);
    return this.login_stmt.executeQuery().next();
  }

  public boolean AddFile(String name, int size, String owner) throws Exception {
    this.add_file_stmt.setString(1, name);
    this.add_file_stmt.setInt(2, size);
    this.add_file_stmt.setString(3, owner);
    return (this.add_file_stmt.executeUpdate() > 0);
  }

  public ResultSet GetFile(String name) throws Exception {
    this.get_file_stmt.setString(1, name);
    ResultSet rs = this.get_file_stmt.executeQuery();
    return rs;
  }
}

public class FileSystem {
  private static FileSystem FS;
  private DBHdl db;
  static {
    try {
      FS = new FileSystem();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private FileSystem() {
    this.db = new DBHdl();
  }
  
  public synchronized boolean Register(String user, String password) throws Exception {
    return this.db.AddUser(user, password);
  }

  public synchronized boolean Login(String user, String password) throws Exception{
    return this.db.CheckAuth(user, password);
  }

  public synchronized boolean AddFile(String name, int size, String user) throws Exception {
    return this.db.AddFile(name, size, user);
  }

  public synchronized String GetFile(String name) throws Exception {
    ResultSet rs = this.db.GetFile("whack");
    if (rs.next()) {
      return rs.getString(1) + ':' + rs.getInt(2);
    }
    return "";
  }

  public static FileSystem GetFS() {
    return FS;
  }
}
