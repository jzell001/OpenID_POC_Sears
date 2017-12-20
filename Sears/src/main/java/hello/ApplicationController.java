package hello;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpHeaders;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;
import java.io.IOException;

 
@Controller
public class ApplicationController {
	
	String return_consent_url = "";
	String return_no_consent_url = "";
	String targetURLPost = "";

	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/accessCodeRequest", method = RequestMethod.POST)
	public ResponseEntity<String> accessCodeRequest(@RequestBody String xml_id_urls) {
		
		String client_id_verify = "";
		
		System.out.println("\n");
		System.out.println("in Sears /accessCodeRequest controller");
		System.out.println("\n");
		
		// get client id
		client_id_verify = ParseXml.Parse(xml_id_urls, "id");
		
		// get redirect url
		return_consent_url = ParseXml.Parse(xml_id_urls, "redirect");
		
		// get no consent url
		return_no_consent_url = ParseXml.Parse(xml_id_urls, "noconsent");
		
		System.out.println("\n");
		System.out.println("return_consent_url: " + return_consent_url);
		System.out.println("return_no_consent_url: " + return_no_consent_url);
		System.out.println("\n");
		
		
		System.out.println("\n");
		System.out.println("client_id_verify: " + client_id_verify);
		System.out.println("GlobalVar.client_id: " + GlobalVar.client_id);
		System.out.println("\n");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		
		
		if(client_id_verify.equals(GlobalVar.client_id)) {
			
			System.out.println("\n");
			System.out.println("obtain access code: SUCCESS!");
			System.out.println("exiting Sears /accessCodeRequest controller");
			System.out.println("\n");
			
			// generate random character sequence for access code
			GlobalVar.access_code = RandCharGen.Gen(10);
			
			System.out.println("\n");
			System.out.println("GlobalVar.access_code: " + GlobalVar.access_code);
			System.out.println("\n");
			
			return new ResponseEntity<String>(GlobalVar.access_code, responseHeaders, HttpStatus.CREATED);
			
		} else {
			
			System.out.println("\n");
			System.out.println("inside else statement");
			System.out.println("obtain access code: FAILURE!");
			System.out.println("returning \"null\"");
			System.out.println("\n");
			
			return new ResponseEntity<String>(null, responseHeaders, HttpStatus.CREATED);
			
		}
		
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	@RequestMapping(value = "/accessTokenRequest", method = RequestMethod.POST)
	public ResponseEntity<String> accessTokenRequest(@RequestBody String xml_id_code) {	
		
		String client_id_verify = "";
		String client_secret_verify = "";
		String client_code_verify = "";
		
		
		System.out.println("\n");
		System.out.println("in Sears /accessTokenRequest controller");
		System.out.println("\n");
		
		System.out.println("\n");
		System.out.println("xml_id_code: " + xml_id_code);
		System.out.println("\n");
		
		
		// Get 'id' from xml_id_code
		try {
			client_id_verify = ParseXml.Parse(xml_id_code, "id");
			
			
			System.out.println("\n");
			System.out.println("client_id_verify: " + client_id_verify);
			System.out.println("\n");
			
			
		} catch (Exception e) {
			client_id_verify = null;
		}
		
		// Get 'secret' from xml_id_code
		try {
			client_secret_verify = ParseXml.Parse(xml_id_code, "secret");
			
			
			System.out.println("\n");
			System.out.println("client_secret_verify: " + client_secret_verify);
			System.out.println("\n");
			
			
		} catch (Exception e) {
			client_secret_verify = null;
		}
		
		// Get 'code' from xml_id_code
		try {
			client_code_verify = ParseXml.Parse(xml_id_code, "code");
			
			
			System.out.println("\n");
			System.out.println("client_code_verify: " + client_code_verify);
			System.out.println("\n");
			
			
		} catch (Exception e) {
			client_code_verify = null;
		}
		
		System.out.println("\n");
		System.out.println("client_id_verify: " + client_id_verify);
		System.out.println("client_code_verify: " + client_code_verify);
		System.out.println("\n");
		System.out.println("GlobalVar.access_code: " + GlobalVar.access_code);
		System.out.println("\n");
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		
		
		if( client_id_verify.equals(GlobalVar.client_id) && client_code_verify.equals(GlobalVar.access_code) 
			&& client_secret_verify.equals(GlobalVar.client_secret) ) {
			
			
			System.out.println("\n");
			System.out.println("obtain access token: SUCCESS!");
			System.out.println("exiting Sears /accessTokenRequest controller");
			System.out.println("\n");
			
			// generate access token
			GlobalVar.access_token = RandCharGen.Gen(15);
			
			return new ResponseEntity<String>(GlobalVar.access_token, responseHeaders, HttpStatus.CREATED);
			
		} else {
			
			System.out.println("\n");
			System.out.println("obtain access code: FAILURE!");
			System.out.println("returning \"null\"");
			System.out.println("\n");
			
			return new ResponseEntity<String>(null, responseHeaders, HttpStatus.CREATED);
			
		}
		
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
		
	
	@RequestMapping("/userInfo")
	public String authenticate(
			@RequestParam(value = "client-id", required = true, defaultValue = "") String cl_id_final,
			@RequestParam(value = "client-secret", required = true, defaultValue = "") String cl_secr_final,
			@RequestParam(value = "access-tok", required = true, defaultValue = "") String acc_tok_final) {
		
		System.out.println("\n");
		System.out.println("in Sears /userInfo controller");
		System.out.println("\n");
		
		System.out.println("\n");
		System.out.println("client-id: " + cl_id_final);
		System.out.println("client-secret: " + cl_secr_final);
		System.out.println("access-tok: " + acc_tok_final);
		System.out.println("\n");
		
		System.out.println("\n");
		System.out.println("return_consent_url: " + return_consent_url);
		System.out.println("return_no_consent_url: " + return_no_consent_url);
		System.out.println("\n");
		
		
		if( cl_id_final.equals(GlobalVar.client_id) && cl_secr_final.equals(GlobalVar.client_secret) &&
			acc_tok_final.equals(GlobalVar.access_token)) {
			
			
			System.out.println("\n");
			System.out.println("authenticate with access token: SUCCESS!");
			System.out.println("exiting Sears /userInfo controller to /consent");
			System.out.println("\n");
			
			return "redirect:/consent";
			
		} else {
			
			
			System.out.println("\n");
			System.out.println("authenticate with access token: FAILURE!");
			System.out.println("exiting Sears /userInfo controller to /logoutCall");
			System.out.println("\n");
			
			return "redirect:/errorPage";
			
		}
		
		
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("/goBackSuccess")
	public String goBackSuccess() {
		
		System.out.println("\n");
		System.out.println("in Sears /goBackSuccess controller");
		System.out.println("\n");
		
		return "redirect:" + return_consent_url + "?name=" + GlobalVar.user_details;
	}	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("/logoutCall")
	public String logoutCall() {
				
		System.out.println("\n");
		System.out.println("in Sears /logoutCall controller");		
		System.out.println("return_no_consent_url: " + return_no_consent_url);
		System.out.println("\n");
		
		return "redirect:/logoutPage";
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("/logoutBack")
	public String logoutBack() {
		
		System.out.println("\n");
		System.out.println("in Sears /logoutBack controller");
		
		System.out.println("return_no_consent_url: " + return_no_consent_url);
		System.out.println("\n");
		
		return "redirect:" + return_no_consent_url;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
}






