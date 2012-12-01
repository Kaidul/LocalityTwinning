/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smsfrommodem;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
/**
*
* @author aniK
*/
public class JsonParser{
/**
* @param args the command line arguments
*/
public String parsed(String  text) throws Exception {
String json = text;
Gson gson = new Gson(); 
Page page = gson.fromJson(json, Page.class);
return page.id;
}

/*private static String readUrl(String urlString) throws Exception {
BufferedReader reader = null;
try {
URL url = new URL(urlString);
reader = new BufferedReader(new InputStreamReader(url.openStream()));
StringBuffer buffer = new StringBuffer();
int read;
char[] chars = new char[1024];
while ((read = reader.read(chars)) != -1)
buffer.append(chars, 0, read);
return buffer.toString();
} finally {
if (reader != null)
reader.close();
}
}*/
static class Page {
String id;
}

//
}