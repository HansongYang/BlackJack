package comp3004;

import junit.framework.TestCase;

public class JUnitTest extends TestCase{
	public void testPlayerBusted() {
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
	
	public void testPlayerBlackJack() {
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
	
	public void testPlayerSplit() {
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
	
	public void testPlayerValue() {
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
	
	public void testPlayerGetCard() {
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,11);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals("Correct", "King", game.player.getFirstCard().getRankName());
		assertEquals("Correct", "Queen", game.player.getLastCard().getRankName());
		
		Card card = new Card(0,10);
		Card card2 = new Card(0,9);
		game.player.add2(card);
		game.player.add2(card2);
		
		assertEquals("Jack", game.player.getFirstCard().getRankName());
		assertEquals("10", game.player.getLastCard().getRankName());
	}
}
