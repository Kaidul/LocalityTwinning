/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ARKO
 */
public class TimerToFetchServer extends TimerTask {
    //private Iterator<String> uploadedFileItr;
    private Parsing parse;
    public TimerToFetchServer() {
        parse=new Parsing();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM POSTID";
                ResultSet rs = stmt.executeQuery(sql);
               int i=0;
                while (rs.next()) {
                    if(i==0)
                    {
                     rs.first();
                     i++;
                    }
                      
                    try {
                        parse.send_msg(rs.getString("ID"),rs.getString("PHN"));
                    } catch (Exception ex) {
                        Logger.getLogger(TimerToFetchServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(rs.getString("ID")+"   "+rs.getString("PHN"));
                }
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }
}
