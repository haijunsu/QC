
public class Forest extends BaseProject {

	public static final String[] ALPHBET = new String[] { "a", "b", "c", "d" };

	public static final int WORD_LENGTH = 5;

	public String[] words = null;

	public Forest() {
		// generate words
		int amount = getRandomNumber(300, 600);
		msg("amount = " + amount);
		words = new String[amount];
		for (int i = 0; i < amount; i++) {
			int[] seq = getRandomNumberSequence(0, 4, WORD_LENGTH);
			words[i] = ALPHBET[seq[0]];
			for (int j = 1; j < seq.length; j++) {
				words[i] += ALPHBET[seq[j]];
			}
		}
	}

	@Override
	public String getName() {
		return "Forest";
	}

	public String[] getWords() {
		return words;
	}

}
