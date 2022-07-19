package de.eyesonly5x5.brainstorm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Globals daten = Globals.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daten.setMyContext( this );
        daten.setMetrics(getResources());

        TextView AusG = findViewById(R.id.Kopf);
        AusG.setText(getString(R.string.title1));
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        AusG = findViewById(R.id.NonoGram);
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        daten.setSoundBib(true,new Globals.SoundBib( true,this));
        daten.setSoundBib(false,new Globals.SoundBib( false,this));

        Button SupaHirn = findViewById(R.id.SupaHirn);
        SupaHirn.setTextSize( daten.getMetrics().pxToDp((int)(SupaHirn.getTextSize()*daten.getMetrics().getFaktor())) );
        Button SupraHirni = findViewById(R.id.SupraHirni);
        SupraHirni.setTextSize( daten.getMetrics().pxToDp((int)(SupraHirni.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG5 = findViewById(R.id.NonoG5);
        NonoG5.setTextSize( daten.getMetrics().pxToDp((int)(NonoG5.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG6 = findViewById(R.id.NonoG6);
        NonoG6.setTextSize( daten.getMetrics().pxToDp((int)(NonoG6.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG7 = findViewById(R.id.NonoG7);
        NonoG7.setTextSize( daten.getMetrics().pxToDp((int)(NonoG7.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG8 = findViewById(R.id.NonoG8);
        NonoG8.setTextSize( daten.getMetrics().pxToDp((int)(NonoG8.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG9 = findViewById(R.id.NonoG9);
        NonoG9.setTextSize( daten.getMetrics().pxToDp((int)(NonoG9.getTextSize()*daten.getMetrics().getFaktor())) );
        Button NonoG10 = findViewById(R.id.NonoG10);
        NonoG10.setTextSize( daten.getMetrics().pxToDp((int)(NonoG10.getTextSize()*daten.getMetrics().getFaktor())) );
        // Button Sudoku3 = findViewById(R.id.Sudoku3);
        Button Sudoku9 = findViewById(R.id.Sudoku9);
        Sudoku9.setTextSize( daten.getMetrics().pxToDp((int)(Sudoku9.getTextSize()*daten.getMetrics().getFaktor())) );
        Sudoku9.setWidth( (int) ((Sudoku9.getText().length()/1.5f)*Sudoku9.getTextSize()) );
        Button Solitar = findViewById(R.id.Solitar);
        Solitar.setTextSize( daten.getMetrics().pxToDp((int)(Solitar.getTextSize()*daten.getMetrics().getFaktor())) );
        Solitar.setWidth( (int) ((Solitar.getText().length()/1.5f)*Solitar.getTextSize()) );
        Button Memory = findViewById(R.id.Memory);
        Memory.setTextSize( daten.getMetrics().pxToDp((int)(Memory.getTextSize()*daten.getMetrics().getFaktor())) );
        Button PIT = findViewById(R.id.PIT);
        PIT.setTextSize( daten.getMetrics().pxToDp((int)(PIT.getTextSize()*daten.getMetrics().getFaktor())) );

        SupaHirn.setOnClickListener(view -> {
            SupaHirn.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_supahirn);
            daten.setWoMischen( "SupaHirn" );
            daten.setGameData(getResources().getIntArray(R.array.color6),4);
            startActivity(new Intent(getApplicationContext(),SupaHirnActivity.class));
            SupaHirn.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        SupraHirni.setOnClickListener(view -> {
            SupraHirni.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_supahirn);
            daten.setWoMischen( "SupraHirni" );
            daten.setGameData(getResources().getIntArray(R.array.color8),5);
            startActivity(new Intent(getApplicationContext(),SupaHirnActivity.class));
            SupraHirni.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG5.setOnClickListener(view -> {
            NonoG5.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG5" );
            daten.setGameData(5);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG5.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG6.setOnClickListener(view -> {
            NonoG6.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG6" );
            daten.setGameData(6);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG6.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG7.setOnClickListener(view -> {
            NonoG7.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG7" );
            daten.setGameData(7);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG7.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG8.setOnClickListener(view -> {
            NonoG8.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG8" );
            daten.setGameData(8);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG8.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG9.setOnClickListener(view -> {
            NonoG9.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG9" );
            daten.setGameData(9);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG9.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        NonoG10.setOnClickListener(view -> {
            NonoG10.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_nonogram);
            daten.setWoMischen( "NonoG10" );
            daten.setGameData(10);
            startActivity(new Intent(getApplicationContext(),NonoGramActivity.class));
            NonoG10.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
/*        Sudoku3.setOnClickListener(view -> {
            Sudoku3.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_sudoku);
            daten.setGameData(3);
            startActivity(new Intent(getApplicationContext(),SudokuActivity.class));
            Sudoku3.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
*/        Sudoku9.setOnClickListener(view -> {
            Sudoku9.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_sudoku);
            daten.setWoMischen( "Sudoku" );
            daten.setGameData(9);
            startActivity(new Intent(getApplicationContext(),SudokuActivity.class));
            Sudoku9.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        Solitar.setOnClickListener(view -> {
            Solitar.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_solitar);
            daten.setWoMischen( "Solitar" );
            daten.setGameData(7);
            startActivity(new Intent(getApplicationContext(),SolitarActivity.class));
            Solitar.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        Memory.setOnClickListener(view -> {
            Memory.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_memory);
            daten.setWoMischen( "Memory" );
            daten.setGameData("Memory" );
            startActivity(new Intent(getApplicationContext(),MemoryActivity.class));
            Memory.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
        PIT.setOnClickListener(view -> {
            PIT.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            daten.setActivity(R.layout.activity_pit);
            daten.setWoMischen( "PIT" );
            daten.setGameData( "PIT" );
            startActivity(new Intent(getApplicationContext(), PITActivity.class));
            PIT.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
        });
    }
}