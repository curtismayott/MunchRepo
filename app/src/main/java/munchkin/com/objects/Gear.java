package munchkin.com.objects;

public class Gear extends Treasure{
	GearPositions position;
    int numHands;
    public Gear(){}
    public Gear(String cardName, CARD_TYPE cardType, int cardImage, int bonusAmount, GearPositions gearPosition, int numHands, String classType){
        super(cardName, cardType, cardImage, bonusAmount, classType);
        setGearPosition(gearPosition);
        setNumHands(numHands);
    }
    public GearPositions getGearPosition(){ return position; }
    public void setGearPosition(GearPositions position){ this.position = position; }
    public String toString(){
        return getCardName() + " || " + " || " + getBonusAmount() + " || " + getGearPosition().toString();
    }
    public int getNumHands(){ return numHands; }
    public void setNumHands(int numHands){ this.numHands = numHands; }
}
