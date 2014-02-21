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

/**
 * A JPanel containing a simple color chooser.
 *
 * @author Poil d'Ortie
 */
public class PdoColorChooser extends JPanel {
    private static final boolean TICKS = false;
    private static final boolean LABELS = false;
    private static final Dimension DIM_COLOR = new Dimension(48, 75);
    private static final Dimension DIM_LABEL = new Dimension(35, 23);
    private static final Dimension DIM_SLIDE = new Dimension(200, 23);
    private static final BorderLayout ELEMENT = new BorderLayout(1, 1);
    private static final FlowLayout FLOW =
            new FlowLayout(FlowLayout.CENTER, 1, 1);
    private static final GridLayout GRID = new GridLayout(3, 1, 1, 1);

    private DataBus dataBus = DataBus.getInstance();
    private String key;
    private int currentR, currentG, currentB;
    private JLabel labelR, labelG, labelB;
    private JSlider slideR, slideG, slideB;
    private JButton color;

    /**
     * Default Constructor.
     *
     * @param key the key of the color to update
     */
    public PdoColorChooser(String key) {
        super(new BorderLayout());
        this.key = key;

        Color initialColor = dataBus.getDisplayColor(key);
        currentR = initialColor.getRed();
        currentG = initialColor.getGreen();
        currentB = initialColor.getBlue();

        JPanel valeurs = new JPanel();
        valeurs.setLayout(GRID);
        JPanel red = new JPanel(FLOW);
        valeurs.add(red);
        labelR = new JLabel(Integer.toString(currentR), JLabel.CENTER);
        labelR.setForeground(new Color(128, 0, 0));
        labelR.setBorder(DataBus.ETCHED_BORDER);
        labelR.setMinimumSize(DIM_LABEL);
        labelR.setMaximumSize(DIM_LABEL);
        labelR.setPreferredSize(DIM_LABEL);
        red.add(labelR);
        slideR = new JSlider(0, 255, currentR);
        slideR.setBorder(DataBus.ETCHED_BORDER);
        slideR.setMinimumSize(DIM_SLIDE);
        slideR.setMaximumSize(DIM_SLIDE);
        slideR.setPreferredSize(DIM_SLIDE);
        slideR.setMajorTickSpacing(50);
        slideR.setMinorTickSpacing(10);
        slideR.setPaintTicks(TICKS);
        slideR.setPaintLabels(LABELS);
        red.add(slideR);
        slideR.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideRStateChanged(e);
            }
        });

        JPanel green = new JPanel(FLOW);
        valeurs.add(green);
        labelG = new JLabel(Integer.toString(currentG), JLabel.CENTER);
        labelG.setForeground(new Color(0, 128, 0));
        labelG.setBorder(DataBus.ETCHED_BORDER);
        labelG.setMinimumSize(DIM_LABEL);
        labelG.setMaximumSize(DIM_LABEL);
        labelG.setPreferredSize(DIM_LABEL);
        green.add(labelG);
        slideG = new JSlider(0, 255, currentG);
        slideG.setBorder(DataBus.ETCHED_BORDER);
        slideG.setMinimumSize(DIM_SLIDE);
        slideG.setMaximumSize(DIM_SLIDE);
        slideG.setPreferredSize(DIM_SLIDE);
        slideG.setMajorTickSpacing(50);
        slideG.setMinorTickSpacing(10);
        slideG.setPaintTicks(TICKS);
        slideG.setPaintLabels(LABELS);
        green.add(slideG);
        slideG.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideGStateChanged(e);
            }
        });

        JPanel blue = new JPanel(FLOW);
        valeurs.add(blue);
        labelB = new JLabel(Integer.toString(currentB), JLabel.CENTER);
        labelB.setForeground(new Color(0, 0, 128));
        labelB.setBorder(DataBus.ETCHED_BORDER);
        labelB.setMinimumSize(DIM_LABEL);
        labelB.setMaximumSize(DIM_LABEL);
        labelB.setPreferredSize(DIM_LABEL);
        blue.add(labelB);
        slideB = new JSlider(0, 255, currentB);
        slideB.setBorder(DataBus.ETCHED_BORDER);
        slideB.setMinimumSize(DIM_SLIDE);
        slideB.setMaximumSize(DIM_SLIDE);
        slideB.setPreferredSize(DIM_SLIDE);
        slideB.setMajorTickSpacing(50);
        slideB.setMinorTickSpacing(10);
        slideB.setPaintTicks(TICKS);
        slideB.setPaintLabels(LABELS);
        blue.add(slideB);
        slideB.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideBStateChanged(e);
            }
        });

        JPanel pColor = new JPanel();
        color = new JButton();
        color.setOpaque(true);
        color.setBorder(DataBus.ETCHED_BORDER);
        color.setBackground(new Color(currentR, currentG, currentB));
        color.setMinimumSize(DIM_COLOR);
        color.setMaximumSize(DIM_COLOR);
        color.setPreferredSize(DIM_COLOR);
        pColor.add(color);

        this.add(valeurs, BorderLayout.CENTER);
        this.add(pColor, BorderLayout.EAST);
    }

    private final void update() {
        labelR.setText(Integer.toString(currentR));
        labelG.setText(Integer.toString(currentG));
        labelB.setText(Integer.toString(currentB));
        color.setBackground(new Color(currentR, currentG, currentB));
        dataBus.setDisplayColor(key, new Color(currentR, currentG, currentB));
    }

    private void slideRStateChanged(ChangeEvent e) {
        currentR = this.slideR.getValue();
        update();
    }

    private void slideGStateChanged(ChangeEvent e) {
        currentG = this.slideG.getValue();
        update();
    }

    private void slideBStateChanged(ChangeEvent e) {
        currentB = this.slideB.getValue();
        update();
    }
}