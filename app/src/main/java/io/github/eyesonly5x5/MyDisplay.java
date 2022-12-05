package io.github.eyesonly5x5;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class MyDisplay {
    private DisplayMetrics metrics;
    private String woMischen = "NixDA";

    public MyDisplay(Resources hier ){
        metrics = hier.getDisplayMetrics();
    }

    public int getWidthPixels(){
        return(metrics.widthPixels);
    }
    public int getHeightPixels(){
        return(metrics.heightPixels);
    }
    public int getDensity(){
        return (int) metrics.density;
    }
    public int getDensityDpi(){
        return(metrics.densityDpi);
    }
    public int getMinPixels(){
        return( (metrics.widthPixels < metrics.heightPixels) ? metrics.widthPixels : metrics.heightPixels );
    }
    public int getMaxPixels(){
        return( (metrics.widthPixels < metrics.heightPixels) ? metrics.heightPixels : metrics.widthPixels );
    }
    public int getButtonSize( int lang ){
        return( (int) (getMinPixels() / (Math.sqrt(lang)+2)) );
    }
    public int getButtonSize( int lang, int margin ){
        return( getButtonSize( lang, margin, 0 ) );
    }

    public int getButtonSize( int lang, int margin, int rand ){
        return( (int) ((getMinPixels()-rand-(margin*lang)) / lang) );
    }
    public void setWoMischen( String wert ){ woMischen = wert; }
    public String getWoMischen( ){ return( woMischen ); }

    public double getScreenSize() {
        double ySize = metrics.heightPixels / metrics.ydpi;
        double xSize = metrics.widthPixels / metrics.xdpi;

        // Bildschirmgrösse in Zoll
        return( Math.sqrt(xSize * xSize + ySize * ySize) );
    }

    public int getTextSize(){
        return((int) getScreenSize()*4);
    }

    public int pxToDp(int px) { return (int) (px / metrics.density); }
    public int dpToPx(int dp) { return (int) (dp * metrics.density); }
    public float getFaktor( boolean flg ){
        float ret = 1.0f;
        if( getMinPixels() < 600 ){
            ret = ("|SupaHirn|SupraHirni|".contains(getWoMischen())&&!flg)?0.4f:1.0f;
            //ret = 1.0f;
        } else if( getMinPixels() <= 1080 ){
            ret = 1.0f;
        } else if( getMinPixels() <= 1600 ){
            ret = 2.0f;
        } else {
            ret = 2.5f;
        }
        return( ret );
    }
    public float getFaktor( ) {
        return( getFaktor( false ) );
    }
}
