package edu.byuh.cis.cs203.slide203ai.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * This class is responsible for drawing the 5x5 grid on the screen
 */
public class Grid {

    private final int dim = 5;
    private float lineWidth;
    private Paint paint;
    private RectF bounds;
    private float cellWidth;

    /**
     * Set up the dimensions of the grid
     * @param x left side of grid
     * @param y top of grid
     * @param cellWidth size of each cell
     */
    public Grid(float x, float y, float cellWidth) {
        this.cellWidth = cellWidth;
        lineWidth = cellWidth/20;
        bounds = new RectF(x, y, x+cellWidth*dim, y+cellWidth*dim);
        paint = new Paint();
        paint.setStrokeWidth(lineWidth);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Simple "getter" method. Watch out Holub!
     * @return the uppermost y-coordinate of the grid
     */
    public float getTop() {
        return bounds.top;
    }

    /**
     * Draw the grid on the screen
     * @param c the Canvas object (supplied by the View)
     */
    public void draw(Canvas c) {
        for (int i=0; i<=dim; ++i) {
            c.drawLine(bounds.left, bounds.top + cellWidth*i, bounds.right, bounds.top + cellWidth*i, paint);
        }
        for (int i=0; i<=dim; ++i) {
            c.drawLine(bounds.left + cellWidth*i, bounds.top, bounds.left + cellWidth*i, bounds.bottom, paint);
        }
    }

}
