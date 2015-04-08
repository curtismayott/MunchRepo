package munchkin.com.objects;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import munchkin.com.DAO.CRAWHelper;
import munchkin.com.DAO.CreatureHelper;

public class Deck {
	Stack<Card> doorCards;
    int treasureCardCount;
    Stack<Card> treasureCards;
    int doorCardCount;
    Context context;
	public Deck(Context context){
		// database actions here
		doorCards = new Stack<Card>();
        doorCardCount = 0;
        treasureCards = new Stack<Card>();
        treasureCardCount = 0;
        this.context = context;
        init();
	}
    public void init(){
        CRAWHelper helper = new CRAWHelper(context);
        Cursor model = helper.getTreasureCards();
        model.moveToFirst();

        while(!model.isAfterLast()){
            if(helper.getCardType(model).equals("GEAR")){
                // String cardName, CARD_TYPE cardType, int bonusAmount, GearPositions gearPosition, int numHands, String classType
                treasureCards.add(new Gear(
                        helper.getCardName(model),
                        CARD_TYPE.GEAR,
                        helper.getCardImage(model),
                        helper.getBonus(model),
                        GearPositions.valueOf(helper.getGearPosition(model)),
                        helper.getNumHands(model),
                        helper.getClassType(model)
                ));
            }
            Log.e("Deck.java", treasureCardCount + " " + (treasureCards.get(treasureCardCount)).toString());
            treasureCardCount++;
            model.moveToNext();
        }
        model = helper.getDoorCards();
        model.moveToFirst();
        while(!model.isAfterLast()){
            if(helper.getCardType(model).equals("CLASS")){
                doorCards.add(new ClassCard(helper.getCardName(model), helper.getCardImage(model)));
            }else if(helper.getCardType(model).equals("RACE")){
                doorCards.add(new RaceCard(helper.getCardName(model), helper.getCardImage(model)));
            }
            doorCardCount++;
            model.moveToNext();
        }
        CreatureHelper cHelper = new CreatureHelper(context);
        model = cHelper.getCards();
        model.moveToFirst();
        while(!model.isAfterLast()){
            doorCards.add(new Creature(
                    cHelper.getCardName(model),
                    cHelper.getCardImage(model),
                    cHelper.getLevel(model),
                    BAD_STUFF.valueOf(cHelper.getBadStuff(model)),
                    cHelper.getRewardTreasure(model),
                    cHelper.getRewardLevel(model),
                    cHelper.getHatedClass(model),
                    cHelper.getHatedValue(model),
                    cHelper.getHatedEffect(model)
            ));
            doorCardCount++;
            model.moveToNext();
        }
        shuffle();
        Log.e("Deck.java :: init()", "Database CardNumbers: " + helper.getNumberCards());
    }
	public void shuffle(){
        long seed = System.nanoTime();
        Collections.shuffle(treasureCards, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(treasureCards, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(doorCards, new Random(seed));
        seed = System.nanoTime();
        Collections.shuffle(doorCards, new Random(seed));
    }
    public void discardCard(Card c){
        CARD_TYPE cardType = c.getCardType();
        if(cardType == CARD_TYPE.GEAR
                || cardType == CARD_TYPE.CREATURE_BUFF
                || cardType == CARD_TYPE.SPECIAL){
            Log.e("Deck.java discardCard", "Discarding Treasure: " + c.getCardName());
            discardTreasure(c);
        }else{
            Log.e("Deck.java discardCard", "Discarding Door: " + c.getCardName());
            discardDoor(c);
        }
    }
    public Card drawTreasure(){
        if(!treasureCards.isEmpty()) {
            Card card =  treasureCards.pop();
            Log.e("Deck :: drawTreasure()", "Treasure CardName: " + card.getCardName());
            return card;
        }else return null;
	}
    public ArrayList<Card> drawTreasure(int numCards){
        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < numCards; i++){
            Card c = drawTreasure();
            if(c != null) {
                cards.add(drawTreasure());
            }
        }
        return cards;
    }
    public void discardTreasure(Card card){ treasureCards.push(card); }
    public Card drawDoor(){
        if(!doorCards.isEmpty()){
            Card card = doorCards.pop();
            Log.e("Deck :: drawDoor()", "Door CardName: " + card.getCardName());
            return card;
        } else return null;
    }
    public ArrayList<Card> drawDoor(int numCards){
        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i = 0; i < numCards; i++){
            Card c = drawDoor();
            if(c != null){
                cards.add(drawDoor());
            }
        }
        return cards;
    }
    public void discardDoor(Card card){ doorCards.add(card); }
}
