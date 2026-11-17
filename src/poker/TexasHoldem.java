package poker;

public class TexasHoldem {
	final String [] cards = "A,2,3,4,5,6,7,8,9,T,J,Q,K".split(",");
	final int nocards = cards.length-1*4;
	final int MAX_PLAYERS_ON_DECK = 9;
	static class Game{
		int playersInGame;
		
	}
	
	
	public enum Sticks{
		HEARTS,
		SPADES,
		DIAMONDS,
		CLUBS
	}
	
}
