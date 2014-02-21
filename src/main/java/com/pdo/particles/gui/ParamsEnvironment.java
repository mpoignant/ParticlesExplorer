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
 * Panel containing emitter parameters controls.
 *
 * @author Poil d'Ortie
 */
public class ParamsEnvironment extends JPanel {
    private static final boolean TICKS = false;
    private static final boolean LABELS = false;
    private static final Dimension DIM_LABEL = new Dimension(80, 18);
    private static final Dimension DIM_SLIDE = new Dimension(200, 18);
    private static final BorderLayout ELEMENT = new BorderLayout(1, 1);
    private static final FlowLayout FLOW =
            new FlowLayout(FlowLayout.CENTER, 1, 1);
    private static final GridLayout GRID = new GridLayout(3, 1, 1, 1);

    private DataBus dataBus = DataBus.getInstance();
    private JLabel labelGravity, labelDuration, labelWind;

    protected JSlider slideGravity, slideDuration, slideWind;

    /**
     * Default constructor.
     */
    public ParamsEnvironment() {
        super(new BorderLayout());

        JPanel main = new JPanel();
        main.setLayout(GRID);
        JPanel gravity = new JPanel(new GridLayout(2, 1, 1, 1));
        gravity.setBorder(DataBus.ETCHED_BORDER);
        main.add(gravity);
        JPanel gravityL = new JPanel(FLOW);
        gravity.add(gravityL);
        labelGravity =
                new JLabel(
                        "Gravity : " + dataBus.getEnvironmentGravity() + " g",
                        SwingConstants.LEFT);
        gravityL.add(labelGravity);
        JPanel gravityS = new JPanel(FLOW);
        gravity.add(gravityS);
        slideGravity = new JSlider(0, 4, dataBus.getEnvironmentGravity());
        slideGravity.setMinimumSize(DIM_SLIDE);
        slideGravity.setPreferredSize(DIM_SLIDE);
        slideGravity.setMajorTickSpacing(10);
        slideGravity.setMinorTickSpacing(5);
        slideGravity.setPaintTicks(TICKS);
        slideGravity.setPaintLabels(LABELS);
        gravityS.add(slideGravity);
        slideGravity.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideGravityStateChanged(e);
            }
        });

        JPanel duration = new JPanel(new GridLayout(2, 1, 1, 1));
        duration.setBorder(DataBus.ETCHED_BORDER);
        main.add(duration);
        JPanel durationL = new JPanel(FLOW);
        duration.add(durationL);
        labelDuration =
                new JLabel(
                        "Lifespan : " + dataBus.getEnvironmentLifespan() + " frames",
                        SwingConstants.LEFT);
        durationL.add(labelDuration);
        JPanel durationS = new JPanel(FLOW);
        duration.add(durationS);
        slideDuration = new JSlider(1, dataBus.LIFESPAN_MAX_VALUE, dataBus.getEnvironmentLifespan());
        slideDuration.setMinimumSize(DIM_SLIDE);
        slideDuration.setPreferredSize(DIM_SLIDE);
        slideDuration.setMajorTickSpacing(20);
        slideDuration.setMinorTickSpacing(5);
        slideDuration.setPaintTicks(TICKS);
        slideDuration.setPaintLabels(LABELS);
        durationS.add(slideDuration);
        slideDuration.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideDurationStateChanged(e);
            }
        });

        JPanel wind = new JPanel(new GridLayout(2, 1, 1, 1));
        wind.setBorder(DataBus.ETCHED_BORDER);
        main.add(wind);
        JPanel windL = new JPanel(FLOW);
        wind.add(windL);
        labelWind = new JLabel("Wind : " + dataBus.getEnvironmentWind(), SwingConstants.LEFT);
        windL.add(labelWind);
        JPanel windS = new JPanel(FLOW);
        wind.add(windS);
        slideWind = new JSlider(-100, 100, dataBus.getEnvironmentWind());
        slideWind.setMinimumSize(DIM_SLIDE);
        slideWind.setPreferredSize(DIM_SLIDE);
        slideWind.setMajorTickSpacing(20);
        slideWind.setMinorTickSpacing(5);
        slideWind.setPaintTicks(TICKS);
        slideWind.setPaintLabels(LABELS);
        windS.add(slideWind);
        slideWind.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                slideWindStateChanged(e);
            }
        });

        this.add(main, "Center");
    }

    private final void update() {
        labelGravity.setText("Gravity : " + dataBus.getEnvironmentGravity() + " g");
        labelDuration.setText("Lifespan : " + dataBus.getEnvironmentLifespan() + " frames");
        labelWind.setText("Wind : " + dataBus.getEnvironmentWind());
    }

    private void slideGravityStateChanged(ChangeEvent e) {
        dataBus.setEnvironmentGravity(slideGravity.getValue());
        update();
    }

    private void slideDurationStateChanged(ChangeEvent e) {
        dataBus.setEnvironmentLifespan(slideDuration.getValue());
        update();
    }

    private void slideWindStateChanged(ChangeEvent e) {
        dataBus.setEnvironmentWind(slideWind.getValue());
        update();
    }
}