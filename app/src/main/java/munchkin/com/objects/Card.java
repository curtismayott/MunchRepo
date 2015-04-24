package munchkin.com.objects;

public interface Card {
	public String getCardName();
    public void setCardName(String cardName);
    public CARD_TYPE getCardType();
    public void setCardType(CARD_TYPE cardType);
    public int getCardImage();
    public void setCardImage(int cardImage);
}
