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

import com.pdo.particles.gui.DialogAbout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Class containing all shared data. This class, implementing the singleton pattern, is able to load and save itself.
 *
 * @author Poil d'Ortie
 */
public class DataBus {
    private static DataBus dataBus = null;

    //TODO : add constants for environment max values (lifespan, flow)
    public static final int FLOW_MAX_VALUE = 30;
    public static final int LIFESPAN_MAX_VALUE = 100;

    /**
     * The size of the arc that rounds off the corners of obstacles.
     */
    public static final double RECTANGLE_ARC_SIZE = 10.0;
    /**
     * The only instance of EtchedBorder used in the GUI
     */
    public static final EtchedBorder ETCHED_BORDER = new EtchedBorder();
    /**
     * The <b>play</b> icon
     */
    public static ImageIcon ANIM_PLAY;// = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/anim-play.png")));
    /**
     * The <b>pause</b> icon
     */
    public static ImageIcon ANIM_PAUSE;// = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/anim-pause.png")));
    /**
     * The <b>emitter</b> icon
     */
    public static ImageIcon EMIT_ON;// = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/emit-on.png")));
    /**
     * The <b>emitter off</b> icon
     */
    public static ImageIcon EMIT_OFF;// = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/emit-off.png")));
    /**
     * The <b>reinit emitter</b> icon
     */
    public static ImageIcon EMIT_NEW;// = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/emit-new.png")));
    /**
     * The application logo
     */
    private ImageIcon[] logos;

    // MainFrame Data
    private int mainFrameX, mainFrameY;
    private int mainFrameWidth, mainFrameHeight;

    // DialogAbout
    private DialogAbout dialogAbout;

    // Simulation Data
    // Animation
    private long sleepTimeBetweenFrames;
    // Emitter parameters
    // Number of particles
    private int numberOfParticles;
    // Particle Initial Distance
    private int particleInitialDistanceFromEmitter;
    // Emitter Power
    private int emitterPower;
    // Emitter Movement on/off
    private boolean emitterMoving;
    // Emitter Position
    private int emitterX, emitterY;
    // Emitter Fixed Angle on/off
    private boolean emitterAngled;
    // Emitter Angle
    private int emitterAngle;
    // Emitter Spread
    private int emitterSpread;
    // Evironment parameters
    private int environmentGravity;
    // Evironment Lifespan
    private int environmentLifespan;
    // Evironment Wind
    private int environmentWind;
    // Display Colors
    private Hashtable displayColors;
    // Display Rendering Mode
    private int displayRenderingMode;
    // Obstacles
    private Vector obstacles;
    // Particles Pool
    private ParticlesPool particlesPool;

    private long frameCtr;
    private long startingTime;
    private double fps;

