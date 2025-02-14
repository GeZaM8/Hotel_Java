/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import koneksi.Koneksi;
import model.User;

/**
 *
 * @author Serge
 */
public class UserDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    ArrayList<User> listUser;
    User user;
    
    public UserDAO() {
        this.con = Koneksi.getConnection();
    }
    
    public ArrayList<User> getAllUser() {
        try {
            listUser = new ArrayList<>();
            ps = con.prepareStatement("SELECT * FROM users", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));

                listUser.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listUser;
    }
    
    public User login(String username, String password) {
        user = null;
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, username);
            ps.setString(2, password);
            
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("userID"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return user;
    }
    
    
    public boolean register(User newUser) {
    boolean isSuccess = false;
    try {
        String query = "INSERT INTO users (username, password, role, email) VALUES (?, ?, ?, ?)";
        ps = con.prepareStatement(query);
        ps.setString(1, newUser.getUsername());
        ps.setString(2, newUser.getPassword());
        ps.setString(3, newUser.getRole());
        ps.setString(4, newUser.getEmail());
        
        int rowsInserted = ps.executeUpdate();
        if (rowsInserted > 0) {
            isSuccess = true;
        }
    } catch (SQLException e) {
        System.out.println("Error: " + e);
    }
    return isSuccess;
}}
