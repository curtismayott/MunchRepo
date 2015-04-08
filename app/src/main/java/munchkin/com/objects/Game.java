package munchkin.com.objects;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
	// key = player name
	// value = player object
	HashMap<Integer,Player> players;
	Deck decks;
    ArrayList<Card> activeCreatures;
	public Game(Context context){
		players = new HashMap<Integer,Player>();
		// first key is card name
		// second key is 
        decks = new Deck(context);
        activeCreatures = new ArrayList<Card>();
	}
    public int getNumberPlayers(){
        int count = 0;
        for(int i : players.keySet()){
            count++;
        }
        return count;
    }
    public boolean addPlayer(Player p, int playerNumber){
        players.put(playerNumber, p);
        return true;
    }
    public Player getPlayer(int playerNumber){ return players.get(playerNumber); }
    public void drawTreasure(int playerNumber, int numCards){
        if(!decks.treasureCards.isEmpty()) {
            Log.e("Game.java :: drawTreas", "Drawing");
            (players.get(playerNumber)).addInventory(decks.drawTreasure(numCards));
        }else Log.e("Game.java :: drawTreas", "Deck empty");
    }
    public void drawDoor(int playerNumber, int numCards){
        if(!decks.doorCards.isEmpty()) {
            Log.e("Game.java :: drawDoor", "Drawing");
            (players.get(playerNumber)).addInventory(decks.drawDoor(numCards));
        }else Log.e("Game.java :: drawDoor", "Deck empty");
    }
    public void discardCard(int playerNumber, Card c){
        (players.get(playerNumber)).removeInventory(c);
        decks.discardCard(c);
    }






    public int getPlayerBonusValue(int playerNumber){
        return (players.get(playerNumber)).getBonusValue();
    }
    public ArrayList<Card> getPlayerClassRaceCards(int playerNumber){
        Log.e("Game.java", "P#: " + playerNumber);
        Log.e("Game.java", "PlayerName: " + (players.get(playerNumber)).getPlayerName());
        ArrayList<Card> cards = (players.get(playerNumber)).getClassRaceCards();
        if(cards != null && !cards.isEmpty()) return cards;
        else return null;
    }
    public ArrayList<Card> getGear(int playerNumber){
        ArrayList<Card> cards = (players.get(playerNumber)).getGear();
        if(cards != null && !cards.isEmpty()) return cards;
        else return null;
    }
    public ArrayList<Card> getInventory(int playerNumber){
        ArrayList<Card> cards = (players.get(playerNumber)).getInventory();
        if(cards != null && !cards.isEmpty()) return cards;
        else return null;
    }
    public ArrayList<Card> getEffects(int playerNumber){
        ArrayList<Card> cards = (players.get(playerNumber)).getEffects();
        if(cards != null && !cards.isEmpty()) return cards;
        else return null;
    }


    public ArrayList<Card> getActiveCreatures(){
        return activeCreatures;
    }
    public boolean kickOpenTheDoor(int playerNumber){
        Card card = decks.drawDoor();
        if(card.getCardType() == CARD_TYPE.CREATURE) {
            activeCreatures.add(card);
            return true;
        }else if(card.getCardType() == CARD_TYPE.CURSE){
            // handle curse card
            return true;
        }else{
            ArrayList<Card> cards = new ArrayList<Card>();
            cards.add(card);
            (players.get(playerNumber)).addInventory(cards);
            return false;
        }
    }









}