    private DataBus() {
        int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
        mainFrameWidth = Math.min(780, screenX);
        mainFrameHeight = Math.min(550, screenY);
        mainFrameX = screenX / 2 - mainFrameWidth / 2;
        mainFrameY = screenY / 2 - mainFrameHeight / 2;
        sleepTimeBetweenFrames = 30L;
        numberOfParticles = 7;
        particleInitialDistanceFromEmitter = 5;
        emitterPower = 12;
        emitterMoving = true;
        emitterX = 0;
        emitterY = 0;
        emitterAngled = false;
        emitterAngle = 0;
        emitterSpread = 5;
        environmentGravity = 1;
        environmentLifespan = 30;
        environmentWind = 0;
        displayColors = new Hashtable();
        displayColors.put("bgColor", Color.black);
        displayColors.put("partColor", Color.white);
        displayRenderingMode = 0;
        obstacles = new Vector();
        particlesPool = new ParticlesPool((LIFESPAN_MAX_VALUE + 1) * FLOW_MAX_VALUE);

        try {
            ANIM_PLAY = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/anim-play.png")));
            ANIM_PAUSE = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/anim-pause.png")));
            EMIT_ON = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/emit-on.png")));
            EMIT_OFF = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/emit-off.png")));
            EMIT_NEW = new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/emit-new.png")));

            logos = new ImageIcon[4];
            for (int i = 1; i <= 4; i++) {
                logos[i - 1] =
                        new ImageIcon(ImageIO.read(ClassLoader.getSystemResource("images/splash" + i + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The only way to obtain the DataBus instance
     *
     * @return DataBus
     */
    public static DataBus getInstance() {
        if (dataBus == null) {
            dataBus = new DataBus();
            return dataBus;
        } else {
            return dataBus;
        }
    }

    /**
     * mainFrameLocation getter
     *
     * @return Point
     */
    public Point getMainFrameLocation() {
        return new Point(this.mainFrameX, this.mainFrameY);
    }

    /**
     * mainFrameLocation setter
     *
     * @param loc the new mainFrameLocation
     */
    public void setMainFrameLocation(Point loc) {
        this.mainFrameX = loc.x;
        this.mainFrameY = loc.y;
    }

    /**
     * mainFrameDimension getter
     *
     * @return Dimension
     */
    public Dimension getMainFrameDimension() {
        return new Dimension(this.mainFrameWidth, this.mainFrameHeight);
    }

    /**
     * mainFrameDimension setter
     *
     * @param width  the new mainFrameDimension width
     * @param height the new mainFrameDimension height
     */
    public void setMainFrameDimension(int width, int height) {
        this.mainFrameWidth = width;
        this.mainFrameHeight = height;
    }

    /**
     * dialogAbout getter
     *
     * @return DialogAbout
     */
    public DialogAbout getDialogAbout() {
        return this.dialogAbout;
    }

    /**
     * dialogAbout setter
     *
     * @param about the new dialogAbout
     */
    public void setDialogAbout(DialogAbout about) {
        this.dialogAbout = about;
    }

    /**
     * SplashScreen Logo getter
     *
     * @return ImageIcon
     */
    public ImageIcon getSplahScreenLogo() {
        return logos[(int) (Math.random() * 4.0F)];
    }


    /**
     * sleepTimeBetweenFrames getter
     *
     * @return long
     */
    public long getSleepTimeBetweenFrames() {
        return this.sleepTimeBetweenFrames;
    }

    /**
     * sleepTimeBetweenFrames setter
     *
     * @param sleepTimeBetweenFrames the new sleepTimeBetweenFrames
     */
    public void setSleepTimeBetweenFrames(long sleepTimeBetweenFrames) {
        this.sleepTimeBetweenFrames = sleepTimeBetweenFrames;
    }

    /**
     * numberOfParticles getter
     *
     * @return int
     */
    public int getNumberOfParticles() {
        return this.numberOfParticles;
    }

    /**
     * numberOfParticles setter
     *
     * @param numberOfParticles the new numberOfParticles
     */
    public void setNumberOfParticles(int numberOfParticles) {
        this.numberOfParticles = numberOfParticles;
    }

    /**
     * particleInitialDistanceFromEmitter getter
     *
     * @return int
     */
    public int getParticleInitialDistanceFromEmitter() {
        return this.particleInitialDistanceFromEmitter;
    }

    /**
     * emitterPower getter
     *
     * @return int
     */
    public int getEmitterPower() {
        return this.emitterPower;
    }

    /**
     * emitterPower setter
     *
     * @param emitterPower the new emitterPower
     */
    public void setEmitterPower(int emitterPower) {
        this.emitterPower = emitterPower;
    }

    /**
     * emitterMoving getter
     *
     * @return boolean
     */
    public boolean getEmitterMoving() {
        return this.emitterMoving;
    }

    /**
     * emitterMoving setter
     *
     * @param moving the new emitterMoving
     */
    public void setEmitterMoving(boolean moving) {
        this.emitterMoving = moving;
    }

    /**
     * emitterX getter
     *
     * @return int
     */
    public int getEmitterX() {
        return this.emitterX;
    }

    /**
     * emitterX setter
     *
     * @param emitterX the new emitterX
     */
    public void setEmitterX(int emitterX) {
        this.emitterX = emitterX;
    }

    /**
     * emitterY getter
     *
     * @return int
     */
    public int getEmitterY() {
        return this.emitterY;
    }

    /**
     * emitterY setter
     *
     * @param emitterY the new emitterY
     */
    public void setEmitterY(int emitterY) {
        this.emitterY = emitterY;
    }

    /**
     * emitterAngled getter
     *
     * @return boolean
     */
    public boolean isEmitterAngled() {
        return this.emitterAngled;
    }

    /**
     * emitterAngled setter
     *
     * @param emitterAngled the new emitterAngled
     */
    public void setEmitterAngled(boolean emitterAngled) {
        this.emitterAngled = emitterAngled;
    }

    /**
     * emitterAngle getter
     *
     * @return int
     */
    public int getEmitterAngle() {
        return this.emitterAngle;
    }

    /**
     * emitterAngle setter
     *
     * @param emitterAngle the new emitterAngle
     */
    public void setEmitterAngle(int emitterAngle) {
        this.emitterAngle = emitterAngle;
    }

    /**
     * emitterSpread getter
     *
     * @return int
     */
    public int getEmitterSpread() {
        return this.emitterSpread;
    }

    /**
     * emitterSpread setter
     *
     * @param emitterSpread the new emitterSpread
     */
    public void setEmitterSpread(int emitterSpread) {
        this.emitterSpread = emitterSpread;
    }

    /**
     * environmentGravity getter
     *
     * @return int
     */
    public int getEnvironmentGravity() {
        return this.environmentGravity;
    }

    /**
     * environmentGravity setter
     *
     * @param environmentGravity the new environmentGravity
     */
    public void setEnvironmentGravity(int environmentGravity) {
        this.environmentGravity = environmentGravity;
    }

    /**
     * environmentLifespan getter
     *
     * @return int
     */
    public int getEnvironmentLifespan() {
        return this.environmentLifespan;
    }

    /**
     * environmentLifespan setter
     *
     * @param environmentLifespan the new environmentLifespan
     */
    public void setEnvironmentLifespan(int environmentLifespan) {
        this.environmentLifespan = environmentLifespan;
    }

    /**
     * environmentWind getter
     *
     * @return int
     */
    public int getEnvironmentWind() {
        return this.environmentWind;
    }

    /**
     * environmentWind setter
     *
     * @param environmentWind the new environmentWind
     */
    public void setEnvironmentWind(int environmentWind) {
        this.environmentWind = environmentWind;
    }

    /**
     * displayColor getter
     *
     * @return Hashtable
     */
    public Color getDisplayColor(String key) {
        return (Color) this.displayColors.get(key);
    }

    /**
     * getter
     *
     * @return Hashtable
     */
    public void setDisplayColor(String key, Color color) {
        this.displayColors.put(key, color);
    }

    /**
     * displayRenderingMode getter
     *
     * @return int
     */
    public int getDisplayRenderingMode() {
        return this.displayRenderingMode;
    }

    /**
     * displayRenderingMode setter
     *
     * @param displayRenderingMode the new displayRenderingMode
     */
    public void setDisplayRenderingMode(int displayRenderingMode) {
        this.displayRenderingMode = displayRenderingMode;
    }

    /**
     * obstacles getter
     *
     * @return Vector
     */
    public Vector getObstacles() {
        return this.obstacles;
    }

    /**
     * particlesPool getter
     *
     * @return ParticlesPool
     */
    public ParticlesPool getParticlesPool() {
        return this.particlesPool;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(long startingTime) {
        this.startingTime = startingTime;
    }

    public long getFrameCtr() {
        return frameCtr;
    }

    public void setFrameCtr(long frameCtr) {
        this.frameCtr = frameCtr;
    }

    public double getFps() {
        return fps;
    }

    public void setFps(double fps) {
        this.fps = fps;
    }

    /**
     * Loads a pxp file into the DataBus.
     *
     * @param fileName the pxp file to load
     */
    public void load(String fileName) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File(fileName)));

            // int
            mainFrameX = Integer.parseInt(props.get("mainFrameX").toString());
            mainFrameY = Integer.parseInt(props.get("mainFrameY").toString());
            mainFrameWidth =
                    Integer.parseInt(props.get("mainFrameWidth").toString());
            mainFrameHeight =
                    Integer.parseInt(props.get("mainFrameHeight").toString());
            sleepTimeBetweenFrames =
                    Integer.parseInt(
                            props.get("sleepTimeBetweenFrames").toString());
            numberOfParticles =
                    Integer.parseInt(props.get("numberOfParticles").toString());
            particleInitialDistanceFromEmitter =
                    Integer.parseInt(
                            props
                                    .get("particleInitialDistanceFromEmitter")
                                    .toString());
            emitterPower =
                    Integer.parseInt(props.get("emitterPower").toString());
            emitterX = Integer.parseInt(props.get("emitterX").toString());
            emitterY = Integer.parseInt(props.get("emitterY").toString());
            emitterAngle =
                    Integer.parseInt(props.get("emitterAngle").toString());
            emitterSpread =
                    Integer.parseInt(props.get("emitterSpread").toString());
            environmentGravity =
                    Integer.parseInt(props.get("environmentGravity").toString());
            environmentLifespan =
                    Integer.parseInt(props.get("environmentLifespan").toString());
            environmentWind =
                    Integer.parseInt(props.get("environmentWind").toString());
            displayRenderingMode =
                    Integer.parseInt(props.get("displayRenderingMode").toString());
            // boolean
            emitterMoving =
                    Boolean
                            .valueOf(props.get("emitterMoving").toString())
                            .booleanValue();
            emitterAngled =
                    Boolean
                            .valueOf(props.get("emitterAngled").toString())
                            .booleanValue();
            // Colors
            for (int i = 0;
                 i < Integer.parseInt(props.get("displayColors").toString());
                 i++) {
                displayColors.put(
                        props.get("colorkey" + i).toString(),
                        new Color(
                                Integer.parseInt(
                                        props.get("colorval" + i).toString())));
            }
            // Obstacles
            StringTokenizer st = null;
            PdoRectangle rect = null;
            for (int i = 0;
                 i < Integer.parseInt(props.get("obstacles").toString());
                 i++) {
                st =
                        new StringTokenizer(
                                props.get("obstacle" + i).toString(),
                                "|");
                rect =
                        new PdoRectangle(
                                Integer.parseInt(st.nextToken()),
                                Integer.parseInt(st.nextToken()),
                                Integer.parseInt(st.nextToken()),
                                Integer.parseInt(st.nextToken()), DataBus.RECTANGLE_ARC_SIZE);
                obstacles.add(rect);
            }
        } catch (IOException e) {
            System.err.println("File " + fileName + "");
        }
    }

    /**
     * Saves the DataBus into a pxp file.
     *
     * @param fileName the pxp file to save
     */
    public void save(String fileName) {
        Properties props = new Properties();
        props.put("mainFrameX", "" + mainFrameX);
        props.put("mainFrameY", "" + mainFrameY);
        props.put("mainFrameWidth", "" + mainFrameWidth);
        props.put("mainFrameHeight", "" + mainFrameHeight);
        props.put("sleepTimeBetweenFrames", "" + sleepTimeBetweenFrames);
        props.put("numberOfParticles", "" + numberOfParticles);
        props.put(
                "particleInitialDistanceFromEmitter",
                "" + particleInitialDistanceFromEmitter);
        props.put("emitterPower", "" + emitterPower);
        props.put("emitterMoving", "" + emitterMoving);
        props.put("emitterX", "" + emitterX);
        props.put("emitterY", "" + emitterY);
        props.put("emitterAngled", "" + emitterAngled);
        props.put("emitterAngle", "" + emitterAngle);
        props.put("emitterSpread", "" + emitterSpread);
        props.put("environmentGravity", "" + environmentGravity);
        props.put("environmentLifespan", "" + environmentLifespan);
        props.put("environmentWind", "" + environmentWind);
        props.put("displayRenderingMode", "" + displayRenderingMode);

        Enumeration keys = displayColors.keys();
        Enumeration elems = displayColors.elements();
        int i = 0;
        while (keys.hasMoreElements()) {
            props.put("colorkey" + i, "" + keys.nextElement());
            props.put(
                    "colorval" + i,
                    "" + ((Color) elems.nextElement()).getRGB());
            i++;
        }
        props.put("displayColors", "" + i);
        i = 0;
        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            props.put(
                    "obstacle" + i,
                    ((PdoRectangle) iter.next()).saveState());
            i++;
        }
        props.put("obstacles", "" + i);
        try {
            props.store(
                    new FileOutputStream(new File(fileName)),
                    "Particles Explorer Save File");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dumps the Databus content to a PrintStream
     *
     * @param out the PrintStream to dump to
     */
    public void dump(PrintStream out) {
        out.println("mainFrameX" + " : " + mainFrameX);
        out.println("mainFrameY" + " : " + mainFrameY);
        out.println("mainFrameWidth" + " : " + mainFrameWidth);
        out.println("mainFrameHeight" + " : " + mainFrameHeight);
        out.println("sleepTimeBetweenFrames" + " : " + sleepTimeBetweenFrames);
        out.println("numberOfParticles" + " : " + numberOfParticles);
        out.println(
                "particleInitialDistanceFromEmitter"
                        + " : "
                        + particleInitialDistanceFromEmitter);
        out.println("emitterPower" + " : " + emitterPower);
        out.println("emitterMoving" + " : " + emitterMoving);
        out.println("emitterX" + " : " + emitterX);
        out.println("emitterY" + " : " + emitterY);
        out.println("emitterAngled" + " : " + emitterAngled);
        out.println("emitterAngle" + " : " + emitterAngle);
        out.println("emitterSpread" + " : " + emitterSpread);
        out.println("environmentGravity" + " : " + environmentGravity);
        out.println("environmentLifespan" + " : " + environmentLifespan);
        out.println("environmentWind" + " : " + environmentWind);
        out.println("displayRenderingMode" + " : " + displayRenderingMode);

        Enumeration keys = displayColors.keys();
        Enumeration elems = displayColors.elements();
        int i = 0;
        while (keys.hasMoreElements()) {
            out.println("colorkey" + i + " : " + keys.nextElement());
            out.println(
                    "colorval"
                            + i
                            + " : "
                            + ((Color) elems.nextElement()).getRGB());
            i++;
        }
        i = 0;
        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            out.println(
                    "obstacle"
                            + i
                            + " : "
                            + ((PdoRectangle) iter.next()).saveState());
            i++;
        }
    }
}