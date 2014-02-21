/**************************************************************************
 Particles Explorer
 Copyright (C) 2000-2002  Poil d'Ortie

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 **************************************************************************/

package com.pdo.particles.utils;

import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

/**
 * The simulation's obstacles
 *
 * @author Poil d'Ortie
 */
public class PdoRectangle extends RoundRectangle2D.Double {
    private String bounds;
    private boolean active;

    private Line2D bottom;
    private Line2D left;
    private Line2D right;
    private Line2D top;

    /**
     * Default Constructor.
     *
     * @param xStart first corner's x coordinate
     * @param yStart first corner's y coordinate
     * @param xEnd   second corner's x coordinate
     * @param yEnd   second corner's y coordinate
     */
    public PdoRectangle(int xStart, int yStart, int xEnd, int yEnd, double round) {
        super(
                (double) Math.min(xStart, xEnd),
                (double) Math.min(yStart, yEnd),
                (double) (Math.max(xStart, xEnd) - Math.min(xStart, xEnd)),
                (double) (Math.max(yStart, yEnd) - Math.min(yStart, yEnd)), round, round);

        bounds = "" + xStart + "|" + yStart + "|" + xEnd + "|" + yEnd + "|";
        top = new Line2D.Double(x, y, x + width, y);
        bottom = new Line2D.Double(x, y + height, x + width, y + height);
        left = new Line2D.Double(x, y, x, y + height);
        right = new Line2D.Double(x + width, y, x + width, y + height);
        active = false;
    }

    /**
     * Returns the obstacle's bottom line.
     *
     * @return Line2D
     */
    public Line2D getBottom() {
        return this.bottom;
    }

    /**
     * Returns the obstacle's left line.
     *
     * @return Line2D
     */
    public Line2D getLeft() {
        return this.left;
    }

    /**
     * Returns the obstacle's right line.
     *
     * @return Line2D
     */
    public Line2D getRight() {
        return this.right;
    }

    /**
     * Returns the obstacle's top line.
     *
     * @return Line2D
     */
    public Line2D getTop() {
        return this.top;
    }

    /**
     * active getter.
     *
     * @return boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * active setter.
     *
     * @param active the new active value
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    protected String saveState() {
        return bounds + active + "|";
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        if (active) {
            return "***("
                    + x
                    + ","
                    + y
                    + ")->("
                    + (x + width)
                    + ","
                    + (y + height)
                    + ")***";
        } else {
            return "("
                    + x
                    + ","
                    + y
                    + ")->("
                    + (x + width)
                    + ","
                    + (y + height)
                    + ")";
        }
    }
}