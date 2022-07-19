package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private final int[][] Colors = new int[11][5];
    List<Integer> Color = new ArrayList<>();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;
    private TextView Ausgabe;
    List<Button> buttons = new ArrayList<>();
    List<ImageView> imgButtons = new ArrayList<>();
    List<TextView> TextV = new ArrayList<>();
    private int Zuege = 0;
    private int Anzahl = 0;
    private int Activity=-1;
    private Context myContext;
    private boolean gewonnen = true;
    private SoundBib SoundW;
    private SoundBib SoundF;
    private int Buty = 90;
    private int Buty2 = 90;
    private int[][] NonoG;
    private int[][] SudoK;
    List<String> piTron = new ArrayList<>();
    private GridLayout myBlock;
    private boolean geloest;
    private boolean dashEnde;
    private int istGedrueckt = 0;
    private int MemoryPic = 0;
    private ArrayList<Integer> MemoryPics = new ArrayList<>();
    private String woMischen = "Zauber";
    private boolean geMischt = false;
    List<RelativeLayout>viewButIDs = new ArrayList<>();

    private Globals() { }

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

    public byte[][] getTast() {
        return Tast;
    }

    public int getZuege() {
        return Zuege;
    }
    public int decZuege( int Anzahl ) {
        Zuege -= Anzahl;
        return( Zuege );
    }
    public int decZuege() {
        return --Zuege;
    }
    public int incZuege() { return ++Zuege; }
    public int incZuege( int Anzahl ) {
        Zuege += Anzahl;
        return( Zuege );
    }

    public TextView getAusgabe() {
        return Ausgabe;
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

    public boolean getDashEnde() {
        return dashEnde;
    }
    public void setDashEnde( boolean wert) {
        dashEnde = wert;
    }

    public int getActivity(){
        return Activity;
    }
    public void setActivity(int act){
        Activity = act;
    }
    public void setMyContext( SupaHirnActivity c){
        myContext = c;
    }
    public void setMyContext( MainActivity c) { myContext = c; }
    public Context getMyContext( ) { return( myContext ); }

    public void addButton(Button button) {
        buttons.add(button);
    }
    public void addText(TextView Text) { TextV.add(Text); }
    public void addImgButton(ImageView button) {
        imgButtons.add(button);
    }

    public int getAnzahl(){ return Anzahl; }

    public void setSudoK0( int pos, int wert ){ SudoK[0][pos] = wert; }
    public void setSudoK1( int pos, int wert ){ SudoK[1][pos] = wert; }
    public int getSudoK0( int pos ){ return( SudoK[0][pos] ); }
    public int getSudoK1( int pos ){ return( SudoK[1][pos] ); }

    public void setNonoG( int vec, int pos, int wert ){ NonoG[vec][pos] = wert; }
    public int getNonoG( int vec, int pos ){ return( NonoG[vec][pos] ); }

    public int getMemoryPic(){ return( MemoryPic ); }
    public int getMemoryPicsLEN(){ return( MemoryPics.size() ); }
    public int getMemoryPicDraw(){
        int ret = 0;
        switch( MemoryPic ){
            case 0:
                ret = R.drawable.pics_01_00;
                break;
            case 1:
                ret = R.drawable.pics_02_00;
                break;
            case 2:
                ret = R.drawable.pics_03_00;
                break;
        }
        return( ret );
    }
    public RelativeLayout getViewButID( int pos ){ return( viewButIDs.get(pos) ); }
    public void addViewButIDs( RelativeLayout x ){
        viewButIDs.add( x );
    }

    public void setWoMischen( String wert ){
        woMischen = wert;
        metrics.setWoMischen(wert);
    }
    public String getWoMischen( ){ return( woMischen ); }
    public boolean getGeMischt(){ return( geMischt ); }
    public void setGeMischt( boolean wert ) { geMischt = wert; }

    public void setPiTron( int pos, String wert ){ piTron.set( pos, wert ); }
    public String getPiTron( int pos ){ return( piTron.get( pos ) ); }

    public int[][] getColors(){ return( Colors ); }

    public List<Integer> getColor(){ return( Color ); }

    public GridLayout getMyBlock(){ return( myBlock ); }
    public void setMyBlock( GridLayout Block ){ myBlock = Block; }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void Mischer() {
        int id, id1, id2, tmp;
        Random r = new Random();
        Zuege = 0;
        gewonnen = false;
        Ausgabe.setText("Züge: " + Zuege);
        NonoG = new int[2][Anzahl*Anzahl];
        geMischt = true;
        piTron = null;
        piTron = new ArrayList<>();
        if( woMischen == "Memory" ){
            Ausgabe.setText( myContext.getString( R.string.Punkte ) + Zuege );
            for( int i = 0; i < MemoryPics.size(); i++ ){
                NonoG[0][i] = MemoryPics.get(i);
                NonoG[0][(13+i)] = MemoryPics.get(i);
                NonoG[1][i] = 0;
                NonoG[1][(13+i)] = 0;
            }
            NonoG[0][12] = 0;
            NonoG[1][12] = 2;
            for (int i = 0; i < 55; i++) {
                id1 = r.nextInt(maxFelder);
                id2 = r.nextInt(maxFelder);
                tmp = NonoG[0][id1];
                NonoG[0][id1] = NonoG[0][id2];
                NonoG[0][id2] = tmp;
                tmp = NonoG[1][id1];
                NonoG[1][id1] = NonoG[1][id2];
                NonoG[1][id2] = tmp;
            }
            for (id = 0; id < maxFelder; id++) {
                ImageView button = imgButtons.get(id);
                button.setBackground( button.getContext().getResources().getDrawable( getMemoryPicDraw() ) );
            }
            /*Log.d( "DingsDA:", "Anz:"+MemoryPics.size() );
            for (int i = 0; i < NonoG[0].length; i++) {
                Log.d( "DingsDA:", "i:"+i+":NG:"+NonoG[0][i] );
            }*/
        } else if( woMischen == "PIT" ){
            for( int i = 0; i < MemoryPics.size(); i++ ){
                NonoG[0][i] = MemoryPics.get(i);
                NonoG[1][i] = 0;
                ImageView button = imgButtons.get(i);
                button.setBackground( button.getContext().getResources().getDrawable( NonoG[0][i] ) );
            }
            for( int i = 0; i < (Anzahl*Anzahl); i++ ){
                viewButIDs.get(i).setBackgroundColor(viewButIDs.get(i).getContext().getResources().getColor(R.color.gray2));
                piTron.add( ( (i/4)+1 )+"_"+( (i%4)+1 ) );
            }
        } else {
            for (id = 0; id < maxFelder; id++) {
                Button button = buttons.get(id);
                Flg[id] = true;
                button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkGreen));
                button.setTextColor(button.getContext().getResources().getColor(R.color.white));
            }
            for (int i = 0; i < 25; i++) {
                id = r.nextInt(maxFelder);
                for (int idS : Tast[id]) if (idS > 0) changeFlg(idS - 1);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void ColorMischer() {
        int id;
        Random r = new Random();
        Zuege = (maxFelder / Anzahl);
        gewonnen = false;
        for (int i = 0; i < Anzahl; i++) {
            id = r.nextInt( Color.size() );
            Colors[0][i] = id;
            Colors[Colors.length-1][i] = -1;
        }
        for (id = 0; id < maxFelder; id++) {
            Button button = buttons.get(id);
            if( id < Anzahl ) {
                button.setBackgroundColor(button.getContext().getResources().getColor(R.color.black));
                button.setText("");
            } else {
                button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkGreen));
            }
        }
        for (id = 0; id < Zuege; id++) {
            Button button = buttons.get(maxFelder+id);
            button.setText( (id!=(Zuege-1))?"":"<- Setzen" );
            button.setBackgroundColor( button.getContext().getResources().getColor( (id!=(Zuege-1))?R.color.gray: R.color.Gelb ) );
        }
    }

    public void openHiddenColor(){
        for (int id = 0; id < Anzahl; id++) {
            Button button = buttons.get(id);
            button.setBackgroundColor( Color.get( Colors[0][id] ) );
            // button.setText(""+Colors[0][id]);
        }
    }

    public void NonoGMischer(){
        Random r = new Random();
        String s;
        for( int i = 0; i<NonoG[0].length; i++ ){
            NonoG[0][i] = r.nextInt( 2 );
            NonoG[1][i] = 0;
        }
        loadNonogram();
        for( int i = 0; i<Anzahl; i++ ){
            List<Integer> z = new ArrayList<>();
            z.add(0);
            for( int j = 0; j<Anzahl; j++ ){
                if( NonoG[0][i+(j*Anzahl)] == 1 ){
                    z.set( z.size()-1, z.get( z.size()-1 )+1 );
                } else if( z.get( z.size()-1 ) != 0 ) z.add(0);
            }
            s = "";
            for( int j = 0; j<z.size(); j++ ){
                if( z.get(j) != 0 ) s += "" + z.get(j) + "\n";
            }
            TextV.get(i+1).setText( (s.length()!=0)?s.substring(0,s.length()-1):"0" );
        }
        for( int i = 0; i<Anzahl; i++ ){
            List<Integer> z = new ArrayList<>();
            z.add(0);
            for( int j = 0; j<Anzahl; j++ ){
                if( NonoG[0][j+(i*Anzahl)] == 1 ){
                    z.set( z.size()-1, z.get( z.size()-1 )+1 );
                } else if( z.get( z.size()-1 ) != 0 ) z.add(0);
            }
            s = "";
            for( int j = 0; j<z.size(); j++ ){
                if( z.get(j) != 0 ) s += "" + z.get(j) + "-";
            }
            TextV.get(i+Anzahl+1).setText( (s.length()!=0)?s.substring(0,s.length()-1):"0" );
        }
        for( int i = 0; i<NonoG[0].length; i++ ){
            Button button = buttons.get(i);
            button.setBackgroundColor(button.getContext().getResources().getColor((NonoG[1][i] == 1)?R.color.DarkRed:R.color.DarkGreen));
        }
        gewonnen = false;
    }

    /*
    @SuppressLint("SetTextI18n")
    public void NonoGView(){
        for( int i = 0; i<NonoG[0].length; i++ ){
            Button button = buttons.get(i);
            button.setText( "" + NonoG[0][i] );
        }
    }
    */

    public void DasIstEs(){
        gewonnen = true;
        for( int i = 0; i<NonoG[0].length; i++ ){
            Button button = buttons.get(i);
            if( NonoG[0][i] == NonoG[1][i] ){
                button.setBackgroundColor( button.getContext().getResources().getColor( (NonoG[0][i]==0)? R.color.Richtig0: R.color.Richtig1 ) );
            } else {
                gewonnen = false;
            }
        }
    }

    public void DasIstEsPIT(){
        gewonnen = true;
        for( int j=0; j<Anzahl; j++ ) {
            for (int i = 0; i < Anzahl; i++) {
                viewButIDs.get(j + (i * Anzahl)).setBackgroundColor(viewButIDs.get(j + (i * Anzahl)).getContext().getResources().getColor(R.color.gray2));
            }
        }
        for( int j=0; j<Anzahl; j++ ) {
            for (int i=0; i<Anzahl; i++) {
                String tmp[] = { piTron.get( i+(j*Anzahl) ), "" };
                String[] x = tmp[0].split("_");
                tmp[0] = "";
                tmp[1] = "";
                for (int ii=0; ii<Anzahl; ii++) {
                    if (i == ii) continue;
                    tmp[0] += ","+piTron.get( ii+(j*Anzahl) ).split("_")[0];
                    tmp[1] += ","+piTron.get( ii+(j*Anzahl) ).split("_")[1];
                }
                if( tmp[0].contains( x[0] ) || tmp[1].contains( x[1] ) ){
                    gewonnen = false;
                    viewButIDs.get(i+(j*Anzahl)).setBackgroundColor(viewButIDs.get(i+(j*Anzahl)).getContext().getResources().getColor(R.color.color2));
                }
                // Log.d("DingsDA:", "j:"+j+":i:"+i+":x0:"+x[0]+":x1:"+x[1]+":tmp0:"+tmp[0]+":tmp1:"+tmp[1]+":t1:"+tmp[0].contains( x[0] )+":t2:"+tmp[1].contains( x[1] ));
            }
        }
        // Log.d("DingsDA:", "---------------------------------------------------");
        for( int j=0; j<Anzahl; j++ ) {
            for (int i=0; i<Anzahl; i++) {
                String tmp[] = { piTron.get( j+(i*Anzahl) ), "" };
                String[] x = tmp[0].split("_");
                tmp[0] = "";
                tmp[1] = "";
                for (int ii=0; ii<Anzahl; ii++) {
                    if (i == ii) continue;
                    tmp[0] += ","+piTron.get( j+(ii*Anzahl) ).split("_")[0];
                    tmp[1] += ","+piTron.get( j+(ii*Anzahl) ).split("_")[1];
                }
                if( tmp[0].contains( x[0] ) || tmp[1].contains( x[1] ) ){
                    gewonnen = false;
                    viewButIDs.get(j+(i*Anzahl)).setBackgroundColor(viewButIDs.get(j+(i*Anzahl)).getContext().getResources().getColor(R.color.color2));
                }
                // Log.d("DingsDA:", "j:"+j+":i:"+i+":x0:"+x[0]+":x1:"+x[1]+":tmp0:"+tmp[0]+":tmp1:"+tmp[1]+":t1:"+tmp[0].contains( x[0] )+":t2:"+tmp[1].contains( x[1] ));
            }
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

    public void DasIstEsSoli(){
        int anz = 0;
        geloest = false;
        for( int i = 0; i < NonoG[0].length; i++ ){
            if( NonoG[0][i] == 0 ) anz++;
            if( NonoG[0][i] >= 1 ) {
                int[] p = new int[]{ i+1, i-1, i+7, i-7 };
                for( int j = 0; j < p.length; j++ ) {
                    if (p[j] < 0) p[j] = 0;
                    if (p[j] > NonoG[0].length) p[j] = 0;
                    if ( NonoG[0][p[j]] == 1 ) {
                        geloest = true;
                    }
                }
            }
        }
        gewonnen = ( (NonoG[0][24] == 1) && (anz == 32) );

        dashEnde = (!gewonnen && !geloest) || gewonnen;
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

    public void saveSolitar( ){
        String data = "";
        for( int i = 0; i<NonoG[0].length; i++ ){
            data += ""+NonoG[0][i]+",";
            data += ""+NonoG[1][i]+"\n";
        }
        speichern( "Solitar.txt", data );
    }

    public void loadSolitar(){
        String[] data;
        int zahl = 0;
        int[][] tmp = new int[2][Anzahl*Anzahl];

        data = laden( "Solitar.txt", "0,0" );
        for( int i = 0; i<data.length; i++ ) {
            String[] x = data[i].split(",");
            tmp[0][i] = Integer.parseInt( x[0] );
            if( tmp[0][i] == 0 ) zahl++;
            tmp[1][i] = Integer.parseInt( x[1] );
        }
        if( zahl < 32 ){
            for( int i = 0; i<(NonoG[0].length); i++ ){
                NonoG[0][i] = tmp[0][i];
                NonoG[1][i] = tmp[1][i];
            }
            Zuege = zahl-1;
        }
    }

    public void deleSolitar(){
        loeschen( "Solitar.txt" );
    }

    public void saveNonogram( ){
        String data = "";
        for( int i = 0; i<NonoG[0].length; i++ ){
            data += ""+NonoG[0][i]+",";
            data += ""+NonoG[1][i]+"\n";
        }
        speichern( "NonoG"+Anzahl+".txt", data );
    }

    public void loadNonogram(){
        String[] data;
        boolean flg = false;
        int[][] tmp = new int[2][Anzahl*Anzahl];

        data = laden( "NonoG"+Anzahl+".txt", "0,0" );
        for( int i = 0; i<data.length; i++ ) {
            String[] x = data[i].split(",");
            tmp[0][i] = Integer.parseInt( x[0] );
            if( tmp[0][i] > 0 ) flg = true;
            tmp[1][i] = Integer.parseInt( x[1] );
        }
        for( int i = 0; (i<(NonoG[0].length)) && flg; i++ ){
            NonoG[0][i] = tmp[0][i];
            NonoG[1][i] = tmp[1][i];
        }
    }

    public void deleNonogram(){
        loeschen( "NonoG"+Anzahl+".txt" );
    }

// /data/user/0/de.eyesonly5x5.brainstorm/files/Solitar.txt
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

    public void SolitarInit(){
        for( int i = 0; i<(Anzahl*Anzahl); i++ ){
            NonoG[0][i] = 1;
            switch( i ){
                case 0: case 1: case 5: case 6: case 7: case 8: case 12: case 13:
                case 35: case 36: case 40: case 41: case 42: case 43: case 47: case 48:
                    NonoG[0][i] = -1;
                    break;
                case 24:
                    NonoG[0][i] = 0;
            }
            NonoG[1][i] = 0;
        }
        Zuege = 0;
        istGedrueckt = 0;
    }

    public int istGedrueckt(){ return( istGedrueckt ); }
    public void istGedrueckt( int wert ){ istGedrueckt = wert; }

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

    public int getButy(){ return( (metrics.getFaktor()<2)?Buty:Buty*3/4 ); }
    public int getButy2(){ return( Buty2 ); }

    public int[] getButtonIDs() {
        int wer = getWerWoWas();
        int Buttys = (wer==0)?9:(wer==1)?16:(wer==2)?25:(wer==3)?anzahlButtons():(wer==4)?100:(wer>=5)?Anzahl*Anzahl:0;
        int[] ret = new int[Buttys];

        if( wer < 3 ){
            for (int i = 0; i < ret.length; i++) {
                ret[i] = myContext.getResources().getIdentifier("b"+(i+1), "id", myContext.getPackageName());
            }
        } else {
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (i + 1);
            }
            if( wer == 5 ){
                NonoG = new int[2][Anzahl*Anzahl];
            } else if( wer == 6 ){
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
            case R.layout.activity_pit:
                ret = 1;
                break;
            case R.layout.activity_memory:
                ret = 2;
                break;
            case R.layout.activity_supahirn:
                ret = 3;
                break;
            case R.layout.activity_nonogram:
            case R.layout.activity_solitar:
                ret = 5;
                break;
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

    public void setGameData( String txt ) {
        Random r = new Random();
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        imgButtons = null;
        imgButtons = new ArrayList<>();
        viewButIDs = null;
        viewButIDs = new ArrayList<>();
        istGedrueckt = 0;
        dashEnde = false;
        MemoryPics = new ArrayList<>();
        if( txt == "Memory" ) {
            Anzahl = 5;
            MemoryPic = r.nextInt(3 );
            String dat = "pics_" + StrZ(MemoryPic+1, 2) + "_";
            for (int i = 1; i <= 12; i++) {
                MemoryPics.add(myContext.getResources().getIdentifier(dat + StrZ(i, 2), "drawable", "de.eyesonly5x5.brainstorm"));
            }
        } else {
            Anzahl = 4;
            MemoryPic = r.nextInt(2 );
            String dat = (MemoryPic == 0) ? "eye" : "face";
            for (int i = 1; i <= Anzahl; i++) {
                for (int j = 1; j <= Anzahl; j++) {
                    // Log.d( "DingsDA:", "i:"+i+":j:"+j+":NG:"+dat +"_"+ i +"_"+ j + ":vB:"+ (((i-1)*Anzahl)+j) +":id:"+ myContext.getResources().getIdentifier("i"+ (((i-1)*Anzahl)+j), "id", "de.eyesonly5x5.brainstorm") );
                    MemoryPics.add(myContext.getResources().getIdentifier(dat +"_"+ i +"_"+ j, "drawable", "de.eyesonly5x5.brainstorm"));
                    // viewButIDs.add(  myContext.getResources().getIdentifier("i"+ (((i-1)*Anzahl)+j), "id", "de.eyesonly5x5.brainstorm"));
                }
            }
        }
        maxFelder = Anzahl*Anzahl;
        NonoG = new int[2][Anzahl*Anzahl];
    }

    public void setGameData(int[] wert, int anzahl) {
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        imgButtons = null;
        imgButtons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        Color = null;
        Color = new ArrayList<>();
        Anzahl = anzahl;
        istGedrueckt = 0;

        for (int x : wert) Color.add(x);
    }

    public void setGameData(String[] tast) {
        maxFelder = tast.length;
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        istGedrueckt = 0;

        if( maxFelder > 1 ){
            for ( byte feld = 0; feld < 100; feld++ )
                for( byte x = 0; x<9; x++ ) this.Tast[feld][x] = 0;
            int i = 0;
            for (String x : tast) {
                String[] tmp = x.split(",");
                int j = 0;
                for (String y : tmp) {
                    this.Tast[i][j] = Byte.parseByte(y.trim());
                    j++;
                }
                i++;
            }
            i = 0;
            for (String x : tast) {
                Flg[i] = true;
                i++;
            }
        } else {
            maxFelder = 100;
            for ( byte feld = 0; feld < 100; feld++ )
                for( byte x = 0; x<9; x++ ) this.Tast[feld][x] = beRechneFeld((byte) (feld + 1), x );
            for ( int i = 0; i < 100; i++ ) Flg[i] = true;
        }
    }

    private byte beRechneFeld( byte id, byte i ){
        byte[] arr = {(byte) (id-9), (byte) (id-10), (byte) (id-11), (byte) (id-1), id, (byte) (id+1), (byte) (id+9), (byte) (id+10), (byte) (id+11)};
        if( (id%10)==1 ) { arr[2] = 0; arr[3] = 0; arr[6] = 0; }
        else if( (id%10)==0 ) { arr[0] = 0; arr[5] = 0; arr[8] = 0; }

        if( arr[i] < 0 ) arr[i] = 0;
        if( arr[i] > maxFelder ) arr[i] = 0;
        return( arr[i] );
    }

    @SuppressLint("ResourceAsColor")
    public void changeFlg(int id) {
        Button button = buttons.get(id);
        if (Flg[id]) {
            button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkRed));
            button.setTextColor(button.getContext().getResources().getColor(R.color.Gelb));
        } else {
            button.setBackgroundColor(button.getContext().getResources().getColor(R.color.DarkGreen));
            button.setTextColor(button.getContext().getResources().getColor(R.color.white));
        }
        Flg[id] = !Flg[id];
    }

    public boolean checkFlg() {
        boolean Flage = true;
        for (int i = 0; i < maxFelder && Flage; i++) if (!Flg[i]) Flage = false;
        return (Flage);
    }

    public boolean checkColor( Button button ){
        int richtig=0, vorhanden=0;
        int[][] c = new int[2][Anzahl];
        for( int i=0; i < Anzahl; i++ ) {
            c[0][i] = Colors[0][i];
            c[1][i] = Colors[Colors.length - 1][i];
            Colors[Colors.length - 1][i] = -1;
            if (c[0][i] == c[1][i]) {
                richtig++;
                c[0][i] = -1;
                c[1][i] = -1;
            }
        }
        for( int i=0; i < Anzahl; i++ ) {
            for( int j=0; j < Anzahl; j++ ){
                if( (c[0][j] != -1) && (c[1][i] != -1) && (c[0][j] == c[1][i]) ){
                    vorhanden++;
                    c[0][j] = -1;
                    c[1][i] = -1;
                }
            }
        }
        button.setText("R:"+richtig+" / V:"+vorhanden);
        return( richtig == Anzahl );
    }

    public boolean colorPos( int id ){
        boolean Flage;
        Flage = ((Zuege+200) == (id+1)) || (id >= (((Zuege*Anzahl)-Anzahl)) && (id < (Zuege*Anzahl)));
        return( Flage );
    }

    public void toogleColor( int id ){
        Button button = buttons.get(id);
        NonoG[1][id] = (NonoG[1][id] == 0)?1:0;
        button.setBackgroundColor(button.getContext().getResources().getColor((NonoG[1][id] == 1)?R.color.DarkRed:R.color.DarkGreen));
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
    private String StrZ( int wert, int len ){
        String ret = ""+ wert;
        while( ret.length() < len ) ret = "0"+ ret;
        return( ret );
    }

    public void Anleitung( Context dasDA, int Wat ) {
        Dialog customDialog = new Dialog( dasDA );
        customDialog.setContentView(R.layout.anleitung);
        TextView oView = customDialog.findViewById( R.id.Anleitung );
        oView.setText( Wat );
        Button bView = customDialog.findViewById( R.id.Warte );
        bView.setOnClickListener(view ->  {
            customDialog.dismiss();
        });
        customDialog.setCancelable(false);
        customDialog.show();
    }
}