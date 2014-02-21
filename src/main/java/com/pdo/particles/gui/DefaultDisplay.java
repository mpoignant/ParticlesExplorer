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
import com.pdo.particles.utils.Particle;
import com.pdo.particles.utils.PdoRectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * The particles display
 *
 * @author Poil d'Ortie
 */
public class DefaultDisplay extends JPanel implements Runnable, MouseListener, MouseMotionListener, ComponentListener {

    private DataBus dataBus = DataBus.getInstance();

    private MainFrame parent;

    private Thread mainThread;

    private JPopupMenu treeMenu;

    private Font fontInfo;

    private double xBak, yBak;

    protected int width = 400;

    protected int height = 400;

    protected double oldx, oldy, x, y, dx, dy;

    protected boolean threadIsRunning = false;

    protected boolean mouseInside = false;

    protected boolean mouseLeftClick = false;

    protected boolean mouseRightClick = false;

    protected int mouseX, mouseY;

    protected int mouseStartX, mouseStartY, mouseEndX, mouseEndY;

    protected boolean emitterRunning = true;

    /**
     * Method DefaultDisplay.
     *
     * @param parent the main frame
     */
    public DefaultDisplay(MainFrame parent) {
        super(null);
        this.parent = parent;

        fontInfo = new Font("SansSerif", Font.BOLD, 10);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);

