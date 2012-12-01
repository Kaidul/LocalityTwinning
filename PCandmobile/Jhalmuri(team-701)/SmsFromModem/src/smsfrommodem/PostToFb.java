/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ARKO
 */
public class PostToFb {
     private static String serverUrl;
    private static Map<String, String> params;
    
    public String statusSet(String accessToken, String mssg) {
        String postId = null;
        try {        
            
            // Instantiate the choreography, using a previously instantiated TembooSession object, eg:
                serverUrl = "https://graph.facebook.com/440976572631087/feed";
                params = new HashMap<String, String>();
                params.put("access_token", accessToken);
                params.put("message", mssg);
                
                postId=doSubmit(serverUrl, params);
        } catch (Exception ex) {
            Logger.getLogger(PostToFb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return postId;
    }
    
    public String doSubmit(String url, Map<String, String> data) throws Exception {
		URL siteUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		
		Set keys = data.keySet();
		Iterator keyIter = keys.iterator();
		String content = "";
		for(int i=0; keyIter.hasNext(); i++) {
			Object key = keyIter.next();
			if(i!=0) {
				content += "&";
			}
			content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
		}
		System.out.println(content);
		out.writeBytes(content);
		out.flush();
		out.close();
                StringBuilder stringBuld=new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
                while((line=in.readLine())!=null) {
			stringBuld.append(line);
		}
                line=stringBuld.toString();
		
		in.close();
                JsonParser perser=new JsonParser();
                String temp=perser.parsed(line);
                System.out.println(temp);
                return temp;
	}
}
