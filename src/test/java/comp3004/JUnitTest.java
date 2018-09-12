package comp3004;

import junit.framework.TestCase;

public class JUnitTest extends TestCase{
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
	
	public void testPlayerGetCard() { // Testing the correctness of a card when players draw a card.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,11);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals("King", game.player.getFirstCard().getRankName());
		assertEquals("Queen", game.player.getLastCard().getRankName());
		
		Card card3 = new Card(0,10);
		Card card4 = new Card(0,9);
		game.player.add2(card3);
		game.player.add2(card4);
		
		assertEquals("Jack", game.player.getFirstCard2().getRankName());
		assertEquals("10", game.player.getLastCard2().getRankName());
		
		assertEquals("King", game.player.getCard(0).getRankName());
	}
	
	public void testAceValue() { // Testing the value of ace in different situations.
		BlackJack game = new BlackJack();
		Card card = new Card(0,0); //Ace Card
		Card card2 = new Card(0,6);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals(17, game.player.value());
		
		game.player.add(card2);
		assertEquals(13, game.player.value());
	}
}
