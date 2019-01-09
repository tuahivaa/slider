package edu.byuh.cis.cs203.slide203ai.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import edu.byuh.cis.cs203.slide203ai.R;


public class Splash extends Activity {

    private ImageView sv;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        sv = new ImageView(this);
        sv.setImageResource(R.drawable.splash);
        sv.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(sv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent m) {
        if (m.getAction() == MotionEvent.ACTION_UP) {
            float w = sv.getWidth();
            float h = sv.getHeight();
            RectF one = new RectF(0, h*(340f/1024f),
                    w*(300f/375f), h);
            RectF two = new RectF(w*(300f/375f),
                    h*(340f/1024f), w, h);
            RectF prefsButton = new RectF(w*(226f/300f),
                    0, w, h*(64f/512f));
            RectF about = new RectF(0,0,w,h*(128/1024f));
            float x = m.getX();
            float y = m.getY();
            if (one.contains(x,y)) {
                //start game in "right-duck" mode
                Intent charlie = new Intent(this, MainActivity.class);
                charlie.putExtra("MODE", "ONE");
                startActivity(charlie);
                finish();
            } else if (two.contains(x,y)) {
                //start game in "left-duck" mode
                Intent charlie = new Intent(this, MainActivity.class);
                charlie.putExtra("MODE", "TWO");
                startActivity(charlie);
                finish();
            } else if (about.contains(x,y)){

                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setTitle("ABOUT!")
                        .setMessage("BLUE = one player. RED = two player")
                        .setNeutralButton("ok",null)
                        ;
                AlertDialog box = ab.create();
                box.show();
                //about button

            }
            else {
                Intent connie = new Intent(this, Prefs.class);
                startActivity(connie);
            }
        }
        return true;
    }
}
