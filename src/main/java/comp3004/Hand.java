package comp3004;

import java.awt.*;
import java.util.*;

public class Hand{
	public int count = 0;
	public int count2 = 0;
	private Card[] hands;
	private Card[] hands2;
	boolean splitted = false;
	
	public Hand() {
		hands = new Card[20];
		hands2 = new Card[20];
	}
	
	public void add(Card card){
		hands[count++] = card;
	}
	
	public void add2(Card card) {
		hands2[count2++] = card;
	}
	
	public Card getLastCard(){
		return hands[count-1];
	}
	
	public Card getLastCard2() {
		return hands2[count2-1];
	}
	
	public Card getFirstCard() {
		return hands[0];
	}
	
	public Card getFirstCard2() {
		return hands2[0];
	}
	
	public Card getCard(int i) {
		if(i >= count) {
			return null;
		}
		return hands[i];
	}
	
	public boolean split() {
		if(hands[0].getRankName().equals(hands[1].getRankName())){
			hands2[0] = hands[1];
			hands[1] = null;
			count--;
			count2++;
			splitted = true;
		    return true;
		}
		return false;
	}
	
	public int value(){
		int sum = 0;
		int rank = 0;
		int counter = count;
		int aces = 0;
		for(int i = 0; i< counter; i++){
			rank = hands[i].getRank();
			sum += rank;
			if( rank == 11){
				aces++;
			}
		}
		while (aces > 0 && sum > 21){
			sum -= 10;
			aces--;
		}		
		return sum;
	}
	
	public int value2() {
		int sum = 0;
		int rank = 0;
		int counter = count2;
		int aces = 0;
		for(int i = 0; i< counter; i++){
			rank = hands2[i].getRank();
			sum += rank;
			if( rank == 11){
				aces++;
			}
		}
		while (aces > 0 && sum > 21){
			sum -= 10;
			aces--;
		}		
		return sum;
	}
	
	public boolean hasBlackJack(){
		int total = hands[0].getRank() + hands[1].getRank();
		return total == 21;
	}
	
	public boolean hasBlackJack2(){
		int total = hands2[0].getRank() + hands2[1].getRank();
		return total == 21;
	}


	public boolean isBusted(){
		return value() > 21;
	}
	
	public boolean isBusted2() {
		return value2() > 21;
	}
}
