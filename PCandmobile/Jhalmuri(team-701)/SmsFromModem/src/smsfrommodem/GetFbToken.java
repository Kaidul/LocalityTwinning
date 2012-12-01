/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ARKO
 */
public class GetFbToken {
    
    
    @SuppressWarnings("empty-statement")
    public String doSubmit(String url, Map<String, String> data) throws Exception {
        String token="";
		URL siteUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
		conn.setRequestMethod("GET");
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
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line = "";
                line=in.readLine();
                System.out.println(line);
		in.close();
                if(line!=null){
                    int i;
                    for(i=0;i<line.length();i++){
                        if(line.charAt(i)=='&'){
                            break;
                        }
                    }
                    token=line.substring(13, i);
                System.out.println(token);
                
                }
                return token;
	}
}
