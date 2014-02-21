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

import javax.swing.*;

/**
 * Tabbed Panel containing all simulation controls.
 *
 * @author Poil d'Ortie
 */
public class ParamsPanel extends JTabbedPane {
    protected ParamsEnvironment envParams;
    protected ParamsEmitter emitterParams;
    protected ParamsDisplay dispParams;

    /**
     * Default constructor.
     */
    public ParamsPanel() {
        super();
        // Emitter tools
        emitterParams = new ParamsEmitter();
        this.addTab("Emitter", emitterParams);
        // Environment tools
        envParams = new ParamsEnvironment();
        this.addTab("Environment", envParams);
        // Display tools
        dispParams = new ParamsDisplay();
        this.addTab("Display", dispParams);
    }
}