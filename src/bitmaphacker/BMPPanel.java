package bitmaphacker;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import bitmaphacker.gui.ImagePanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.*;

/**
 * Panel for holding the displaying the BMP file
 * @author Mehrad Hajati
 */
public final class BMPPanel extends ImagePanel{
    
    private final BitmapHacker controller;
    
    /** Creates the BMPPanel.
     * @param con The BitmapHacker controller object.
     */
    public BMPPanel(BitmapHacker con) {
        super();
        controller = con;
        // Set mouse and resize listeners:
        addMouseListener(new MouseClickMonitor()); // listens for mouse clicks
        addMouseMotionListener(new MouseMoveMonitor()); // listens for mouse motion
        addComponentListener(new PanelSizeMonitor()); // listens for panel resizing
    }
    
    /** Paints graphics on the panel.
     * @param g Graphics context in which to draw.
     */
    @Override
    public void paintComponent(Graphics g) {

        // Paint background:
        super.paintComponent(g);

        // Check there is a SEM reference image:
        BufferedImage image = controller.getImage();
        if (image==null) { return; }

        // Tightly fit the image inside the panel but maintain aspect ratio:
        calculateTightFitTransform();
        tightFitImage(g,image);
        
        
        // Use Java2D graphics for overlays:
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
    }
    
    /** Calculates the transformations required to maintain the sample image aspect ratio
     * and fit tightly within this panel, keeping zoom box information in mind.
     */
    public void calculateTightFitTransform() {
        // Get the current sample image:
        BufferedImage image = controller.getImage();

        // If no sample image exists then return:
        if (image==null) { return; }

        // Width and height of panel in pixels:
        double panelWidth  = this.getWidth();
        double panelHeight = this.getHeight();
        
        // Width and height of image in pixels:
        double imageWidth  = image.getWidth();
        double imageHeight = image.getHeight();

        // Calculate scaling:
        scaling = Math.min( panelWidth/imageWidth , panelHeight/imageHeight );
        double scaledWidth  = scaling*imageWidth;
        double scaledHeight = scaling*imageHeight;

        // Determine translation required to centre the image on the panel:
        double translateX = 0.5*(panelWidth  - scaledWidth );
        double translateY = 0.5*(panelHeight - scaledHeight);
        translation = new MyPoint2D(translateX,translateY);
        
        // Create forward and inverse affine transformations:
        createTightFitTransform();
        
    }
    
    /** Listens for mouse clicks. */
    private class MouseClickMonitor extends MouseAdapter {
        @Override
        public void mouseClicked (MouseEvent e) {
            requestFocusInWindow(); // this is important to help the keyTyped method fire
            
        }
        @Override
        public void mouseExited( MouseEvent e ) {
            // Tell the controller about the mouse exit:
            //controller.mouseExit();
        }
    }
    
    
    /** Listens for mouse movement. */
    private class MouseMoveMonitor extends MouseMotionAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            requestFocusInWindow(); // this is important to help the keyTyped method fire
            if (panelToImage==null) { return; }
            // Transform the current cursor location:
            MyPoint2D p = new MyPoint2D(e.getPoint());
            p.transform(panelToImage); // transform from panel to EBSD image pixel coordinates
            // Tell the controller about the mouse move:
            //controller.mouseHoverAFM(p);
        }
    }
}
