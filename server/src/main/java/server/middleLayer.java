import java.sql.*;
import java.util.*;

public class middleLayer {
    private Connection con;
    private Statement stmt;

    public middleLayer(String url, String username, String password) throws ClassNotFoundException, SQLException{
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
                pstmt.setInt(1, Integer.parseInt(val[0]));
                pstmt.setString(2, val[1]);
                pstmt.setInt(3, Integer.parseInt(val[2]));
                pstmt.setString(4, val[3]);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else if (table.equals("client")){   
            String sql = "INSERT INTO client (clientNum, clientFirstName, clientSirName) VALUES (?, ?, ?);";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(val[0]));
                pstmt.setString(2, val[1]);
                pstmt.setInt(3, Integer.parseInt(val[2]));
                pstmt.setString(4, val[3]);
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