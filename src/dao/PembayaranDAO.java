/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.Booking;

/**
 *
 * @author Serge
 */
public class PembayaranDAO extends BookingDAO {
    @Override
    public ArrayList<Booking> getListBooking() {
        listBooking = new ArrayList<>();
        String query = "SELECT * FROM booking WHERE status = ?";

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "Pending");
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
}
