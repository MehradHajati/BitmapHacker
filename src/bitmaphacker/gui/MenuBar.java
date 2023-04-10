package bitmaphacker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import bitmaphacker.BitmapHacker;
import javax.swing.event.MenuListener;

/**
 * The menu on the BitmapHacker window.
 * @author Mehrad Hajati
 */
public final class MenuBar extends JMenuBar{
    
    private final BitmapHacker controller;
    
    // Buttons
    private JMenuItem miAbout, miExit, miLoad, miFlip;
    
    //Menus
    private JMenu view;
    
    /**
     * Makes the menu bar
     * @param con BitmapHacker window to place the menu on
     */
    public MenuBar(BitmapHacker con){
        super();
        this.controller = con;
        makeMenuItems();
        addMenuItems();
    }
    
    private void makeMenuItems() {
        MenuListener listener = new MenuListener();
        miAbout = makeMenuItem("About", "Display information about BitmapHacker", listener);
        miExit = makeMenuItem("Exit", "Exit BitmapHacker without saving", listener);
        miLoad = makeMenuItem("Load", "Load new BMP file", listener);
        miFlip = makeMenuItem("Flip", "Flip the picture", listener);
        
        
    }
    
    private JMenuItem makeMenuItem(String text, String tip, ActionListener al){
        JMenuItem mi = new JMenuItem(text);
        mi.setToolTipText(tip);
        mi.addActionListener(al);
        return mi;
    }
    
    private void addMenuItems(){
        JMenu mainMenu = new JMenu("BitmapHacker");
        JMenu view = new JMenu("View");
        this.add(mainMenu);
        mainMenu.add(miAbout);
        mainMenu.add(miExit);
        mainMenu.add(miLoad);
        mainMenu.add(miFlip);
        this.add(view);
    }
    
    
    /**
     * Action listener for menu items.
     */
    private class MenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if(src == null){ return;}
            if(src == miExit){ controller.exit(); }
            else if(src == miAbout) {controller.about(); }
            else if(src == miLoad) {controller.loadBMPFile(); }
            else if(src == miFlip) { controller.flip(); }
        }
    }
}
