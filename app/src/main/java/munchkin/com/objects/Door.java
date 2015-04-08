package munchkin.com.objects;

/**
 * Created by darkbobo on 3/3/15.
 */
public class Door implements Card{
    String cardName;
    CARD_TYPE cardType;
    int cardImage;
    public Door(){}
    public Door(String cardName, CARD_TYPE cardType, int cardImage){
        setCardName(cardName);
        setCardType(cardType);
        setCardImage(cardImage);
    }
    @Override
    public String getCardName() { return cardName; }
    @Override
    public void setCardName(String cardName) { this.cardName = cardName; }
    @Override
    public CARD_TYPE getCardType() { return cardType; }
    @Override
    public void setCardType(CARD_TYPE cardType) { this.cardType = cardType; }
    @Override
    public int getCardImage() { return cardImage; }
    @Override
    public void setCardImage(int cardImage) { this.cardImage = cardImage; }
}
