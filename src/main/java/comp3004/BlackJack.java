package comp3004;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BlackJack {
	public Hand player;
	public Hand dealer;
	public Deck deck;
	boolean stop = false;
	boolean second = false;
	Scanner sc = new Scanner(System.in);
	String choose = "";
	String playerSuit = "",playerRank = "", dealerSuit = "", dealerRank = "";
	int suit = 0, rank = 0;
	
	public BlackJack() {
		deck = new Deck();
		deck.shuffle();
		player = new Hand();
		dealer = new Hand();
	}
	
	public void console() {
		player.add(deck.next());
		System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".");
		player.add(deck.next());
		System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".\n");
		dealer.add(deck.next());
		System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + ".");
		dealer.add(deck.next());
		System.out.println("Dealer has one card face down.");
		System.out.println("Player's current score: " + player.value() +". \n");
		if(dealer.hasBlackJack()) {
			System.out.println("Dealer has blackjack. Dealer's score is " + dealer.value() + ".");
			return;
		} else if(player.hasBlackJack() && !dealer.hasBlackJack()) {
			System.out.println("The player has blackjack. Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
			return;
		}
		game();
	}
	
	public void file() {
		System.out.println("Please input a file's path.");
		choose = sc.nextLine();
	
		BufferedReader br = null;
		String line = "";
		try {                                                                                                                                                                                                                                                                             
			br = new BufferedReader(new FileReader(choose));                                                                                                                                                                                                                                                                                                                                                                                                                                                             
			if((line = br.readLine()) != null) {                                                                                                                                                                                                                                        
				System.out.println("File content: " + line);
			}                                                                                                                                                                                                                                                                               
		} catch(FileNotFoundException e) {                                                                                                                                                                                                                                                
			 System.err.println("File not found: " + e.getMessage());  
			 return;
		} catch(IOException e) {                                                                                                                                                                                                                                                          
			 System.err.println("Caught IOException: " + e.getMessage()); 
			 return;
		}finally {                                                                                                                                                                                                                                                                        
			if (null != br) { 
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					return;
				} 
			}                                                                                                                                                                                                                                                 
		}
		game2(line);
	}
	
	public void PlayerHit() {
		if(player.splitted) {
			if(second) {
				player.add2(deck.next());
				System.out.println("Player receives " + player.getLastCard2().getRankName() + " of " + player.getLastCard2().getSuitName() + ".");
				System.out.println("Player's second stack current score: " + player.value2() + ".");
				if(player.isBusted2()) {
					System.out.println("Player's second stack busted.  Player's second stack score is " + player.value2() + ".");
				}
			}else {
				player.add(deck.next());
				System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".");
				System.out.println("Player's first stack current score: " + player.value() + ".");
				if(player.isBusted()) {
					System.out.println("Player's first stack busted. Player's first stack score is " + player.value() + ".");
				}
			}
		}else {
			player.add(deck.next());
			System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + ".");
			System.out.println("Player's current score: " + player.value() + ".");
			if(player.isBusted()) {
				System.out.println("Player busted. Dealer Wins! Dealer's score is " + dealer.value() + ". Player's score is " + player.value() + ".");
				stop = true;
			}
		}
	}
	
	public void DealerHit() {
		if(player.isBusted() && player.isBusted2()) {
			System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
			return;
		}
		System.out.println("Dealer has " + dealer.getFirstCard().getRankName() + " of " + dealer.getFirstCard().getSuitName() + " and "+ dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + ".");
		while(true) {
			if(dealer.value() <= 16 || (dealer.value() == 17 && (dealer.getFirstCard().getRankName().equals("Ace") || dealer.getLastCard().getRankName().equals("Ace")))) {
				System.out.println("Dealer choose to Hit!");
				dealer.add(deck.next());
				System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + ".");
				System.out.println("Dealer's current score: " + dealer.value() + ".");
				if(dealer.isBusted()) {
					System.out.println("Dealer busted. Player wins! Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
					stop = true;
					return;
				}
			} else {
				System.out.println("Dealer choose to stand!");
				if(player.splitted) {
					if(player.isBusted() && dealer.value() >= player.value2()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
					}else if(player.isBusted2() && dealer.value() >= player.value()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
					}else if(dealer.value() >= player.value() && dealer.value() >= player.value2()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
					}else if(player.isBusted() && player.value2() > dealer.value()) {
						System.out.println("Player's second stack wins. Player's second stack score is " + player.value2() + ". Dealer's score is " + dealer.value() + ".");
					}else if(player.isBusted2() && player.value() > dealer.value()) {
						System.out.println("Player's first stack wins. Player's first stack score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
					}else {
						if(player.value() > player.value2()) {
							System.out.println("Player's first stack wins. Player's first stack score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
						}else {
							System.out.println("Player's second stack wins. Player's second stack score is " + player.value2() + ". Dealer's score is " + dealer.value() + ".");
						}
					}
					stop = true;
					return;
				}else {
					if(dealer.value() >= player.value()) {
						System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's score is " + player.value() + ".");
					}else {
						System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
					}
					stop = true;
					return;
				}
			}
		}
	}
	
	public void game() {
		while(!stop) {
			if(player.split()) {
				System.out.println("Please choose to Hit (H) or Stand (S) or Split (D).");
			}else {
				System.out.println("Please choose to Hit (H) or Stand (S).");
			}
			choose = sc.nextLine();
			if(choose.equals("H") || choose.equals("h") ) {
				PlayerHit();
			}else if (choose.equals("S") || choose.equals("s") ) {
				if(dealer.value() <= 17 && dealer.split()) {
					split2();
				}else {
					DealerHit();
				}
			}else if (choose.equals("D") || choose.equals("d")) {
				split();
			}else {
				System.out.println("Wrong input. Please input again.");
			}
		}
	}
	
	public void split() {
		System.out.println("Player choose to split.");
		player.add(deck.next());
		System.out.println("Player receives " + player.getLastCard().getRankName() + " of " + player.getLastCard().getSuitName() + " for the first stack.");
		System.out.println("First Stack: Player's current score: " + player.value() + ". \n");
		
		while(!stop) {
			if(!player.isBusted()) {
				System.out.println("Please choose to Hit (H) or Stand (S) for the first stack.");
				choose = sc.nextLine();
			}
			if((choose.equals("H") || choose.equals("h")) && !player.isBusted()) {
				PlayerHit();
			} else if (choose.equals("S") || choose.equals("s") || player.isBusted() || player.hasBlackJack()) {
				player.add2(deck.next());
				System.out.println("Player receives " + player.getLastCard2().getRankName() + " of " + player.getLastCard2().getSuitName() + " for the second stack.");
				System.out.println("Second Stack: Player's current score: " + player.value2() + ". \n");				
				
				while(!stop) {
					if(!player.isBusted2()) {
						System.out.println("Please choose to Hit (H) or Stand (S) for the second stack.");
						choose = sc.nextLine();
					}
					if((choose.equals("H") || choose.equals("h")) && !player.isBusted2()) {
						second = true;
						PlayerHit();
						second = false;
					}else if (choose.equals("S") || choose.equals("s") || player.isBusted2()) {
						DealerHit();
					}else {
						System.out.println("Wrong input. Please input again.");
					}
				}
			}else {
				System.out.println("Wrong input. Please input again.");
			}
		}
	}
	
	public void split2() {
		System.out.println("Dealer choose to split.");
		dealer.add(deck.next());
		System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + " for the first stack.");
		System.out.println("First Stack: Dealer's current score: " + dealer.value() + ". \n");
		
		while(true) {
			if(dealer.value() <= 16 || (dealer.value() == 17 && (dealer.getFirstCard().getRankName().equals("Ace") || dealer.getLastCard().getRankName().equals("Ace")))) {
				System.out.println("Dealer choose to Hit for the first stack!");
				dealer.add(deck.next());
				System.out.println("Dealer receives " + dealer.getLastCard().getRankName() + " of " + dealer.getLastCard().getSuitName() + " for the first stack.");
				System.out.println("Dealer's first stack current score: " + dealer.value() + ".");
				if(dealer.isBusted()) {
					System.out.println("Dealer's first stack busted.");
				}
			} else if (dealer.value2() <= 16 || (dealer.value2() == 17 && (dealer.getFirstCard2().getRankName().equals("Ace") || dealer.getLastCard2().getRankName().equals("Ace")))) {
				if(dealer.count2 == 1){
					dealer.add2(deck.next());
					System.out.println("Dealer receives " + dealer.getLastCard2().getRankName() + " of " + dealer.getLastCard2().getSuitName() + " for the second stack.");
					System.out.println("Second Stack: Dealer's current score: " + dealer.value2() + ". \n");
				}else{
					System.out.println("Dealer choose to Hit for the second stack!");
					dealer.add2(deck.next());
					System.out.println("Dealer receives " + dealer.getLastCard2().getRankName() + " of " + dealer.getLastCard2().getSuitName() + " for the second stack.");
					System.out.println("Dealer's second stack current score: " + dealer.value2() + ".");
				}
				if(dealer.isBusted2()) {
					System.out.println("Dealer's second stack busted.");
				}
			} else {
				if(dealer.isBusted() && dealer.isBusted2()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(dealer.isBusted() && player.value() > dealer.value2()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(dealer.isBusted2() && player.value() >= dealer.value()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(player.value() >= dealer.value() && player.value() >= dealer.value2()) {
					System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
				}else if(dealer.isBusted() && dealer.value2() >= player.value()) {
					System.out.println("Dealer's second stack wins. Dealer's second stack score is " + dealer.value2() + ". Player's score is " + player.value() + ".");
				}else if(dealer.isBusted2() && dealer.value() >= player.value()) {
					System.out.println("Dealer's first stack wins. Dealer's first stack score is " + dealer.value() + ". Player's score is " + player.value() + ".");
				}else {
					if(dealer.value() > dealer.value2()) {
						System.out.println("Dealer's first stack wins. Dealer's first stack score is " + dealer.value() + ". Player's score is " + player.value() + ".");
					}else {
						System.out.println("Dealer's second stack wins. Dealer's second stack score is " + dealer.value2() + ". Player's score is " + player.value() + ".");
					}
				}
				stop = true;
				break;
			}
		}
	}
	
	public void Suit(char i) {
		switch(i) {
			case 'S': playerSuit = "Spades"; suit = 3; break;
			case 'C': playerSuit = "Clubs"; suit = 0; break;
			case 'H': playerSuit = "Hearts"; suit = 2; break;
			case 'D': playerSuit = "Diamonds"; suit = 1; break;
			default: playerSuit = "Unknown";
		}
	}
	
	public boolean Rank(char i) {
		switch(i) {
			case 'A': playerRank = "Ace"; rank = 0; break;
			case 'J': playerRank = "Jack"; rank = 10; break;
			case 'Q': playerRank = "Queen"; rank = 11; break;
			case 'K': playerRank = "King"; rank = 12; break;
			case '2': 
			case '3': 
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9': rank = Character.getNumericValue(i) - 1; playerRank = String.valueOf(i); break;
			case '1': playerRank = "10"; rank = 9; break;
			default: playerRank = "Unknown";
		}
		if(rank == 9) {
			return true;
		}
		return false;
	}
	
	public void Suit2(char i) {
		switch(i) {
			case 'S': dealerSuit = "Spades"; suit = 3; break;
			case 'C': dealerSuit = "Clubs"; suit = 0; break;
			case 'H': dealerSuit = "Hearts"; suit = 2; break;
			case 'D': dealerSuit = "Diamonds"; suit = 1; break;
			default: dealerSuit = "Unknown";
		}
	}
	
	public boolean Rank2(char i) {
		switch(i) {
			case 'A': dealerRank = "Ace"; rank = 0; break;
			case 'J': dealerRank = "Jack"; rank = 10; break;
			case 'Q': dealerRank = "Queen"; rank = 11; break;
			case 'K': dealerRank = "King"; rank = 12; break;
			case '2': 
			case '3': 
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9': rank = Character.getNumericValue(i) - 1; dealerRank = String.valueOf(i); break;
			case '1': dealerRank = "10"; rank = 9; break;
			default: dealerRank = "Unknown";
		}
		if(rank == 9) {
			return true;
		}
		return false;
	}
	
	public void game2(String line) {
		int i = 0;

		Suit(line.charAt(i));

		if(playerSuit.equals("Unknown")) {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		i++;
		
		if(Rank( line.charAt(i))) {
			i++;
		}

		i++;
		
		if(playerRank.equals("Unknown")  || line.charAt(i) != ' ') {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		
		i++;
		Card a = new Card(suit,rank);
		player.add(a);
		System.out.println("Player receives the " + playerRank + " of "+ playerSuit + ".");
		
		Suit(line.charAt(i));
		
		if(playerSuit.equals("Unknown")) {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		
		i++;
		
		if(Rank(line.charAt(i))) {
			i++;
		}
		
		i++;
		
		if(playerRank.equals("Unknown") || line.charAt(i) != ' ') {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		Card b = new Card(suit,rank);
		player.add(b);
		System.out.println("Player receives the " + playerRank + " of "+ playerSuit + ". Player's current score is " + player.value() + ".");
		i++;
		
		if(player.getFirstCard().getRankName().equals(player.getLastCard().getRankName()) && player.getFirstCard().getSuitName().equals(player.getLastCard().getSuitName())) {
			System.out.println("This card has already played.");
			return;
		}
		
		Suit2(line.charAt(i));
	
		if(dealerSuit.equals("Unknown")) {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		i++;
		
		if(Rank2(line.charAt(i))) {
			i++;
		}
		i++;
		
		if(dealerRank.equals("Unknown") || line.charAt(i) != ' ') {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		Card c = new Card(suit,rank);
		dealer.add(c);
		System.out.println("Dealer receives the " + dealerRank + " of " + dealerSuit + ".");
		i++;
		
		Suit2(line.charAt(i));
	
		if(dealerSuit.equals("Unknown")) {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		i++;
		
		if(Rank2(line.charAt(i))) {
			i++;
		}
		i++;
		
		if(dealerRank.equals("Unknown")) {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		
		Card d = new Card(suit, rank);
		dealer.add(d);
		System.out.println("Dealer receives the " + dealerRank + " of " + dealerSuit + ". Dealer's current score is " + dealer.value() + ".\n"); 

		if(dealer.getFirstCard().getRankName().equals(dealer.getLastCard().getRankName()) && dealer.getFirstCard().getSuitName().equals(dealer.getLastCard().getSuitName())) {
			System.out.println("This card has already played.");
			return;
		}
		
		if(player.hasBlackJack()) {
			System.out.println("Player has Blackjack. Player's score is " + player.value() + ".");
		}
		if(dealer.hasBlackJack()) {
			System.out.println("Dealer has Blackjack. Dealer's score is " + dealer.value() + ". Dealer wins.");
		}
		if(i == line.length()) {
			return;
		}else if(line.charAt(i) != ' ') {
			System.out.println("The content of this file is incorrect.");
			return;
		}
        i++;
        
		while(true) {
			if(line.charAt(i) == 'H') {
				i += 2;
				if(player.splitted) {
					Suit(line.charAt(i));
					i++;
					if(Rank(line.charAt(i))) {
						i++;
					}
					i++;
					Card card = new Card(suit,rank);
					player.add(card);
					System.out.println("Player choose to hit and receives " + playerRank + " of "+ playerSuit + " for the first stack. Player's first stack current score is " + player.value() + ".");
				}else {
					Suit(line.charAt(i));
					i++;
					if(Rank(line.charAt(i))) {
						i++;
					}
					i++;
					Card card = new Card(suit,rank);
					player.add(card);
					System.out.println("Player choose to hit and receives " + playerRank + " of "+ playerSuit + ". Player's current score is " + player.value() + ".");
				}
			}else if(line.charAt(i) == 'S') {
				if(player.splitted) {
					System.out.println("Player choose to stand for the first stack.");
					i += 2;
					while(true) {
						if(line.charAt(i) == 'S') {
							System.out.println("Player choose to stand for the second stack.");
							break;
						}else if (line.charAt(i) == 'H') {
							i+=2;
							Suit(line.charAt(i));
							i++;
							if(Rank(line.charAt(i))) {
								i++;
							}
							i++;
							Card card = new Card(suit,rank);
							player.add2(card);
							System.out.println("Player choose to hit and receives " + playerRank + " of "+ playerSuit + " for the second stack. Player's second stack current score is " + player.value2() + ".");
						}else {
							Suit(line.charAt(i));
							i++;
							if(Rank(line.charAt(i))) {
								i++;
							}
							i++;
							Card card = new Card(suit,rank);
							player.add2(card);
							System.out.println("Player choose to hit and receives " + playerRank + " of "+ playerSuit + " for the second stack. Player's second stack current score is " + player.value2() + ".");
						}
						if(i < (line.length()-1)) {
							i++;
						}else {
							break;
						}
					}
				}else {
					System.out.println("Player choose to stand.");
					if(dealer.getFirstCard().getRankName().equals(dealer.getLastCard().getRankName())) {
						dealer.split();
						i += 2;
						Suit2(line.charAt(i));
						i++;
						if(Rank2(line.charAt(i))) {
							i++;
						}
						Card card = new Card(suit,rank); 
						dealer.add(card); 
						System.out.println("Dealer receives " + dealerRank + " of "+ dealerSuit + " for the first stack. Dealer's first stack current score is "+ dealer.value() + ".");
						
						while(true) {
							i += 2;
							if(!dealer.isBusted()){
								Suit2(line.charAt(i));
								i++;
								if(Rank2(line.charAt(i))) {
									i++;
								}
								Card s = new Card(suit,rank); 
								dealer.add(s); 
								System.out.println("Dealer choose to hit and receives " + dealerRank + " of "+ dealerSuit + " for the first stack. Dealer's first stack current score is "+ dealer.value() + ".");
							}else {
								Suit2(line.charAt(i));
								i++;
								if(Rank2(line.charAt(i))) {
									i++;
								}
								Card s = new Card(suit,rank); 
								dealer.add2(s); 
								System.out.println("Dealer choose to hit and receives " + dealerRank + " of "+ dealerSuit + " for the second stack. Dealer's second stack current score is "+ dealer.value2() + ".");
								if(i == (line.length()-1)){
									break;
								}
							}
						}
					}else {
						while(true) {
							i += 2;
							Suit2(line.charAt(i));
							i++;
							if(Rank2(line.charAt(i))) {
								i++;
							}
							Card card = new Card(suit,rank); 
							dealer.add(card); 
							System.out.println("Dealer choose to hit and receives " + dealerRank + " of "+ dealerSuit + ". Dealer's current score is "+ dealer.value() + ".");
							if(i == (line.length()-1)) {
								break;
							}
						}
					}
				}
			}else if(line.charAt(i) == 'D') {
				System.out.println("Player choose to split.");
				player.split();
				i += 2;
				Suit(line.charAt(i));
				i++;
				if(Rank(line.charAt(i))) {
					i++;
				}
				i++;
				Card card = new Card(suit,rank);
				player.add(card);
				System.out.println("Player receives " + playerRank + " of "+ playerSuit + " for the first stack. Player's current score is " + player.value() + ".");
			}
		
			if(i < (line.length()-1)) {
				i++;
			}else {
				break;
			}
		}
		
		if(playerSuit.equals("Unknown") || playerRank.equals("Unknown") || dealerSuit.equals("Unknown") || dealerRank.equals("Unknown")) {
			System.out.println("The content of this file is incorrect.");
			return;
		}
		
		if(player.splitted) {
			if(player.isBusted() && player.isBusted2()) {
				System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
			}else if(player.isBusted() && dealer.value() >= player.value2()) {
				System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
			}else if(player.isBusted2() && dealer.value() >= player.value()) {
				System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
			}else if(dealer.value() >= player.value() && dealer.value() >= player.value2()) {
				System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's first stack score is " + player.value() + ". Player's second stack score is " + player.value2() + ".");
			}else if(player.isBusted() && player.value2() > dealer.value()) {
				System.out.println("Player's second stack wins. Player's second stack score is " + player.value2() + ". Dealer's score is " + dealer.value() + ".");
			}else if(player.isBusted2() && player.value() > dealer.value()) {
				System.out.println("Player's first stack wins. Player's first stack score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
			}else {
				if(player.value() > player.value2()) {
					System.out.println("Player's first stack wins. Player's first stack score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
				}else {
					System.out.println("Player's second stack wins. Player's second stack score is " + player.value2() + ". Dealer's score is " + dealer.value() + ".");
				}
			}
		}else if(dealer.splitted) {
			if(dealer.isBusted() && dealer.isBusted2()) {
				System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
			}else if(dealer.isBusted() && player.value() > dealer.value2()) {
				System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
			}else if(dealer.isBusted2() && player.value() >= dealer.value()) {
				System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
			}else if(player.value() >= dealer.value() && player.value() >= dealer.value2()) {
				System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's first stack score is " + dealer.value() + ". Dealer's second stack score is " + dealer.value2() + ".");
			}else if(dealer.isBusted() && dealer.value2() >= player.value()) {
				System.out.println("Dealer's second stack wins. Dealer's second stack score is " + dealer.value2() + ". Player's score is " + player.value() + ".");
			}else if(dealer.isBusted2() && dealer.value() >= player.value()) {
				System.out.println("Dealer's first stack wins. Dealer's first stack score is " + dealer.value() + ". Player's score is " + player.value() + ".");
			}else {
				if(dealer.value() > dealer.value2()) {
					System.out.println("Dealer's first stack wins. Dealer's first stack score is " + dealer.value() + ". Player's score is " + player.value() + ".");
				}else {
					System.out.println("Dealer's second stack wins. Dealer's second stack score is " + dealer.value2() + ". Player's score is " + player.value() + ".");
				}
			}
		}else {
			if(player.isBusted()) {
				System.out.println("Player busted. Dealer wins. Dealer's score is " + dealer.value() + ". Player's score is " + player.value() + ".");
			}else if(dealer.isBusted()) {
				System.out.println("Dealer busted. Player wins. Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
			}else if(dealer.value() >= player.value()) {
				System.out.println("Dealer wins. Dealer's score is " + dealer.value() + ". Player's score is " + player.value() + ".");
			}else {
				System.out.println("Player wins. Player's score is " + player.value() + ". Dealer's score is " + dealer.value() + ".");
			}
		}
	}
	
	public boolean input(String choose) {
		if(choose.equals("c") || choose.equals("C") ) {
			console();
		}
		else if(choose.equals("f") || choose.equals("F") ) {
			file();
		}
		else {
			System.out.println("Wrong input, please input again.");
			start();
			return false;
		}
		return true;
	}
	
	public void start() {
		System.out.println("Please select console (c) input or file (f) input.");
		String choose = sc.nextLine();
		input(choose);
	}
	
	public void main(String[] args){
		new BlackJack();
		start();
	}
}
