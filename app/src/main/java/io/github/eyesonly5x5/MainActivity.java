package io.github.eyesonly5x5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.eyesonly5x5.brainsuku.R;

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
        // AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        daten.setSoundBib(true,new Globals.SoundBib( true,this));
        daten.setSoundBib(false,new Globals.SoundBib( false,this));

        // Button Sudoku3 = findViewById(R.id.Sudoku3);
        Button Sudoku9 = findViewById(R.id.Sudoku9);
        Sudoku9.setTextSize( daten.getMetrics().pxToDp((int)(Sudoku9.getTextSize()*daten.getMetrics().getFaktor())) );
        Sudoku9.setWidth( (int) ((Sudoku9.getText().length()/1.5f)*Sudoku9.getTextSize()) );

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
    }
}