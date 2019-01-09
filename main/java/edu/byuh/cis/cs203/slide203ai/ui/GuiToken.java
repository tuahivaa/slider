package edu.byuh.cis.cs203.slide203ai.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import edu.byuh.cis.cs203.slide203ai.R;
import edu.byuh.cis.cs203.slide203ai.logic.Player;
import edu.byuh.cis.cs203.slide203ai.util.TickListener;

/**
 * Represents a single X or O on the grid.
 * It is the graphical analog to the Player enum.
 */
public class GuiToken implements TickListener {

    private static class GridPosition {
        char row;
        char column;

        GridPosition(char r, char c) {
            row = r;
            column = c;
        }
    }

    private Player player;
    private RectF bounds;
    private PointF velocity;
    private PointF destination;
    private float tolerance;
    private Bitmap image;
    private GridPosition pos;
    public static int movers = 0;
    private boolean falling;


    /**
     * Create a new GuiToken object
     * @param p The Player (X or O) who created the token
     * @param parent which button was tapped to create the token
     * @param res the Resources object (used for loading image)
     */
    public GuiToken(Player p, GuiButton parent, Resources res, Context c) {
        this.bounds = new RectF(parent.getBounds());
        falling = false;
        velocity = new PointF();
        destination = new PointF();
        tolerance = bounds.height()/10f;
        player = p;


        if ( Prefs.getTheme( c ) == "USA" ) {

            if (player == Player.X) {
                image = BitmapFactory.decodeResource(res, R.drawable.player_x);
            } else {
                image = BitmapFactory.decodeResource(res, R.drawable.player_o);
            }
        } else {


            if (player == Player.X) {
                image = BitmapFactory.decodeResource(res, R.drawable.tour);
            } else {
                image = BitmapFactory.decodeResource(res, R.drawable.liberty);
            }

        }




        image = Bitmap.createScaledBitmap(image, (int) bounds.width(), (int) bounds.height(), true);
        if (parent.isTopButton()) {
            pos = new GridPosition((char) ('A' - 1), parent.getLabel());
        } else {
            pos = new GridPosition(parent.getLabel(), (char) ('1' - 1));
        }

    }

    /**
     * Draw the token at the correct location, using the correct
     * image (X or O)
     * @param c The Canvas object supplied by onDraw
     */
    public void draw(Canvas c) {
        c.drawBitmap(image, bounds.left, bounds.top, null);
    }

    public boolean matches(char row, char col) {
        return (pos.row == row && pos.column == col);
    }

    /**
     * Move the token by its current velocity.
     * Stop when it reaches its destination location.
     */
    private void move() {
        if (falling) {
            velocity.y *= 2;
        } else {
            if (isMoving()) {
                float dx = destination.x - bounds.left;
                float dy = destination.y - bounds.top;
                if (PointF.length(dx, dy) < tolerance) {
                    bounds.offsetTo(destination.x, destination.y);
                    velocity.set(0,0);
                    movers--;
                    if (fellOff()) {
                        velocity.set(0, 1);
                        falling = true;
                    }
                }
            }
        }
        bounds.offset(velocity.x, velocity.y);
    }

    private boolean fellOff() {
        return (pos.column > '5' || pos.row > 'E');
    }

    public boolean isInvisible(int h) {
        return (bounds.top > h);
    }

    /**
     * Helper method for tokens created by the top row of buttons
     */
    public void moveDown() {
        setGoal(bounds.left, bounds.top+bounds.height());
        pos.row++;
    }

    /**
     * Helper method for tokens created by the left column of buttons
     */
    public void moveRight() {
        setGoal(bounds.left+bounds.width(), bounds.top);
        pos.column++;
    }

    /**
     * Is animation currently happening?
     * @return true if the token is currently moving (i.e. has a non-zero velocity); false otherwise.
     */
    public boolean isMoving() {
        return (velocity.x > 0 || velocity.y > 0);
    }

    /**
     * Assign a destination location to the token
     * @param x the X coordinate where the token should stop
     * @param y the X coordinate where the token should stop
     */
    private void setGoal(float x, float y) {
        movers++;
        destination.set(x,y);
        float dx = destination.x - bounds.left;
        float dy = destination.y - bounds.top;
        velocity.x = dx/11f;
        velocity.y = dy/11f;
    }

    @Override
    public void onTick() {
        //Log.d("CS203", "onTick: movers="+movers);
        move();
    }

    public static boolean anyMoving() {
        //Log.d("CS203", "anyMoving: movers="+movers);
        return (movers > 0);
    }

    public static void resetMovers() {
        movers = 0;
    }
}
