package bitmaphacker;

/**
 *
 * @author Mehrad Hajati
 */
public final class MouseInteractionManager {
    
    private final BitmapHacker controller;
    private MyPoint2D clickedPoint = null; // clicked point position
    private MyPoint2D cursorPoint = null; // current cursor position
   
    public MouseInteractionManager(BitmapHacker con) {
        controller = con;
    }
    
    public boolean hasClickedPoint() { return ( clickedPoint != null ); }
    public MyPoint2D getClickedPoint() { return clickedPoint; }
    //public MyPoint2D getCursorPoint() { return cursorPoint; }
    
    public MyPoint2D getPixel() {
        MyPoint2D p = clickedPoint;
        if (p==null) { p = cursorPoint; }
        return p;
    }
    
    
    public void mouseExit() {
        // Clear current cursor location:
        cursorPoint = null;
        // Check what to redraw:
        if (clickedPoint==null) {
            // Clear the cursor location bar:
            //controller.updateCursorBar(null);
            // Clear the CubePanel:
            //controller.repaintCube();
        }
    }

    public void mouseMove(MyPoint2D p) { // p should be in image coordinates
        // Store the current cursor location:
        cursorPoint = p;
        // Update the cursor location bar:
        //controller.updateCursorBar(p);
        // Check what to redraw:
        if (clickedPoint==null) {
            // Redraw both panels:
            //controller.repaintEBSD();
            //controller.repaintCube();
        } else {
            // Redraw only the CubePanel:
            //controller.repaintCube();
        }
    }
    
    private void toggleClickedPoint(MyPoint2D p) {
        if (clickedPoint==null) {
            clickedPoint = p;
        } else {
            clickedPoint = null;
            cursorPoint = p; // probably not necessary but no harm
        }
    }
}
