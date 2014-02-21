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
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * The Menu Bar.
 *
 * @author Poil d'Ortie
 */
public class Menu extends JMenuBar {
    private DataBus dataBus = DataBus.getInstance();

    private JFileChooser chooser;

    private JMenu menuFile;

    private JMenuItem menuFileLoad, menuFileSaveAs, menuFileExit;

    private JMenu menuHelp;

    private JMenuItem menuHelpAbout;

    /**
     * Default constructor.
     *
     * @param file The file to load at startup
     */
    public Menu(String file) {
        super();

        chooser = new JFileChooser();
        // chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setFileFilter(new PxpFileFilter());
        // File
        menuFile = new JMenu();
        menuFile.setText("File");
        this.add(menuFile);
        // File -> Load
        menuFileLoad = new JMenuItem();
        menuFileLoad.setText("Load");
        menuFileLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileLoadActionPerformed(e);
            }
        });
        menuFile.add(menuFileLoad);
        // File -> Save as
        menuFileSaveAs = new JMenuItem();
        menuFileSaveAs.setText("Save as");
        menuFileSaveAs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileSaveAsActionPerformed(e);
            }
        });
        menuFile.add(menuFileSaveAs);
        menuFile.addSeparator();
        // File -> Exit
        menuFileExit = new JMenuItem();
        menuFileExit.setText("Quit");
        menuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileExitActionPerformed(e);
            }
        });
        menuFile.add(menuFileExit);

        // Help
        menuHelp = new JMenu();
        menuHelp.setText("Help");
        this.add(menuHelp);
        // Help -> About
        menuHelpAbout = new JMenuItem();
        menuHelpAbout.setText("About Particles Explorer");
        menuHelpAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpAboutActionPerformed(e);
            }
        });
        menuHelp.add(menuHelpAbout);

        if (file != null) {
            dataBus.load(file);
        }
    }

    private void fileLoadActionPerformed(ActionEvent e) {
        String file = null;

        if (((MainFrame) this.getTopLevelAncestor()).display.threadIsRunning) {
            ((MainFrame) this.getTopLevelAncestor()).display.stop();
        }
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        int returnVal = chooser.showDialog(((MainFrame) this.getTopLevelAncestor()), "Open file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile().getAbsolutePath();
        }

        if (file != null) dataBus.load(file);
        ((MainFrame) this.getTopLevelAncestor()).setBounds(dataBus.getMainFrameLocation().x, dataBus.getMainFrameLocation().y, dataBus
                .getMainFrameDimension().width, dataBus.getMainFrameDimension().height);
        ((MainFrame) this.getTopLevelAncestor()).updateControls();
        ((MainFrame) this.getTopLevelAncestor()).display.start();
    }

    private void fileSaveAsActionPerformed(ActionEvent e) {
        String file = null;

        if (((MainFrame) this.getTopLevelAncestor()).display.threadIsRunning) {
            ((MainFrame) this.getTopLevelAncestor()).display.stop();
        }
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        int returnVal = chooser.showDialog(((MainFrame) this.getTopLevelAncestor()), "Save file");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile().getAbsolutePath();

        }

        if (!file.endsWith(".pxp")) {
            if (file.indexOf(".") != -1) {
                file = file.substring(0, file.lastIndexOf(".")) + ".pxp";
            } else {
                file += ".pxp";
            }
        }
        dataBus.save(file);
        ((MainFrame) this.getTopLevelAncestor()).display.start();
    }

    private void fileExitActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    private void helpAboutActionPerformed(ActionEvent e) {
        JFrame parent = (JFrame) this.getTopLevelAncestor();
        Dimension dlgSize = dataBus.getDialogAbout().getPreferredSize();
        Dimension frmSize = parent.getSize();
        Point loc = parent.getLocation();
        dataBus.getDialogAbout().setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
        dataBus.getDialogAbout().setModal(true);
        dataBus.getDialogAbout().setVisible(true);
    }

    static class PxpFileFilter extends FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            if (f.getAbsolutePath().endsWith(".pxp")) {
                return true;
            } else {
                return false;
            }
        }

        public String getDescription() {
            return "Particles Explorer Save File";
        }
    }
}
