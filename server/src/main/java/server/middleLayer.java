package server;

import java.sql.*;
import java.util.*;

public class middleLayer {
    private Connection con;
    private Statement stmt;

    String url = "jdbc:mysql://localhost:3306/dyschu";
    String username = "root";
    String password = "PiE85397";

    public void write(String[] val, String table) throws SQLException{
        if (table.equals("employee")){   
            String sql = "INSERT INTO employee (employeeFirstName, employeeSirName, jobTitleCode, employeePassword ) VALUES (?, ?, ?, ?);";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, val[0]);  			//First Name
                pstmt.setString(2, val[1]);		 	//Sir/Last Name
                pstmt.setInt(3, Integer.parseInt(val[2]));	//Job Code
		pstmt.setString(4, val[3]);			//Password
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else if (table.equals("clientCase")){   
            String sql = "INSERT INTO clientCase (clientFirstName, clientSirName, jobDescription, employeeTypeNeeded, caseAccepted) VALUES (?, ?, ?, ?, ?);";

            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, val[0]);             	//First Name
                pstmt.setString(2, val[1]);			//Last Name
		pstmt.setString(3, val[2]);			//Job Desciption
		pstmt.setInt(4, Integer.parseInt(val[3]));	//Employee Type
		pstmt.setInt(5, Integer.parseInt(val[4]));	//Case Status
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Invalid Request - Expected 'clientCase' or 'employee'");
        }
    }
    public String read(String identifier, String table){
        String sql;
        if (table.equals("clientCase")){
            sql = "SELECT caseNum, clientFirstName, clientSirName, caseAccepted FROM case WHERE caseNum = "+identifier+";";
        }else if (table.equals("employee")){
            sql = "SELECT employeeFirstName, employeeSirName, jobTitle FROM employee WHERE employeeNum = "+identifier+";";
        }else{
            return "Invalid Request - Expected 'clientCase' or 'employee'";
        }
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, identifier);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    if (table.equals("clientCase")) {
                        return rs.getString(1)+'|'+rs.getString(2)+'|'+rs.getString(3)+'|'+rs.getString(4);
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
    public void change(String table, String targetColumn, String newData, String identifer){
	String sql = "UPDATE " + table + " SET " + targetColumn + " = ? WHERE identifier_column = "+identifier;
        try (Connection con = DriverManager.getConnection(url, username, password)){
	    PreparedStatement pstmt = con.prepareStatement(sql);{
        	pstmt.setString(1, newData);
        	pstmt.setString(2, identifier);
        	pstmt.executeUpdate();
	    }
	}
    }
}
