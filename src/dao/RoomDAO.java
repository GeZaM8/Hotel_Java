/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import model.Room;

/**
 *
 * @author Serge
 */
public class RoomDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<Room> listRoom;
    Room room;
    
    public RoomDAO() {
        this.con = Koneksi.getConnection();
    }
    
    public ArrayList<Room> getListRoom() {
        try {
            listRoom = new ArrayList<>();
            ps = con.prepareStatement("SELECT * FROM rooms", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                room = new Room();
                room.setId(rs.getInt("RNum"));
                room.setName(rs.getString("RName"));
                room.setType(rs.getString("RType"));
                room.setStatus(rs.getString("RStatus"));
                room.setPrice(rs.getInt("Price"));

                listRoom.add(room);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listRoom;
    }
    
    public DefaultTableModel getModelAllRoom() {
        Object[][] dataTable = new Object[getListRoom().size()][5];
        String jenkel;
        listRoom = getListRoom();
        
        for (int i = 0; i < listRoom.size(); i++) {
            dataTable[i][0] = listRoom.get(i).getId();
            dataTable[i][1] = listRoom.get(i).getName();
            dataTable[i][2] = listRoom.get(i).getType();
            dataTable[i][3] = listRoom.get(i).getStatus();
            dataTable[i][4] = listRoom.get(i).getPrice();
        }
        String[] colNames = {"No Ruangan", "Nama Ruangan", "Jenis", "Status", "Harga"};
        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);
        
        return model;
    }
    
    public Room getRoomById(int id) {
        room = new Room();
        try {
            ps = con.prepareStatement("SELECT * FROM rooms WHERE RNum = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                room.setId(rs.getInt("RNum"));
                room.setName(rs.getString("RName"));
                room.setType(rs.getString("RType"));
                room.setStatus(rs.getString("RStatus"));
                room.setPrice(rs.getInt("Price"));
            }
            else {
                throw new SQLException("Guru tidak ditemukan");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            return room = null;
        }
        return room;
    }
    
    
    public void saveRoom(Room room) {
        String query = "SELECT * FROM rooms WHERE RNum = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, room.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                query = "UPDATE rooms SET RName = ?, RType = ?, RStatus = ?, Price = ? WHERE RNum = ?";
            } else {
                query = "INSERT INTO rooms (RName, RType, RStatus, Price, RNum) VALUES (?, ?, ?, ?, ?)";
            }
            ps = con.prepareStatement(query);
            ps.setString(1, room.getName());
            ps.setString(2, room.getType());
            ps.setString(3, room.getStatus());
            ps.setInt(4, room.getPrice());
            ps.setInt(5, room.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
     public void deleteRoom(int id) {
        String query = "DELETE FROM rooms WHERE RNum = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Room berhasil dihapus");
        }
        catch (SQLException e) {
            System.out.println("Room tidak ditemukan dengan Nomor = " + id);
        }
    }
}
