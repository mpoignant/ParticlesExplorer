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

package com.pdo.particles;

import com.pdo.particles.gui.MainFrame;
import com.pdo.particles.utils.DataBus;

import java.awt.*;
import java.util.StringTokenizer;

/**
 * Main class, used to launch the application.
 *
 * @author Poil d'Ortie
 */
public class Main {
    /**
     * Method Main.
     *
     * @param file pxp save file to load at startup
     */
    public Main(String file) {
        MainFrame frame = new MainFrame(file);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation(
                (screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    /**
     * Method main.
     *
     * @param args comman line parameters
     */
    public static void main(String[] args) {

        // Detecting Java Version
        String s = System.getProperty("java.version");
        StringTokenizer stringtokenizer = new StringTokenizer(s, ".");
        String s1 = stringtokenizer.nextToken();
        String s2 = stringtokenizer.nextToken();
        boolean JAVA1_4ABOVE;
        if (s1.equals("1")
                && (s2.equals("1") || s2.equals("2") || s2.equals("3"))) {
            JAVA1_4ABOVE = false;
        } else {
            JAVA1_4ABOVE = true;
        }

        // Use HW acceleration for 1.4 and above
        if (JAVA1_4ABOVE) {
            System.out.println("Using Hardware Acceleration");
            System.setProperty("sun.java2d.ddoffscreen", "true");
            System.setProperty("sun.java2d.ddscale", "true");
        }


        DataBus.getInstance();
        if (args.length == 1) {
            new Main(args[0]);
        } else {
            new Main(null);
        }
    }
}
