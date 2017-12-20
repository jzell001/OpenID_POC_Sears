package hello;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class UserXmlString {
	
    public static String UserXml(String urlPost, String urlGet, String email, String password, 
								String signature) throws Exception {       
		
        
		// ------------------------------------------------------------------------------------- 
		// ---------------- **BEGIN** POST/GET method calls for user info ---------------------- 
		
		String targetURLPost = urlPost + signature;
		String xmlPost = "";
		String user_id = "";
		String targetURLGet = "";
		String xmlFinal = "";
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

			// -------------------------------------------------------------------------------- 
			// ---------------- **BEGIN** POST method call for user info ---------------------- 
            HttpPost httpPost = new HttpPost(targetURLPost);
            List <NameValuePair> nvps = new ArrayList <NameValuePair>();
            nvps.add(new BasicNameValuePair("logon", email));
            nvps.add(new BasicNameValuePair("pd", password));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse responsePost = httpclient.execute(httpPost);
			//System.out.println("responsePost: " + responsePost + "\n");

            try {
                //System.out.println("responsePost.getStatusLine(): " + responsePost.getStatusLine() + "\n");
                HttpEntity entityPost = responsePost.getEntity();

				//System.out.println("responsePost.getEntity(): " + entityPost + "\n");
				
				xmlPost = EntityUtils.toString(entityPost);
				
				
				System.out.println("\n");
				System.out.println("xmlPost: " + xmlPost);
				System.out.println("\n");
				
				
				String code = "";
				try {
					code = ParseXml.Parse(xmlPost, "code");
					
					if (Integer.parseInt(code) >= 400) {
						
						
						System.out.println("\n");
						System.out.println("code: " + code);
						System.out.println("exiting UserXmlString.java with xmlPost instead of xmlFinal");
						System.out.println("\n");
						
						
						return null;
						//return xmlPost;
					}
					
					
					System.out.println("\n");
					System.out.println("code: " + code);
					System.out.println("\n");
					
					
				} catch (Exception e) {
				}
				
				
				
				
				
				// ----------------------------------------------------------------------- 
				// ---------------- **BEGIN** Parse XML for user_id ---------------------- 
				
				try {
					user_id = ParseXml.Parse(xmlPost, "id");
					//System.out.println("user_id: " + user_id);
				} catch (Exception e) {
					//e.printStackTrace();
					return null;
				}				
				
				// ---------------- **END** Parse XML for user_id ---------------------- 
				
				
				
				//System.out.println("\n");
				//System.out.println("\n");
				
                EntityUtils.consume(entityPost);
				
			} catch (Exception e) {

			//e.printStackTrace();
			return null;

            } finally {
                responsePost.close();
            }

			// ---------------- **END** POST method call for user info ---------------------- 
			
			
			
			// -------------------------------------------------------------------------------- 
			// ---------------- **BEGIN** GET method call for user info ---------------------- 
			
			//targetURLGet = urlGet + user_id + "&sid=" + sid + "&ts=" + ts + "&sig=" + ds;
			targetURLGet = urlGet + user_id + "&" + signature;
			//System.out.println("targetURLGet: " + targetURLGet + "\n");
			
			HttpGet httpGet = new HttpGet(targetURLGet);
            CloseableHttpResponse responseGet = httpclient.execute(httpGet);

            try {
                //System.out.println(responseGet.getStatusLine());
                HttpEntity entityGet = responseGet.getEntity();
				
				//System.out.println("\n");
				//System.out.println("\n");
				//System.out.println("responseGet.getEntity(): " + entityGet + "\n");
				
				xmlFinal = EntityUtils.toString(entityGet);
				//System.out.println("xmlFinal: " + xmlFinal);
				
				//System.out.println("\n");
				//System.out.println("\n");
				
                EntityUtils.consume(entityGet);
				
			} catch (Exception e) {

			//e.printStackTrace();
			return null;

            } finally {
                responseGet.close();
            }
			// ---------------- **END** GET method call for user info ---------------------- 
			
		} catch (Exception e) {

			//e.printStackTrace();
			return null;
			
        } finally {
			try {
				httpclient.close();
			} catch (Exception e) {

				//e.printStackTrace();
				return null;
			}
        }
	
		// ---------------- **END** POST/GET method calls for user info ----------------------  
		
		return xmlFinal;
		
    }
}