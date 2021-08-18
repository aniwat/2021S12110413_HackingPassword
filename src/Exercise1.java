import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * 
 * @author Aniwat Keereena
 * ID: 6470301021
 * Assignments: Hacking Password
 */
public class Exercise1 {

	public static final String PASSWORD_DICTIONARY_FILE = "D:\\Development\\EclipseWorkspace\\HackingPassword\\res\\10k-most-common.txt";
	
	public static final String ORIGINAL_VALUE = "d54cc1fe76f5186380a0939d2fc1723c44e8a5f7";

	public static void main(String[] args) {
		System.out.println("======================== Exercise1 Started ========================");
		long start = System.currentTimeMillis();
		
		ArrayList<String> wordFromDictionary = new ArrayList<String>();

		// Read word from dictionary file
		System.out.println("Reading dictionary file ...");
		try {
			FileInputStream fis = new FileInputStream(PASSWORD_DICTIONARY_FILE);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
				String line;
				while ((line = br.readLine()) != null) {
					wordFromDictionary.add(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.format("Count: Word from dictionary = %,d%n%n", wordFromDictionary.size());

		// Calculate substitution
		System.out.println("Creating substitution ...");
		ArrayList<String> wordFromSub = new ArrayList<String>();
		
		Substitution sub = new Substitution();
		for (String word : wordFromDictionary) {
			sub.calculate(word, wordFromSub);
		}
		System.out.format("Count: Substitution word = %,d%n%n", wordFromSub.size());

		boolean founded = false;
		
		System.out.println("Finding a answer ...");
		
		// Find answer from dictionary
		for (String word : wordFromDictionary) {
			if (checkAnswer(word)) {
				founded = true;
				break;
			}
		}
		
		// Find answer from substitution
		if (!founded) {
			for (String word : wordFromSub) {
				if (checkAnswer(word)) {
					break;
				}
			}
		}

		long elapsedTimeMillis = System.currentTimeMillis() - start;
		
		// Get elapsed time in seconds
		float elapsedTimeSec = elapsedTimeMillis / 1000F;
		System.out.format("%nElapsed time = %,d milliseconds%n", elapsedTimeMillis);
		System.out.format("Elapsed time = %.3f seconds%n", elapsedTimeSec);
		System.out.println("======================== Exercise1 Terminated ========================");
	}
	
	public static String getSha1HexDigest(String word) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		digest.update(word.getBytes("UTF-8"));
		return String.format("%040x", new BigInteger(1, digest.digest()));
	}

	public static boolean checkAnswer(String word) {
		try {
			String hex = getSha1HexDigest(word);
			if (ORIGINAL_VALUE.equalsIgnoreCase(hex)) {
				System.out.println("Original value of \"" + ORIGINAL_VALUE + "\" is " + word);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
