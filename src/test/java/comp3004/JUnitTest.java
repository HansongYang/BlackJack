package comp3004;

import junit.framework.TestCase;

public class JUnitTest extends TestCase{
	public void testCardNumber() { // Testing the number of cards
		BlackJack game = new BlackJack();
		assertEquals(52, game.deck.cards.length);
	}
	
	public void testShuffle() { // Testing shuffling procedure
		BlackJack game = new BlackJack();
		Card card = game.deck.cards[0];
		game.deck.shuffle();
		Card card2 = game.deck.cards[0];
		assertEquals(card, card2);
	}
	
	public void testFileInput() { // Testing file input
		BlackJack game = new BlackJack();
		game.start();
	}
	
	public void testConsoleInput() { // Testing console input
		BlackJack game = new BlackJack();
		game.start();
	}
	
	public void testCardVisibility() { // Testing the card visibility of player and dealer.
		BlackJack game = new BlackJack();
		game.console();
	}
	
	public void testGame() { // Testing the game which includes player hits, player stands, player busts, dealer hits, dealer stands, dealer busts, soft 17.   
		BlackJack game = new BlackJack();
		game.console();
	}
	
	public void testPlayerBusted() { // Testing the correctness of a player has busted.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,11);
		Card card3 = new Card(0,10);
		
		game.player.add(card);
		game.player.add(card2);
		game.player.add(card3);
		assertEquals(true, game.player.isBusted());
		
		game.player.add2(card2);
		game.player.add2(card3);
		assertEquals(false, game.player.isBusted2());
	}
	
	public void testPlayerBlackJack() { // Testing the correctness of a player has BlackJack.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,0);
		Card card3 = new Card(0, 11);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals(true, game.player.hasBlackJack());
		
		game.player.add2(card);
		game.player.add2(card3);
		
		assertEquals(false, game.player.hasBlackJack2());		
	}
	
	public void testPlayerSplit() { // Testing the correctness of split action.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(1,12);
		Card card3 = new Card(0,8);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals(true, game.player.split());

		game.player.add(card3);
		
		assertEquals(false, game.player.split());
		
		game.dealer.add(card);
		game.dealer.add(card2);
		
		assertEquals(true, game.dealer.split());

		game.dealer.add(card3);
		
		assertEquals(false, game.dealer.split());
	}
	
	public void testPlayerValue() { // Testing the correctness of a player's card value.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(1,12);
		Card card3 = new Card(1,8);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals(20, game.player.value());
		game.player.add2(card3);
		game.player.add2(card2);
		assertEquals(19, game.player.value2());
	}
	
	public void testCardValue() { // Testing the correctness of J,Q,K's value
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,11);
		Card card3 = new Card (0,10);
		game.player.add(card);
		game.player.add(card2);
		game.player.add(card3);
		
		assertEquals(10, game.player.getFirstCard().getRank());
		assertEquals(10, game.player.getLastCard().getRank());
		assertEquals(10, game.player.getCard(1).getRank());
	}
	
	public void testAceValue() { // Testing the value of ace in different situations.
		BlackJack game = new BlackJack();
		Card card = new Card(0,0); //Ace Card
		Card card2 = new Card(0,6); // 7
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals(18, game.player.value());
		
		game.player.add(card2);
		assertEquals(8, game.player.value());
		
		game.player.add(card2);
		game.player.add(card);
		assertEquals(16, game.player.value());
	}
}
