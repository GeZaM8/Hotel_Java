/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import model.Booking;
/**
 *
 * @author HP
 */
public class BookingDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<Booking> listBooking;
    Booking booking;

    public BookingDAO() {
        this.con = Koneksi.getConnection();
    }

    public ArrayList<Booking> getListBooking() {
        listBooking = new ArrayList<>();
        String query = "SELECT * FROM bookingtbl";

        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("BNum"));
                booking.setRoomId(rs.getInt("Room")); // Ganti roomId -> Room
                booking.setCustomerId(rs.getInt("Customer")); // Ganti customerId -> Customer
                booking.setStatus(rs.getString("status"));
                booking.setDate(rs.getDate("BDate")); // Ganti date -> BDate
                booking.setDuration(rs.getInt("Duration"));
                booking.setCost(rs.getInt("Cost"));
                listBooking.add(booking);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources();
        }

        return listBooking;
    }

    public DefaultTableModel getModelAllBooking() {
        listBooking = getListBooking();
        Object[][] dataTable = new Object[listBooking.size()][7];

        for (int i = 0; i < listBooking.size(); i++) {
            dataTable[i][0] = listBooking.get(i).getId();
            dataTable[i][1] = listBooking.get(i).getRoomId();
            dataTable[i][2] = listBooking.get(i).getCustomerId();
            dataTable[i][3] = listBooking.get(i).getStatus();
            dataTable[i][4] = listBooking.get(i).getDuration();
            dataTable[i][5] = listBooking.get(i).getDate();
            dataTable[i][6] = listBooking.get(i).getCost();
        }

        String[] colNames = {"ID", "Room ID", "Customer ID", "Status", "Date", "Duration", "Cost"};
        return new DefaultTableModel(dataTable, colNames);
    }

    public void saveBooking(Booking booking) {
        String query;
        boolean isUpdate = false;

        try {
            // Periksa apakah booking dengan ID tersebut sudah ada
            ps = con.prepareStatement("SELECT * FROM booking WHERE BNum = ?");
            ps.setInt(1, booking.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                isUpdate = true;
                query = "UPDATE booking SET Room = ?, Customer = ?, status = ?, BDate = ?, Duration = ?, Cost = ? WHERE BNum = ?";
            } else {
                query = "INSERT INTO booking (Room, Customer, status, BDate, Duration, Cost) VALUES (?, ?, ?, ?, ?, ?)";
            }

            ps = con.prepareStatement(query);
            ps.setInt(1, booking.getRoomId());
            ps.setInt(2, booking.getCustomerId());
            ps.setString(3, booking.getStatus());
            ps.setDate(4, booking.getDate());
            ps.setInt(5, booking.getDuration());
            ps.setInt(6, booking.getCost());

            if (isUpdate) {
                ps.setInt(7, booking.getId());
            }

            ps.executeUpdate();
            System.out.println("Booking berhasil " + (isUpdate ? "diperbarui" : "disimpan"));

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    public void deleteBooking(int id) {
        String query = "DELETE FROM booking WHERE BNum = ?";
        
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Booking berhasil dihapus");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }
    
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            System.out.println("Error saat menutup resource: " + e.getMessage());
        }
    }
}