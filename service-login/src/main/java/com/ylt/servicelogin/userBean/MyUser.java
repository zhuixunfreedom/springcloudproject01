package com.ylt.servicelogin.userBean;


public class MyUser {

  private long id;
  private String username;
  private String password;
  private long permission;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getPermission() {
    return permission;
  }

  public void setPermission(long permission) {
    this.permission = permission;
  }

  public MyUser() {
  }

  public MyUser(long id, String username, String password,long permission) {

    this.id = id;
    this.username = username;
    this.password = password;
    this.permission = permission;
  }

  @Override
  public String toString() {
    return "MyUser{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", permission=" + permission +
            '}';
  }
}
