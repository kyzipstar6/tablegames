package poker;

import javafx.scene.layout.HBox;

public class Player {
	Card card1;
	Card card2;
	HBox cards;
	public Player(Card card1, Card card2) {
		this.card1=card1;			this.card2=card2;

	}
	
	void setCards(Card card1, Card card2){
		this.card1=card1;			this.card2=card2;

	}
	Card [] getCards(){
		return new Card[]{card1,card2};
	}
}