package de.eyesonly5x5.brainsuku;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SudokuActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;
    int derButton;
    int nrButton=0;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        BUTTON_IDS = daten.getButtonIDs();
        TEXT_IDS = daten.getTextIDs();
        SetzeSpielfaeche();

        Button DasIstEs = findViewById( R.id.DasIstEs );
        DasIstEs.setTextSize( daten.getMetrics().pxToDp((int)(DasIstEs.getTextSize()*daten.getMetrics().getFaktor())) );
        DasIstEs.setOnClickListener(view -> {
            if( DasIstEs.getText().equals( getApplicationContext().getString( R.string.Mischa ) ) ){
                TextView oView = findViewById( R.id.Kopf );
                daten.SudokuMischer();
                setzeContectMenu();
                DasIstEs.setText( R.string.title9 );
            } else {
                daten.DasIstEsSudo();
                daten.getSoundBib( daten.getGewonnen() ).playSound();
                if( daten.getGewonnen( ) ) DasIstEs.setText(R.string.Mischa);
            }
        });
        if( true ){
            TextView oView = findViewById( R.id.Kopf );
            daten.SudokuMischer();
            setzeContectMenu();
            DasIstEs.setText( R.string.title9 );
        }

    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        daten.saveSudoku();
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
        switch (item.getItemId()){
            case R.id.mischy:
                daten.deleSudoku();
                daten.SudokuMischer();
                setzeContectMenu();
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitSudoK );
                return( true );
        }
        return( super.onOptionsItemSelected( item) );
    }

    @SuppressLint("ResourceType")
    private void setzeContectMenu(){
        for( int i = 0; i<daten.getMaxFelder(); i++ ){
            Button button = daten.buttons.get(i);
            if( daten.getSudoK1(i) == 0 ) {
                button.setBackgroundResource( R.drawable.round_btn_1 );
                button.setTextColor( button.getContext().getResources().getColor( R.color.white ) );
                if( daten.getSudoK0( i ) != 0 ) {
                    button.setText("" + daten.getSudoK0(i));
                } else {
                    button.setText("");
                }
                registerForContextMenu(button);
                button.setOnClickListener(view -> {
                    if (!daten.getGewonnen()) {
                        daten.setSudoK0( (button.getId() -1), nrButton );
                        if( nrButton > 0 ) {
                            button.setText("" + nrButton);
                        } else {
                            button.setText("");
                        }
                    }
                });
            } else {
                button.setBackgroundResource( R.drawable.round_btn_2 );
                button.setTextColor( button.getContext().getResources().getColor( R.color.black ) );
                button.setText( ""+ daten.getSudoK0( i ) );
                button.setOnClickListener(null);
            }
        }
    }

    private void SetzeSpielfaeche(){
        Button oView;
        GridLayout gridLayout = findViewById( R.id.butty );

        gridLayout.removeAllViews();

        int column = 1;
        int row = 5;
        int total = row*column;
        gridLayout.setColumnCount( column );
        gridLayout.setRowCount( row );

        for(int i = 0, c = 0, r = 0; i < total; i++, c++){
            if( c == column ){ c = 0; r++; }
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.topMargin = 5;
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);

            if( r==0 || r==2 || r==4 ){
                if( c==0 ) {
                    oView = new Button(this);
                    oView.setBackgroundResource( R.drawable.bg_rounded_g );
                    if( r==0 ) {
                        oView.setText(R.string.title10);
                        oView.setTypeface(Typeface.DEFAULT_BOLD);
                        oView.setTextColor(oView.getContext().getResources().getColor(R.color.Richtig1));
                        oView.setTextSize(getResources().getDimension(R.dimen.SudoLogo));
                        oView.setGravity(Gravity.CENTER);
                        oView.setId(TEXT_IDS[c]);
                        param.height = (int) getResources().getDimension(R.dimen.NonoNG);
                    } else {
                        param.height = 10;
                    }
                    param.width = GridLayout.LayoutParams.MATCH_PARENT;
                    param.setGravity( Gravity.CENTER );
                    param.topMargin = 5;
                    param.bottomMargin = 25;
                } else continue;
            } else if( r==3 ){
                if( c==0 ) {
                    GridLayout gL2 = new GridLayout( this );

                    int total2 = 10;
                    gL2.setColumnCount( total2 );
                    gL2.setRowCount( 1 );

                    for(int i2 = 0; i2 < total2; i2++) {
                        GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
                        param2.topMargin = 1;
                        param2.bottomMargin = 1;
                        param2.rightMargin = 1;
                        param2.leftMargin = 1;
                        param2.setGravity(Gravity.CENTER);
                        param2.columnSpec = GridLayout.spec(i2);
                        param2.rowSpec = GridLayout.spec(0);

                        oView = new Button(this);
                        oView.setId(90 + i2);
                        oView.setTag(i2);
                        oView.setText("" + i2);
                        oView.setTextColor(oView.getContext().getResources().getColor(R.color.white));
                        oView.setTextSize( ((getResources().getDimension(R.dimen.SudoLogo)/2)*daten.getMetrics().getFaktor()) -2 );
                        oView.setTypeface(Typeface.DEFAULT_BOLD);
                        oView.setBackgroundResource(R.drawable.round_btn_1);
                        if (daten.getSudoK0(derButton) == i2)
                            oView.setBackgroundResource(R.drawable.round_btn_3);
                        param2.height = (int) (getResources().getDimension(R.dimen.SudoBut) * daten.getMetrics().getFaktor());
                        param2.width = (int) (getResources().getDimension(R.dimen.SudoBut) * daten.getMetrics().getFaktor());
                        oView.setPadding(0, 0, 0, 0);
                        final int ii = i2;
                        oView.setOnClickListener(view -> {
                            findViewById( 90+nrButton ).setBackgroundResource(R.drawable.round_btn_1);
                            nrButton = ii;
                            findViewById( 90+nrButton ).setBackgroundResource(R.drawable.round_btn_3);
                            //daten.buttons.get(90+nrButton).setBackgroundResource(R.drawable.round_btn_3);
                        });

                        oView.setGravity(Gravity.CENTER);
                        gL2.addView(oView, param2);
                        daten.addButton(oView);
                    }
                    gridLayout.addView( gL2, param );
                }
                continue;
            } else {
                GridLayout gL1 = new GridLayout( this );
                int column1 = 3;
                int row1 = 3;
                int total1 = row1*column1;
                gL1.setColumnCount( column1 );
                gL1.setRowCount( row1 );
                for( int i1 = 0, r1 = 0, c1 = 0; i1 < total1; i1++, c1++ ) {
                    if( c1 == column1 ){ c1 = 0; r1++; }
                    GridLayout.LayoutParams param1 = new GridLayout.LayoutParams();
                    param1.topMargin = 5;
                    param1.setGravity(Gravity.CENTER);
                    param1.columnSpec = GridLayout.spec(c1);
                    param1.rowSpec = GridLayout.spec(r1);
                    param1.width = GridLayout.LayoutParams.WRAP_CONTENT;
                    param1.height = GridLayout.LayoutParams.WRAP_CONTENT;

                    int[] quad = geneQuad(r1, c1);
                    GridLayout gL2 = new GridLayout(this);
                    int column2 = 3;
                    int row2 = 3;
                    int total2 = row2 * column2;
                    gL2.setColumnCount(column2);
                    gL2.setRowCount(row2);
                    if ((c1 + r1) % 2 == 0) {
                        gL2.setBackgroundColor(getResources().getColor(R.color.gray1));
                    } else {
                        gL2.setBackgroundColor(getResources().getColor(R.color.gray2));
                    }
                    for (int i2 = 0, c2 = 0, r2 = 0; i2 < total2; i2++, c2++) {
                        if (c2 == column2) { c2 = 0; r2++; }
                        GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
                        param2.setMargins(1, 1, 1, 1);
                        param2.setGravity(Gravity.CENTER);
                        param2.columnSpec = GridLayout.spec(c2);
                        param2.rowSpec = GridLayout.spec(r2);
                        oView = new Button(this);
                        oView.setId(BUTTON_IDS[quad[(c2 + (3 * r2))]]);
                        oView.setTag(BUTTON_IDS[quad[(c2 + (3 * r2))]]);
                        oView.setTextColor(oView.getContext().getResources().getColor(R.color.white));
                        oView.setTextSize(((getResources().getDimension(R.dimen.SudoLogo) / 2) * daten.getMetrics().getFaktor())-10);
                        oView.setTypeface(Typeface.DEFAULT_BOLD);
                        oView.setBackgroundResource(R.drawable.round_btn_1);
                        param2.height = (int) (getResources().getDimension(R.dimen.SudoBut) * daten.getMetrics().getFaktor());
                        param2.width = (int) (getResources().getDimension(R.dimen.SudoBut) * daten.getMetrics().getFaktor());
                        oView.setGravity(Gravity.CENTER);
                        oView.setPadding(0, 0, 0, 0);
                        gL2.addView(oView, param2);
                        daten.addButton(oView);
                    }
                    param1.rightMargin = 5;
                    gL1.addView(gL2,param1);
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
    }

    private int[] geneQuad( int x, int y ){
        int start = 0;
        int[] ret = new int[daten.getAnzahl()+1];
        if( x == 0 && y == 0 ){
            start = 0;
        } else if( x == 0 && y == 1 ){
            start = 3;
        } else if( x == 0 && y == 2 ){
            start = 6;
        } else if( x == 1 && y == 0 ){
            start = 27;
        } else if( x == 1 && y == 1 ){
            start = 30;
        } else if( x == 1 && y == 2 ){
            start = 33;
        } else if( x == 2 && y == 0 ){
            start = 54;
        } else if( x == 2 && y == 1 ){
            start = 57;
        } else if( x == 2 && y == 2 ){
            start = 60;
        }
        for( int i=0; i<=2; i++ ){
            ret[i] = start+i;
            ret[i+3] = start+i+daten.getAnzahl();
            ret[i+6] = start+i+(daten.getAnzahl()*2);
        }
        return( ret );
    }
}
