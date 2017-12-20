package hello;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


public class DigitalSignature {
  
  public static String sid = ""; 				  // site identifier created to uniquely identify site 
  public static String timestamp = "";
  public static String digitalSignature = "";
  
  public static void main(String[] args) {
	  
    sid = "3"; 									  // site identifier created to uniquely identify site 
    String key = "fdprL5qe2/St1AkzF7jUw3DC00A=";  // secret key, retrieved from a key store 
	
    Date now = Calendar.getInstance().getTime();
	
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    timestamp = formatter.format(now);
    
	try {
		
      String encryptData = "sid=" + sid + "ts=" + timestamp;
      SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
      Mac mac = Mac.getInstance("HmacSHA1");
      mac.init(signKey);
      byte[] rawData = mac.doFinal(encryptData.getBytes());
      String base64 = Base64.encodeBase64String(rawData);
      digitalSignature = URLEncoder.encode(base64, "UTF-8");
      
    } catch (Exception ex) {
    }
	
  }
  
}
