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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * The about dialog.
 *
 * @author Poil d'Ortie
 */
public class DialogAbout extends JDialog {
    private DataBus dataBus = DataBus.getInstance();

    private JPanel cPane;

    /**
     * Default constructor.
     *
     * @param parent The main frame.
     */
    public DialogAbout(JFrame parent) {
        super(parent);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        this.setTitle("About Particles Explorer");
        this.setSize(new Dimension(320, 240));
        this.setResizable(false);

        cPane = (JPanel) this.getContentPane();
        cPane.setLayout(new BorderLayout());

        JPanel panelText = new JPanel(new BorderLayout());
        cPane.add(panelText, BorderLayout.CENTER);

        StringBuffer gplText = new StringBuffer();
        gplText.append("<html><body><table width=\"320\" cellspacing=\"0\" cellpadding=\"0\">");
        gplText.append("  <tr><td align=\"left\">");
        gplText.append("    <font size=\"+1\"><b>Particles Explorer</b></font>");
        gplText.append("  </td></tr>");
        gplText.append("  <tr><td align=\"right\">");
        gplText.append("    <i>Copyright (C) 2001-2002  Poil d'Ortie</i>");
        gplText.append("  </td></tr>");
        gplText.append("  <tr><td></td></tr>");
        gplText.append("  <tr><td align=\"center\">");
        gplText.append("    This program is free software; you can redistribute it and/or modify it under the terms of the");
        gplText.append("    GNU General Public License as published by the Free Software Foundation");
        gplText.append("  </td></tr>");
        gplText.append("  <tr><td></td></tr>");
        gplText.append("  <tr><td align=\"center\">");
        gplText.append("    Icons from & inspired by Dean S. Jones' icons");
        gplText.append("    dean@gallant.com www.gallant.com/icons.htm");
        gplText.append("  </td></tr>");
        gplText.append("  <tr><td></td></tr>");
        gplText.append("  <tr><td align=\"center\">");
        gplText.append("    Contact : http://poildortie.free.fr");
        gplText.append("  </td></tr>");
        gplText.append("  <tr><td>&nbsp;</td></tr>");
        gplText.append("</table></body></html>");
        panelText.add(new JLabel(gplText.toString()), BorderLayout.CENTER);

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cPane.add(panelButton, BorderLayout.SOUTH);
        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOkActionPerformed(e);
            }
        });
        panelButton.add(btnOk);

        pack();
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            dispose();
        }
        super.processWindowEvent(e);
    }

    private void btnOkActionPerformed(ActionEvent e) {
        dispose();
    }
}