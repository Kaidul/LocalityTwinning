/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.Message.MessageTypes;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.GatewayException;
import org.smslib.TimeoutException;

/**
 *
 * @author ARKO
 */
public class SendAndReceiveMessage {
        String cmtId;
    public void doIt() throws Exception {



        OutboundNotification outboundNotification = new OutboundNotification();
        InboundNotification inboundNotification = new InboundNotification();
        System.out.println("Example: Send message from a serial gsm modem.");
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        SerialModemGateway gateway = new SerialModemGateway("modem.com5", "COM5", 460800, "MobiData", "");
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("");
        // Explicit SMSC address set is required for some modems.
        // Below is for VODAFONE GREECE - be sure to set your own!--- i set my own
        gateway.setSmscNumber("+8801700000600");
        Service.getInstance().setOutboundMessageNotification(outboundNotification);
        Service.getInstance().setInboundMessageNotification(inboundNotification);
        Service.getInstance().addGateway(gateway);
        Service.getInstance().startService();
        System.out.println();
        System.out.println("Modem Information:");
        System.out.println("  Manufacturer: " + gateway.getManufacturer());
        System.out.println("  Model: " + gateway.getModel());
        System.out.println("  Serial No: " + gateway.getSerialNo());
        System.out.println("  SIM IMSI: " + gateway.getImsi());
        System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
        System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
        System.out.println();
        // Send a message synchronously.
        //OutboundMessage msg = new OutboundMessage("+8801718847772", "Hello from SMSLib!");
        //Service.getInstance().sendMessage(msg);
        System.out.println("log1");
        //System.out.println(msg);
        System.out.println("log2");
        // Or, send out a WAP SI message.
        //OutboundWapSIMessage wapMsg = new OutboundWapSIMessage("306974000000",  new URL("http://www.smslib.org/"), "Visit SMSLib now!");
        //Service.getInstance().sendMessage(wapMsg);
        //System.out.println(wapMsg);
        // You can also queue some asynchronous messages to see how the callbacks
        // are called...
        //msg = new OutboundMessage("309999999999", "Wrong number!");
        //srv.queueMessage(msg, gateway.getGatewayId());
        //msg = new OutboundMessage("308888888888", "Wrong number!");
        //srv.queueMessage(msg, gateway.getGatewayId());
        System.out.println("Now Sleeping - Hit <enter> to terminate.");
        System.in.read();
        Service.getInstance().stopService();
    }

    public class OutboundNotification implements IOutboundMessageNotification {

        @Override
        public void process(AGateway gateway, OutboundMessage msg) {
            System.out.println("log3");
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println("log4");
            System.out.println(msg);
            System.out.println("log5");
        }
    }

    public class InboundNotification implements IInboundMessageNotification {

        @Override
        public void process(AGateway gateway, MessageTypes mt, InboundMessage im) {
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println("Message type" + " " + mt);
            System.out.println("Message" + " " + im.getText());
            System.out.println("Message" + " " + im.getOriginator());
            String mssgCode = im.getText().substring(0, 3);
            System.out.println(mssgCode);
            String mssgNo = im.getOriginator();
            if ("REG".equals(mssgCode)) {
                System.out.println("reg");
                System.out.println(mssgNo + " " + im.getText().substring(4, 8) + " " + im.getText().substring(9));
                insertusers(mssgNo, im.getText().substring(4, 8), im.getText().substring(9));
            } else if ("PST".equals(mssgCode) && searchusers(mssgNo)) {
                System.out.println("pst");
                String postMssg = im.getText().substring(4);
                String postId;
                PostToFb post = new PostToFb();
                postId = post.statusSet(Main.fbToken, namereturn(mssgNo) + " says: " + postMssg);
                if ("".equals(postId)) {
                    System.out.println("post not unsuccessful");
                } else {
                    insertpostid(mssgNo, postId);
                    System.out.println("post not successful");
                }
            } else if ("CMT".equals(mssgCode) && searchusers(mssgNo)) {
                System.out.println("cmt");
                String commentMssg = im.getText().substring(4);
                String userName;
                String postId = postidreturn(mssgNo);
                CommentToFb comment = new CommentToFb();
                cmtId=comment.commentSet(Main.fbToken, namereturn(mssgNo) + " says: " + commentMssg, postId);
                System.out.println(postId);

            }
            try {
                Service.getInstance().deleteMessage(im);
            } catch (TimeoutException ex) {
                Logger.getLogger(SendAndReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GatewayException ex) {
                Logger.getLogger(SendAndReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SendAndReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(SendAndReceiveMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //AMADER FUNCTION STARTS FROM HERE
    String phonenumberreturn(String p_id) {
        String result = "";
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM POSTID WHERE ID='" + p_id + "'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.first();
                result = rs.getString("PHN");
                rs.close();
                con.close();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }

    String postidreturn(String phn_no) {
        String result = "";
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM POSTID WHERE PHN='" + phn_no + "'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.first();
                result = rs.getString("ID");
                rs.close();
                con.close();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }

    void insertpostid(String phn_no, String pid) {

        String host = "jdbc:derby://localhost:1527/JhalMuri";
        String uName = "jhalmuri";
        String uPass = "jhalmuri";

        if (searchusers(phn_no)) {
            updateid(phn_no, pid);
        } else {
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM POSTID";
                ResultSet rs = stmt.executeQuery(sql);
                rs.moveToInsertRow();
                rs.updateString("PHN", phn_no);
                rs.updateString("ID", pid);
                rs.insertRow();
                rs.close();
                con.close();

            } catch (SQLException err) {
                System.out.println(err.getMessage());
            }
        }

    }

    void updateid(String phn_no, String p_id) {
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM POSTID WHERE PHN='" + phn_no + "'";
                try (ResultSet rs = stmt.executeQuery(sql)) {
                    rs.first();
                    rs.updateString("ID", p_id);
                    rs.updateRow();
                    rs.close();
                }
                con.close();

            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    void insertusers(String phn_no, String Postal_Code, String name) {
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM USERS";
                ResultSet rs = stmt.executeQuery(sql);
                rs.moveToInsertRow();
                rs.updateString("PHN", phn_no);
                rs.updateString("POSATAL", Postal_Code);
                rs.updateString("NAME", name);

                rs.insertRow();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    boolean searchusers(String phn_no) {
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM USERS WHERE PHN='" + phn_no + "'";
                ResultSet rs = stmt.executeQuery(sql);
                if (!rs.first()) {
                    return false;
                } else {
                    return true;
                }



            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return false;
    }

    String namereturn(String phn_no) {
        String result = "";
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM USERS WHERE PHN='" + phn_no + "'";
                ResultSet rs = stmt.executeQuery(sql);
                rs.first();
                result = rs.getString("NAME");
                rs.close();
                con.close();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return result;
    }
}
