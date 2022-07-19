package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NonoGramActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonogram);

        BUTTON_IDS = daten.getButtonIDs();
        TEXT_IDS = daten.getTextIDs();
        TextView AusGtmp = findViewById(R.id.First);
        AusGtmp.setTextSize( daten.getMetrics().pxToDp((int)(AusGtmp.getTextSize()*daten.getMetrics().getFaktor())) );

        SetzeSpielfaeche();
        Button DasIstEs = findViewById( R.id.DasIstEs );
        DasIstEs.setTextSize( daten.getMetrics().pxToDp((int)(DasIstEs.getTextSize()*daten.getMetrics().getFaktor())) );

        DasIstEs.setOnClickListener(view -> {
            if( DasIstEs.getText().equals( getApplicationContext().getString( R.string.Mischa ) ) ){
                // TextView oView = findViewById( R.id.Kopf );
                daten.NonoGMischer();
                DasIstEs.setText( R.string.title9 );
                // Random Ran = new Random();
                // oView.setRotation( Ran.nextInt( 360 ) );
            } else {
                daten.DasIstEs();
                daten.getSoundBib( daten.getGewonnen() ).playSound();
                daten.setGewonnen( true );
                DasIstEs.setText(R.string.Mischa);
                daten.deleNonogram();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        daten.saveNonogram();
        if( daten.getGewonnen() ) daten.deleNonogram();
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
                daten.deleNonogram();
                daten.NonoGMischer();
                Button button = findViewById( R.id.DasIstEs );
                button.setText( R.string.title9 );

                // daten.NonoGView();
                //Random Ran = new Random();
                //oView.setRotation( Ran.nextInt( 360 ) );
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitNonoG );
                return( true );
        }

        return( super.onOptionsItemSelected( item) );
    }

    private void SetzeSpielfaeche(){
        Button oView;
        int buttonSize = 0;
        GridLayout gridLayout = findViewById( R.id.butty );

        gridLayout.removeAllViews();

        int column = daten.getAnzahl()+1;
        int row = daten.getAnzahl()+1;
        int total = row*column;
        buttonSize = daten.getMetrics().getButtonSize( (daten.getAnzahl()+4)*(daten.getAnzahl()+4) );
        gridLayout.setColumnCount( column );
        gridLayout.setRowCount( row );

        for(int i = 0, c = 0, r = 0; i < total; i++, c++){
            if( c == column ){ c = 0; r++; }

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.topMargin = 6;

            if( i==0 ){
                oView = new Button(this);
                oView.setGravity( Gravity.CENTER );
                oView.setId( R.id.Kopf );
                param.height = 0;
                param.width = 0;
            } else if( r==0 ){
                oView = new Button(this);
                oView.setTextColor( Color.parseColor("#FF000000" ) );
                oView.setBackgroundColor( Color.parseColor("#FFFFFFFF" ) );
                // oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt)*daten.getMetrics().getFaktor(true) );
                oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt) );
                oView.setId( TEXT_IDS[c-1] );
                param.height = (int) (getResources().getDimension(R.dimen.NonoNG)*daten.getMetrics().getFaktor());
                //param.width = (int) (50*daten.getMetrics().getFaktor());
                param.width = buttonSize;
                //param.topMargin = 0;
            } else if( c==0 ){
                oView = new Button(this);
                oView.setTextColor( Color.parseColor("#FF000000" ) );
                oView.setBackgroundColor( Color.parseColor("#FFFFFFFF" ) );
                //oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt)*daten.getMetrics().getFaktor(true) );
                oView.setTextSize( getResources().getDimension(R.dimen.NonoTxt) );
                oView.setId( TEXT_IDS[(daten.getAnzahl()+r-1)] );
                //param.height = (int) (90);
                param.height = buttonSize;
                param.width = (int) (getResources().getDimension(R.dimen.NonoNG)*daten.getMetrics().getFaktor())-40;
                param.topMargin = 16;
            } else {
                oView = new Button(this);
                oView.setId( BUTTON_IDS[(c+(daten.getAnzahl()*(r-1)-1))] );
                oView.setTag( BUTTON_IDS[(c+(daten.getAnzahl()*(r-1)-1))] );
                oView.setTextColor(oView.getContext().getResources().getColor(R.color.white));
                oView.setBackgroundColor(oView.getContext().getResources().getColor(R.color.DarkGreen));
                Button finalOView = oView;
                oView.setOnClickListener(view -> {
                    if (!daten.getGewonnen()) {
                        daten.toogleColor((Integer.parseInt(finalOView.getTag().toString()) - 1));
                    }
                });
                //param.height = (int)(50*daten.getMetrics().getFaktor());
                //param.width = (int) (50*daten.getMetrics().getFaktor());
                param.height = buttonSize;
                param.width = buttonSize;
            }
            oView.setGravity( Gravity.CENTER );
            oView.setPadding( 0, 0, 0, 0);

            param.rightMargin = 6;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(c);
            param.rowSpec = GridLayout.spec(r);
            gridLayout.addView( oView, param );
            if( i==0 || r==0 || c==0 ) {
                daten.addText(oView);
            } else {
                daten.addButton(oView);
            }
        }
    }
}