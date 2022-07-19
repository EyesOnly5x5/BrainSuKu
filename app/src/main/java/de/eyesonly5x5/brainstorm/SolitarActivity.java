package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SolitarActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;
    // int[] scrSize;
    boolean hilfe = false;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitar);

        BUTTON_IDS = daten.getButtonIDs();
        TEXT_IDS = daten.getTextIDs();
        SetzeSpielfaeche();

        Button DasIstEs = findViewById( R.id.DasIstEs );
        DasIstEs.setTextSize( daten.getMetrics().pxToDp((int)(DasIstEs.getTextSize()*daten.getMetrics().getFaktor())) );
        DasIstEs.setOnClickListener(view -> {
            SolitarNeuStart();
        });
        if( true ){
            SolitarNeuStart();
        }
        TextView vw = findViewById(R.id.Losung);
        vw.setTextSize( daten.getMetrics().pxToDp((int)(vw.getTextSize()*daten.getMetrics().getFaktor())) );
        //vw.setText( "X:"+scrSize[0]+":Y:"+scrSize[1]+":gBS:"+ daten.getMetrics().getFaktor()+":sc:"+getResources().getDisplayMetrics().density );
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        daten.saveSolitar();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return( true );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        TextView oView = findViewById( R.id.Kopf );
        switch (item.getItemId()){
            case R.id.mischy:
                daten.deleSolitar();
                SolitarNeuStart();
                hilfe = false;
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitSoli );
                return( true );
        }
        return( super.onOptionsItemSelected( item) );
    }

    private void setzeButtonClick(){
        for( int i = 0, posLos = 0; i<daten.getMaxFelder(); i++ ){
            Button button = daten.buttons.get(i);
            if( daten.getNonoG( 0, i ) >= 0 ){
                button.setText( "" + ++posLos );

                final int ii = i;
                button.setOnClickListener(view -> {
                    if( !daten.getGewonnen() && (daten.getNonoG( 0, ii ) == 1) && (daten.istGedrueckt() == 0) ) {
                        button.setBackgroundResource(R.drawable.sh_btn_2);
                        daten.setNonoG( 0, ii, 2 );
                        daten.istGedrueckt( ii );
                    } else if( !daten.getGewonnen() && (daten.getNonoG( 0, ii ) == 2) && (daten.istGedrueckt() == ii) ){
                        button.setBackgroundResource(R.drawable.sh_btn_1);
                        daten.setNonoG( 0, ii, 1 );
                        daten.istGedrueckt( 0 );
                    } else if( !daten.getGewonnen() && (daten.getNonoG( 0, ii ) == 0) && (daten.istGedrueckt() != 0) ) {
                        int s = daten.istGedrueckt();
                        int b = ii;
                        int z = (b-s)/2;
                        if( (daten.getNonoG( 0, (s+z) ) == 1) && ((Math.abs(z) == 1) || (Math.abs(z) == 7))  ) {
                            daten.setNonoG( 0, (s+z), 0 );
                            daten.setNonoG( 0, s, 0 );
                            daten.setNonoG( 0, b, 1 );
                            button.setBackgroundResource(R.drawable.sh_btn_1);
                            daten.buttons.get(s+z).setBackgroundResource(R.drawable.sh_btn_3);
                            daten.buttons.get(s).setBackgroundResource(R.drawable.sh_btn_3);
                            daten.istGedrueckt( 0 );
                            daten.incZuege();
                            daten.DasIstEsSoli();
                            if( !daten.getGewonnen() ) {
                                TextView vw = findViewById(R.id.Losung);
                                vw.setText( (hilfe) ? ""+loesung( 1 )[daten.getZuege()]:"" );
                            }
                        }
                        if( daten.getGewonnen() ){
                            daten.getSoundBib( true ).playSound();
                        } else if( daten.getDashEnde() ){
                            daten.getSoundBib( false ).playSound();
                        }
                    } else {
                        Toast.makeText( this, "Zug nicht erlaubt.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                button.setOnClickListener(null);
            }
        }
    }

    private String[] loesung( int los ){
        String[] aLos;
        if( los == 1 ){
            /* 1.Lösung: */
            aLos = new String[]{ "15-17", "28-16", "21-23", "07-21", "16-28", "31-23", "24-22",
                    "21-23", "26-24", "23-25", "32-24", "24-26", "33-25", "26-24", "12-26", "27-25",
                    "13-27", "24-26", "27-25", "10-12", "25-11", "12-10", "03-11", "10-12",
                    "08-10", "01-09", "09-11", "02-10", "17-05", "12-10", "05-17" };
        } else if( los == 2 ){
            /* 2.Lösung:
            Der "Weltrekord" ist eine Lösung mit nur 18 Zügen von E. Bergholt aus dem Jahre 1912.
            */
            aLos = new String[]{
                    "15-17", "28-16", "21-23", "24-22", "26-24", "33-25", "18-30",
                    "31-33-25", "09-23", "01-09", "06-18-30-28-16-04",
                    "07-21-23-25", "13-11", "10-12",
                    "27-13-11", "03-01-09", "08-10-12-26-24-10", "05-17" };
        } else if( los == 3 ){
            /* 3.Lösung:
            Die folgende Lösungsweg ist elegant. Man wendet viermal den L-Zug an und gelangt dann zur Hausfigur
            (das Haus steht auf dem Kopf), die man mit einem Sechsfachsprung abbaut.
            */
            aLos = new String[]{
                    "05-17", "08-10", "01-09", "03-01", "16-04", "01-09", "28-16",
                    "21-23", "07-21", "24-22", "21-23", "26-24", "33-25", "31-33",
                    "18-30", "33-25", "06-18", "13-11", "27-13", "10-12", "13-11",
                    "24-26-12-10-08-22-24", "17-15", "29-17", "18-16", "15-17" };
        } else {
            aLos = new String[]{};
        }
        return( aLos );
    }
    private void SetzeSpielfaeche(){
        Button oView;
        GridLayout gridLayout = findViewById( R.id.butty );

        gridLayout.removeAllViews();

        int column = 1;
        int row = 3;
        int total = row*column;
        gridLayout.setColumnCount( column );
        gridLayout.setRowCount( row );

        for(int i = 0, c = 0, r = 0; i < total; i++, c++){
            if( c == column ){ c = 0; r++; }
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.topMargin = 15;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);

            if( r==0 || r==2 ) {
                oView = new Button(this);
                oView.setBackgroundResource(R.drawable.bg_rounded_g);
                if( r==0 ) {
                    oView.setText(R.string.title11);
                    oView.setTypeface(Typeface.DEFAULT_BOLD);
                    oView.setTextColor(oView.getContext().getResources().getColor(R.color.Richtig1));
                    oView.setTextSize((getResources().getDimension(R.dimen.NonoLogo) * 1.5f));
                    oView.setGravity(Gravity.CENTER);
                    oView.setId(TEXT_IDS[c]);
                    Button finalOView = oView;
                    oView.setOnClickListener(view -> {
                        hilfe = daten.getZuege() == 0;
                        finalOView.setTextColor(finalOView.getContext().getResources().getColor((!hilfe) ? R.color.Richtig1 : R.color.color2));
                        TextView vw = findViewById(R.id.Losung);
                        vw.setText((hilfe) ? "" + loesung(1)[daten.getZuege()] : "");
                    });
                    param.height = (int) getResources().getDimension(R.dimen.NonoNG);
                } else {
                    param.height = 10;
                }
                param.width = GridLayout.LayoutParams.MATCH_PARENT;
                param.setGravity(Gravity.CENTER);
                param.topMargin = 25;
                param.bottomMargin = 25;
            } else {
                GridLayout gL1 = new GridLayout( this );
                int column1 = 3;
                int row1 = 3;
                int total1 = row1*column1;
                gL1.setColumnCount( column1 );
                gL1.setRowCount( row1 );
                for( int i1 = 0, r1 = 0, c1 = 0; i1 < total1; i1++, c1++ ) {
                    if (c1 == column1) {
                        c1 = 0;
                        r1++;
                    }
                    GridLayout.LayoutParams param1 = new GridLayout.LayoutParams();
                    param1.topMargin = 0;
                    param1.setGravity(Gravity.CENTER);
                    param1.columnSpec = GridLayout.spec(c1);
                    param1.rowSpec = GridLayout.spec(r1);
                    param1.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param1.height = GridLayout.LayoutParams.WRAP_CONTENT;

                    int[][] quad = geneQuad(r1+1, c1+1 );
                    GridLayout gL2 = new GridLayout(this);
                    int column2 = quad[0].length;
                    int row2 = quad.length;
                    int total2 = row2 * column2;
                    gL2.setColumnCount(column2);
                    gL2.setRowCount(row2);
                    if (total2 == 4) {
                        gL2.setBackgroundColor(getResources().getColor(R.color.gray2));
                    } else {
                        gL2.setBackgroundColor(getResources().getColor(R.color.gray1));
                    }
                    for (int i2 = 0, c2 = 0, r2 = 0; i2 < total2; i2++, c2++) {
                        if (c2 == column2) {
                            c2 = 0;
                            r2++;
                        }
                        GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
                        // param2.topMargin = 15;
                        param2.setMargins(5, 5, 5, 5);
                        param2.setGravity(Gravity.CENTER);
                        param2.columnSpec = GridLayout.spec(c2);
                        param2.rowSpec = GridLayout.spec(r2);
                        oView = new Button(this);
                        if (total2 == 4) oView.setVisibility(View.INVISIBLE);
                        oView.setId(BUTTON_IDS[(quad[r2][c2])]);
                        oView.setTag(BUTTON_IDS[(quad[r2][c2])]);
                        // oView.setText( ""+BUTTON_IDS[(quad[r2][c2])] );
                        oView.setTextColor(oView.getContext().getResources().getColor(R.color.white));
                        oView.setTextSize((getResources().getDimension(R.dimen.NonoLogo) ));
                        oView.setTypeface(Typeface.DEFAULT_BOLD);
                        oView.setBackgroundResource(R.drawable.sh_btn_1);
                        if (BUTTON_IDS[(quad[r2][c2])] == 25)
                            oView.setBackgroundResource(R.drawable.sh_btn_3);
                        param2.height = (int) (getResources().getDimension(R.dimen.SoliBut) * daten.getMetrics().getFaktor());
                        param2.width = (int) (getResources().getDimension(R.dimen.SoliBut) * daten.getMetrics().getFaktor());
                        oView.setGravity(Gravity.CENTER);
                        oView.setPadding(0, 0, 0, 0);
                        gL2.addView(oView, param2);
                        daten.addButton(oView);
                    }
                    gL1.addView(gL2,param1);
                    continue;
                }
                param.setGravity( Gravity.CENTER );
                gridLayout.addView(gL1, param);
                continue;
            }

            oView.setGravity(Gravity.CENTER);
            if (i != 0) oView.setPadding(0, 0, 0, 0);

            param.rightMargin = 15;
            gridLayout.addView(oView, param);
            if (i == 0 || r == 0 || c == 0) daten.addText(oView);
            else daten.addButton(oView);
        }
        daten.sortButtons();
        daten.setGewonnen( false );
        TextView vw = findViewById( R.id.Losung );
        vw.setText( ""+ loesung( 1 )[daten.getZuege()] );
    }

    private void SolitarNeuStart(){
        daten.SolitarInit();
        setzeButtonClick();
        daten.loadSolitar();
        for( int i = 0; i < daten.getMaxFelder(); i++ ) {
            if( daten.getNonoG( 0, i ) == 0 ) {
                daten.buttons.get(i).setBackgroundResource(R.drawable.sh_btn_3);
            } else if( daten.getNonoG( 0, i ) == 1 ) {
                daten.buttons.get(i).setBackgroundResource(R.drawable.sh_btn_1);
            } else if( daten.getNonoG( 0, i ) == 2 ) {
                daten.buttons.get(i).setBackgroundResource(R.drawable.sh_btn_2);
                daten.istGedrueckt( i );
            }
        }
        daten.DasIstEsSoli();
        hilfe = false;
        Button but = findViewById( TEXT_IDS[0] );
        but.setTextColor( but.getContext().getResources().getColor( R.color.Richtig1 ) );
        TextView vw = findViewById( R.id.Losung );
        vw.setText( (hilfe) ? ""+loesung( 1 )[daten.getZuege()]:"" );
    }

    private int[][] geneQuad( int x, int y ){
        int[][] ret = new int[3][3];
        if( x == 1 && y == 1 ){
            ret = new int[][]{{0, 1}, {7, 8}};
        } else if( x == 1 && y == 2 ){
            ret = new int[][]{{2,3,4},{ 9,10,11}};
        } else if( x == 1 && y == 3 ){
            ret = new int[][]{{5,6},{12,13}};
        } else if( x == 2 && y == 1 ){
            ret = new int[][]{{14,15},{21,22},{28,29}};
        } else if( x == 2 && y == 2 ){
            ret = new int[][]{{16,17,18},{23,24,25},{30,31,32}};
        } else if( x == 2 && y == 3 ){
            ret = new int[][]{{19,20},{26,27},{33,34}};
        } else if( x == 3 && y == 1 ){
            ret = new int[][]{{35,36},{42,43}};
        } else if( x == 3 && y == 2 ){
            ret = new int[][]{{37,38,39},{44,45,46}};
        } else if( x == 3 && y == 3 ){
            ret = new int[][]{{40,41},{47,48}};
        }
        return( ret );
    }
}

