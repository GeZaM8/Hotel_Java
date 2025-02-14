/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Serge
 */
public class Koneksi {
     private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            String dbUrl = "jdbc:mysql://localhost:3306/" +
                    "hotel_java";
            String username = "root";
            String password = "";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(dbUrl, username, password);
                System.out.println("Koneksi sukses");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Koneksi gagal : " + e);
            }
            return connection;
        }
    }

    public static void main(String[] args) {
        getConnection();
    }
}
