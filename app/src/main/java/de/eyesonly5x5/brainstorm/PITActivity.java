package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PITActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    // byte[][] Tast = daten.getTast();
    int[] BUTTON_IDS;
    final int[] oldID = { -1, -1 };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(daten.getActivity());
        // daten.setMyContext( this );
        BUTTON_IDS = daten.getButtonIDs();
        TextView AusG = findViewById(R.id.Ausgabe);
        daten.setAusgabe( AusG );
        // AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        TextView AusGtmp = findViewById(R.id.Kopf);
        AusGtmp.setTextSize( daten.getMetrics().pxToDp((int)(AusGtmp.getTextSize()*daten.getMetrics().getFaktor())) );
        Button DasIstEs = findViewById( R.id.DasIstEs );
        DasIstEs.setTextSize( daten.getMetrics().pxToDp((int)(DasIstEs.getTextSize()*daten.getMetrics().getFaktor())) );

        DasIstEs.setOnClickListener(view -> {
            if( DasIstEs.getText().equals( getApplicationContext().getString( R.string.NeuStart ) ) ){
                daten.Mischer();
                DasIstEs.setText( R.string.title9 );
            } else {
                daten.DasIstEsPIT();
                daten.getSoundBib( daten.getGewonnen() ).playSound();
                // if( daten.getGewonnen() ) DasIstEs.setText(R.string.NeuStart);
                DasIstEs.setText(R.string.NeuStart);
            }
        });

        for( int i = 1; i <= (daten.getAnzahl()*daten.getAnzahl()); i++ ) {
            daten.addViewButIDs( findViewById( getResources().getIdentifier("i" + i, "id", "de.eyesonly5x5.brainstorm")) );
        }

        for(int id : BUTTON_IDS) {
            ImageView button;
            button = findViewById(id);
            button.setOnClickListener(view -> {
                if( daten.getGeMischt() ){
                    daten.setGeMischt( false );
                    oldID[0] = -1; // Erstes
                    oldID[1] = -1; // Zweites
                }

                if( !daten.getGewonnen()) {
                    int id1 = Integer.parseInt(button.getTag().toString()) - 1;
                    // Log.d("DingsDA:", "id:"+id1+":old:"+oldID[0]+":NG:"+daten.getNonoG( 0, id1 ));
                    if( id1 == oldID[0]){
                        // Nix zu tun da vorher schon gedrückt
                        daten.getViewButID( oldID[0] ).setBackgroundColor( getResources().getColor( R.color.gray2 ) );
                        oldID[0] = -1;
                    } else if( oldID[0] == -1 ){
                        oldID[0] = id1;
                        daten.getViewButID( oldID[0] ).setBackgroundColor( getResources().getColor( R.color.white ) );
                    } else if( (oldID[0] != -1) && (oldID[1] == -1) ) {
                        oldID[1] = id1;
                        daten.imgButtons.get(oldID[0]).setBackground( daten.imgButtons.get(oldID[0] ).getContext().getResources().getDrawable( daten.getNonoG(0, oldID[1] ) ) );
                        daten.imgButtons.get(oldID[1]).setBackground( daten.imgButtons.get(oldID[1] ).getContext().getResources().getDrawable( daten.getNonoG(0, oldID[0] ) ) );
                        daten.setNonoG( 1, 0, daten.getNonoG( 0, oldID[0] ) );
                        daten.setNonoG( 0, oldID[0], daten.getNonoG( 0, oldID[1] ) );
                        daten.setNonoG( 0, oldID[1], daten.getNonoG( 1, 0 ) );
                        daten.getViewButID(oldID[0]).setBackgroundColor(daten.getViewButID(oldID[0]).getContext().getResources().getColor(R.color.gray2));
                        daten.getViewButID(oldID[1]).setBackgroundColor(daten.getViewButID(oldID[1]).getContext().getResources().getColor(R.color.gray2));
                        String tmp = daten.getPiTron( oldID[0] );
                        daten.setPiTron( oldID[0], daten.getPiTron( oldID[1] ) );
                        daten.setPiTron( oldID[1], tmp );
                        oldID[0] = -1;
                        oldID[1] = -1;
                    }
                }
            });
            button.getLayoutParams().width *= daten.getMetrics().getFaktor();
            button.getLayoutParams().height *= daten.getMetrics().getFaktor();
            daten.addImgButton(button);
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return( true );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mischy:
                daten.Mischer();
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitPIT );
                return( true );
        }
        return( super.onOptionsItemSelected( item) );
    }
}