import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 
 * @author Aniwat Keereena
 * ID: 6470301021
 * Assignments: Hacking Password
 */
public class Substitution {
	
	// Substitution pattern
	public static final char[][] SUBSTITUTION_PATTERN_MAPPING = {{ 'a', 'A' },
			{ 'b', 'B' }, 
			{ 'c', 'C' }, 
			{ 'd', 'D' },
			{ 'e', 'E', '3' },
			{ 'f', 'F' },
			{ 'g', 'G' },
			{ 'h', 'H' },
			{ 'i', 'I', 'l', 'L', '1' }, // Include L cause similar as I
			{ 'j', 'J' },
			{ 'k', 'K' },
			// Skip L
			{ 'm', 'M' },
			{ 'n', 'N' },
			{ 'o', 'O', '0' },
			{ 'p', 'P' },
			{ 'q', 'Q' },
			{ 'r', 'R' },
			{ 's', 'S' },
			{ 't', 'T' },
			{ 'u', 'U' },
			{ 'v', 'V' },
			{ 'w', 'W' },
			{ 'x', 'X' },
			{ 'y', 'Y' },
			{ 'z', 'Z' }};
	
	// Calculate regular expression substitution pattern
	// Create regular from substitution array >> a|A|b|B|c|C|d|D|e|E|3|f|F...
	public static final String REGEX_SUBSTITUTION;
	
	// Create substitution for each pattern >> {"aA", "bB", "cC", "dD", "eE3", "fF", ...}
	public static final String[] SUBSTITUTION_EACH_PATTERN;
	static {
		SUBSTITUTION_EACH_PATTERN = new String[SUBSTITUTION_PATTERN_MAPPING.length];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SUBSTITUTION_PATTERN_MAPPING.length; i++) {
			StringBuilder sc = new StringBuilder();
			for (int j = 0; j < SUBSTITUTION_PATTERN_MAPPING[i].length; j++) {
				sb.append(SUBSTITUTION_PATTERN_MAPPING[i][j]);
				sc.append(SUBSTITUTION_PATTERN_MAPPING[i][j]);
				// Append pipe '|' except last element
				if (!(i == SUBSTITUTION_PATTERN_MAPPING.length - 1 && j == SUBSTITUTION_PATTERN_MAPPING[i].length - 1)) {
					sb.append('|');
				}
			}
			SUBSTITUTION_EACH_PATTERN[i] = sc.toString(); // Assign each pattern to array
		}
		REGEX_SUBSTITUTION = sb.toString();
	}	
	
	/*
	 * Calculate Substitution
	 */
	public void calculate(String word, ArrayList<String> substitution) {
		// Not contain substitution character
		if (!Pattern.compile(REGEX_SUBSTITUTION).matcher(word).find()) {
			return;
		}

		calculate(word, 0, substitution);
	}
	
	/*
	 * Recursive method
	 */
	private void calculate(String word, final int position, ArrayList<String> substitution) {
		// Last character must be add to ArrayList
		if (position == word.length()) {
			substitution.add(word);
			return;
		}

		boolean foundSubstitution = false;
		for (int i = 0; i < SUBSTITUTION_EACH_PATTERN.length; i++) {
			if (SUBSTITUTION_EACH_PATTERN[i].contains(word.charAt(position) + "")) {
				for (int j = 0; j < SUBSTITUTION_PATTERN_MAPPING[i].length; j++) {
					StringBuilder sb = new StringBuilder(word);
					sb.setCharAt(position, SUBSTITUTION_PATTERN_MAPPING[i][j]);
					calculate(sb.toString(), position + 1, substitution);
				}
				foundSubstitution = true;
				break;
			}
		}

		// Skip founded string
		if (!foundSubstitution) {
			calculate(word, position + 1, substitution);
		}
	}
	
}
