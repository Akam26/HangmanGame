package com.example.akam.s198596_s198572;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

public class play extends AppCompatActivity {
    Button button;

    private RelativeLayout keyboard;

    private ArrayList<String> word;
    private ArrayList<String> usedLetters;
    private ArrayList<String> usedWords = new ArrayList<String>();
    private int wordLength;
    private int noOfWrongGuesses;
    private int noOfCorrectGuesses;
    private int win=0;
    private int lose=0;
    private Button neste;

    private static boolean harTrykketNei = false;

    public void makeRandomWord(){
        Random random = new Random();
        String[] words = getResources().getStringArray(R.array.ordliste);

        String rand = words[random.nextInt(words.length)];

        if(!usedWords.isEmpty()) {
            if(usedWords.size() != words.length) {
                while (usedWords.contains(rand)) {
                    rand = words[random.nextInt(words.length)];
                    Log.d("Generate", "Avslår " + rand);
                }
            }
            else{
                //Spillet er slutt
                congratulation();
            }
        }

        //når ordet er valgt
        usedWords.add(rand);

        noOfWrongGuesses = 0;
        noOfCorrectGuesses = 0;
        wordLength = 0;

        word = new ArrayList<>();

        for(int i =0; i<rand.length(); i++)
        {
            String letter = Character.toString(rand.charAt(i));
            if (!word.contains(letter)) wordLength++;
            word.add(letter);
        }

        Log.d("PLAY", "word.size() = " + word.size());
    }

    private void updateMask()
    {
        String strMask = "";
        int count = 0, total = word.size();

        for (String wordLetter : word) {
            if (wordLetter.equals(" ")) {
                strMask += " ";
            } else if (usedLetters.contains(wordLetter)) {
                strMask += wordLetter;
            } else {
                strMask += "_";
            }

            if (count++ < total) strMask += " ";
        }

        ((TextView)findViewById(R.id.textView2)).setText(strMask);
    }

    public void buttonPress(View view) {
        Button button = (Button)view;
        Button letter = button;//button.getText().toString();
        Log.d("PLAY", "Trykket på " + letter.getText().toString());
        guess(letter);
        button.setEnabled(false);

    }



    private boolean guess(Button letter) {
        usedLetters.add(letter.getText().toString());

        updateMask();

        Log.d("PLAY", "noOfCorrectGuesses = " + noOfCorrectGuesses);
        Log.d("PLAY", "noOfWrongGuesses = " + noOfWrongGuesses);

        if (word.contains(letter.getText().toString())) {
            if (++noOfCorrectGuesses == wordLength) {
                Log.d("PLAY", "Du har vunnet.");
                win++;


                //***********HER***********
                showButton();
                updateStats();
                return true;
            } else {
                //noOfCorrectGuesses++;
                Log.d("PLAY", "Riktig bokstav.");
                return true;
            }
        } else {
            noOfWrongGuesses++;

            if (noOfWrongGuesses == 6) {
                Log.d("PLAY", "Du har tapt.");
                lose++;
                showButton();
                onBackPressed();
                updateStats();
            }

            updateImage();

        }
        return false;
    }

