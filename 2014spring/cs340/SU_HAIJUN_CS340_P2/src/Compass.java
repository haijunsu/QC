/**
 *
 * @author Haijun Su
 *
 */
public class Compass extends BaseProject {

	private int id;

	private Forest forest;

	private String magicword;

	public Compass(int id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see BaseProject#getName()
	 */
	@Override
	public String getName() {
		return "Prince-" + id + "'s compass";
	}

	public boolean find() {
		int startIndex = getRandomNumber(0, forest.words.length / 10); //compass may skip some word from begin
		msg("Find word from index: " + startIndex);
		for (int i = startIndex; i < forest.words.length; i++) {
			if (magicword.equals(forest.words[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param forest
	 *            the forest to set
	 */
	public void setForest(Forest forest) {
		this.forest = forest;
	}

	/**
	 * @param magicword
	 *            the magicword to set
	 */
	public void setMagicword(String magicword) {
		this.magicword = magicword;
	}

}
