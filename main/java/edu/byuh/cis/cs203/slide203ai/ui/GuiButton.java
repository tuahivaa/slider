package edu.byuh.cis.cs203.slide203ai.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;
import edu.byuh.cis.cs203.slide203ai.R;

/**
 * Responsible for the drawing and logic of each of the 10 buttons.
 */
public class GuiButton {

    private RectF bounds;
    private boolean pressed;
    private static Bitmap pressedButton, unpressedButton;
    private static boolean firstLoad = true;
    private char label;

    /**
     * Create a new button object
     * @param name the button's label, A-E or 1-5.
     * @param parent the View that created the button
     * @param x the left side of the button
     * @param y the top side of the button
     * @param width how tall/wide the button should be
     */
    public GuiButton(char name, View parent, float x, float y, float width) {
        label = name;
        bounds = new RectF(x, y, x+width, y+width);
        pressed = false;
        //This "firstLoad" thing is kind of a hack to save memory.
        if (firstLoad) {
            firstLoad = false;
            pressedButton = BitmapFactory.decodeResource(parent.getResources(), R.drawable.pressed_button);
            unpressedButton = BitmapFactory.decodeResource(parent.getResources(), R.drawable.unpressed_button);
            pressedButton = Bitmap.createScaledBitmap(pressedButton, (int)width, (int)width, true);
            unpressedButton = Bitmap.createScaledBitmap(unpressedButton, (int)width, (int)width, true);
        }
    }

    /**
     * Is the given (x,y) inside the button or not?
     * @param x
     * @param y
     * @return true if inside, false if outside
     */
    public boolean contains(float x, float y) {
        return bounds.contains(x, y);
    }

    /**
     * Called by the View when the user touches the button
     */
    public void press() {
        pressed = true;
    }

    /**
     * Called by the View when the user lets go of the button
     */
    public void release() {
        pressed = false;
    }

    //Draw the button on the screen, in the correct place
    public void draw(Canvas c) {
        if (pressed) {
            c.drawBitmap(pressedButton, bounds.left, bounds.top, null);
        } else {
            c.drawBitmap(unpressedButton, bounds.left, bounds.top, null);
        }
    }

    //"Getter" method for the button's label.
    public char getLabel() {
        return label;
    }

    /**
     * Is this button on the top row or the left column?
     * @return true if top, false if side.
     */
    public boolean isTopButton() {
        return (label >= '1' && label <= '5');
    }

    /**
     * Is this button on the top row or the left column?
     * @return true if side, false if top.
     */
    public boolean isLeftButton() {
        return (label >= 'A' && label <= 'E');
    }

    /**
     * Get the current dimensions of the button
     * @return a RectF object
     */
    public RectF getBounds() {
        return bounds;
    }

}
