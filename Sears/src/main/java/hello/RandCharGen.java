package hello;

import java.util.Random;


public class RandCharGen {
	
    public static String Gen(int n) {
		
        final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final int N = alphabet.length();
		
		
		String GenSeq = "";

		Random r = new Random();

		for (int i = 0; i < n; i++) {
			
			GenSeq = GenSeq + alphabet.charAt(r.nextInt(N));
			
		}
		
		System.out.println("\n");
		System.out.print("Random Sequence Generated: " + GenSeq);
		System.out.println("\n");
		
		return GenSeq;
		
		
    }
}











