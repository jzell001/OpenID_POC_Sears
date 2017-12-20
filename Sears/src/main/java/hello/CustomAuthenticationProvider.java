package hello;
 
import java.util.ArrayList;
 
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	String email = "";
	String password = "";

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		// get the username (email) and password that the user entered
		email = authentication.getName();
        password = authentication.getCredentials().toString();
        
		System.out.println("\n");
		System.out.println("email = " + email + ", password = " + password);
		System.out.println("\n");
		
		// use the credentials and authenticate against the third-party system
		
		
		// ---------------- **BEGIN** obtain DigitalSignature via method call ---------------------- 
		
		DigitalSignature.main(null);
		String sid = DigitalSignature.sid;
		String ts  = DigitalSignature.timestamp;
		String ds  = DigitalSignature.digitalSignature;
		String signature = "sid=" + sid + "&ts=" + ts + "&sig=" + ds;
		
		// ---------------- **END** obtain DigitalSignature via method call ---------------------- 
		
		
		
		// ---------------- **BEGIN** print statements ---------------------- 
		
		String urlPost = "http://web301p.qa.ch3.s.com:5781/universalservices/v6/user/auth?";
		String urlGet = "http://web301p.qa.ch3.s.com:5781/universalservices/v4/user?id=";
		
		System.out.println("\n");
		System.out.println("signature: " + signature);
		System.out.println("\n");
		
		
		// ---------------- **END** print statements ---------------------- 
		
		
		 
		// ---------------- **BEGIN** POST/GET method calls for user info ----------------------
		
		try {
			GlobalVar.user_details = UserXmlString.UserXml(urlPost, urlGet, email, password, signature);
			
			System.out.println("\n");
			System.out.println("GlobalVar.user_details: " + GlobalVar.user_details);
			System.out.println("\n");
			
			if (GlobalVar.user_details == null) {
				
				System.out.println("\n");
				System.out.println("Login fail!");
				System.out.println("\n");
				
				return null;
			}
			
		} catch (Exception e) {
        }
		// ---------------- **END** POST/GET method calls for user info ----------------------		
		
		
		if ( GlobalVar.user_details != null) {
			System.out.println("\n");
			System.out.println("Succesful authentication!");
			System.out.println("\n");
        	return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());	
        }
        
		// below shouldn't be reached, but just in case
		System.out.println("\n");
		System.out.println("Login fail!");
		System.out.println("\n");
        return null;
	}
 
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
 
}








