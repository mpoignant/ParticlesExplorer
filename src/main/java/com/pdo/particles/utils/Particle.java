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

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.Vector;

/**
 * A particle.
 *
 * @author Poil d'Ortie
 */
public class Particle {
    private double dx, dy;
    private int lifespan;

    private double x, y;
    private double oldx, oldy;

    private int red, green, blue;
    private int age;

    /**
     * Default Constructor.
     */
    public Particle() {
        this.lifespan = 0;
        this.age = 0;
        double angle = Math.random() * 360;
        oldx = x = 0 + Math.random() * Math.cos(angle) * 0;
        oldy = y = 0 + Math.random() * Math.sin(angle) * 0;

        dx = 0 * Math.random() * Math.cos(angle);
        dy = 0 * Math.random() * Math.sin(angle);

        this.red = 0;
        this.green = 0;
        this.blue = 0;
    }

    /**
     * Create a particle for a random emitter
     *
     * @param lifespan     particle's lifespan
     * @param emitterX     emitter x coordinate
     * @param emitterY     emitter y coordinate
     * @param particleInitialDistanceFromEmitter
     *                     initial distance from emitter
     * @param emitterPower emitter power
     * @param red          particle's color's red component
     * @param green        particle's color's green component
     * @param blue         particle's color's blue component
     */
    public Particle(
            int lifespan,
            double emitterX,
            double emitterY,
            int particleInitialDistanceFromEmitter,
            int emitterPower,
            int red,
            int green,
            int blue) {

        this.lifespan = lifespan;
        this.age = lifespan;
        double angle = Math.random() * 360;
        oldx = x = emitterX + Math.random() * Math.cos(angle) * particleInitialDistanceFromEmitter;
        oldy = y = emitterY + Math.random() * Math.sin(angle) * particleInitialDistanceFromEmitter;

        dx = emitterPower * Math.random() * Math.cos(angle);
        dy = emitterPower * Math.random() * Math.sin(angle);

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Create a particle for an angled emitter
     *
     * @param lifespan     particle's lifespan
     * @param emitterX     emitter x coordinate
     * @param emitterY     emitter y coordinate
     * @param angle        emitter angle
     * @param dAngle       emitter spread
     * @param particleInitialDistanceFromEmitter
     *                     initial distance from emitter
     * @param emitterPower emitter power
     * @param red          particle's color's red component
     * @param green        particle's color's green component
     * @param blue         particle's color's blue component
     */
    public Particle(
            int lifespan,
            double emitterX,
            double emitterY,
            int angle,
            int dAngle,
            int particleInitialDistanceFromEmitter,
            int emitterPower,
            int red,
            int green,
            int blue) {

        this.lifespan = lifespan;
        this.age = lifespan;

        double dRad = dAngle * Math.PI * 2 / 360;
        double rad =
                Math.abs(-angle - 270) * Math.PI * 2 / 360
                        + (-dRad / 2 + dRad * Math.random());
        this.oldx = this.x = emitterX + Math.cos(rad) * particleInitialDistanceFromEmitter;
        this.oldy = this.y = emitterY + Math.sin(rad) * particleInitialDistanceFromEmitter;

        double speed = (double) emitterPower / 2.0 + (double) emitterPower / 2.0 * Math.random();
        this.dx = speed * Math.cos(rad);
        this.dy = speed * Math.sin(rad);

        this.red = red;
        this.green = green;
        this.blue = blue;
    }


    /**
     * particle's x coordinate getter.
     *
     * @return double
     */
    public double getX() {
        return this.x;
    }

    /**
     * particle's y coordinate getter.
     *
     * @return double
     */
    public double getY() {
        return this.y;
    }

    /**
     * particle's old x coordinate getter.
     *
     * @return double
     */
    public double getOldX() {
        return this.oldx;
    }

    /**
     * particle's old y coordinate getter.
     *
     * @return double
     */
    public double getOldY() {
        return this.oldy;
    }

    /**
     * particle's age getter.
     *
     * @return int
     */
    public int getAge() {
        return this.age;
    }

    /**
     * particle's color getter.
     *
     * @return Color
     */
    public Color getColor() {
        return new Color(this.red, this.green, this.blue);
    }

    /**
     * particle's color setter.
     *
     * @param color the new particle's color
     */
    public void setColor(Color color) {
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
    }

    /**
     * Reinit a particle for a random emitter
     *
     * @param lifespan     particle's lifespan
     * @param emitterX     emitter x coordinate
     * @param emitterY     emitter y coordinate
     * @param particleInitialDistanceFromEmitter
     *                     initial distance from emitter
     * @param emitterPower emitter power
     * @param red          particle's color's red component
     * @param green        particle's color's green component
     * @param blue         particle's color's blue component
     */
    public void reinit(
            int lifespan,
            double emitterX,
            double emitterY,
            int particleInitialDistanceFromEmitter,
            int emitterPower,
            int red,
            int green,
            int blue) {

        this.lifespan = lifespan;
        this.age = lifespan;
        double angle = Math.random() * 360;
        this.oldx = this.x = emitterX + Math.random() * Math.cos(angle) * particleInitialDistanceFromEmitter;
        this.oldy = this.y = emitterY + Math.random() * Math.sin(angle) * particleInitialDistanceFromEmitter;

        this.dx = emitterPower * Math.random() * Math.cos(angle);
        this.dy = emitterPower * Math.random() * Math.sin(angle);

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Reinit a particle  for a random emitter
     *
     * @param lifespan     particle's lifespan
     * @param emitterX     emitter x coordinate
     * @param emitterY     emitter y coordinate
     * @param angle        emitter angle
     * @param dAngle       emitter spread
     * @param particleInitialDistanceFromEmitter
     *                     initial distance from emitter
     * @param emitterPower emitter power
     * @param red          particle's color's red component
     * @param green        particle's color's green component
     * @param blue         particle's color's blue component
     */
    public void reinit(
            int lifespan,
            double emitterX,
            double emitterY,
            int angle,
            int dAngle,
            int particleInitialDistanceFromEmitter,
            int emitterPower,
            int red,
            int green,
            int blue) {

        this.lifespan = lifespan;
        this.age = lifespan;

        double dRad = dAngle * Math.PI * 2 / 360;
        double rad =
                Math.abs(-angle - 270) * Math.PI * 2 / 360
                        + (-dRad / 2 + dRad * Math.random());
        this.oldx = this.x = emitterX + Math.cos(rad) * particleInitialDistanceFromEmitter;
        this.oldy = this.y = emitterY + Math.sin(rad) * particleInitialDistanceFromEmitter;

        double speed = (double) emitterPower / 2.0 + (double) emitterPower / 2.0 * Math.random();
        this.dx = speed * Math.cos(rad);
        this.dy = speed * Math.sin(rad);

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Moves the particle .
     *
     * @param width     the display width
     * @param height    the display height
     * @param obstacles the obstacles
     * @param gravity   the gravity
     * @param wind      the wind
     * @param bgRed     the background color's red component
     * @param bgGreen   the background color's green component
     * @param bgBlue    the background color's blue component
     */
    public void moveParticle(
            int width,
            int height,
            Vector obstacles,
            int gravity,
            int wind,
            int bgRed,
            int bgGreen,
            int bgBlue) {

        oldx = x;
        oldy = y;

        dy += 0.4 * gravity;

        x += dx + (double) wind / 8.0;
        y += dy;

        if (x < -dx || x > width - 1 - dx) {
            dx *= -1;
        }
        if (y < -dy) {
            dy *= -1;
        }
        if (y > height - dy - 1) {
            dy *= -0.5;
        }

        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            PdoRectangle rect = (PdoRectangle) iter.next();
            Line2D traj =
                    new Line2D.Double(
                            (double) oldx,
                            (double) oldy,
                            (double) x,
                            (double) y);

            if (rect.contains(oldx, oldy)) {
                age = 0;
            } else {
                if (traj.intersectsLine(rect.getTop())
                        || traj.intersectsLine(rect.getBottom())) {
                    if (dy > 0) {
                        dy *= -0.5;
                    } else {
                        dy *= -1;
                    }
                    y = oldy + dy;
                    x = oldx + dx;
                }
                if (traj.intersectsLine(rect.getLeft())
                        || traj.intersectsLine(rect.getRight())) {
                    dx *= -1;
                    y = oldy + dy;
                    x = oldx + dx;
                }
            }
        }

        if (lifespan < 1) lifespan = 1;
        if (bgRed >= red) {
            red += 255 / lifespan;
            if (red > bgRed) {
                red = bgRed;
            }
        } else {
            red -= 255 / lifespan;
            if (red < bgRed) {
                red = bgRed;
            }
        }
        if (bgGreen >= green) {
            green += 255 / lifespan;
            if (green > bgGreen) {
                green = bgGreen;
            }
        } else {
            green -= 255 / lifespan;
            if (green < bgGreen) {
                green = bgGreen;
            }
        }
        if (bgBlue >= blue) {
            blue += 255 / lifespan;
            if (blue > bgBlue) {
                blue = bgBlue;
            }
        } else {
            blue -= 255 / lifespan;
            if (blue < bgBlue) {
                blue = bgBlue;
            }
        }
        age--;
    }
}