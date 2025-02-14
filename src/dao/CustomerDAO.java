package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import model.Customer;

/**
 * Data Access Object for Customer
 * @author Serge
 */
public class CustomerDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<Customer> listCustomer;
    Customer customer;

    public CustomerDAO() {
        this.con = Koneksi.getConnection();
    }

    public ArrayList<Customer> getListCustomer() {
        try {
            listCustomer = new ArrayList<>();
            ps = con.prepareStatement("SELECT * FROM customers", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("CustNum"));
                customer.setName(rs.getString("CustName"));
                customer.setPhone(rs.getString("CustPhone"));
                customer.setGender(rs.getString("CustGen"));
                customer.setAddress(rs.getString("CustAdd"));
                customer.setDob(rs.getDate("CustDob"));

                listCustomer.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listCustomer;
    }

    public DefaultTableModel getModelAllCustomer() {
        Object[][] dataTable = new Object[getListCustomer().size()][6];
        listCustomer = getListCustomer();

        for (int i = 0; i < listCustomer.size(); i++) {
            dataTable[i][0] = listCustomer.get(i).getId();
            dataTable[i][1] = listCustomer.get(i).getName();
            dataTable[i][2] = listCustomer.get(i).getPhone();
            dataTable[i][3] = listCustomer.get(i).getGender();
            dataTable[i][4] = listCustomer.get(i).getAddress();
            dataTable[i][5] = listCustomer.get(i).getDob();
        }
        String[] colNames = {"Nomor", "Nama", "Phone", "Jenis Kelamin", "Tempat TInggal", "Tanggal Lahir"};
        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);

        return model;
    }

    public Customer getCustomerById(int id) {
        customer = new Customer();
        try {
            ps = con.prepareStatement("SELECT * FROM customers WHERE CustNum = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                customer.setId(rs.getInt("CustNum"));
                customer.setName(rs.getString("CustName"));
                customer.setPhone(rs.getString("CustPhone"));
                customer.setGender(rs.getString("CustGen"));
                customer.setAddress(rs.getString("CustAdd"));
                customer.setDob(rs.getDate("CustDob"));
            } else {
                throw new SQLException("Customer tidak ditemukan");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            return null;
        }
        return customer;
    }

    public void saveCustomer(Customer customer) {
        String query = "SELECT * FROM customers WHERE CustNum = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, customer.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                query = "UPDATE customers SET CustName = ?, CustPhone = ?, CustGen = ?, CustAdd = ?, CustDob = ? WHERE CustNum = ?";
            } else {
                query = "INSERT INTO customers (CustName, CustPhone, CustGen, CustAdd, CustDob, CustNum) VALUES (?, ?, ?, ?, ?, ?)";
            }
            ps = con.prepareStatement(query);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            ps.setString(3, customer.getGender());
            ps.setString(4, customer.getAddress());
            ps.setDate(5, customer.getDob());
            ps.setInt(6, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteCustomer(int id) {
        String query = "DELETE FROM customers WHERE CustNum = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Customer berhasil dihapus");
        } catch (SQLException e) {
            System.out.println("Customer tidak ditemukan dengan ID = " + id);
        }
    }
}