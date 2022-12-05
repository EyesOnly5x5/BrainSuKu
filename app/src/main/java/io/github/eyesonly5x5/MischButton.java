package io.github.eyesonly5x5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import de.eyesonly5x5.brainsuku.R;

public class MischButton extends Fragment {
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    // MainActivity global = (MainActivity) super.getA  getApplication();
    Globals daten = Globals.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View fragview = inflater.inflate(R.layout.misch_button, parent, false);
        TextView AusG = fragview.findViewById(R.id.Ausgabe);
        daten.setAusgabe( AusG );
        AusG.setTextSize( daten.getMetrics().pxToDp((int)(AusG.getTextSize()*daten.getMetrics().getFaktor())) );
        if( daten.getWoMischen() == "Memory" ){
            AusG.setText( getString( R.string.Punkte ) );
        }
        Button Mischa = fragview.findViewById(R.id.Mischa);
        Mischa.setTextSize( daten.getMetrics().pxToDp((int)(Mischa.getTextSize()*daten.getMetrics().getFaktor())) );
        Mischa.setOnClickListener(view -> {
            daten.Mischer();
            Mischa.setText(R.string.Mischa2);
        });

        return(fragview);
    }
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }
}