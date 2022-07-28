package de.eyesonly5x5.brainsuku;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Globals  extends ListActivity {
    @SuppressLint("StaticFieldLeak")
    private static final Globals instance = new Globals();
    private static MyDisplay metrics;

    // Beispiele für Daten...
    private byte[][] Tast = new byte[100][9];
    private int maxFelder = 0;
    private final boolean[] Flg = new boolean[100];
    List<Integer> Color = new ArrayList<>();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;
    private TextView Ausgabe;
    List<Button> buttons = new ArrayList<>();
    List<TextView> TextV = new ArrayList<>();
    private int Zuege = 0;
    private int Anzahl = 0;
    private int Activity=-1;
    private Context myContext;
    private Resources myRes;
    private boolean gewonnen = true;
    private SoundBib SoundW;
    private SoundBib SoundF;
    private int Buty = 90;
    private int[][] SudoK;
    private boolean geloest;
    private boolean dashEnde;
    private int istGedrueckt = 0;
    private String woMischen = "Zauber";
    private boolean geMischt = false;

    // private Globals() { }

    public static Globals getInstance() {
        return instance;
    }

    public static void setMetrics( Resources hier ){
        metrics = new MyDisplay( hier );
    }
    public static MyDisplay getMetrics( ){
        return( metrics );
    }

    public int getMaxFelder() {
        return this.maxFelder;
    }

    public void setAusgabe(TextView wert) {
        Ausgabe = wert;
    }

    public SoundBib getSoundBib(boolean s) {
        return( (s)?SoundW:SoundF );
    }
    public void setSoundBib(boolean s, SoundBib wert) {
        if( s ) SoundW = wert;
        else SoundF = wert;
    }

    public boolean getGewonnen() {
        return gewonnen;
    }
    public void setGewonnen( boolean wert) {
        gewonnen = wert;
    }

    public void setActivity(int act){
        Activity = act;
    }
    public void setMyContext( MainActivity c) {
        myContext = c;
        myRes = myContext.getResources();
    }

    public void addButton(Button button) {
        buttons.add(button);
    }
    public void addText(TextView Text) { TextV.add(Text); }

    public int getAnzahl(){ return Anzahl; }

    public void setSudoK0( int pos, int wert ){ SudoK[0][pos] = wert; }
    public void setSudoK1( int pos, int wert ){ SudoK[1][pos] = wert; }
    public int getSudoK0( int pos ){ return( SudoK[0][pos] ); }
    public int getSudoK1( int pos ){ return( SudoK[1][pos] ); }

    public void setWoMischen( String wert ){
        woMischen = wert;
        metrics.setWoMischen(wert);
    }
    public String getWoMischen( ){ return( woMischen ); }
    public List<Integer> getColor(){ return( Color ); }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void Mischer() {
        int id, id1, id2, tmp;
        Random r = new Random();
        Zuege = 0;
        gewonnen = false;
        Ausgabe.setText("Züge: " + Zuege);
        geMischt = true;
        for (id = 0; id < maxFelder; id++) {
            Button button = buttons.get(id);
            Flg[id] = true;
            button.setBackgroundColor(myRes.getColor(R.color.DarkGreen));
            button.setTextColor(myRes.getColor(R.color.white));
        }
        for (int i = 0; i < 25; i++) {
            id = r.nextInt(maxFelder);
            for (int idS : Tast[id]) if (idS > 0) changeFlg(idS - 1);
        }
    }

    public void DasIstEsSudo(){
        gewonnen = checkSudokuZeilen() && checkSudokuSpalten() && checkSudokuBloecke();
        if( !gewonnen ){
            for( int i = 0; i < SudoK[0].length; i++ ){
                if( (SudoK[0][i] != 0) && (SudoK[0][i] != SudoK[2][i]) ){
                    buttons.get(i).setBackgroundResource( R.drawable.round_btn_3 );
                } else if( (SudoK[0][i] == 0) || ((SudoK[0][i] == SudoK[2][i]) && (SudoK[1][i] == 0)) ) {
                    buttons.get(i).setBackgroundResource( R.drawable.round_btn_1 );
                }
            }
        }
    }

    public void SudokuMischer(){
        for( int i = 0; i<SudoK[0].length; i++ ){
            SudoK[0][i] = 0;
            SudoK[1][i] = 0;
            SudoK[2][i] = 0;
        }
        SudokuGeneratorInit( );
        loadSudoku();
        gewonnen = false;
    }

    @SuppressLint("ResourceType")
    public void sortButtons(){
        // List<Button> but = new ArrayList<>();
        Button tmp;
        boolean tausch = true;
        while( tausch ){
            tausch = false;
            for( int i = 0; i<(buttons.size()-1); i++ ) {
                if( buttons.get(i).getId() > buttons.get((i+1)).getId() ) {
                    tmp = buttons.get(i);
                    buttons.set( i, buttons.get((i+1)) );
                    buttons.set( (i+1), tmp );
                    tausch = true;
                }
            }
        }
    }

    public void saveSudoku( ){
        String data = "";
        for( int i = 0; i<SudoK[0].length; i++ ){
            data += ""+SudoK[0][i]+",";
            data += ""+SudoK[1][i]+",";
            data += ""+SudoK[2][i]+"\n";
        }
        speichern( "SudoKuh.txt", data );
    }

    public void loadSudoku(){
        String[] data;
        int[][] tmp = new int[3][Anzahl*Anzahl];
        geloest = true;
        int zahl = 0;

        data = laden( "SudoKuh.txt", "0,0,0" );
        for( int i = 0; i<data.length; i++ ) {
            String[] x = data[i].split(",");
            tmp[0][i] = Integer.parseInt( x[0] );
            if( tmp[0][i] == 0 ){
                geloest = false;
                zahl++;
            }
            tmp[1][i] = Integer.parseInt( x[1] );
            tmp[2][i] = Integer.parseInt( x[2] );
        }
        if( !geloest && (zahl < 56) ){
            for( int i = 0; i<(SudoK[0].length); i++ ){
                SudoK[0][i] = tmp[0][i];
                SudoK[1][i] = tmp[1][i];
                SudoK[2][i] = tmp[2][i];
            }
        }
    }

    public void deleSudoku(){
        loeschen( "SudoKuh.txt" );
    }

    private void speichern( String filename, String data ){
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = myContext.openFileOutput(filename, myContext.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loeschen( String filename ){
        File file = new File( myContext.getFilesDir(), filename );
        file.delete();
    }

    private String[] laden( String filename, String vorlage ){
        String[] ret = new String[Anzahl*Anzahl];
        int i;
        for( i = 0; i < ret.length; i++ ) ret[i] = vorlage;
        i = 0;
        try {
            File in = new File( myContext.getFilesDir(), filename );
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                ret[i++] = line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return( ret );
    }

    private void SudokuGeneratorInit(){
        int x,y;
        Random r = new Random();
        for( int i=0; i<Anzahl; i++ ) SudoK[0][i] = i+1;
        for( int i=0; i<55; i++ ) {
            x = r.nextInt( Anzahl );
            y = r.nextInt( Anzahl );
            int tmp = SudoK[0][x];
            SudoK[0][x] = SudoK[0][y];
            SudoK[0][y] = tmp;
        }
        SudokuGenerator( 0 );
        for( int i=0; i<(Anzahl*Anzahl); i++ ) SudoK[2][i] = SudoK[0][i];

        for( int i=0; i<55; i++ ){
            x = r.nextInt( (Anzahl*Anzahl) );
            SudoK[0][x] = 0;
        }
        for( int i=0; i<(Anzahl*Anzahl); i++ ){
            SudoK[1][i] = 0;
            if( SudoK[0][i] != 0 ) SudoK[1][i] = 1;
        }
    }

    private void SudokuGenerator( int pos ){
        boolean flg;
        if( pos >= 0 && pos < (Anzahl * Anzahl) ) {
            if (SudoK[0][pos] != 0) {
                SudokuGenerator(pos + 1);
            } else {
                flg = true;
                while( flg ) {
                    SudoK[0][pos]++;
                    if (SudoK[0][pos] > Anzahl) {
                        SudoK[0][pos] = 0;
                        pos--;
                        if( pos < 0 ) break;
                        continue;
                    }
                    if( checkSudokuZeile( pos / Anzahl ) && checkSudokuSpalte( pos % Anzahl ) && checkSudokuBlock( pos ) ) {
                        SudokuGenerator(pos + 1);
                        flg = false;
                    }
                }
            }
        }
    }

    private boolean checkSudokuBlock( int pos ){
        boolean ret = true;
        int[] quad;
        if( Anzahl > 3 ){
            quad = geneQuad( pos );
            for( int i=0; i<Anzahl && ret; i++ ){
                if (SudoK[0][quad[i]] == 0) continue;
                for( int ii=0; ii<Anzahl && ret; ii++ ){
                    if (i == ii) continue;
                    if (SudoK[0][quad[i]] == SudoK[0][quad[ii]]) {
                        ret = false;
                    }
                }
            }
        }
        return( ret );
    }

    private boolean checkSudokuBloecke(){
        boolean ret = true;
        int[] quad;
        for( int pos=0; pos<Anzahl && ret; pos++ ){
            quad = geneQuad( pos );
            for( int i=0; i<Anzahl && ret; i++ ){
                if (SudoK[0][quad[i]] == 0) ret = false;
                for( int ii=0; ii<Anzahl && ret; ii++ ){
                    if (i == ii) continue;
                    if (SudoK[0][quad[i]] == SudoK[0][quad[ii]]) {
                        ret = false;
                    }
                }
            }
        }
        return( ret );
    }

    private int[] geneQuad( int pos ){
        int start = 0;
        int x, y;
        int[] ret = new int[Anzahl+1];
        x = pos / Anzahl;
        y = pos % Anzahl;
        if( x >= 0 && x <= 2 && y >= 0 && y <= 2 ){
            start = 0;
        } else if( x >= 0 && x <= 2 && y >= 3 && y <= 5 ){
            start = 3;
        } else if( x >= 0 && x <= 2 && y >= 6 && y <= 8 ){
            start = 6;
        } else if( x >= 3 && x <= 5 && y >= 0 && y <= 2 ){
            start = 27;
        } else if( x >= 3 && x <= 5 && y >= 3 && y <= 5 ){
            start = 30;
        } else if( x >= 3 && x <= 5 && y >= 6 && y <= 8 ){
            start = 33;
        } else if( x >= 6 && x <= 8 && y >= 0 && y <= 2 ){
            start = 54;
        } else if( x >= 6 && x <= 8 && y >= 3 && y <= 5 ){
            start = 57;
        } else if( x >= 6 && x <= 8 && y >= 6 && y <= 8 ){
            start = 60;
        }
        for( int i=0; i<=2; i++ ){
            ret[i] = start+i;
            ret[i+3] = start+i+Anzahl;
            ret[i+6] = start+i+(Anzahl*2);
        }
        return( ret );
    }

    private boolean checkSudokuZeile( int j ){
        boolean ret = true;
        for (int i=0; i<Anzahl && ret; i++) {
            if (SudoK[0][i+(j*Anzahl)] == 0) continue;
            for (int ii=0; ii<Anzahl && ret; ii++) {
                if (i == ii) continue;
                if (SudoK[0][i+(j*Anzahl)] == SudoK[0][ii+(j*Anzahl)]) {
                    ret = false;
                }
            }
        }
        return( ret );
    }

    private boolean checkSudokuSpalte( int j ){
        boolean ret = true;
        for (int i=0; i<Anzahl && ret; i++) {
            if (SudoK[0][j+(i*Anzahl)] == 0) continue;
            for (int ii=0; ii<Anzahl && ret; ii++) {
                if (i == ii) continue;
                if (SudoK[0][j+(i*Anzahl)] == SudoK[0][j+(ii*Anzahl)]) {
                    ret = false;
                }
            }
        }
        return( ret );
    }
    private boolean checkSudokuZeilen(){
        boolean ret = true;
        for( int j=0; j<Anzahl && ret; j++ ) {
            for (int i=0; i<Anzahl && ret; i++) {
                if (SudoK[0][i+(j*Anzahl)] == 0) ret = false;
                for (int ii=0; ii<Anzahl && ret; ii++) {
                    if (i == ii) continue;
                    if (SudoK[0][i+(j*Anzahl)] == SudoK[0][ii+(j*Anzahl)]) {
                        ret = false;
                    }
                }
            }
        }
        return( ret );
    }

    private boolean checkSudokuSpalten(){
        boolean ret = true;
        for( int j=0; j<Anzahl && ret; j++ ) {
            for (int i=0; i<Anzahl && ret; i++) {
                if (SudoK[0][j+(i*Anzahl)] == 0) ret = false;
                for (int ii=0; ii<Anzahl && ret; ii++) {
                    if (i == ii) continue;
                    if (SudoK[0][j+(i*Anzahl)] == SudoK[0][j+(ii*Anzahl)]) {
                        ret = false;
                    }
                }
            }
        }
        return( ret );
    }
 /*
 0 1 2
 3 4 5
 6 7 8
 */
    @SuppressLint("WrongConstant")
    private int anzahlButtons(){
        int AnzBut = (((metrics.getMaxPixels()) / (int)(this.Buty*metrics.getFaktor()))-3);
        // int dimenX = (int) metrics.getMinPixels() / (column+1);
        if( AnzBut > 11 ) AnzBut = 11;
        AnzBut *= Anzahl;
        return( AnzBut );
    }

    public int[] getButtonIDs() {
        int wer = getWerWoWas();
        int Buttys = (wer==0)?9:(wer==1)?16:(wer==2)?25:(wer==3)?anzahlButtons():(wer==4)?100:(wer>=5)?Anzahl*Anzahl:0;
        int[] ret = new int[Buttys];

        if( wer < 3 ){
            for (int i = 0; i < ret.length; i++) {
                ret[i] = myRes.getIdentifier("b"+(i+1), "id", myContext.getPackageName());
            }
        } else {
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (i + 1);
            }
            if( wer == 6 ){
                SudoK = new int[3][Anzahl*Anzahl];
            }
        }
        BUTTON_IDS = ret;
        maxFelder = BUTTON_IDS.length;
        return (BUTTON_IDS);
    }

    public int[] getTextIDs() {
        int[] ret = new int[Anzahl*2];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = (300 + i);
        }
        TEXT_IDS = ret;
        return (TEXT_IDS);
    }

    @SuppressLint("NonConstantResourceId")
    private int getWerWoWas(){
        int ret = -1;
        switch( Activity ){
            case R.layout.activity_sudoku:
                ret = 6;
                break;
        }
        return( ret );
    }

    public void setGameData( int anzahl ) {
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        Anzahl = anzahl;
        istGedrueckt = 0;
        dashEnde = false;
    }

    @SuppressLint("ResourceAsColor")
    public void changeFlg(int id) {
        Button button = buttons.get(id);
        if (Flg[id]) {
            button.setBackgroundColor(myRes.getColor(R.color.DarkRed));
            button.setTextColor(myRes.getColor(R.color.Gelb));
        } else {
            button.setBackgroundColor(myRes.getColor(R.color.DarkGreen));
            button.setTextColor(myRes.getColor(R.color.white));
        }
        Flg[id] = !Flg[id];
    }

    static class SoundBib extends AppCompatActivity {
        private SoundPool soundPool;
        List<Integer> sound = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_main);
        }

        public SoundBib(boolean s, Context context) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

            if( s ) {
                sound.add(soundPool.load(context, R.raw.won1, 1));
                sound.add(soundPool.load(context, R.raw.won2, 1));
                sound.add(soundPool.load(context, R.raw.won3, 1));
                sound.add(soundPool.load(context, R.raw.won4, 1));
                sound.add(soundPool.load(context, R.raw.won5, 1));
            } else {
                sound.add(soundPool.load(context, R.raw.fail1, 1));
                sound.add(soundPool.load(context, R.raw.fail2, 1));
                sound.add(soundPool.load(context, R.raw.fail3, 1));
                sound.add(soundPool.load(context, R.raw.fail4, 1));
            }
        }

        // When users click on the button "Gun"
        public void playSound() {
            soundPool.autoPause();
            Random r = new Random();
            int id = r.nextInt(sound.size());
            soundPool.play(sound.get(id), 0.25F, 0.25F, 0, 0, 1);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            soundPool.release();
            soundPool = null;
        }
    }

    public void Anleitung( Context dasDA, int Wat ) {
        Dialog customDialog = new Dialog( dasDA );
        customDialog.setContentView(R.layout.anleitung);
        TextView oView = customDialog.findViewById( R.id.Anleitung );
        String str = myRes.getString( Wat, myRes.getString( R.string.Wunschliste ) );
        oView.setText( str );
        Button bView = customDialog.findViewById( R.id.Warte );
        bView.setOnClickListener(view -> customDialog.dismiss());
        customDialog.setCancelable(false);
        customDialog.show();
    }
}