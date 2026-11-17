package poker;
import java.util.*;

public class Posibilities extends Main {
	public static void main(String[] args) {
		final String [] cards = "A,2,3,4,5,6,7,8,9,T,J,Q,K".split(",");
		int players = 6;
		final int nocards = cards.length-1*4;
		List<Card> cds = new ArrayList<>();
		double nextCardPair = 100/cards.length;
		double nextCardSuited = 1/nocards;
		System.out.println("You have the odds of a pair preflop of "+nextCardPair+"%");
		for (int i = 7;i<10;i++) {
		}
		double nextCardGraded = (100/((nocards)*cards.length))*2;
		System.out.println("You have the odds of a graded pair of cards "+nextCardGraded+"%");
		double setflop = ( (nextCardPair = 100/cards.length-1))*(nextCardPair = 100/cards.length-2)*(nextCardPair = 100/cards.length-3); 
		double setturn = setflop *(nextCardPair = 100/cards.length-4);
		double setriver = setturn *(nextCardPair = 100/cards.length-5);
		System.out.println("If you have pairs preflop, you have chances "+setflop+"% of making a set on the flop, "+
		setturn+"% of making a set on the turn, "+setriver+"% of making a set on the flop. ");
		String flushchances = "If you have cards of the same flush preflop ";
		int acum = 0;
		double threesameflop = nextCardSuited/3;
		double threesameturn = threesameflop * nextCardSuited;
		double threesameriver = threesameturn * nextCardSuited;
		double [] flushchancesperc = {threesameflop,threesameturn,threesameriver}
		;
		String [] stages = "Flop,Turn,River".split(",");
		for (int i = 0;i<flushchancesperc.length;i++) {
			double d = flushchancesperc[i];
			flushchances += " you have chances of " + d + " of getting a flush in the " +stages[i];
		}
		String straightflushchances = "If you have cards of the same flush and they are graded preflop ";
		double straightflushflop = (nextCardGraded*nextCardSuited)/3;
		double straightflushturn = straightflushflop *(nextCardGraded*nextCardSuited);
		double straightflushriver = straightflushturn *(nextCardGraded*nextCardSuited);
		double [] straightflushchancesperc = {straightflushflop,straightflushturn,straightflushriver};
		for (int i = 0;i<straightflushchancesperc.length;i++) {
			double d = straightflushchancesperc[i];
			straightflushchances += " you have chances of " + d + " of getting a flush in the " +stages[i];
		}


	}
	void calculateWinningChances(Hand [] oponenthands) {
		List<Card> cards = shuffle(shuffleForFirstTime());
	}
}
