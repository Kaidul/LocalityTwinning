/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

/**
 *
 * @author ARKO
 */
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.harshadura.gsm.smsdura.GsmModem;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import org.smslib.*;

public class Main {

    private static String port = "COM5"; //Modem Port.
    private static int bitRate = 115200; //this is also optional. leave as it is.
    private static String modemName = "MOBIDATA"; //this is optional.
    private static String modemPin = ""; //Pin code if any have assigned to the modem.
    private static String SMSC = "+8801700000600"; //Message Center Number ex. Mobitel
    private static Timer timerToFetchServer;
    private static Timer timerToGetFbToken;
    private static String serverUrl;
    private static Map<String, String> params;
    public static String fbToken;

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //GsmModem gsmModem = new GsmModem();
        //GsmModem.configModem(port, bitRate, modemName, modemPin, SMSC);
        //.Sender("+8801746178910", "ki re vogleY... ki khobor?"); // (tp, msg)
        
        serverUrl = "https://jhal-muri.appspot.com/fbtoken";
        params = new HashMap<String, String>();
        params.put("token", "get");
        GetFbToken getToken = new GetFbToken();
        //params.put("redirect_uri", "https://www.facebook.com/connect/login_success.html");
        //params.put("scope", "user_groups");
        //params.put("response_type", "token");
        //PostCls postcls=new PostCls();
        //postcls.doSubmit(serverUrl, params);

        fbToken = getToken.doSubmit(serverUrl, params);
        System.out.println(String.valueOf(fbToken.length()));
        if(fbToken==""){
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            java.net.URI uri;
            uri = new java.net.URI("https://jhal-muri.appspot.com/redir.jsp");
            desktop.browse(uri);
            timerToGetFbToken = new Timer();
            timerToGetFbToken.schedule(new TimerTask() {
                @Override
                public void run() {
                    GetFbToken getToken = new GetFbToken();
                    try {
                        fbToken = getToken.doSubmit(serverUrl, params);
                        System.out.println(fbToken);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!(fbToken.charAt(fbToken.length() - 1) == '}')) {
                        timerToFetchServer.cancel();
                    }
                }
            }, 180000, 300000);
        }
        else if (fbToken.charAt(fbToken.length() - 1) == '}') {

            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            java.net.URI uri;
            uri = new java.net.URI("https://jhal-muri.appspot.com/redir.jsp");
            desktop.browse(uri);
            timerToGetFbToken = new Timer();
            timerToGetFbToken.schedule(new TimerTask() {
                @Override
                public void run() {
                    GetFbToken getToken = new GetFbToken();
                    try {
                        fbToken = getToken.doSubmit(serverUrl, params);
                        System.out.println(fbToken);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!(fbToken.charAt(fbToken.length() - 1) == '}')) {
                        timerToFetchServer.cancel();
                    }
                }
            }, 180000, 300000);
        }
        System.out.println(fbToken);
        timerToFetchServer=new Timer();
        timerToFetchServer.schedule(new TimerToFetchServer(), 10000, 60000);
        //PostToFb post=new PostToFb();
        //post.statusSet(fbToken);

        SendAndReceiveMessage sndMssg=new SendAndReceiveMessage();
        sndMssg.doIt();

    }
}
