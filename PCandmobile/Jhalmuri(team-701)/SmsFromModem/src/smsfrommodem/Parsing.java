/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

/**
 *
 * @author ARKO
 */
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aniK
 */
public class Parsing {
    private PostCls post=new PostCls();
    private static String serverUrl;
    private static Map<String, String> params;
    /**
     * @param args the command line arguments
     */
    //private String[]sendedCmtnew String[100];;
    public void send_msg(String post_id, String phn) throws Exception {
        //sendedCmt=new String[100];
        String json = readUrl("https://graph.facebook.com/" + post_id + "?fields=comments&access_token=AAAGTGyAfs6wBANJWv4iLvt6dbBIypdB5jSCr2QpbIjfwp7dgGTK3ODySuBEh2pxGGV5VrAYdGQrdwIwWP9qRspdpgQFzekWOKDSwbgZDZD");
        Gson gson = new Gson();
        Page page = gson.fromJson(json, Page.class);
        for (Data item : page.comments.data) {
            String cmnt_id = item.id;
            String cmnt = item.message;

            if (!searchusers(cmnt_id)) {
                serverUrl = "http://api.planetgroupbd.com/planetapi.php";
                params = new HashMap<String, String>();
                params.put("user", "jhalmuri");
                params.put("pass", "123456");
                params.put("num", phn);
                params.put("senderid", "jhalmuri");
                params.put("msg", cmnt);
                post.doSubmit(serverUrl, params);
                insertusers(cmnt_id);

            }
            System.out.println(" cmnt:" + cmnt + " id:" + cmnt_id + '\n');

        }

    }

    void insertusers(String cmnt_id) {
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM SENDED";
                ResultSet rs = stmt.executeQuery(sql);
                rs.moveToInsertRow();
                rs.updateString("CMNTID", cmnt_id);
                rs.insertRow();
                rs.close();
                con.close();
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    boolean searchusers(String cmnt_id) {
        try {
            String host = "jdbc:derby://localhost:1527/JhalMuri";
            String uName = "jhalmuri";
            String uPass = "jhalmuri";
            try (Connection con = DriverManager.getConnection(host, uName, uPass)) {
                Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                String sql = "SELECT * FROM SENDED WHERE CMNTID='" + cmnt_id + "'";
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

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) {
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    static class Page {

        Comments comments;
    }

    static class Comments {

        List<Data> data;
    }

    static class Data {

        String id;
        String message;
    }
//
}
