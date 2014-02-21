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
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel containing emitter parameters controls.
 *
 * @author Poil d'Ortie
 */
public class ParamsEmitter extends JPanel {
    private static final boolean TICKS = false;
    private static final boolean LABELS = false;
    private static final Dimension DIM_LABEL = new Dimension(80, 18);
    private static final Dimension DIM_SLIDE = new Dimension(200, 18);
    private static final BorderLayout ELEMENT = new BorderLayout(1, 1);
    private static final FlowLayout FLOW =
            new FlowLayout(FlowLayout.CENTER, 1, 1);

    protected DataBus dataBus = DataBus.getInstance();
    protected JLabel labelVMax,
            labelAngle,
            labelDAngle,
            labelNbParts,
            labelPosX,
            labelPosY;
    protected JSlider slideVMax, slideAngle, slideDAngle, slideNbParts;
    protected JTextField textPosX, textPosY;
    protected JCheckBox chkMoving, chkAngled;
    protected JButton btnMove;

    /**
     * Default constructor.
     */
    public ParamsEmitter() {
        super(new BorderLayout());

        // Emitter
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout(1, 1));

        createEmitterPanel(main);
        createEmitterPositionPanel(main);

        update();
        this.add(main, "Center");
    }

    private void createEmitterPanel(JPanel main) {
        // Flux
        JPanel flux = new JPanel(new GridLayout(2, 1, 1, 1));
        flux.setBorder(new TitledBorder(DataBus.ETCHED_BORDER, "Flow"));
        main.add(flux, "North");

        // Power
        JPanel vMax = new JPanel(FLOW);
        flux.add(vMax);
        // Label
        labelVMax = new JLabel("Power : " + dataBus.getEmitterPower(), SwingConstants.LEFT);
        labelVMax.setMinimumSize(DIM_LABEL);
        labelVMax.setMaximumSize(DIM_LABEL);
        labelVMax.setPreferredSize(DIM_LABEL);
        vMax.add(labelVMax);
        // Slide
        slideVMax = new JSlider(1, 30, dataBus.getEmitterPower());
        slideVMax.setMinimumSize(DIM_SLIDE);
        slideVMax.setMaximumSize(DIM_SLIDE);
        slideVMax.setPreferredSize(DIM_SLIDE);
        slideVMax.setMajorTickSpacing(10);
        slideVMax.setMinorTickSpacing(5);
        slideVMax.setPaintTicks(TICKS);
        slideVMax.setPaintLabels(LABELS);
        vMax.add(slideVMax);
        slideVMax.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideVMaxStateChanged(e);
            }
        });

        // Debit
        JPanel nbParts = new JPanel(FLOW);
        flux.add(nbParts);
        // Label
        labelNbParts =
                new JLabel("Flow : " + dataBus.getNumberOfParticles(), SwingConstants.LEFT);
        labelNbParts.setMinimumSize(DIM_LABEL);
        labelNbParts.setMaximumSize(DIM_LABEL);
        labelNbParts.setPreferredSize(DIM_LABEL);
        nbParts.add(labelNbParts);
        // Slide
        slideNbParts = new JSlider(0, dataBus.FLOW_MAX_VALUE, dataBus.getNumberOfParticles());
        slideNbParts.setMinimumSize(DIM_SLIDE);
        slideNbParts.setPreferredSize(DIM_SLIDE);
        slideNbParts.setMajorTickSpacing(10);
        slideNbParts.setMinorTickSpacing(5);
        slideNbParts.setPaintTicks(TICKS);
        slideNbParts.setPaintLabels(LABELS);
        nbParts.add(slideNbParts);
        slideNbParts.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideNbPartsStateChanged(e);
            }
        });
    }

    private void createEmitterPositionPanel(JPanel main) {
        // Angle
        JPanel angle = new JPanel(new BorderLayout());
        angle.setBorder(new TitledBorder(DataBus.ETCHED_BORDER, "Angle"));
        main.add(angle, "Center");
        // Random
        chkAngled = new JCheckBox("Random", true);
        chkAngled.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkAngledActionPerformed(e);
            }
        });
        angle.add(chkAngled, "North");
        // User Defined
        JPanel usrAngle = new JPanel(new GridLayout(2, 1, 1, 1));
        usrAngle.setBorder(DataBus.ETCHED_BORDER);
        angle.add(usrAngle, "Center");
        // Direction
        JPanel direction = new JPanel(FLOW);
        usrAngle.add(direction);
        // Label
        labelAngle = new JLabel("Dir : " + dataBus.getEmitterAngle(), SwingConstants.LEFT);
        labelAngle.setMinimumSize(DIM_LABEL);
        labelAngle.setMaximumSize(DIM_LABEL);
        labelAngle.setPreferredSize(DIM_LABEL);
        labelAngle.setEnabled(false);
        direction.add(labelAngle);
        // Slide
        slideAngle = new JSlider(-180, 180, dataBus.getEmitterAngle());
        slideAngle.setMinimumSize(DIM_SLIDE);
        slideAngle.setMaximumSize(DIM_SLIDE);
        slideAngle.setPreferredSize(DIM_SLIDE);
        slideAngle.setMajorTickSpacing(20);
        slideAngle.setMinorTickSpacing(5);
        slideAngle.setPaintTicks(TICKS);
        slideAngle.setPaintLabels(LABELS);
        slideAngle.setEnabled(false);
        slideAngle.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideAngleStateChanged(e);
            }
        });
        direction.add(slideAngle);

        // Spread
        JPanel spread = new JPanel(FLOW);
        usrAngle.add(spread);
        // Label
        labelDAngle =
                new JLabel("Spread : " + dataBus.getEmitterSpread(), SwingConstants.LEFT);
        labelDAngle.setMinimumSize(DIM_LABEL);
        labelDAngle.setMaximumSize(DIM_LABEL);
        labelDAngle.setPreferredSize(DIM_LABEL);
        labelDAngle.setEnabled(false);
        spread.add(labelDAngle);
        // Slide
        slideDAngle = new JSlider(0, 360, dataBus.getEmitterSpread());
        slideDAngle.setMinimumSize(DIM_SLIDE);
        slideDAngle.setMaximumSize(DIM_SLIDE);
        slideDAngle.setPreferredSize(DIM_SLIDE);
        slideDAngle.setMajorTickSpacing(20);
        slideDAngle.setMinorTickSpacing(5);
        slideDAngle.setPaintTicks(TICKS);
        slideDAngle.setPaintLabels(LABELS);
        slideDAngle.setEnabled(false);
        slideDAngle.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideDAngleStateChanged(e);
            }
        });
        spread.add(slideDAngle);

        // Position
        JPanel position = new JPanel(new BorderLayout(1, 1));
        position.setBorder(new TitledBorder(DataBus.ETCHED_BORDER, "Position"));
        main.add(position, "South");
        // Mobile
        chkMoving = new JCheckBox("Moving", true);
        chkMoving.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chkMovingActionPerformed(e);
            }
        });
        position.add(chkMoving, "North");

        JPanel usrPos = new JPanel(FLOW);
        usrPos.setBorder(DataBus.ETCHED_BORDER);
        position.add(usrPos, "Center");
        // x :
        labelPosX = new JLabel("x : ");
        labelPosX.setEnabled(false);
        usrPos.add(labelPosX);
        // posX
        textPosX = new JTextField("0", 3);
        textPosX.setHorizontalAlignment(SwingConstants.CENTER);
        textPosX.setEnabled(false);
        usrPos.add(textPosX);
        // y :
        labelPosY = new JLabel(" y : ");
        labelPosY.setEnabled(false);
        usrPos.add(labelPosY);
        // posY
        textPosY = new JTextField("0", 3);
        textPosY.setHorizontalAlignment(SwingConstants.CENTER);
        textPosY.setEnabled(false);
        usrPos.add(textPosY);
        // separator
        usrPos.add(new JLabel("  "));
        // Move
        btnMove = new JButton("Change");
        btnMove.setEnabled(false);
        btnMove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnMoveActionPerformed(e);
            }
        });
        usrPos.add(btnMove);
    }

    private final void update() {
        labelVMax.setText("Power : " + dataBus.getEmitterPower());
        labelAngle.setText("Dir : " + dataBus.getEmitterAngle());
        labelDAngle.setText("Spread : " + dataBus.getEmitterSpread());
        labelNbParts.setText("Debit : " + dataBus.getNumberOfParticles());
    }

    private void slideVMaxStateChanged(ChangeEvent e) {
        dataBus.setEmitterPower(slideVMax.getValue());
        update();
    }

    private void chkAngledActionPerformed(ActionEvent e) {
        if (chkAngled.isSelected()) {
            dataBus.setEmitterAngled(false);
            labelAngle.setEnabled(false);
            slideAngle.setEnabled(false);
            labelDAngle.setEnabled(false);
            slideDAngle.setEnabled(false);
        } else {
            dataBus.setEmitterAngled(true);
            labelAngle.setEnabled(true);
            slideAngle.setEnabled(true);
            labelDAngle.setEnabled(true);
            slideDAngle.setEnabled(true);
        }
    }

    private void slideAngleStateChanged(ChangeEvent e) {
        dataBus.setEmitterAngle(slideAngle.getValue());
        update();
    }

    private void slideDAngleStateChanged(ChangeEvent e) {
        dataBus.setEmitterSpread(slideDAngle.getValue());
        update();
    }

    private void slideNbPartsStateChanged(ChangeEvent e) {
        dataBus.setNumberOfParticles(slideNbParts.getValue());
        update();
    }

    private void btnMoveActionPerformed(ActionEvent e) {
        dataBus.setEmitterX(Integer.parseInt(textPosX.getText()));
        dataBus.setEmitterY(Integer.parseInt(textPosY.getText()));
        update();
    }

    private void chkMovingActionPerformed(ActionEvent e) {
        if (chkMoving.isSelected()) {
            dataBus.setEmitterMoving(true);
            labelPosX.setEnabled(false);
            textPosX.setEnabled(false);
            labelPosY.setEnabled(false);
            textPosY.setEnabled(false);
            btnMove.setEnabled(false);
        } else {
            dataBus.setEmitterMoving(false);
            labelPosX.setEnabled(true);
            textPosX.setEnabled(true);
            labelPosY.setEnabled(true);
            textPosY.setEnabled(true);
            btnMove.setEnabled(true);
        }
    }
}