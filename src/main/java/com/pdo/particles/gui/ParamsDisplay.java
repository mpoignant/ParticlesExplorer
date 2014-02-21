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
    protected PdoColorChooser bgColor;
    protected JComboBox typePart;

    /**
     * Default constructor.
     */
    public ParamsDisplay() {
        super(new GridLayout(3, 1, 1, 1));

        // Particles color
        partColor = new PdoColorChooser("partColor");
        partColor.setBorder(
                new TitledBorder(DataBus.ETCHED_BORDER, "Particles color"));
        this.add(partColor);

        // Background color
        bgColor = new PdoColorChooser("bgColor");
        bgColor.setBorder(
                new TitledBorder(DataBus.ETCHED_BORDER, "Background color"));
        this.add(bgColor);

        // Particles look n feel
        JPanel partT = new JPanel(new FlowLayout());
        partT.setBorder(new TitledBorder(DataBus.ETCHED_BORDER, "Particle"));
        this.add(partT);
        typePart = new JComboBox();
        typePart.addItem("Line");
        typePart.addItem("Square");
        typePart.addItem("Both");
        typePart.setSelectedIndex(dataBus.getDisplayRenderingMode());
        typePart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typePartActionPerformed(e);
            }
        });
        partT.add(typePart);
    }

    private final void typePartActionPerformed(ActionEvent e) {
        dataBus.setDisplayRenderingMode(typePart.getSelectedIndex());
    }
}