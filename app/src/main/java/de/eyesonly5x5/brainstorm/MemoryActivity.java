package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MemoryActivity extends AppCompatActivity {
    Globals daten = Globals.getInstance();
    byte[][] Tast = daten.getTast();
    int[] BUTTON_IDS;
    final int[] oldID = { -1, -1, 0, 1, 0 };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(daten.getActivity());
        // daten.setMyContext( this );
        BUTTON_IDS = daten.getButtonIDs();
        TextView AusGtmp = findViewById(R.id.Kopf);
        AusGtmp.setTextSize( daten.getMetrics().pxToDp((int)(AusGtmp.getTextSize()*daten.getMetrics().getFaktor())) );

        for(int id : BUTTON_IDS) {
            ImageView button;
            button = findViewById(id);
            button.setBackground( ContextCompat.getDrawable( this, daten.getMemoryPicDraw() ) );
            button.setOnClickListener(view -> {
                TextView AusG = daten.getAusgabe();
                if( daten.getGeMischt() ){
                    daten.setGeMischt( false );
                    oldID[0] = -1; // Erstes
                    oldID[1] = -1; // Zweites
                    oldID[2] =  0; // Nietenzähler
                    oldID[3] =  1; // gefindet in folge.
                    oldID[4] =  0; // gefindet gesammt.
                }
                if( (oldID[0] != -1) && (oldID[1] != -1) ) {
                    if (daten.getNonoG(0, oldID[0]) != daten.getNonoG(0, oldID[1])) {
                        daten.setNonoG(1, oldID[0], (daten.getNonoG(0, oldID[0]) == 0) ? 2 : 0);
                        daten.setNonoG(1, oldID[1], (daten.getNonoG(0, oldID[1]) == 0) ? 2 : 0);
                        daten.imgButtons.get(oldID[0]).setBackground(ContextCompat.getDrawable(daten.getMyContext(), daten.getMemoryPicDraw()));
                        daten.imgButtons.get(oldID[1]).setBackground(ContextCompat.getDrawable(daten.getMyContext(), daten.getMemoryPicDraw()));
                    }
                    oldID[0] = -1;
                    oldID[1] = -1;
                }

                if( !daten.getGewonnen()) {
                    int id1 = Integer.parseInt(button.getTag().toString()) - 1;
                    // Log.d("DingsDA:", "id:"+id1+":old:"+oldID[0]+":NG:"+daten.getNonoG( 0, id1 ));
                    if( id1 == oldID[0]){
                        // Nix zu tun da vorher schon gedrückt
                    } else if( oldID[0] == -1 && daten.getNonoG(1, id1 ) == 0 ){
                        oldID[0] = id1;
                        daten.setNonoG( 1, id1, 1 );
                        button.setBackground( ContextCompat.getDrawable( this, daten.getNonoG( 0, id1 ) ) );
                    } else if( (oldID[0] != -1) && (oldID[1] == -1) && (daten.getNonoG( 1, id1 ) == 0) ) {
                        oldID[1] = id1;
                        daten.setNonoG( 1, id1, 1 );
                        button.setBackground( ContextCompat.getDrawable( this, daten.getNonoG( 0, id1 ) ) );
                        if( (oldID[0] != -1) && (oldID[1] != -1) ) {
                            if (daten.getNonoG(0, oldID[0]) != daten.getNonoG(0, oldID[1])) {
                                daten.decZuege();
                                oldID[3] = 1;
                            } else {
                                daten.incZuege(oldID[3]);
                                oldID[3]++;
                                oldID[4]++;
                            }
                        }
                    } else if( id1 != -1 && (daten.getNonoG(0, id1 ) == 0) ){
                        daten.getSoundBib(false).playSound();
                        // Toast.makeText(this, "Niete gefindet.", Toast.LENGTH_LONG).show();
                        MyToast();
                        if( oldID[2] == 0 ){
                            daten.incZuege( 2 );
                            oldID[2] = 1;
                        } else {
                            daten.decZuege(oldID[2]);
                            oldID[2]++;
                        }
                    }
                    if( oldID[4] < daten.getMemoryPicsLEN() )
                        AusG.setText( getString( R.string.Punkte ) + daten.getZuege() );
                    else {
                        AusG.setText(getString(R.string.Punkte) + " " + daten.getZuege() + " --> Bravo");
                        daten.setGewonnen(true);
                        daten.getSoundBib(true).playSound();
                        Button Mischa = findViewById(R.id.Mischa);
                        Mischa.setText( R.string.Mischa );
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
                Button Mischa = findViewById(R.id.Mischa);
                Mischa.setText( R.string.Mischa2 );
                return( true );
            case R.id.AnLeit:
                daten.Anleitung( this, R.string.AnleitMemo );
                return( true );
        }
        return( super.onOptionsItemSelected( item) );
    }
    private void MyToast(){
        // Get your custom_toast.xml ayout
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));

        // set a message
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Niete gefindet.");

        // Toast...
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}