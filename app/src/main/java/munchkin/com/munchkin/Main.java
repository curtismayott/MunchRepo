package munchkin.com.munchkin;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import munchkin.com.objects.BAD_STUFF;
import munchkin.com.objects.CARD_TYPE;
import munchkin.com.objects.Card;
import munchkin.com.objects.Creature;
import munchkin.com.objects.Game;
import munchkin.com.objects.Gender;
import munchkin.com.objects.PHASES;
import munchkin.com.objects.Player;
import munchkin.com.processes.CPProcess;
import munchkin.com.processes.ProcessPhase;

/*
fragmentIDS:
1: Table View
2: Card View
if gameStart != true ((SharedPreffs "GAME_STARTED"))
100: Combat
*/

public class Main extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    public static Game game;
    public static int turnCount = 1;
    public static PHASES phase = PHASES.TURN_START;
	public static final String GAME_STARTED = "GAME_STARTED";
	public static final String PREFFS_NAME = "Munchkin";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1, turnCount))
                .commit();
    }
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.tv);
                break;
            case 2:
                mTitle = getString(R.string.yc);
                break;
            case 3:
                mTitle = getString(R.string.options);
                break;
        }
    }
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.interrupt) {
            final Dialog interruptDialog = new Dialog(this);
            interruptDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            interruptDialog.setContentView(R.layout.dialog_select_creature);
            TextView label = (TextView)interruptDialog.findViewById(R.id.select_label);
            label.setText("Interrupt");
            LinearLayout cardContainer = (LinearLayout)interruptDialog.findViewById(R.id.creature_container);
            ArrayList<Card> cards = new ArrayList<Card>();
            if(game.getPlayer(1).getAllGear() != null && game.getPlayer(1).getInventory() != null) {
                cards.addAll(game.getPlayer(1).getAllGear());
                cards.addAll(game.getPlayer(1).getInventory());
            }
            if(cards.size() != 0) {
                for (Card card : cards) {
                    final ImageButton layout = new ImageButton(this);
                    layout.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
                    layout.setPadding(5, 5, 5, 5);
                    layout.setImageResource(card.getCardImage());
                    layout.setScaleType(ImageView.ScaleType.FIT_XY);
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Dialog optionSelect = new Dialog(v.getContext());
                            optionSelect.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                            optionSelect.setContentView(R.layout.dialog_option_interrupt);
                            final RadioButton rbPlayer1 = (RadioButton) optionSelect.findViewById(R.id.player_1);
                            final RadioButton rbPlayer2 = (RadioButton) optionSelect.findViewById(R.id.player_2);
                            final RadioButton rbPlayer3 = (RadioButton) optionSelect.findViewById(R.id.player_3);
                            final RadioButton rbPlayer4 = (RadioButton) optionSelect.findViewById(R.id.player_4);
                            Button submit = (Button) optionSelect.findViewById(R.id.submit);
                            submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int player = 1;
                                    if (rbPlayer1.isChecked()) {
                                        player = 1;
                                    } else if (rbPlayer2.isChecked()) {
                                        player = 2;
                                    } else if (rbPlayer3.isChecked()) {
                                        player = 3;
                                    } else if (rbPlayer4.isChecked()) {
                                        player = 4;
                                    }
                                    layout.setVisibility(View.GONE);
                                    optionSelect.dismiss();
                                }
                            });
                            optionSelect.show();
                        }
                    });
                    cardContainer.addView(layout);
                }
            }else{
                Toast.makeText(this, "No Inventory or Gear.", Toast.LENGTH_SHORT);
            }
            Button finish = (Button)interruptDialog.findViewById(R.id.select_submit);
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interruptDialog.dismiss();
                }
            });
            interruptDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_PLAYER_NUMBER = "player_number";
        LinearLayout cardContainer;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, int playerNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putInt(ARG_PLAYER_NUMBER, playerNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;// = inflater.inflate(R.layout.fragment_main, container, false);
            Bundle b = getArguments();
            int sectionNumber = b.getInt(ARG_SECTION_NUMBER);
            final int playerNumber = b.getInt(ARG_PLAYER_NUMBER);
            SharedPreferences preffs = getActivity().getSharedPreferences(PREFFS_NAME, MODE_PRIVATE);
            boolean gameStarted = preffs.getBoolean(GAME_STARTED, false);
            if(gameStarted) {
				if(game.getPlayer(playerNumber).isComputer() && phase != PHASES.LOOK_FOR_TROUBLE && phase != PHASES.KICK_OPEN_THE_DOOR && phase == PHASES.IN_COMBAT){
					sectionNumber = 1;
				}
                switch (sectionNumber) {
                    case 1:     // table view
                        rootView = inflater.inflate(R.layout.fragment_main, container, false);

                        cardContainer = (LinearLayout)rootView.findViewById(R.id.card_container);
                        rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(cardContainer.getVisibility() == View.VISIBLE){
                                    cardContainer.setVisibility(View.GONE);
                                }
                            }
                        });
                        cardContainer.setVisibility(View.GONE);
                        LinearLayout player1 = (LinearLayout) rootView.findViewById(R.id.player1);
                        player1.addView(getPlayerCard(1));


                        LinearLayout player2 = (LinearLayout) rootView.findViewById(R.id.player2);
                        player2.addView(getPlayerCard(2));

                        LinearLayout player3 = (LinearLayout) rootView.findViewById(R.id.player3);
                        player3.addView(getPlayerCard(3));

                        LinearLayout player4 = (LinearLayout) rootView.findViewById(R.id.player4);
                        player4.addView(getPlayerCard(4));

                        Button kotd = (Button)rootView.findViewById(R.id.draw_card);
                        if(!game.getPlayer(playerNumber).isComputer() && (phase == PHASES.KICK_OPEN_THE_DOOR || phase == PHASES.TURN_START)){
                            kotd.setVisibility(View.VISIBLE);
                            kotd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(game.kickOpenTheDoor(playerNumber)){
                                        phase = PHASES.IN_COMBAT;
                                        loadDisplay(100, playerNumber);
                                    }else{
                                        Toast.makeText(getActivity(), "Door Card added to Inventory", Toast.LENGTH_SHORT).show();
                                        phase = PHASES.LOOK_FOR_TROUBLE;
                                        loadDisplay(1, playerNumber);

                                    }
                                }
                            });
                        }else kotd.setVisibility(View.GONE);

                        Button lootRoom = (Button)rootView.findViewById(R.id.loot_room);
                        if(!game.getPlayer(playerNumber).isComputer() && phase == PHASES.LOOK_FOR_TROUBLE || phase == PHASES.LOOT_THE_ROOM){
                            lootRoom.setVisibility(View.VISIBLE);
                            lootRoom.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getActivity(), "Door Card added to Inventory", Toast.LENGTH_SHORT).show();
                                    game.drawDoor(playerNumber, 1);
                                    phase = PHASES.CHARITY;
                                }
                            });
                        }else lootRoom.setVisibility(View.GONE);

                        Button lookForTrouble = (Button)rootView.findViewById(R.id.look_for_trouble);
                        if(!game.getPlayer(playerNumber).isComputer() && (phase == PHASES.LOOK_FOR_TROUBLE || phase == PHASES.LOOT_THE_ROOM)){
                            lookForTrouble.setVisibility(View.VISIBLE);
                            lookForTrouble.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    final Dialog selectCreatureDialog = new Dialog(getActivity());
                                    selectCreatureDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                    selectCreatureDialog.setContentView(R.layout.dialog_select_creature);
                                    LinearLayout creatureContainer = (LinearLayout)selectCreatureDialog.findViewById(R.id.creature_container);
                                    for(final Card card : (game.getPlayer(playerNumber)).getCreatures()){
                                        ImageButton cardView = new ImageButton(getActivity());
                                        cardView.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
                                        cardView.setImageResource(card.getCardImage());
                                        cardView.setScaleType(ImageView.ScaleType.FIT_XY);
                                        cardView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                (game.getActiveCreatures()).add(card);
                                                selectCreatureDialog.dismiss();
                                                loadDisplay(100, playerNumber);
                                            }
                                        });
                                        creatureContainer.addView(cardView);

                                    }
                                    Button submit = (Button)selectCreatureDialog.findViewById(R.id.select_submit);
                                    submit.setVisibility(View.GONE);
                                    selectCreatureDialog.show();
                                    phase = PHASES.LOOK_FOR_TROUBLE;
                                }
                            });
                        }else lookForTrouble.setVisibility(View.GONE);

                        Button finishTurn = (Button)rootView.findViewById(R.id.finish_turn);
                        if(!game.getPlayer(playerNumber).isComputer() && phase == PHASES.CHARITY){
                            finishTurn.setVisibility(View.VISIBLE);
                            finishTurn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if((game.getPlayer(playerNumber)).getInventory().size() <= ((game.getPlayer(playerNumber)).getHandCapacity())){
                                        phase = PHASES.KICK_OPEN_THE_DOOR;
                                        if(turnCount == game.getNumberPlayers()) {
                                            turnCount = 1;
                                        }else turnCount++;
                                        int pNum = playerNumber;
                                        if(pNum == game.getNumberPlayers()){
                                            pNum = 1;
                                        }else{
                                            pNum++;
                                        }
                                        loadDisplay(2, pNum);
                                    }else{
                                        Toast.makeText(getActivity(), "Cannot end turn with cards in hand greater than " +
                                                (game.getPlayer(playerNumber)).getHandCapacity(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            finishTurn.setVisibility(View.GONE);
                        }


                        if(game.getPlayer(playerNumber).isComputer()){
							if(phase == PHASES.KICK_OPEN_THE_DOOR){
								Thread gearControlProcess = new Thread(new CPProcess(
										getActivity(), playerNumber, ProcessPhase.EQUIP_GEAR, 1
								));
								gearControlProcess.run();
								while (gearControlProcess.isAlive()) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

								final Dialog displayDialog = new Dialog(getActivity());
								displayDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								displayDialog.setContentView(R.layout.dialog_select_creature);
								TextView label = (TextView) displayDialog.findViewById(R.id.select_label);
								label.setText(game.getPlayer(playerNumber).getPlayerName() + "'s Door");
								final Button submit = (Button) displayDialog.findViewById(R.id.select_submit);
								game.kickOpenTheDoor(playerNumber);
								submit.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										if (game.getActiveCreatures() != null && game.getActiveCreatures().size() > 0) {
											displayDialog.dismiss();
											loadDisplay(100, playerNumber);
										} else {
											displayDialog.dismiss();
											loadDisplay(1, playerNumber);
										}
									}
								});
								LinearLayout newCardView = (LinearLayout) displayDialog.findViewById(R.id.creature_container);
								if (game.getActiveCreatures() != null && game.getActiveCreatures().size() > 0) {
									for (Card inventory : game.getActiveCreatures()) {
										ImageButton image = new ImageButton(getActivity());
										image.setScaleType(ImageView.ScaleType.FIT_XY);
										image.setImageResource(inventory.getCardImage());
										image.setLayoutParams(new LinearLayout.LayoutParams(400, 520));
										newCardView.addView(image);
									}
								}
								displayDialog.show();
							}else if(phase == PHASES.LOOK_FOR_TROUBLE){
								final ArrayList<Card> playerCreatures = game.getPlayer(playerNumber).getCreatures();
								int cIndex = -1;
								int tLevel = 1000;
								for(int i = 0; i < playerCreatures.size(); i++){
									if(((Creature)(playerCreatures.get(i))).getLevel() < game.getPlayer(i).getBonusValue()
											&& ((Creature)(playerCreatures.get(i))).getLevel() < tLevel){
										tLevel = ((Creature)(playerCreatures.get(i))).getLevel();
										cIndex = i;
									}
								}
								if(cIndex <= 0){
									final Dialog lftDialog = new Dialog(getActivity());
									lftDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									lftDialog.setContentView(R.layout.dialog_select_creature);
									TextView label = (TextView)lftDialog.findViewById(R.id.select_label);
									label.setText(game.getPlayer(playerNumber).getPlayerName() + " is Looking For Trouble");
									LinearLayout cContainer = (LinearLayout)lftDialog.findViewById(R.id.creature_container);
									ImageButton image = new ImageButton(getActivity());
									image.setScaleType(ImageView.ScaleType.FIT_XY);
									image.setImageResource(playerCreatures.get(cIndex).getCardImage());
									image.setLayoutParams(new LinearLayout.LayoutParams(400, 520));
									cContainer.addView(image);
									Button submit = (Button)lftDialog.findViewById(R.id.select_submit);
									final int tcIndex = cIndex;
									submit.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											game.getActiveCreatures().add(playerCreatures.get(tcIndex));
											game.getPlayer(playerNumber).removeInventory(playerCreatures.get(tcIndex));
											phase = PHASES.IN_COMBAT;
											loadDisplay(100, playerNumber);
											lftDialog.dismiss();
										}
									});
								}else{
									final Dialog ltrDialog = new Dialog(getActivity());
									ltrDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									ltrDialog.setContentView(R.layout.dialog_select_creature);
									TextView label = (TextView)ltrDialog.findViewById(R.id.select_label);
									label.setText(game.getPlayer(playerNumber).getPlayerName() + " has looted the room.");
									Button submit = (Button)ltrDialog.findViewById(R.id.select_submit);
									submit.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											game.drawDoor(playerNumber, 1);
											phase = PHASES.CHARITY;
											loadDisplay(1, playerNumber);
											ltrDialog.dismiss();
										}
									});
								}
							}else if(phase == PHASES.CHARITY){
								Thread gearControlProcess = new Thread(new CPProcess(
										getActivity(), playerNumber, ProcessPhase.FINISH_TURN, 1
								));
								gearControlProcess.run();
								while (gearControlProcess.isAlive()) {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}

							}
                        }


                        break;
                    case 2:     // card view
                        rootView = inflater.inflate(R.layout.fragment_card_view, container, false);
                        /*
                          label(armor,weapon)
                          <image>
                        */
                        Log.e("Main.java", "Player1: " + (game.getPlayer(playerNumber)).getPlayerName());
                        if(!game.getPlayer(playerNumber).isComputer()) {
                            LinearLayout inventoryView = (LinearLayout) rootView.findViewById(R.id.inventory_scroll_view);
                            ArrayList<Card> inventoryCards = game.getInventory(playerNumber);
                            if (inventoryCards != null && !inventoryCards.isEmpty()) {
                                for (Card card : inventoryCards) {
                                    inventoryView.addView(getCardLayout(playerNumber, card));
                                }
                            }
                        }

                        LinearLayout classRaceView = (LinearLayout)rootView.findViewById(R.id.class_race_scroll_view);
                        ArrayList<Card> classRaceCards = game.getPlayerClassRaceCards(playerNumber);
                        if(classRaceCards != null && !classRaceCards.isEmpty()) {
                            for (Card card : classRaceCards) {
                                classRaceView.addView(getCardLayout(playerNumber, card));
                            }
                        }
                        LinearLayout gearView = (LinearLayout)rootView.findViewById(R.id.gear_scroll_view);
                        ArrayList<Card> gearCards = game.getGear(playerNumber);
                        if(gearCards != null && !gearCards.isEmpty()) {
                            for (Card card : gearCards) {
                                gearView.addView(getCardLayout(playerNumber, card));
                            }
                        }
						LinearLayout backpackView = (LinearLayout)rootView.findViewById(R.id.backpack_scroll_view);
						ArrayList<Card> backpackCards = game.getPlayer(playerNumber).getBackpack();
						if(backpackCards != null && !backpackCards.isEmpty()){
							for(Card card : backpackCards){
								backpackView.addView(getCardLayout(playerNumber, card));
							}
						}

                        LinearLayout effectsView = (LinearLayout)rootView.findViewById(R.id.effects_scroll_view);
                        ArrayList<Card> effectsCards = game.getEffects(playerNumber);
                        if(effectsCards != null && !effectsCards.isEmpty()) {
                            for (Card card : effectsCards) {
                                effectsView.addView(getCardLayout(playerNumber, card));
                            }
                            effectsView.addView(effectsView);
                        }
                        break;
                    case 100:       // combat
                        rootView = inflater.inflate(R.layout.fragment_combat, container, false);
                        LinearLayout playerView = (LinearLayout)rootView.findViewById(R.id.player_container);
                        LinearLayout gearViewCombat = (LinearLayout)rootView.findViewById(R.id.gear_scroll_view);
                        LinearLayout inventoryView = (LinearLayout)rootView.findViewById(R.id.inventory_scroll_view);
                        LinearLayout creatureView = (LinearLayout)rootView.findViewById(R.id.creature_container);
                        if(!game.getPlayer(playerNumber).isComputer()) {
                            for(int i = 1; i < game.getNumberPlayers(); i++){
								if(i != playerNumber) {
                                    FrameLayout layout = getPlayerCard(i);
                                    layout.setLayoutParams(new LinearLayout.LayoutParams(500, 400));
                                    playerView.addView(layout);
                                }
                            }

                            if((game.getPlayer(playerNumber)).getAllGear() != null) {
                                for (Card card : (game.getPlayer(playerNumber)).getAllGear()) {
                                    gearViewCombat.addView(getCardLayout(playerNumber, card));
                                }
                            }

                            if((game.getPlayer(playerNumber)).getInventory() != null) {
                                for (Card card : (game.getPlayer(playerNumber)).getInventory()) {
                                    inventoryView.addView(getCardLayout(playerNumber, card));
                                }
                            }

                            if(game.getActiveCreatures() != null) {
                                for (int i = 0; i < (game.getActiveCreatures()).size(); i++) {
                                    creatureView.addView(getCreatureLayout(i));
                                }
                            }
                        }
                        Button attackButton = (Button)rootView.findViewById(R.id.combat_attack);
                        attackButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Dialog resultsDialog = new Dialog(getActivity());
                                resultsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                resultsDialog.setContentView(R.layout.dialog_select_creature);
                                int creaturePower = 0;
                                TextView labelView = (TextView)resultsDialog.findViewById(R.id.select_label);
                                LinearLayout container = (LinearLayout)resultsDialog.findViewById(R.id.creature_container);
                                final Button finishButton = (Button)resultsDialog.findViewById(R.id.select_submit);
                                if((game.getPlayer(playerNumber)).getBonusValue() > creaturePower) {
                                    labelView.setText("You Win!");
                                    for(Card card : game.getActiveCreatures()) {
                                        creaturePower += ((Creature)card).getLevel();
                                        game.drawTreasure(playerNumber, ((Creature)card).getRewardTreasure());
                                        (game.getPlayer(playerNumber)).setLevel(
                                                (game.getPlayer(playerNumber)).getLevel() + ((Creature)card).getRewardLevel()
                                        );
                                        final ImageButton button = new ImageButton(getActivity());
                                        button.setLayoutParams(new LinearLayout.LayoutParams(400, 520));
                                        button.setScaleType(ImageView.ScaleType.FIT_XY);
                                        button.setImageResource(card.getCardImage());
                                        container.addView(button);
                                    }
                                }else{
                                    labelView.setText("You Lose!");
                                    int count = 0;
                                    for(Card card : game.getActiveCreatures()){
                                        if(((Creature)card).getBadStuff() == BAD_STUFF.DISCARD){
                                            finishButton.setVisibility(View.GONE);
                                            for(Card inventory : (game.getPlayer(playerNumber)).getInventory()){
                                                final ImageButton button = new ImageButton(getActivity());
                                                button.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
                                                button.setScaleType(ImageView.ScaleType.FIT_XY);
                                                button.setImageResource(inventory.getCardImage());
                                                button.setActivated(false);
                                                button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        button.setActivated(true);
                                                        finishButton.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                                container.addView(button);
                                                count++;
                                            }
                                        }else if(((Creature)card).getBadStuff() == BAD_STUFF.DROP_ITEM){
                                            finishButton.setVisibility(View.GONE);
                                            for(Card inventory : (game.getPlayer(playerNumber)).getGear()) {
                                                final ImageButton button = new ImageButton(getActivity());
                                                button.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
                                                button.setScaleType(ImageView.ScaleType.FIT_XY);
                                                button.setImageResource(inventory.getCardImage());
                                                button.setActivated(false);
                                                button.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        button.setActivated(true);
                                                        finishButton.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                                container.addView(button);
                                                count++;
                                            }
                                        }else if(((Creature)card).getBadStuff() == BAD_STUFF.LOSE_LEVEL){
                                            if((game.getPlayer(playerNumber)).getLevel() > 1) {
                                                (game.getPlayer(playerNumber)).setLevel(
                                                        (game.getPlayer(playerNumber)).getLevel() - 1
                                                );
                                            }
                                        }
                                    }
                                }

                                finishButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        resultsDialog.dismiss();
                                        for(Card card : game.getActiveCreatures()) {
                                            if (phase == PHASES.LOOK_FOR_TROUBLE && game.getPlayer(playerNumber).checkForInventory(card)) {
                                                game.discardCard(playerNumber, card);
                                            }
                                        }
                                        game.getActiveCreatures().clear();
                                        phase = PHASES.CHARITY;
                                        loadDisplay(2, playerNumber);
                                    }
                                });
                                resultsDialog.show();
                            }
                        });
						/*
						TODO: run away button - dice roll
						*/
                        Button runButton = (Button)rootView.findViewById(R.id.combat_run);
						runButton.setOnClickListener(new View.OnClickListener() {
							ImageView dieView;
							Handler handler;
							Timer timer;
							boolean rolling;
							@Override
							public void onClick(View v) {
								handler = new Handler(callback);
								rolling = false;
								final Dialog runawayDialog = new Dialog(v.getContext());
								runawayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
								runawayDialog.setContentView(R.layout.dialog_run_away);
								TextView label = (TextView)runawayDialog.findViewById(R.id.die_text);
								label.setText("Run Away");
								dieView = (ImageView)runawayDialog.findViewById(R.id.die_image);
								dieView.setImageResource(R.drawable.die3d);
								final Button rollDie = (Button)runawayDialog.findViewById(R.id.die_roll);
								timer = new Timer();
								rollDie.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										if(!rolling) {
											rollDie.setText("Finish");
											timer.schedule(new Roll(), 400);
										}else{
											/*
											TODO: badstuff happens
											*/
											timer.cancel();
											runawayDialog.dismiss();
											game.getActiveCreatures().clear();
											loadDisplay(2, playerNumber);
										}
									}
								});
								runawayDialog.show();
							}
							class Roll extends TimerTask{
								@Override
								public void run() {
									handler.sendEmptyMessage(0);
								}
							}
							Handler.Callback callback = new Handler.Callback(){
								@Override
								public boolean handleMessage(Message msg) {
									Random random = new Random();
									switch(random.nextInt(6) + 1){
										case 1:
											dieView.setImageResource(R.drawable.die_one);
											break;
										case 2:
											dieView.setImageResource(R.drawable.die_two);
											break;
										case 3:
											dieView.setImageResource(R.drawable.die_three);
											break;
										case 4:
											dieView.setImageResource(R.drawable.die_four);
											break;
										case 5:
											dieView.setImageResource(R.drawable.die_five);
											break;
										case 6:
											dieView.setImageResource(R.drawable.die_six);
											break;
									}
									rolling = true;
									return true;
								}
							};
						});
						/*
						TODO: ask for help button - number treasures (optional), item (optional), person requested (optional)
						*/
                        Button helpButton = (Button)rootView.findViewById(R.id.combat_help);
                        break;
                    case 101:       // computer combat view

                        break;
                }
            }else{
                rootView = inflater.inflate(R.layout.fragment_start_game, container, false);
                final CheckBox p2cb = (CheckBox)rootView.findViewById(R.id.p2_cb);
                final CheckBox p3cb = (CheckBox)rootView.findViewById(R.id.p3_cb);
                final CheckBox p4cb = (CheckBox)rootView.findViewById(R.id.p4_cb);
                final EditText p1Name = (EditText)rootView.findViewById(R.id.player1_name);
                final EditText p2Name = (EditText)rootView.findViewById(R.id.player2_name);
                final EditText p3Name = (EditText)rootView.findViewById(R.id.player3_name);
                final EditText p4Name = (EditText)rootView.findViewById(R.id.player4_name);
                final RadioButton p1MaleRB = (RadioButton)rootView.findViewById(R.id.p1_rb_male);
                final RadioButton p2MaleRB = (RadioButton)rootView.findViewById(R.id.p2_rb_male);
                final RadioButton p3MaleRB = (RadioButton)rootView.findViewById(R.id.p3_rb_male);
                final RadioButton p4MaleRB = (RadioButton)rootView.findViewById(R.id.p4_rb_male);
                Button startButton = (Button)rootView.findViewById(R.id.start_game_button);
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gender p1Gender = p1MaleRB.isChecked() ? Gender.MALE : Gender.FEMALE;
                        game.addPlayer(new Player(p1Name.getText().toString(), p1Gender, false), 1);
                        //else Log.e("Main.java", "Failed to add player");


                        Gender p2Gender = p2MaleRB.isChecked() ? Gender.MALE : Gender.FEMALE;
                        if(p2cb.isChecked()){
                            game.addPlayer(new Player(p2Name.getText().toString(), p2Gender, false), 2);
                        }
                        Gender p3Gender = p3MaleRB.isChecked() ? Gender.MALE : Gender.FEMALE;
                        if(p3cb.isChecked()){
                            game.addPlayer(new Player(p3Name.getText().toString(), p3Gender, true), 3);
                        }
                        Gender p4Gender = p4MaleRB.isChecked() ? Gender.MALE : Gender.FEMALE;
                        if(p4cb.isChecked()){
                            game.addPlayer(new Player(p4Name.getText().toString(), p4Gender, true), 4);
                        }
                        Log.e("Main.java", "NumberPlayers: " + game.getNumberPlayers());
						ArrayList<Thread> playerThreads = new ArrayList<Thread>();
                        for(int i = 1; i <= game.getNumberPlayers(); i++){
                            game.drawDoor(i, 4);
                            game.drawTreasure(i, 4);
							/*
							TODO: should cycle through players and allow equipping gear right here
							*/
                            if(game.getPlayer(i).isComputer()) {
                                Thread gearControlProcess = new Thread(new CPProcess(
                                        getActivity(), i, ProcessPhase.EQUIP_GEAR, 1
                                ));
                                gearControlProcess.run();
                                while(gearControlProcess.isAlive()){
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Log.e("Main Equip", "Sleep Error: " + e.getMessage());
                                    }
                                }
                            }else{
								playerThreads.add(new Thread(new PlayerInitThread(i)));
								playerThreads.get(i-1).run();
								try {
									while(playerThreads.get(i-1).isAlive()) {
										Thread.sleep(1000);
									}
									playerThreads.get(i-1).join();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
                        }
                        SharedPreferences.Editor editor = v.getContext().getSharedPreferences(PREFFS_NAME, MODE_PRIVATE).edit();
                        editor.putBoolean(GAME_STARTED, true);
                        editor.commit();
                        phase = PHASES.TURN_START;
                        loadDisplay(2, 1);
                    }
                });
            }
            return rootView;
        }
        private void loadDisplay(int whichScreen, int playerNumber){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(whichScreen, playerNumber)).commit();
        }
		class PlayerInitThread implements Runnable{
			int pNum;
			public PlayerInitThread(int playerNumber){
				this.pNum = playerNumber;
			}
			@Override
			public void run() {
				final Dialog initialEquipDialog = new Dialog(getActivity());
				initialEquipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				initialEquipDialog.setContentView(R.layout.dialog_init_gear);
				TextView playerName = (TextView)initialEquipDialog.findViewById(R.id.player_name);
				playerName.setText(game.getPlayer(pNum).getPlayerName());
				LinearLayout cardContainer = (LinearLayout)initialEquipDialog.findViewById(R.id.card_container);
				for(Card c : game.getPlayer(pNum).getInventory()){
					cardContainer.addView(getCardLayout(pNum, c));
				}
				Button doneButton = (Button)initialEquipDialog.findViewById(R.id.done_button);
				doneButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						initialEquipDialog.dismiss();
					}
				});
				initialEquipDialog.show();
			}
		}
        private ImageButton getCardLayout(final int playerNumber, final Card card){
            final ImageButton cardContainer = new ImageButton(getActivity());
            cardContainer.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
            cardContainer.setPadding(5, 5, 5, 5);
            cardContainer.setScaleType(ImageView.ScaleType.FIT_XY);
            Log.e("Main.java cardLayout", card.getCardName());
            cardContainer.setImageResource(card.getCardImage());
			cardContainer.setId(card.getCardName().hashCode());
			SharedPreferences prefs = getActivity().getSharedPreferences(PREFFS_NAME, MODE_PRIVATE);
			final boolean tGameStarted = prefs.getBoolean(GAME_STARTED, false);
			cardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog playCardDialog = new Dialog(v.getContext());
                    playCardDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    playCardDialog.setContentView(R.layout.dialog_play_card_options);
                    Button equipCard = (Button) playCardDialog.findViewById(R.id.equip_card);
                    equipCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (card.getCardType() == CARD_TYPE.GEAR
                                    || card.getCardType() == CARD_TYPE.CLASS
                                    || card.getCardType() == CARD_TYPE.RACE) {
                                (game.getPlayer(playerNumber)).equipGear(card);
                                playCardDialog.dismiss();
								if(tGameStarted) {
									loadDisplay(2, playerNumber);
								}else{
									if(game.getPlayer(playerNumber).checkForGear(card)){
										cardContainer.setVisibility(View.GONE);
									}else{
										Toast.makeText(v.getContext(), "Cannot Equip Card", Toast.LENGTH_SHORT).show();
									}
								}
                            } else {
                                Toast.makeText(getActivity(), "Cannot Equip Card", Toast.LENGTH_SHORT).show();
                                playCardDialog.dismiss();
                            }
                        }
                    });

                    Button unequipCard = (Button) playCardDialog.findViewById(R.id.unequip_card);
                    if((game.getPlayer(playerNumber)).checkForGear(card) || (game.getPlayer(playerNumber)).checkForClassRace(card)){
                        unequipCard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if((game.getPlayer(playerNumber)).checkForGear(card) || (game.getPlayer(playerNumber)).checkForClassRace(card)){
                                    (game.getPlayer(playerNumber)).unequipGear(card);
                                }
                                playCardDialog.dismiss();
                                loadDisplay(2, playerNumber);
                            }
                        });
                    }else unequipCard.setVisibility(View.GONE);

                    Button playCard = (Button) playCardDialog.findViewById(R.id.play_card);
                    playCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (card.getCardType() == CARD_TYPE.CREATURE
                                    || card.getCardType() == CARD_TYPE.CURSE
                                    || card.getCardType() == CARD_TYPE.CREATURE_BUFF
                                    || card.getCardType() == CARD_TYPE.SPECIAL) {
                                // handle play card

                                playCardDialog.dismiss();
                                loadDisplay(2, playerNumber);
                            } else {
                                Toast.makeText(getActivity(), "Cannot Play Card", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
					Button backpack = (Button) playCardDialog.findViewById(R.id.send_to_backpack);
					if(card.getCardType() == CARD_TYPE.GEAR) {
						backpack.setVisibility(View.VISIBLE);
						backpack.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								game.getPlayer(playerNumber).addToBackpack(card);
								if(tGameStarted) {
									loadDisplay(2, playerNumber);
								}else{
									if(game.getPlayer(playerNumber).checkForBackpack(card)){
										cardContainer.setVisibility(View.GONE);
									}else{
										Toast.makeText(v.getContext(), "Item was unable to be moved to backpack", Toast.LENGTH_SHORT).show();
									}
								}
								playCardDialog.dismiss();
							}
						});
					}else{
						backpack.setVisibility(View.GONE);
					}
                    Button discardCard = (Button) playCardDialog.findViewById(R.id.discard_card);
                    discardCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            game.discardCard(playerNumber, card);
                            cardContainer.setVisibility(View.GONE);
                            playCardDialog.dismiss();
                            loadDisplay(2, playerNumber);
                        }
                    });
                    Button sellCard = (Button) playCardDialog.findViewById(R.id.sell_card);
                    sellCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            loadDisplay(2, playerNumber);
                        }
                    });
                    Button dismissDialog = (Button) playCardDialog.findViewById(R.id.dismiss_dialog);
                    dismissDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            playCardDialog.dismiss();
                        }
                    });
                    playCardDialog.show();
                }
            });
            return cardContainer;
        }
        private ImageButton getCreatureLayout(int creatureNumber){
            ImageButton layout = new ImageButton(getActivity());
            layout.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
            layout.setPadding(5, 5, 5, 5);
            layout.setImageResource(((game.getActiveCreatures()).get(creatureNumber)).getCardImage());
            layout.setTag((game.getActiveCreatures()).get(creatureNumber).getCardName());
            layout.setScaleType(ImageView.ScaleType.FIT_XY);
            return layout;
        }

        public FrameLayout getPlayerCard(final int playerNum){
            /*
            name   lvl   bonus
            class   race  gender
            TODO: player card too large - make smaller
            */
            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.player_view, null);
            FrameLayout container = (FrameLayout)view.findViewById(R.id.player_container);
            final int pNum = playerNum;
            /*
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.getPlayer(pNum).getGear() != null && game.getPlayer(pNum).getGear().size() > 0) {
                        final Dialog displayDialog = new Dialog(getActivity());
                        displayDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        displayDialog.setContentView(R.layout.dialog_select_creature);
                        Button submit = (Button) displayDialog.findViewById(R.id.select_submit);
                        submit.setVisibility(View.GONE);
                        LinearLayout container = (LinearLayout) displayDialog.findViewById(R.id.creature_container);
                        for (Card inventory : (game.getPlayer(pNum)).getGear()) {
                            ImageButton image = new ImageButton(getActivity());
                            image.setScaleType(ImageView.ScaleType.FIT_XY);
                            image.setImageResource(inventory.getCardImage());
                            image.setLayoutParams(new LinearLayout.LayoutParams(400, 520));
                            container.addView(image);
                        }
                        container.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                displayDialog.dismiss();
                            }
                        });
                        displayDialog.show();
                    } else {
                        Toast.makeText(getActivity(), game.getPlayer(pNum).getPlayerName() + " has no gear equipped.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            */
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (game.getPlayer(playerNum).getAllGear() != null && game.getPlayer(playerNum).getAllGear().size() != 0) {
                        cardContainer.removeAllViews();
                        cardContainer.setVisibility(View.VISIBLE);
                        for (final Card card : game.getPlayer(playerNum).getAllGear()) {
                            ImageButton button = new ImageButton(getActivity());
                            button.setLayoutParams(new LinearLayout.LayoutParams(180, 240));
                            button.setScaleType(ImageView.ScaleType.FIT_XY);
                            button.setImageResource(card.getCardImage());
                            button.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    final Dialog cardView = new Dialog(v.getContext());
                                    cardView.setContentView(R.layout.card_view);
                                    ImageView container = (ImageView)cardView.findViewById(R.id.card_view);
                                    container.setImageResource(card.getCardImage());
                                    container.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            cardView.dismiss();
                                        }
                                    });
                                    cardView.show();
                                }
                            });
                            cardContainer.addView(button);
                            Log.e("Main PlayerCard", card.getCardName());
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "No Gear Equipped", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            TextView nameView = (TextView)container.findViewById(R.id.player_name);
            nameView.setText((game.getPlayer(playerNum)).getPlayerName());

            TextView levelView = (TextView)container.findViewById(R.id.player_level);
            levelView.setText(Integer.toString((game.getPlayer(playerNum)).getLevel()));

            TextView bonusView = (TextView)container.findViewById(R.id.player_bonus);
            bonusView.setText(Integer.toString(((game.getPlayer(playerNum)).getBonusValue())));

            TextView classRaceView = (TextView)container.findViewById(R.id.player_class_race);
            StringBuilder classRace = new StringBuilder();
            if((game.getPlayer(playerNum)).getClassRaceCards() != null && (game.getPlayer(playerNum)).getClassRaceCards().size() != 0) {
                for (Card card : (game.getPlayer(playerNum)).getClassRaceCards()) {
                    classRace.append(card.getCardName() + " ");
                }
            }
            classRaceView.setText(classRace.toString());

            TextView genderView = (TextView)container.findViewById(R.id.player_gender);
            genderView.setText(((game.getPlayer(playerNum)).getGender()).toString());
            return container;
        }
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Main) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
