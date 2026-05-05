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
 * Panel containing display parameters controls.
 *
 * @author Poil d'Ortie
 */
public class ParamsDisplay extends JPanel {
    private DataBus dataBus = DataBus.getInstance();

    protected PdoColorChooser partColor;
    protected PdoColorChooser midColor;
    protected PdoColorChooser bgColor;
    protected JComboBox typePart;
    private JLabel labelMinSize, labelMaxSize;
    private JSlider slideMinSize, slideMaxSize;

    /**
     * Default constructor.
     */
    public ParamsDisplay() {
        super(new GridLayout(4, 1, 1, 1));

        // Particles birth color
        partColor = new PdoColorChooser("partColor");
        partColor.setBorder(
                new TitledBorder(DataBus.ETCHED_BORDER, "Birth color"));
        this.add(partColor);

        // Particles mid-life color
        midColor = new PdoColorChooser("midColor");
        midColor.setBorder(
                new TitledBorder(DataBus.ETCHED_BORDER, "Mid-life color"));
        this.add(midColor);

        // Background / death color
        bgColor = new PdoColorChooser("bgColor");
        bgColor.setBorder(
                new TitledBorder(DataBus.ETCHED_BORDER, "Death color (background)"));
        this.add(bgColor);

        // Particles look n feel + size
        JPanel partT = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        partT.setBorder(new TitledBorder(DataBus.ETCHED_BORDER, "Particle"));
        this.add(partT);

        typePart = new JComboBox();
        typePart.addItem("Line");
        typePart.addItem("Dot");
        typePart.addItem("Both");
        typePart.setSelectedIndex(dataBus.getDisplayRenderingMode());
        typePart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typePartActionPerformed(e);
            }
        });
        partT.add(typePart);

        labelMinSize = new JLabel("Min size: " + dataBus.getParticleMinSize());
        partT.add(labelMinSize);
        slideMinSize = new JSlider(1, 20, dataBus.getParticleMinSize());
        slideMinSize.setPreferredSize(new Dimension(80, 18));
        slideMinSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int v = slideMinSize.getValue();
                if (v > slideMaxSize.getValue()) slideMaxSize.setValue(v);
                dataBus.setParticleMinSize(v);
                labelMinSize.setText("Min size: " + v);
            }
        });
        partT.add(slideMinSize);

        labelMaxSize = new JLabel("Max size: " + dataBus.getParticleMaxSize());
        partT.add(labelMaxSize);
        slideMaxSize = new JSlider(1, 20, dataBus.getParticleMaxSize());
        slideMaxSize.setPreferredSize(new Dimension(80, 18));
        slideMaxSize.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int v = slideMaxSize.getValue();
                if (v < slideMinSize.getValue()) slideMinSize.setValue(v);
                dataBus.setParticleMaxSize(v);
                labelMaxSize.setText("Max size: " + v);
            }
        });
        partT.add(slideMaxSize);
    }

    private final void typePartActionPerformed(ActionEvent e) {
        dataBus.setDisplayRenderingMode(typePart.getSelectedIndex());
    }
}