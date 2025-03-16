package server;

import java.sql.*;
import java.util.*;

public class middleLayer {
    private Connection con;
    private Statement stmt;

    String url = "jdbc:mysql://localhost:3306/dyschu";
    String username = "root";
    String password = "PiE85397";

    public middleLayer() throws ClassNotFoundException, SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(url, username, password);
            this.stmt = con.createStatement();
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public void write(String[] val, String table) throws SQLException{
        if (table.equals("employee")){   
            String sql = "INSERT INTO employee (employeeNum, employeeFirstName, jobTitle, employeeSirName) VALUES (?, ?, ?, ?);";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(val[0]));  //ID
                pstmt.setString(2, val[1]);                 //First Name
                pstmt.setString(3, val[2]);                 //Last Name
                pstmt.setInt(4, Integer.parseInt(val[3]));  //Job Title
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else if (table.equals("client")){   
            String sql = "INSERT INTO client (clientFirstName, clientSirName) VALUES (?, ?);";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, val[0]);                 //First Name
                pstmt.setString(2, val[1]);                 //Last Name
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Invalid Request - Expected 'client' or 'employee'");
        }
    }
    public String read(String identifier, String table){
        String sql;
        if (table.equals("client")){
            sql = "SELECT clientFirstName, clientSirName FROM client WHERE clientNum = "+identifier+";";
        }else if (table.equals("employee")){
            sql = "SELECT employeeFirstName, employeeSirName, jobTitle FROM employee WHERE employeeNum = "+identifier+";";
        }else{
            return "Invalid Request - Expected 'client' or 'employee'";
        }
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, identifier);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if (table.equals("client")) {
                        return rs.getString(1)+'|'+rs.getString(2);
                    }else {
                        return rs.getString(1)+'|'+rs.getString(2)+'|'+rs.getString(3);
                    }
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return "No data found";
    }
    public void close() throws SQLException {
        if (stmt != null){ 
            stmt.close();
        }
        if (con != null){
            con.close();
        }
    }

}
