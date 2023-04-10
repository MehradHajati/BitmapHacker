package bitmaphacker;

/** Manages user interactions with the GUI.
 * @author Mehrad Hajati
 */
public final class InteractionManager {
    
    private final MouseInteractionManager mouseManager; // manages mouse interactions
    
    public InteractionManager(BitmapHacker con) {
        mouseManager = new MouseInteractionManager(con);
    }
    
    // Wrappers for the MouseInteractionManager class:
    public boolean hasClickedPoint() { return mouseManager.hasClickedPoint(); }
    public MyPoint2D getClickedPoint() { return mouseManager.getClickedPoint(); }
    //public MyPoint2D getCursorPoint() { return mouseManager.getCursorPoint(); }
    public MyPoint2D getPixel() { return mouseManager.getPixel(); }
    //public void mouseClick(Point2D p) { mouseManager.mouseClick(p); }
    public void mouseExit() { mouseManager.mouseExit(); }
    public void mouseMove(MyPoint2D p) { mouseManager.mouseMove(p); }
    
}
