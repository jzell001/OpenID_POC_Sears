package hello;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
import org.apache.http.entity.StringEntity;

 
@Controller
public class ApplicationController {
	
	
	String targetURLPost = "";
	String FirstName = "";
	String LastName = "";
	String UserID = "";
	String UserZip = "";
	String UserEmail = "";
	
	String redirect_url = "http://localhost:9999/client/hello";
	String noConsent_url = "http://localhost:9999/client";
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	@RequestMapping("/sendPost")
	public ModelAndView sendPost() {
		
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("in /sendPost controller");
		
		
		// ---------------- **BEGIN** request access code ---------------------------------------------
		
		
		// set up xml file to send to /accessCodeRequest controller
		String xml_head = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><id>";
		String xml_mid1  = "</id><redirect>";
		String xml_mid2  = "</redirect><noconsent>";
		String xml_tail = "</noconsent></user>";
		String xml_id_redirect = xml_head + GlobalVar.client_id_CLIENT + xml_mid1 + redirect_url +
								 xml_mid2 + noConsent_url + xml_tail;
							 
		
		System.out.println("\n");
		System.out.println("xml_id_redirect request: " + xml_id_redirect);
		System.out.println("\n");
		
		
		targetURLPost = "http://localhost:8080/accessCodeRequest";
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
			
			// ---------------- **BEGIN** POST method call for access code ---------------------- 
            HttpPost httpPost = new HttpPost(targetURLPost);
			StringEntity c_id = new StringEntity(xml_id_redirect);
			c_id.setContentType("text/plain; charset=UTF-8");
			httpPost.setEntity(c_id);
            CloseableHttpResponse responsePost = httpclient.execute(httpPost);
			
			
			System.out.println("\n");
			System.out.println("responsePost: " + responsePost + "\n");
			System.out.println("\n");

            try {
                //System.out.println("responsePost.getStatusLine(): " + responsePost.getStatusLine() + "\n");
                HttpEntity entityPost = responsePost.getEntity();
				
				
				System.out.println("\n");
				System.out.println("responsePost.getEntity(): " + entityPost + "\n");
				System.out.println("\n");
				
				GlobalVar.access_code_CLIENT = EntityUtils.toString(entityPost);
				
				
				System.out.println("\n");
				System.out.println("GlobalVar.access_code_CLIENT: " + GlobalVar.access_code_CLIENT);
				System.out.println("\n");
				
				
                EntityUtils.consume(entityPost);
				
			} catch (Exception e) {
				return null;
            } finally {
                responsePost.close();
            }

			// ---------------- **END** POST method call for access code ---------------------- 
			
		} catch (Exception e) {
			return null;
        } finally {
			try {
				httpclient.close();
			} catch (Exception e) {
				return null;
			}
        }
		
		// ---------------- **END** request access code -----------------------------------------------
		
		
		
		
		// ---------------- **BEGIN** request access token --------------------------------------------
		
		// set up xml file to send to /accessTokenRequest controller
		String xml_head_2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><id>";
		String xml_mid1_2  = "</id><secret>";
		String xml_mid2_2  = "</secret><code>";
		String xml_tail_2 = "</code></user>";
		String xml_id_code_2 = xml_head_2 + GlobalVar.client_id_CLIENT + xml_mid1_2 + GlobalVar.client_secret_CLIENT +
							 xml_mid2_2 + GlobalVar.access_code_CLIENT + xml_tail_2;
							 
		
		System.out.println("\n");
		System.out.println("xml_id_code_2 request: " + xml_id_code_2);
		System.out.println("\n");
		
		
		targetURLPost = "http://localhost:8080/accessTokenRequest";
		CloseableHttpClient httpclient2 = HttpClients.createDefault();
        try {
			
			// ---------------- **BEGIN** POST method call for access token ---------------------- 
			
            HttpPost httpPost2 = new HttpPost(targetURLPost);
			StringEntity c_id2 = new StringEntity(xml_id_code_2);
			c_id2.setContentType("text/plain; charset=UTF-8");
			httpPost2.setEntity(c_id2);
            CloseableHttpResponse responsePost2 = httpclient2.execute(httpPost2);
			
			
			System.out.println("\n");
			System.out.println("responsePost2: " + responsePost2 + "\n");
			System.out.println("\n");

            try {
                //System.out.println("responsePost2.getStatusLine(): " + responsePost2.getStatusLine() + "\n");
                HttpEntity entityPost2 = responsePost2.getEntity();
				
				
				System.out.println("\n");
				System.out.println("responsePost2.getEntity(): " + entityPost2 + "\n");
				System.out.println("\n");
				
				GlobalVar.access_token_CLIENT = EntityUtils.toString(entityPost2);
				
				
				System.out.println("\n");
				System.out.println("GlobalVar.access_token_CLIENT: " + GlobalVar.access_token_CLIENT);
				System.out.println("\n");
				
				
                EntityUtils.consume(entityPost2);
				
			} catch (Exception e) {
				return null;
            } finally {
                responsePost2.close();
            }

			// ---------------- **END** POST method call for access token ---------------------- 
			
		} catch (Exception e) {
			return null;
        } finally {
			try {
				httpclient2.close();
			} catch (Exception e) {
				return null;
			}
        }
		
		// ---------------- **END** request access token ----------------------------------------------
		
 
		ModelAndView mv = new ModelAndView("redirectPage");
		mv.addObject("a_id", GlobalVar.client_id_CLIENT);
		mv.addObject("a_secret", GlobalVar.client_secret_CLIENT);
		mv.addObject("a_token", GlobalVar.access_token_CLIENT);
		
		
		return mv;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(@RequestParam(value = "name", required = true, defaultValue = "") String xmlResponse) {
		
		System.out.println("in /hello controller");
		
		UserID = ParseXml.Parse(xmlResponse, "id");
		FirstName = ParseXml.Parse(xmlResponse, "first");
		LastName = ParseXml.Parse(xmlResponse, "last");
		UserEmail = ParseXml.Parse(xmlResponse, "email");
		UserZip = ParseXml.Parse(xmlResponse, "zipcode");
		//SYWNum = ParseXml.Parse(xmlResponse, "number");
		
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("xmlResponse: " + xmlResponse);
		System.out.println("\n");
		System.out.println("\n");
		
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("FirstName: " + FirstName);
		System.out.println("LastName: " + LastName);
		System.out.println("\n");
		System.out.println("\n");
		
		ModelAndView mv = new ModelAndView("hello");
		mv.addObject("name1", FirstName);
		mv.addObject("name2", LastName);
		mv.addObject("name3", UserID);
		mv.addObject("name4", UserEmail);
		mv.addObject("name5", UserZip);
		
		
		return mv;
	}
	
	@RequestMapping("/redirectHome")
	public String redirectHome(
		@RequestParam(value = "no-email", required = false, defaultValue = "") String no_email,
		@RequestParam(value = "no-password", required = false, defaultValue = "") String no_password) {
		
		System.out.println("\n");
		System.out.println("in /redirectHome controller");
		System.out.println("\n");
		
		return "redirect:/" ;
	}
	
	
	
}






