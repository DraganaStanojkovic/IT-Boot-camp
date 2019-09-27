package zavrsni_rad;

public class Rec {
	private String word;
	private String wordType;
	private String definition;

	public Rec(String word, String wordType, String definition) {
		super();
		this.word=word;
		this.wordType = wordType;
		this.definition = definition;
	}

	public String getWord() {
		return word;
	}

	public String getWordType() {
		return wordType;
	}

	public String getDefinition() {
		return definition;
	}

}
