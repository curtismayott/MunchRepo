package munchkin.com.objects;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player{
    String name;
    Gender gender;
    boolean isComputer;
    int level;
    int handCapacity;
    ArrayList<Card> gear;
    ArrayList<Card> inventory;
	ArrayList<Card> backpack;
    // pName, gender, isComputer
    //new Player(p4Name.getText().toString(), p4Gender, true)
    public Player(String name, Gender gender, boolean isComputer){
        this.name = name;
        this.gender = gender;
        this.level = 1;
        this.handCapacity = 5;
        this.isComputer = isComputer;
        gear = new ArrayList<Card>();
        inventory = new ArrayList<Card>();
		backpack = new ArrayList<Card>();
    }
    public String getPlayerName(){ return name; }
    public int getLevel(){ return level; }
    public void setLevel(int level){ this.level = level; }
    public int getHandCapacity(){ return handCapacity; }
    public void setHandCapacity(int handCapacity){ this.handCapacity = handCapacity; }
    public Gender getGender(){ return gender; }
    public void setGender(Gender gender){ this.gender = gender; }
    public boolean isComputer(){ return isComputer; }
    public ArrayList<Card> getGear(){
        ArrayList<Card> result = new ArrayList<Card>();
        for(Card card : gear){
            if(card.getCardType() == CARD_TYPE.GEAR){
                result.add(card);
            }
        }
        return result;
    }
    public ArrayList<Card> getAllGear(){
        return gear;
    }
    public ArrayList<Card> getInventory(){
        return inventory;
    }
    public void addInventory(ArrayList<Card> cards){
        for(Card card : cards){
            inventory.add(card);
        }
    }
    public void removeInventory(Card card){
        if(checkForInventory(card)) inventory.remove(card);
    }
    public ArrayList<Card> getCreatures(){
        ArrayList<Card> results = new ArrayList<Card>();
        for(Card card : inventory){
            if(card.getCardType() == CARD_TYPE.CREATURE){
                results.add(card);
            }
        }
        if(results.size() == 0) return null;
        else return results;
    }
    public ArrayList<Card> getClassRaceCards(){
        ArrayList<Card> results = new ArrayList<Card>();
        for(Card card : gear){
            if(card.getCardType() == CARD_TYPE.CLASS || card.getCardType() == CARD_TYPE.RACE){
                results.add(card);
            }
        }
        if(results.size() == 0) return null;
        else return results;
    }
    public int getBonusValue(){
        int result = 0;
        for(Card card : gear){
            try {
                result += ((Gear) card).getBonusAmount();
            }catch (ClassCastException e){}
        }
        return result;
    }
    public boolean checkForInventory(Card card){
        for(Card inv : inventory){
            if(inv.getCardName().equals(card.getCardName())){
                return true;
            }
        }
        return false;
    }
    public boolean checkForGear(Card card){
        for(Card g : gear){
            if(g.getCardName().equals(card.getCardName())){
                return true;
            }
        }
        return false;
    }
    public boolean checkForClassRace(Card card){
        for(Card g : gear){
            if(g.getCardName().equals(card.getCardName()) && (card.getCardType() == CARD_TYPE.RACE || card.getCardType() == CARD_TYPE.RACE)){
                return true;
            }
        }
        return false;
    }
    public void unequipGear(Card card){
        if(checkForGear(card) || checkForClassRace(card)) {
            gear.remove(card);
            inventory.add(card);
        }
    }
    public void forceEquipGear(Card card){
        inventory.remove(card);
        gear.add(card);
    }
    public void equipGear(Card card){
        if(verifyGear(card)) {
            inventory.remove(card);
            gear.add(card);
        }
    }
	/*
	TODO: Issue with equipping only one hand or more than 2 hands
	*/
    public boolean verifyGear(Card card){
        if(card.getCardType() == CARD_TYPE.GEAR){
            if(getGear().size() != 0) {
                for (Card inv : getGear()) {
                    if (((Gear) card).getNumHands() == 0) {
                        if (((Gear) card).getGearPosition() == ((Gear) inv).getGearPosition()) {
                            return false;
                        } else return true;
                    } else {
                        if ((((Gear) card).getNumHands() + getNumHands()) > 2) {
                            return false;
                        } else return true;
                    }
                }
            }else{
                return true;
            }
        }else{
            return verifyClassRace(card);
        }
        return false;
    }
    public boolean verifyClassRace(Card card){
        if(getClassRaceCards() != null) {
            for (Card inv : getClassRaceCards()) {
                if(inv.getCardType() == card.getCardType()) return false;
            }
        }
        return true;
    }
    public int getNumHands(){
        int result = 0;
        for(Card card : getGear()){
            result += ((Gear)card).getNumHands();
        }
        return result;
    }
	public int getRunawayValue(){
		int runAway = 3;
		/*
		TODO: add runaway gear search
		*/
		return runAway;
	}
    public ArrayList<Card> getEffects(){
        return null;
    }


	public ArrayList<Card> getBackpack(){
		return backpack;
	}
	public void addToBackpack(Card card){
		if(card.getCardType() == CARD_TYPE.GEAR){
			backpack.add(card);
			if(checkForInventory(card)) {
				inventory.remove(card);
			}else{
				gear.remove(card);
			}
		}
	}
	public void backBackToEquipped(Card card){
		if(verifyGear(card)){
			backpack.remove(card);
			gear.add(card);
		}
	}
	public boolean checkForBackpack(Card card){
		return backpack.contains(card);
	}
}