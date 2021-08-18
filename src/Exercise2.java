import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * @author Aniwat Keereena
 * ID: 6470301021
 * Assignments: Hacking Password
 */
public class Exercise2 {
	
	public static final String PASSWORD_DICTIONARY_FILE = "D:\\Development\\EclipseWorkspace\\HackingPassword\\res\\10k-most-common.txt";
	
	public static void main(String[] args) {
		System.out.println("======================== Exercise2 Started ========================");
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

		System.out.println("Creating a rainbow table using the Sh1 algorithm ...");
		System.out.format("Table size = %,d%n%n", wordFromSub.size() + wordFromDictionary.size());
        // Generate rainbow table
		for (String word : wordFromDictionary) {
			try {
				Exercise1.getSha1HexDigest(word);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Find answer from substitution
		for (String word : wordFromSub) {
			try {
				Exercise1.getSha1HexDigest(word);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		long elapsedTimeMillis = System.currentTimeMillis() - start;
		
		// Get elapsed time in seconds
		float elapsedTimeSec = elapsedTimeMillis / 1000F;
		System.out.format("Elapsed time = %,d milliseconds%n", elapsedTimeMillis);
		System.out.format("Elapsed time = %.3f seconds%n", elapsedTimeSec);
		System.out.println("======================== Exercise2 Terminated ========================");
	}

}