        createPopup();
        initFreeEmitter();
    }

    protected void initFreeEmitter() {
        double tmpX = dataBus.getEmitterX();
        double tmpY = dataBus.getEmitterY();
        if (dataBus.getEmitterMoving()) {
            tmpX = width * Math.random();
            tmpY = height * Math.random();
        }
        if (isValidPosition(tmpX, tmpY)) {
            oldx = x = tmpX;
            oldy = y = tmpY;
            dx = 10 - 8 * Math.random();
            dy = 10 - 8 * Math.random();
        } else {
            dataBus.setEmitterMoving(true);
            initFreeEmitter();
        }
    }

    private void addParticle() {
        Color partColor = dataBus.getDisplayColor("partColor");
        for (int i = 0; i < dataBus.getNumberOfParticles(); i++) {
            if (!dataBus.isEmitterAngled()) {
                Particle part = dataBus.getParticlesPool().getFirstFreeParticle();
                part.reinit(dataBus.getEnvironmentLifespan(), x, y, dataBus.getParticleInitialDistanceFromEmitter(), dataBus
                        .getEmitterPower(), partColor.getRed(), partColor.getGreen(), partColor.getBlue());
            } else {
                Particle part = dataBus.getParticlesPool().getFirstFreeParticle();
                part.reinit(dataBus.getEnvironmentLifespan(), x, y, dataBus.getEmitterAngle(), dataBus.getEmitterSpread(), dataBus
                        .getParticleInitialDistanceFromEmitter(), dataBus.getEmitterPower(), partColor.getRed(), partColor.getGreen(),
                        partColor.getBlue());
            }
        }
    }

    private void moveTrail() {
        if (mouseInside) {
            x = oldx = mouseX;
            y = oldy = mouseY;
        } else {
            if (!dataBus.getEmitterMoving()) {
                x = dataBus.getEmitterX();
                y = dataBus.getEmitterY();
            } else {
                oldx = x;
                oldy = y;
                x += dx;
                y += dy;

                if (x < -dx || x > width - 1 - dx) {
                    dx *= -1;
                }
                if (y < -dy) {
                    dy *= -1;
                }
                if (y > height - dy - 1) {
                    dy *= -0.5;
                }

                for (Iterator iter = dataBus.getObstacles().iterator(); iter.hasNext();) {
                    PdoRectangle rect = (PdoRectangle) iter.next();
                    Line2D traj = new Line2D.Double(oldx, oldy, x, y);

                    if (traj.intersectsLine(rect.getTop()) || traj.intersectsLine(rect.getBottom())) {
                        dy *= -1;
                        y = oldy;
                        x = oldx;
                    }
                    if (traj.intersectsLine(rect.getLeft()) || traj.intersectsLine(rect.getRight())) {
                        dx *= -1;
                        y = oldy;
                        x = oldx;
                    }
                }
            }
        }
    }

    /**
     * Redefined to draw the simulation.
     *
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color bgColor = dataBus.getDisplayColor("bgColor");
        g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
        g2.fillRect(0, 0, width, height);

        if (mouseInside) {
            if (threadIsRunning) {
                moveTrail();
                if (mouseLeftClick) {
                    addParticle();
                }
                if (mouseRightClick) {
                    g2.setColor(dataBus.getDisplayColor("partColor"));
                    g2.drawRect(Math.min(mouseStartX, mouseEndX), Math.min(mouseStartY, mouseEndY), Math.max(mouseStartX, mouseEndX)
                            - Math.min(mouseStartX, mouseEndX), Math.max(mouseStartY, mouseEndY) - Math.min(mouseStartY, mouseEndY));
                }
            }
        } else {
            if (threadIsRunning && emitterRunning) {
                moveTrail();
                addParticle();
            }
        }
        paintTrail(g2);
        paintObstacles(g2);
        if (mouseInside && !mouseLeftClick && !mouseRightClick) {
            paintInfos(g2);
        } else if (mouseRightClick) {
            paintInfosObstacle(g2);
        }
    }

    private final void paintTrail(Graphics2D g) {
        Color bgColor = dataBus.getDisplayColor("bgColor");
        for (int i = 0; i < dataBus.getParticlesPool().getPoolSize(); i++) {
            Particle particle = dataBus.getParticlesPool().getParticleAt(i);// (Particle)
            // iter.next();
            Color c = particle.getColor();
            g.setColor(c);

            switch (dataBus.getDisplayRenderingMode()) {
                case 0:
                    g.drawLine((int) (particle.getX()), (int) (particle.getY()), (int) (particle.getOldX()), (int) (particle.getOldY()));
                    break;

                case 1:
                    g.fillRect((int) (particle.getX() - 1), (int) (particle.getY() - 1), 3, 3);
                    break;

                case 2:
                    g.drawLine((int) (particle.getX()), (int) (particle.getY()), (int) (particle.getOldX()), (int) (particle.getOldY()));
                    g.fillRect((int) (particle.getX() - 1), (int) (particle.getY() - 1), 3, 3);
                    break;
            }

            if (threadIsRunning) {
                particle.moveParticle(width, height, dataBus.getObstacles(), dataBus.getEnvironmentGravity(), dataBus.getEnvironmentWind(),
                        bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue());
            }
        }
        g.setColor(dataBus.getDisplayColor("partColor"));
        if (dataBus.getNumberOfParticles() == 0 && !mouseInside) {
            g.fillOval((int) (x - 2), (int) (y - 2), 4, 4);
        }
    }

    private final void paintObstacles(Graphics2D g) {
        for (Iterator iter = dataBus.getObstacles().iterator(); iter.hasNext();) {
            PdoRectangle rect = (PdoRectangle) iter.next();
            if (rect.isActive()) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.gray);
            }
            g.fill(rect);
        }
    }

    private final void paintInfos(Graphics2D g) {
        String info = "(" + mouseX + "," + mouseY + ")";
        g.setColor(dataBus.getDisplayColor("partColor"));
        g.setFont(fontInfo);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle2D rect = fontInfo.getStringBounds(info, g.getFontRenderContext());
        int xText, yText;
        if (mouseX + 10 + (int) rect.getWidth() > width) {
            xText = mouseX - 10 - (int) rect.getWidth();
        } else {
            xText = mouseX + 10;
        }
        if (mouseY > height - 20) {
            yText = mouseY - 10;
        } else {
            yText = mouseY + 20;
        }
        g.drawString(info, xText, yText);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    private final void paintInfosObstacle(Graphics2D g) {
        String info = "(" + mouseStartX + "," + mouseStartY + ")->(" + mouseX + "," + mouseY + ")=(" + Math.abs(mouseX - mouseStartX) + ","
                + Math.abs(mouseY - mouseStartY) + ")";
        g.setColor(dataBus.getDisplayColor("partColor"));
        g.setFont(fontInfo);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Rectangle2D rect = fontInfo.getStringBounds(info, g.getFontRenderContext());
        int xText, yText;
        if (mouseX + 10 + (int) rect.getWidth() > width) {
            xText = mouseX - 10 - (int) rect.getWidth();
        } else {
            xText = mouseX + 10;
        }
        if (mouseY > height - 20) {
            yText = mouseY - 10;
        } else {
            yText = mouseY + 20;
        }
        g.drawString(info, xText, yText);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    /**
     * Start the simulation.
     */
    public void start() {
        if (mainThread == null) {
            mainThread = new Thread(this);
            mainThread.setPriority(Thread.MIN_PRIORITY);
            mainThread.start();
            threadIsRunning = true;
        }
    }

    /**
     * Pause the simulation.
     */
    public void stop() {
        mainThread.interrupt();
        threadIsRunning = false;
        mainThread = null;
    }

    /**
     * Main animation loop.
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        dataBus.setStartingTime(System.currentTimeMillis());
        dataBus.setFrameCtr(0l);

        while (mainThread != null) {
            try {
                // TODO : Adaptative speed
                Thread.sleep(dataBus.getSleepTimeBetweenFrames());
            } catch (InterruptedException e) {

            }
            this.repaint();
            dataBus.setFrameCtr(dataBus.getFrameCtr() + 1);

            if(dataBus.getFrameCtr() > 100) {
                dataBus.setFps((double)(dataBus.getFrameCtr())/(double)((System.currentTimeMillis()-dataBus.getStartingTime())/1000));

                if(dataBus.getFps() < 30) dataBus.setSleepTimeBetweenFrames((long)(dataBus.getSleepTimeBetweenFrames() - 5*(30-dataBus.getFps())));
                else if(dataBus.getFps() > 60) dataBus.setSleepTimeBetweenFrames((long)(dataBus.getSleepTimeBetweenFrames() + 5*(dataBus.getFps() - 60)));
                if(dataBus.getSleepTimeBetweenFrames() < 0) dataBus.setSleepTimeBetweenFrames(0);
                else if(dataBus.getSleepTimeBetweenFrames() > 100) dataBus.setSleepTimeBetweenFrames(100);

                //System.out.println(new StringBuilder("FPS:").append(dataBus.getFps()).toString());
                //System.out.println(new StringBuilder("WAIT:").append(dataBus.getSleepTimeBetweenFrames()).toString());
            }
        }
        mainThread = null;
    }

    private final boolean isValidPosition(double x, double y) {
        int ctr = 0;
        for (Iterator iter = dataBus.getObstacles().iterator(); iter.hasNext();) {
            PdoRectangle rect = (PdoRectangle) iter.next();
            if (rect.contains(x, y)) {
                ctr++;
            }
        }
        if (ctr > 0) {
            return false;
        } else {
            return true;
        }
    }

    private void selectObstacle(int x, int y) {
        for (Iterator iter = dataBus.getObstacles().iterator(); iter.hasNext();) {
            PdoRectangle rect = (PdoRectangle) iter.next();
            if (rect.contains((double) x, (double) y)) {
                rect.setActive(true);
            } else {
                rect.setActive(false);
            }
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        xBak = x;
        yBak = y;
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        mouseInside = true;
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        mouseInside = false;
        initFreeEmitter();
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            if (isValidPosition(e.getX(), e.getY())) {
                mouseX = e.getX();
                mouseY = e.getY();
                parent.params.emitterParams.textPosX.setText("" + mouseX);
                parent.params.emitterParams.textPosY.setText("" + mouseY);
            }
            mouseLeftClick = true;
        } else {
            mouseEndX = mouseStartX = e.getX();
            mouseEndY = mouseStartY = e.getY();
            selectObstacle(e.getX(), e.getY());
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            if (isValidPosition(e.getX(), e.getY())) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
            selectObstacle(e.getX(), e.getY());
            mouseLeftClick = false;
        } else {
            if (mouseRightClick) {
                mouseEndX = e.getX();
                mouseEndY = e.getY();
                if (Math.max(mouseStartX, mouseEndX) - Math.min(mouseStartX, mouseEndX) > 2
                        && Math.max(mouseStartY, mouseEndY) - Math.min(mouseStartY, mouseEndY) > 2) {
                    if (xBak < Math.max(mouseStartX, mouseEndX) && xBak > Math.min(mouseStartX, mouseEndX)
                            && yBak < Math.max(mouseStartY, mouseEndY) && yBak > Math.min(mouseStartY, mouseEndY)) {
                        System.out.println("Overlapping Obstacle");
                    } else {
                        dataBus.getObstacles().add(new PdoRectangle(mouseStartX, mouseStartY, mouseEndX, mouseEndY, DataBus.RECTANGLE_ARC_SIZE));
                    }
                }
                mouseRightClick = false;
            } else {
                treeMenu.show(this, e.getX(), e.getY());
            }
        }
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        if (isValidPosition(e.getX(), e.getY())) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
        mouseLeftClick = false;
        mouseRightClick = false;
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
            if (isValidPosition(e.getX(), e.getY())) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
            mouseLeftClick = true;
        } else {
            mouseX = e.getX();
            mouseY = e.getY();
            mouseEndX = e.getX();
            mouseEndY = e.getY();
            mouseRightClick = true;
        }
    }

    /**
     * @see java.awt.event.ComponentListener#componentHidden(ComponentEvent)
     */
    public void componentHidden(ComponentEvent e) {
    }

    /**
     * @see java.awt.event.ComponentListener#componentMoved(ComponentEvent)
     */
    public void componentMoved(ComponentEvent e) {
    }

    /**
     * @see java.awt.event.ComponentListener#componentShown(ComponentEvent)
     */
    public void componentShown(ComponentEvent e) {
    }

    /**
     * @see java.awt.event.ComponentListener#componentResized(ComponentEvent)
     */
    public void componentResized(ComponentEvent e) {
        this.width = this.getWidth();
        this.height = this.getHeight();
        initFreeEmitter();
    }

    private final void createPopup() {
        treeMenu = new JPopupMenu();
        JMenuItem menuDeleteSelected = new JMenuItem("Delete");
        menuDeleteSelected.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuDeleteSelectedActionPerformed(e);
            }
        });
        treeMenu.add(menuDeleteSelected);
        treeMenu.addSeparator();
        JMenuItem menuDeleteAll = new JMenuItem("Delete All");
        menuDeleteAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuDeleteAllActionPerformed(e);
            }
        });
        treeMenu.add(menuDeleteAll);
    }

    private final void menuDeleteSelectedActionPerformed(ActionEvent e) {
        for (Iterator iter = dataBus.getObstacles().iterator(); iter.hasNext();) {
            PdoRectangle rect = (PdoRectangle) iter.next();
            if (rect.isActive()) {
                dataBus.getObstacles().remove(rect);
            }
        }
    }

    private final void menuDeleteAllActionPerformed(ActionEvent e) {
        dataBus.getObstacles().removeAll(null);
    }
}