import java.sql.*;
import com.mysql.jdbc.Driver;
public class middleLayer {
    public static void main(String ARGS[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dyschu","root","pie8.5397"); 
            Statement stmt = con.createStatement();
            ResultSet rsclient = stmt.executeQuery("select * from client");  
            while(rsclient.next()){ 
                System.out.println(rsclient.getInt(1)+"  "+rsclient.getString(2)+"  "+rsclient.getString(3));  
            }
            ResultSet rsemployee = stmt.executeQuery("select * from employee");  
            while(rsclient.next()){ 
                System.out.println(rsemployee.getInt(1)+"  "+rsemployee.getString(2)+"  "+rsemployee.getString(3)+" "+rsemployee.getString(4));
            con.close(); 
            } 
        } catch(Exception e){ 
            System.out.println(e);
        }  
    }
}