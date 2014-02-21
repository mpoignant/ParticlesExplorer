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
package com.pdo.particles.gui;

import ch.randelshofer.quaqua.QuaquaManager;
import com.pdo.particles.utils.DataBus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

/**
 * The Main Frame.
 *
 * @author Poil d'Ortie
 */
public class MainFrame extends JFrame implements ComponentListener {
    private DataBus dataBus = DataBus.getInstance();

    protected JSplitPane split;

    protected Menu menu;

    protected DefaultDisplay display;

    protected ToolBarAnimation tbAnimation;

    protected ParamsPanel params;

    /**
     * Default constructor.
     *
     * @param file The pxp file to load at startup
     */
    public MainFrame(String file) {
        try {
            String vers = System.getProperty("os.name").toLowerCase();
            if (vers.indexOf("mac") != -1) {
                System.setProperty("Quaqua.tabLayoutPolicy", "wrap");
                UIManager.setLookAndFeel(QuaquaManager.getLookAndFeelClassName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setTitle("Particles Explorer");
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(new Dimension(dataBus.getMainFrameDimension()));
        this.setResizable(true);

        SplashScreen splash = new SplashScreen(dataBus.getSplahScreenLogo(), "Initializing...");
        splash.setVisible(true);

        // Dialog
        dataBus.setDialogAbout(new DialogAbout(this));
        splash.showStatus("Menu...", 15);

        // Menu
        menu = new Menu(file);
        this.setJMenuBar(menu);
        splash.showStatus("Toolbar...", 30);

        // Toolbar
        tbAnimation = new ToolBarAnimation(this);
        this.getContentPane().add(tbAnimation, BorderLayout.NORTH);
        splash.showStatus("Main Panel...", 45);

        // MainPanel
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.addComponentListener(this);
        split.setDividerSize(12);
        split.setOneTouchExpandable(true);
        splash.showStatus("Control Panel...", 60);

        // Control Panel
        params = new ParamsPanel();
        split.setLeftComponent(params);
        splash.showStatus("Display...", 75);

        // Display
        display = new DefaultDisplay(this);
        split.setRightComponent(display);
        this.getContentPane().add(split, BorderLayout.CENTER);
        splash.showStatus("Starting...", 90);

        // Starting
        this.addComponentListener(this);
        display.addNotify();
        display.start();

        // Closing SplashScreen
        splash.showStatus("Done", 100);
        splash.close();
    }

    protected void updateControls() {
        // Main Frame
        this.setLocation(dataBus.getMainFrameLocation());
        this.setSize(dataBus.getMainFrameDimension());

        // Animation Toolbar
        this.tbAnimation.slideSpeed.setValue((int) dataBus.getSleepTimeBetweenFrames());

        // Emitter
        this.params.emitterParams.slideNbParts.setValue(dataBus.getNumberOfParticles());
        this.params.emitterParams.slideVMax.setValue(dataBus.getEmitterPower());
        this.params.emitterParams.slideAngle.setValue(dataBus.getEmitterAngle());
        this.params.emitterParams.slideDAngle.setValue(dataBus.getEmitterSpread());
        this.params.emitterParams.textPosX.setText("" + dataBus.getEmitterX());
        this.params.emitterParams.textPosY.setText("" + dataBus.getEmitterY());
        this.params.emitterParams.chkMoving.setSelected(dataBus.getEmitterMoving());
        if (dataBus.getEmitterMoving()) {
            this.params.emitterParams.labelPosX.setEnabled(false);
            this.params.emitterParams.textPosX.setEnabled(false);
            this.params.emitterParams.labelPosY.setEnabled(false);
            this.params.emitterParams.textPosY.setEnabled(false);
            this.params.emitterParams.btnMove.setEnabled(false);
        } else {
            this.params.emitterParams.labelPosX.setEnabled(true);
            this.params.emitterParams.textPosX.setEnabled(true);
            this.params.emitterParams.labelPosY.setEnabled(true);
            this.params.emitterParams.textPosY.setEnabled(true);
            this.params.emitterParams.btnMove.setEnabled(true);
        }
        this.params.emitterParams.chkAngled.setSelected(!dataBus.isEmitterAngled());
        if (!dataBus.isEmitterAngled()) {
            this.params.emitterParams.labelAngle.setEnabled(false);
            this.params.emitterParams.slideAngle.setEnabled(false);
            this.params.emitterParams.labelDAngle.setEnabled(false);
            this.params.emitterParams.slideDAngle.setEnabled(false);
        } else {
            this.params.emitterParams.labelAngle.setEnabled(true);
            this.params.emitterParams.slideAngle.setEnabled(true);
            this.params.emitterParams.labelDAngle.setEnabled(true);
            this.params.emitterParams.slideDAngle.setEnabled(true);
        }

        // Environment
        this.params.envParams.slideGravity.setValue(dataBus.getEnvironmentGravity());
        this.params.envParams.slideDuration.setValue(dataBus.getEnvironmentLifespan());
        this.params.envParams.slideWind.setValue(dataBus.getEnvironmentWind());

        // Display
        this.params.dispParams.typePart.setSelectedIndex(dataBus.getDisplayRenderingMode());

        repaint();
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.dispose();
            System.exit(0);
        }
    }

    /**
     * @see java.awt.event.ComponentListener#componentHidden(ComponentEvent)
     */
    public void componentHidden(ComponentEvent e) {
    }

    /**
     * @see java.awt.event.ComponentListener#componentShown(ComponentEvent)
     */
    public void componentShown(ComponentEvent e) {
    }

    /**
     * @see java.awt.event.ComponentListener#componentMoved(ComponentEvent)
     */
    public void componentMoved(ComponentEvent e) {
    }

    /**
     * @see java.awt.event.ComponentListener#componentResized(ComponentEvent)
     */
    public void componentResized(ComponentEvent e) {
        dataBus.setMainFrameLocation(new Point(this.getLocation().x, this.getLocation().y));
        dataBus.setMainFrameDimension(this.getWidth(), this.getHeight());
    }
}
