package bitmaphacker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import bitmaphacker.gui.MenuBar;
import javax.swing.JLabel;
import bitmaphacker.gui.TextBar;

/** Manages the visible part of the BitmapHacker application
 * This would be the view component of the MVC structure
 * This class will contain all the main GUI components
 * @author Mehrad Hajati
 */
public final class ViewManager{
    
    private final BitmapHacker controller;
    private MenuBar menuBar;
    private TextBar textBar;
    private BMPPanel panel;
    
    
    // Constructor
    public ViewManager(BitmapHacker con){
        // set the controller
        controller = con;
        // make and add the objects
        makeObjects();
        addObjects();
    }
    
    private void makeObjects(){
        // Instantiate the menu:
        menuBar = new MenuBar(controller);
        // Create the TextBar:
        textBar = new TextBar(controller);
        textBar.setBorder(BorderFactory.createEtchedBorder());
        // Create the panel
        panel = new BMPPanel(controller);
        panel.setBorder(BorderFactory.createEtchedBorder());
    }
    
    private void addObjects(){
        // Put on the menu:
        controller.setJMenuBar(menuBar);
        // Get the content pane:
        Container contentPane = controller.getContentPane();
        contentPane.removeAll();
        // Add the EBSD panel and text bar to a single panel:
        JPanel panelPair = new JPanel();
        panelPair.setLayout(new BorderLayout());
        panelPair.add(textBar  ,BorderLayout.NORTH);
        panelPair.add(panel,BorderLayout.CENTER);
        contentPane.setLayout(new GridLayout(1, 1));
        contentPane.add(panelPair);
        
    }
    
    //Wrappers for BMPPanel Class
    public void repaintPanel(){ panel.repaint(); }
    
    //Wrappers for TextBar Class
    public void updateBar(){ textBar.updateText(); }
}
