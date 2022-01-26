package org.mktab.cinama.model;

public class User {

    private int id;
    private String userName;
    private String password;
    private Role role;

    public User(int id, String userName, String password, String role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        if(role.equals("ADMIN"))
            this.role =Role.ADMIN;
        else if(role.equals("CUSTOMER"))
            this.role =Role.CUSTOMER;
        else
            this.role =Role.CINEMA;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }
}
