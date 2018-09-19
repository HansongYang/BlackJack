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
		assertFalse(card.equals(card2));
	}
	
	public void testCardVisible() {
		BlackJack game = new BlackJack();
		game.console();
		assertEquals(2, game.player.count);
		assertEquals(2, game.dealer.count);
	}
	
	public void testCardDisplayed() {
		BlackJack game = new BlackJack();
		game.console();
		assertTrue(game.player.count>=2);
	}
	
	public void testPlayerHit() { // Testing player hits 
		BlackJack game = new BlackJack();
		game.console();
		assertTrue(game.player.count>=2);
	}
	
	public void testPlayerRepeatedHit() { // Testing player repeatedly hit 
		BlackJack game = new BlackJack();
		Card card = new Card(0,2);
		Card card2 = new Card(0,3);
		game.player.add(card);
		game.player.add(card2);
		assertEquals(2, game.player.count);
		game.PlayerHit();
		game.PlayerHit();
		game.PlayerHit();
		assertEquals(5, game.player.count);
	}
	
	public void testPlayerStand() { // Testing player stand 
		BlackJack game = new BlackJack();
		game.console();
		assertEquals(2, game.player.count);
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
	
	public void testDealerHit() { // Testing dealer hit with score <= 16
		BlackJack game = new BlackJack();
		Card card = new Card(0,2);
		Card card2 = new Card(0,3);
		
		game.dealer.add(card);
		game.dealer.add(card2);
		game.DealerHit();
		assertTrue(game.dealer.value()>7);
	}
	
	public void testDealerSoft17() { //Testing dealer hit with soft 17
		BlackJack game = new BlackJack();
		Card card = new Card(0,5);
		Card card2 = new Card(0,0);
		
		game.dealer.add(card);
		game.dealer.add(card2);
		assertEquals(17, game.dealer.value());
		game.DealerHit();
		assertTrue(game.dealer.value()>=17);
	}
	
	public void testDealerRepeatedHit() { //Testing dealer repeatedly hit
		BlackJack game = new BlackJack();
		int value = game.dealer.value();
		game.console();
		game.DealerHit();
		assertTrue(value < game.dealer.value());
	}
	
	public void testDealerBusted() { // Testing the dealer is busted
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,11);
		Card card3 = new Card(0,10);
		
		game.dealer.add(card);
		game.dealer.add(card2);
		game.dealer.add(card3);
		assertEquals(true, game.dealer.isBusted());
	}
	
	public void testAceValue1() { // Testing the value of ace, two aces one with value 11, one with value 1
		BlackJack game = new BlackJack();
		Card card = new Card(0,0); // Ace Card
		Card card2 = new Card(0,7); // 8
		Card card3 = new Card(2,0); //Ace Card
		
		game.player.add(card);
		game.player.add(card2);
		game.player.add(card3);
		assertEquals(20, game.player.value());
	}
	
	public void testAceValue2() {  // Testing the value of ace, two aces both with value 1
		BlackJack game = new BlackJack();
		Card card = new Card(0,0); // Ace Card
		Card card2 = new Card(0,7); // 8
		Card card3 = new Card(2,8); // 9
		Card card4 = new Card(2,0);
		
		game.player.add(card);
		game.player.add(card2);
		game.player.add(card3);
		game.player.add(card4);
		assertEquals(19, game.player.value());
	}
	
	public void testAceValue3() { //Testing the value of ace, two aces one changing value from 11 to 1
		BlackJack game = new BlackJack();
		Card card = new Card(0,0); // Ace Card
		Card card2 = new Card(0,4); // 5
		Card card3 = new Card(2,9); // 10
		Card card4 = new Card(3,0); //Ace Card
		
		game.player.add(card);
		game.player.add(card2);
		game.player.add(card4);
		assertEquals(17, game.player.value());
		game.player.add(card3);
		assertEquals(17, game.player.value());
	}
	
	public void testJQKValue() { // Testing the correctness of J,Q,K's value
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
	
	public void testPlayerBlackJack() {
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,0);
		game.player.add(card);
		game.player.add(card2);
		assertEquals(true, game.player.hasBlackJack());
	}
	
	public void testDealerBlackJack() {
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,0);
		game.dealer.add(card);
		game.dealer.add(card2);
		assertEquals(true, game.dealer.hasBlackJack());
	}
	
	public void testBlackJack() { // Testing the correctness of a player has BlackJack.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(0,0);
		game.player.add(card);
		game.player.add(card2);
		game.dealer.add(card);
		game.dealer.add(card2);
		
		assertEquals(true, game.player.hasBlackJack());
		assertEquals(true, game.dealer.hasBlackJack());		
		if(game.player.hasBlackJack() && game.dealer.hasBlackJack()) {
			System.out.println("Dealer wins!");
		}else if(game.player.hasBlackJack()) {
			System.out.println("Player wins!");
		}else if(game.dealer.hasBlackJack()) {
			System.out.println("Dealer wins!");
		}
	}
	
	public void testPlayerValue() { // Testing the correctness of a player's card value.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(1,12);
		Card card3 = new Card(1,0);
		game.player.add(card);
		game.player.add(card2);
		
		assertEquals(20, game.player.value());
		game.player.add(card3);
		assertEquals(21, game.player.value());
	}
	
	public void testDealerValue() { // Testing the correctness of a player's card value.
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(1,7);
		Card card3 = new Card(2,0);
		game.dealer.add(card);
		game.dealer.add(card2);
		
		assertEquals(18, game.dealer.value());
		game.dealer.add(card3);
		assertEquals(19,game.dealer.value());
	}
	
	public void testValue() {
		BlackJack game = new BlackJack();
		Card card = new Card(0,12);
		Card card2 = new Card(1,12);
		Card card3 = new Card(1,8);
		
		game.player.add(card);
		game.player.add(card2);
		game.dealer.add(card3);
		game.dealer.add(card);
		
		game.DealerHit();
	}
	
	public void testSplit() { // Testing the correctness of split action.
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
}
