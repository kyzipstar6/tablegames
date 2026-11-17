package poker;

public class Card{
	String stick;
	int stickN;
	public Card(String stick, int stickN) {
		this.stick= stick; this.stickN=stickN;
	}
	String character() {
		return stick;
	}
	int stick() {
		return stickN;
	}
}
