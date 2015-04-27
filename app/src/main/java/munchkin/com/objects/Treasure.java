package munchkin.com.objects;

public class Treasure implements Card {
    String cardName;
    CARD_TYPE cardType;
    int cardImage;
    int bonus;
    String classType;
	int gold;
    public Treasure(){}
    public Treasure(String cardName, CARD_TYPE cardType, int cardImage, int bonus, String classType, int gold){
        setCardName(cardName);
        setCardType(cardType);
        setCardImage(cardImage);
        setBonusAmount(bonus);
        setClassType(classType);
		setGold(gold);
    }
	@Override
	public String getCardName() {
		return cardName;
	}
    @Override
    public void setCardName(String cardName) { this.cardName = cardName; }
    @Override
    public CARD_TYPE getCardType() {
        return cardType;
    }
    @Override
    public void setCardType(CARD_TYPE cardType) { this.cardType = cardType; }
    @Override
    public int getCardImage() { return cardImage; }
    @Override
    public void setCardImage(int cardImage) { this.cardImage = cardImage; }
    public int getBonusAmount(){ return bonus; }
    public void setBonusAmount(int bonus){ this.bonus = bonus; }
    public String getClassType(){ return classType; }
    public void setClassType(String classType){ this.classType = classType; }
	public int getGold() { return gold; }
	public void setGold(int gold) { this.gold = gold; }
}
