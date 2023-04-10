package bitmaphacker.gui;

import bitmaphacker.Dialogs;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * class JFrameExit extends JFrame and has dialogs about exiting
 * @author Mehrad Hajati
 */
public class JFrameExit extends JFrame{
    
    private final String name;
    private final String versionString = "1.0";
    private final String authorString = "Mehrad Hajati";
    private final String contactString = "m.hajati24@gmail.com";
    
    public JFrameExit(String title, String n){
        super(title);
        name = n;
        finish();
    }
    
    /**
     * Methods to avoid warning of "Overridable method call inconstructor":
     */
    private void finish() {
        // Set it to do nothing when closed so that the window listener is used:
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Add the window listener:
        addWindowListener(new ExitWindowMonitor());
    }
    
    
    // -------------------- Public dialog methods --------------------
    
    public final String aboutString() {
        String t = name + " version " + versionString + System.lineSeparator() + System.lineSeparator()
                + authorString + System.lineSeparator()
                + contactString + System.lineSeparator();
        return t;
    }
    
    /**
     * Displays the About dialog
     */
    public final void about() {
        String title = "About " + name;
        Dialogs.inform(this,aboutString(),title);
    }
    
    /**
     * Exit method to exit the program, it will ask the user if they are sure
     */
    public void exit(){
        // Ask for confirmation:
        String title = "Exit " + name;
        int response = Dialogs.exitConfirmation(this,title);
        // Check answer:
        if (response == Dialogs.YES_OPTION) {
            // Run before quitting:
            runBeforeExit();
            // Quit:
            System.out.println("Goodbye.");
            System.exit(0);
        }
    }
    
    /** INCOMPLETE
     * Method to save work before exiting
     */
    public void runBeforeExit(){}
    
    
    /**
     * Method to override the windowClosing method and replace it with our own exit method
     */
    private class ExitWindowMonitor extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent event) {
            exit();
        }
    }
}
