package munchkin.com.munchkin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import munchkin.com.DAO.CRAWHelper;
import munchkin.com.DAO.CreatureHelper;
import munchkin.com.objects.BAD_STUFF;


public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class LoginFragment extends Fragment {

        public LoginFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);
            Button submit = (Button)rootView.findViewById(R.id.login_button);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("Munchkin", MODE_PRIVATE).edit();
                    editor.putBoolean("GAME_STARTED", false);
                    editor.commit();
                    initializeDatabase(v.getContext());
                    Intent i = new Intent(getActivity(), Main.class);
                    startActivity(i);
                }
            });
            return rootView;
        }
        private void initializeDatabase(Context context){
            CRAWHelper helper = new CRAWHelper(context);
            Log.e("Login.java", Boolean.toString(helper.isEmpty()));
            helper.deleteDatabase();
            helper = new CRAWHelper(context);

            if(helper.getNumberCards() == 0) {
                // String cardName, String cardType, String cardImage, int bonus, String gearPosition, int numHands, String classType
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                helper.insert("Broad Sword", "GEAR", R.drawable.broad_sword, 3, "HAND", 1, "FEMALE");
                helper.insert("Bad-Ass Bandana", "GEAR", R.drawable.bad_ass_bandana, 3, "HEADGEAR", 0, "HUMAN");
                helper.insert("Swiss Army Polearm", "GEAR", R.drawable.broad_sword, 4, "HAND", 2, "HUMAN");
                helper.insert("Warrior", "CLASS", R.drawable.warrior, 0, "NONE", 0, "WARRIOR");
                helper.insert("Wizard", "CLASS", R.drawable.wizard, 0, "NONE", 0, "WIZARD");
                helper.insert("Staff of Nepalm", "GEAR", R.drawable.staff_of_nepalm, 5, "HAND", 1, "WIZARD");
                helper.insert("Sneaky Bastard Sword", "GEAR", R.drawable.sneaky_bastard_sword, 2, "HAND", 1, "NONE");
                helper.insert("Boots of Butt-Kicking", "GEAR", R.drawable.boots_of_butt_kicking, 2, "FOOTGEAR", 0, "NONE");
                helper.insert("Buckler of Swashing", "GEAR", R.drawable.buckler_of_swashing, 2, "HAND", 1, "NONE");
                helper.insert("Bow With Ribbons", "GEAR", R.drawable.bow_with_ribbons, 4, "HAND", 2, "ELF");
                helper.insert("Mace of Sharpness", "GEAR", R.drawable.mace_of_sharpness, 4, "HAND", 1, "CLERIC");
                helper.insert("Gentlemen's Club", "GEAR", R.drawable.gentlemens_club, 3, "HAND", 1, "MALE");
                helper.insert("Cleric", "CLASS", R.drawable.cleric, 0, "NONE", 0, "CLERIC");
                helper.insert("Dwarf", "RACE", R.drawable.dwarf, 0, "NONE", 0, "DWARF");
                helper.insert("Elf", "RACE", R.drawable.elf, 0, "NONE", 0, "ELF");
                Log.e("Login.java", "insert");

            }

            CreatureHelper cHelper = new CreatureHelper(context);
            cHelper.deleteDatabase();
            cHelper = new CreatureHelper(context);
            if(cHelper.getNumberCards() == 0){
                // String cardName, String cardImage, int level, String badStuff, int rewardTreasure, int rewardLevel, String hatedClass, int hatedValue, String hatedEffect
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
                cHelper.insert("Platycore", R.drawable.platycore, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "WIZARD", -6, "BONUS");
                cHelper.insert("Potted Plant", R.drawable.potted_plant, 1, (BAD_STUFF.NONE).toString(), 1, 1, "ELF", 1, "DECK_DRAW");
                cHelper.insert("Maul Rat", R.drawable.maul_rat, 1, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "CLERIC", 3, "BONUS");
                cHelper.insert("Flying Frogs", R.drawable.flying_frogs, 2, (BAD_STUFF.LOSE_LEVEL).toString(), 1, 1, "NONE", -1, "RUN_AWAY");
                cHelper.insert("Pukachu", R.drawable.pukachu, 6, (BAD_STUFF.DISCARD).toString(), 2, 1, "NONE", 1, "LEVEL_NO_BONUS");
            }
        }
    }
}
