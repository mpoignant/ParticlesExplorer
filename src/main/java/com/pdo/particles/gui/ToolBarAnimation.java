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

import com.pdo.particles.utils.DataBus;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JToolBar containing all animation controls.
 *
 * @author Poil d'Ortie
 */
public class ToolBarAnimation extends JToolBar {
    private static final Dimension DIM_SLIDE = new Dimension(150, 24);

    private DataBus dataBus = DataBus.getInstance();
    private MainFrame parent;
    private JButton btnAnimPlay;
    private JButton btnAnimPause;
    private boolean anim = true;
    private JButton btnEmitOn;
    private JButton btnEmitOff;
    private boolean emit = true;
    private JButton btnRestart;

    protected JSlider slideSpeed;

    /**
     * Default Constructor.
     *
     * @param parent The Main Frame
     */
    public ToolBarAnimation(MainFrame parent) {
        super();
        this.parent = parent;
        this.setFloatable(false);

        btnAnimPlay = new JButton(DataBus.ANIM_PLAY);
        btnAnimPlay.setToolTipText("Click here to play animation");
        btnAnimPlay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAnimPlayActionPerformed(e);
            }
        });
        btnAnimPlay.setEnabled(false);
        this.add(btnAnimPlay);

        btnAnimPause = new JButton(DataBus.ANIM_PAUSE);
        btnAnimPause.setToolTipText("Click here to pause animation");
        btnAnimPause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnAnimPauseActionPerformed(e);
            }
        });
        this.add(btnAnimPause);

        this.addSeparator();

        btnEmitOn = new JButton(DataBus.EMIT_ON);
        btnEmitOn.setToolTipText("Click here to start emitter");
        btnEmitOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEmitOnActionPerformed(e);
            }
        });
        btnEmitOn.setEnabled(false);
        this.add(btnEmitOn);

        btnEmitOff = new JButton(DataBus.EMIT_OFF);
        btnEmitOff.setToolTipText("Click here to stop emitter");
        btnEmitOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnEmitOffActionPerformed(e);
            }
        });
        this.add(btnEmitOff);

        this.addSeparator();

        btnRestart = new JButton(DataBus.EMIT_NEW);
        btnRestart.setToolTipText("Click here to reinit emitter");
        btnRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnRestartActionPerformed(e);
            }
        });
        this.add(btnRestart);

        this.addSeparator();

        slideSpeed = new JSlider(1, 250, 1);
        slideSpeed.setToolTipText("Use this slider to slow down animation");
        slideSpeed.setMinimumSize(DIM_SLIDE);
        slideSpeed.setMaximumSize(DIM_SLIDE);
        slideSpeed.setPreferredSize(DIM_SLIDE);
        slideSpeed.setMajorTickSpacing(50);
        slideSpeed.setMinorTickSpacing(25);
        slideSpeed.setPaintTicks(false);
        slideSpeed.setPaintLabels(false);
        slideSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideSpeedStateChanged(e);
            }
        });
        //this.add(slideSpeed);

        /*
              this.addSeparator();
              JButton btnDump = new JButton("DataBus");
              btnDump.addActionListener(new ActionListener()  {
                public void actionPerformed(ActionEvent e) {
                  dataBus.dump(System.out);
                }
              });
              this.add(btnDump);
          */
    }

    private void btnAnimPlayActionPerformed(ActionEvent e) {
        parent.display.start();
        btnAnimPlay.setEnabled(false);
        btnAnimPause.setEnabled(true);
    }

    private void btnAnimPauseActionPerformed(ActionEvent e) {
        parent.display.stop();
        btnAnimPlay.setEnabled(true);
        btnAnimPause.setEnabled(false);
    }

    private void btnEmitOnActionPerformed(ActionEvent e) {
        parent.display.emitterRunning = true;
        btnEmitOn.setEnabled(false);
        btnEmitOff.setEnabled(true);
    }

    private void btnEmitOffActionPerformed(ActionEvent e) {
        parent.display.emitterRunning = false;
        btnEmitOn.setEnabled(true);
        btnEmitOff.setEnabled(false);
    }

    private void btnRestartActionPerformed(ActionEvent e) {
        parent.display.initFreeEmitter();
    }

    private void slideSpeedStateChanged(ChangeEvent e) {
        dataBus.setSleepTimeBetweenFrames((long) slideSpeed.getValue());
    }
}