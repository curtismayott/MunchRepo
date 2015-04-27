package munchkin.com.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by darkbobo on 3/3/15.
 */
public class CRAWHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Munchkin";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db;
    public CRAWHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        onCreate(getWritableDatabase());
    }
    @Override
    public void onCreate(SQLiteDatabase _db) {
        db = _db;
        db.execSQL("CREATE TABLE IF NOT EXISTS Cards (id INTEGER PRIMARY KEY, " +
                "CardName TEXT, CardType TEXT, CardImage INTEGER, Bonus INTEGER, " +
                "GearPosition TEXT, NumHands INTEGER, ClassType TEXT, Gold INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public void insert(String cardName, String cardType, int cardImage, int bonus, String gearPosition, int numHands, String classType, int gold){
        ContentValues cv = new ContentValues();
        cv.put("CardName", cardName);
        cv.put("CardType", cardType);
        cv.put("CardImage", cardImage);
        cv.put("Bonus", bonus);
        cv.put("GearPosition", gearPosition);
        cv.put("NumHands", numHands);
        cv.put("ClassType", classType);
		cv.put("Gold", gold);
        getWritableDatabase().insert("Cards", null, cv);
    }
    public Cursor getCards(){
        return getReadableDatabase().rawQuery("SELECT * FROM Cards;", null);
    }
    public Cursor getTreasureCards(){
        String sql ="SELECT * FROM Cards WHERE CardType IN('GEAR');";
        return getReadableDatabase().rawQuery(sql, null);
    }
    public Cursor getDoorCards(){
        String sql ="SELECT * FROM Cards WHERE CardType IN('RACE', 'CLASS');";
        return getReadableDatabase().rawQuery(sql, null);
    }
    public boolean isEmpty(){
        Cursor c = getCards();
        return c.moveToFirst();
    }
    public int getNumberCards(){
        int count = 0;
        Cursor c = getCards();
        if(!c.moveToFirst()) return count;
        c.moveToFirst();
        while(!c.isAfterLast()){
            count++;
            c.moveToNext();
        }
        return count;
    }
    public String getCardName(Cursor c){
        return c.getString(c.getColumnIndex("CardName"));
    }
    public String getCardType(Cursor c){
        return c.getString(c.getColumnIndex("CardType"));
    }
    public int getCardImage(Cursor c){
        return c.getInt(c.getColumnIndex("CardImage"));
    }
    public int getBonus(Cursor c){
        return c.getInt(c.getColumnIndex("Bonus"));
    }
    public String getGearPosition(Cursor c){
        return c.getString(c.getColumnIndex("GearPosition"));
    }
    public int getNumHands(Cursor c){
        return c.getInt(c.getColumnIndex("NumHands"));
    }
    public String getClassType(Cursor c){
        return c.getString(c.getColumnIndex("ClassType"));
    }
	public int getGold(Cursor c) { return c.getInt(c.getColumnIndex("Gold")); }
    public void deleteDatabase(){
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS Cards;");
    }
}