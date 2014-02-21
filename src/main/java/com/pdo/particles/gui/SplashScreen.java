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
import java.awt.*;

/**
 * A SplashScreen for use when starting applications. The splash screen contains
 * a title, and image and a status bar. The status bar can display messages
 * as well as the percentage of completion using a progress bar.
 *
 * @author Tony Johnson
 */
public class SplashScreen extends JWindow {
    private JProgressBar progress;

    /**
     * Create a new SplashScreen
     *
     * @param coolPicture    The image to display in the splash screen
     * @param initialMessage The initial message to display in the progress area
     */
    public SplashScreen(Icon coolPicture, String initialMessage) {
        // Create a JPanel so we can use a BevelBorder
        JPanel panelForBorder = new JPanel(new BorderLayout());
        panelForBorder.setLayout(new BorderLayout());

        // Image
        panelForBorder.add(new JLabel(coolPicture), BorderLayout.CENTER);

        // Progress Bar
        progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        progress.setString(initialMessage);
        panelForBorder.add(progress, BorderLayout.SOUTH);

        getContentPane().add(panelForBorder);
    }

    /**
     * Show or hide the splash screen.
     *
     * @param show True to display the splash screen
     */
    public void setVisible(boolean show) {
        if (show) {
            pack();

            // Plonk it on center of screen
            Dimension windowSize = getSize(),
                    screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.setBounds(
                    (screenSize.width - windowSize.width) / 2,
                    (screenSize.height - windowSize.height) / 2,
                    windowSize.width,
                    windowSize.height);
        }
        super.setVisible(show);
    }

    /**
     * Updates the status bar. This method is thread safe and can be called from
     * any thread.
     *
     * @param message The message to display
     * @param percent The percentage towards completion
     */
    public void showStatus(String message, int percent) {
        if (isVisible()) {
            SwingUtilities.invokeLater(new UpdateStatus(message, percent));
        }
    }

    /**
     * Close the splash screen and free any resources associated with it.
     * This method is thread safe and can be called from any thread.
     */
    public void close() {
        if (isVisible()) {
            SwingUtilities.invokeLater(new CloseSplashScreen());
        }
    }

    private class UpdateStatus implements Runnable {
        private String message;
        private int value;

        public UpdateStatus(String status, int pc) {
            message = status;
            value = pc;
        }

        public void run() {
            progress.setValue(value);
            progress.setString(message);
        }
    }

    private class CloseSplashScreen implements Runnable {
        public void run() {
            setVisible(false);
            dispose();
        }
    }
}