    private void updateImage() {
        if (noOfWrongGuesses == 6) {
            ImageView legr = (ImageView) findViewById(R.id.gallows);
            legr.setImageDrawable(getResources().getDrawable(R.drawable.legh));
        }
        else if(noOfWrongGuesses==1){
            ImageView head = (ImageView) findViewById(R.id.gallows);
            head.setImageDrawable(getResources().getDrawable(R.drawable.hode));
        }
        else if(noOfWrongGuesses ==2) {
            ImageView hals = (ImageView) findViewById(R.id.gallows);
            hals.setImageDrawable(getResources().getDrawable(R.drawable.hals));
        }
        else if(noOfWrongGuesses==3){
            ImageView arml = (ImageView) findViewById(R.id.gallows);
            arml.setImageDrawable(getResources().getDrawable(R.drawable.armv));
        }
        else if(noOfWrongGuesses==4){
            ImageView armr = (ImageView) findViewById(R.id.gallows);
            armr.setImageDrawable(getResources().getDrawable(R.drawable.armh));
        }
        else if(noOfWrongGuesses==5){
            ImageView legl = (ImageView) findViewById(R.id.gallows);
            legl.setImageDrawable(getResources().getDrawable(R.drawable.legv));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("PLAY", "er i onSaveInstanceState");


        outState.putInt("noOfCorrectGuesses", noOfCorrectGuesses);
        outState.putInt("noOfWrongGuesses", noOfWrongGuesses);
        outState.putStringArrayList("usedLetters", usedLetters);
        outState.putStringArrayList("word", word);
        outState.putInt("win", win);
        outState.putInt("lose", lose);
        outState.putStringArrayList("usedWords", usedWords);
        outState.putBoolean("harTrykketNei", harTrykketNei);
        outState.putBoolean("knapp", neste.isEnabled());
        Log.w("neste", String.valueOf(neste.isEnabled()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("PLAY", "er i onRestoreInstanceState");

        noOfWrongGuesses = savedInstanceState.getInt("noOfWrongGuesses");
        noOfCorrectGuesses = savedInstanceState.getInt("noOfCorrectGuesses");
        usedLetters = savedInstanceState.getStringArrayList("usedLetters");
        word = savedInstanceState.getStringArrayList("word");
        win = savedInstanceState.getInt("win");
        lose = savedInstanceState.getInt("lose");
        usedWords = savedInstanceState.getStringArrayList("usedWords");
        harTrykketNei=savedInstanceState.getBoolean("harTrykketNei");
        neste.setEnabled(savedInstanceState.getBoolean("knapp"));
        Log.w("neste", String.valueOf(savedInstanceState.getBoolean("knapp")));

        for (int i = 0; i < keyboard.getChildCount(); i++) {
            Button knapp = (Button)keyboard.getChildAt(i);

            if ( usedLetters.contains( knapp.getText().toString() )) {
                knapp.setEnabled(false);
            }
        }
        updateMask();
        updateImage();
        updateStats();
        //showButton();

            if(noOfWrongGuesses == 6 /*&& harTrykketNei*/){
                onBackPressed();
                }

    }


    private void updateStats(){

        final TextView txtValue = (TextView) findViewById(R.id.stat);
        txtValue.setText(getString(R.string.W) + String.valueOf(win));

        final TextView txtlos = (TextView) findViewById(R.id.stattap);
        txtlos.setText(getString(R.string.L) + String.valueOf(lose));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageValg.loadLang(this);
        setContentView(R.layout.activity_play);
        neste = (Button) findViewById(R.id.button2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences sprfs = getSharedPreferences(LanguageValg.LANGUAGE, Activity.MODE_PRIVATE);
        String lang= sprfs.getString(LanguageValg.LANGUAGE, LanguageValg.DEFAULT_LANGUAGE);
        if(lang.equals("en")){
            button = (Button) findViewById(R.id.æ);
            button.setVisibility(View.GONE);

            button = (Button) findViewById(R.id.ø);
            button.setVisibility(View.GONE);

            button = (Button) findViewById(R.id.å);
            button.setVisibility(View.GONE);
        }



        keyboard = (RelativeLayout)findViewById(R.id.knapper);
        usedLetters = new ArrayList<>();

        Log.d("PLAY", "Startet aktivitet PLAY");
        startNewGame();
        showButton();
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ImageView first = (ImageView) findViewById(R.id.gallows);
                first.setImageDrawable(getResources().getDrawable(R.drawable.step1));


                Log.d("PLAY", "Trykket på knapp");
                startNewGame();
                //onBackPressed();
            }
        });
        Button buttonExit = (Button)this.findViewById(R.id.button);
        buttonExit.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

    }


    private void startNewGame() {
        Log.d("PLAY", "Startet aktivitet PLAY");
        usedLetters = new ArrayList<>();
        ImageView first = (ImageView) findViewById(R.id.gallows);
        first.setImageDrawable(getResources().getDrawable(R.drawable.step1));
        makeRandomWord();
        updateMask();
        showButton();
        updateImage();
        for (int i = 0; i < keyboard.getChildCount(); i++) {
            Button knapp = (Button)keyboard.getChildAt(i);
            knapp.setEnabled(true);
        }

    }
    public boolean showButton()
    {
        if(noOfCorrectGuesses == wordLength || noOfWrongGuesses>=5){
            neste.setEnabled(true);
        }
        else
        {
            neste.setEnabled(false);
        }
        return true;
    }

    public String convArrtoString(){
        String ord = "";
        for(String s : word)
            ord+=s;
        return ord;
    }
    /***************HERRRRR***********/
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("GAMEOVER")

                .setMessage(getString(R.string.losemessage) + getString(R.string.ordvalue) + convArrtoString())
                .setPositiveButton(R.string.ja, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }

                })
                .setNegativeButton(R.string.nei, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startNewGame();
                    }
                })
                .show();
    }
    /*public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("GAME OVER!")
                .setMessage("DU HAR TAPT \n VIL DU AVSLUTTE SPILLET?")
                .setCancelable(false)
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                })
                .setNegativeButton("YES",null)
                .show();
    }*/

    public void congratulation(){
        new AlertDialog.Builder(this)
                .setTitle("OBS!!!")
                .setMessage(getString(R.string.finish)+ win +getString(R.string.taprunde)+lose)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .show();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
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
    /*public boolean norskBok()
    {
        for(int i=0>)
    }*/
}
