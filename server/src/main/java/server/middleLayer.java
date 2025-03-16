package server;

import java.sql.*;
import javax.sql.*;
import java.util.*;

public class middleLayer {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private boolean isConnected = false;

    String url = "jdbc:mysql://localhost:3306/dyschu";
    String username = "root";
    String password = "PiE85397";

    public middleLayer() throws ClassNotFoundException, SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver loaded successfully");
            
            // Establish connection
            System.out.println("Attempting to connect to database at " + url);
            this.con = DriverManager.getConnection(url, username, password);
            this.stmt = con.createStatement();
            isConnected = true;
            System.out.println("Database connected successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found. Make sure the driver JAR is in the classpath");
            throw e;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            System.out.println("Please check if MySQL is running and the database 'dyschu' exists");
            throw e;
        } catch (Exception e) {
            System.out.println("Unexpected error during database connection: " + e);
            e.printStackTrace();
            throw new SQLException("Failed to initialize database connection", e);
        }
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void write(String[] val, String table) throws SQLException{
        if (!isConnected) {
            System.out.println("Database not connected. Cannot write data.");
            return;
        }
        
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
    public String readPassword(String employeeNum){
        String sql = "Select employeePassword FROM employee";
	try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1,employeeNum);
	    try (ResultSet rs =pstmt.executeQuery()){
		if (rs.next()){
			return rs.getString("employeePassword");
		}
	    }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return "No data found";
    }

    public void change(String table, String targetColumn, String newData, String identifier){
	String sql = "UPDATE " + table + " SET " + targetColumn + " = ? WHERE identifier_column = "+identifier;
        try (Connection con = DriverManager.getConnection(url, username, password)){
	    PreparedStatement pstmt = con.prepareStatement(sql);{
        	pstmt.setString(1, newData);
        	pstmt.setString(2, identifier);
        	pstmt.executeUpdate();
	    }
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
    }
}
