package comp3004;

public class Card {
	private int suit;
	private int rank;
	private static final String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    private static final String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    
	public Card(int suit, int rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	public int getRank() {
		if(rank == 0) {
			return 11;
		}else if(rank < 10) {
			return rank + 1;
		}else if(rank >= 10) {
			return 10;
		}else {
			return 0;
		}
	}
	
	public int getSuit() {
		return suit;
	}
	
	public String getRankName() {
		return ranks[rank];
	}
	
	public String getSuitName() {
		return suits[suit];
	}
	
	public String toString() {
		return ranks[rank]+suits[suit];
	}
}
