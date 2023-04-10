package bitmaphacker;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * This class will provide the program with static dialog methods to communicate with the user
 * @author Mehrad Hajati
 */
public class Dialogs {
    
    // Value returned when a yes button is clicked.
    public static final int YES_OPTION = JOptionPane.YES_OPTION;
    // Value returned when a no button is clicked.
    public static final int NO_OPTION = JOptionPane.NO_OPTION;
    // Value returned when an ok button is clicked.
    public static final int OK_OPTION = JOptionPane.OK_OPTION;
    // Value returned when a cancel button is clicked.
    public static final int CANCEL_OPTION = JOptionPane.CANCEL_OPTION;
    // Value returned when a dialog is closed.
    public static final int CLOSED_OPTION = JOptionPane.CLOSED_OPTION;
    
    
    /** Information message dialog.
     * @param parent Object for centering the dialog on.
     * @param message The message to provide.
     * @param title The title of the dialog.
     */
    public static void inform(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent,message,title,JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    /** Asks for exit confirmation.
     * The message displayed is "Are you sure you want to exit?"
     * @param parent Object for centering the dialog on.
     * @param title The title of the dialog.
     * @return Dialogs.YES_OPTION or Dialogs.NO_OPTION.
     */
    public static int exitConfirmation(Component parent, String title){
        return JOptionPane.showConfirmDialog(parent,
                "Are you sure you want to exit?",title,
                JOptionPane.YES_NO_OPTION);
    }
    
    
    /** Error message dialog.
     * @param parent Object for centering the dialog on.
     * @param message The message to provide.
     * @param title The title of the dialog.
     */
    public static void error(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent,message,title,JOptionPane.ERROR_MESSAGE);
    }
    
    /** Question dialog with ok and cancel buttons.
     * @param parent Object for centering the dialog on.
     * @param message The question to ask.
     * @param title The title of the dialog.
     * @return Dialogs.OK_OPTION or Dialogs.CANCEL_OPTION.
     */
    public static int confirm(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent,message,title,JOptionPane.OK_CANCEL_OPTION);
    }
    
    /** Question dialog with yes and no buttons.
     * @param parent Object for centering the dialog on.
     * @param message The question to ask.
     * @param title The title of the dialog.
     * @return Dialogs.YES_OPTION or Dialogs.NO_OPTION.
     */
    public static int yesno(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent,message,title,JOptionPane.YES_NO_OPTION);
    }
    
    
    /** Single selection dialog.
     * @param parent Object for centering the dialog on.
     * @param message The instructions to provide.
     * @param title The title of the dialog.
     * @param options The options available to select from.
     * @param i0 The index of the initial selection.
     * @return The index of the selected option.
     */
    public static int selection(Component parent, String message, String title, String[] options, int i0) {
        // Display the input dialog:
        String selection = (String) JOptionPane.showInputDialog(parent, message, title,JOptionPane.PLAIN_MESSAGE,null,options,options[i0]);
        // Check for user cancelling:
        if (selection==null) { return -1; }
        // Figure out the index of the selection:
        int ind = -1;
        for ( int i=0 ; i<options.length ; i++ ) {
            if ( options[i].equals(selection) ) {
                ind = i;
                break;
            }
        }
        // Return the index:
        return ind;

    }
    
    /** Input dialog with no initial text in the text entry field.
     * @param parent Object for centering the dialog on.
     * @param message The instructions to provide.
     * @param title The title of the dialog.
     * @return The text input by the user.
     */
    public static String input(Component parent, String message, String title) {
        return JOptionPane.showInputDialog(parent,message,title,JOptionPane.PLAIN_MESSAGE);
    }

    
    /** Input dialog with initial text in the text entry field.
     * @param parent Object for centering the dialog on.
     * @param message The instructions to provide.
     * @param title The title of the dialog.
     * @param text The initial text.
     * @return The text input by the user.
     */
    public static String input(Component parent, String message, String title, String text) {
        return (String) JOptionPane.showInputDialog(parent,message,title,JOptionPane.PLAIN_MESSAGE,null,null,text);
    }
    
    /** Question dialog with yes, no and cancel buttons.
     * @param parent Object for centering the dialog on.
     * @param message The question to ask.
     * @param title The title of the dialog.
     * @return Dialogs.YES_OPTION, Dialogs.NO_OPTION or Dialogs.CANCEL_OPTION.
     */
    public static int question(Component parent, String message, String title) {
        return JOptionPane.showConfirmDialog(parent,message,title,JOptionPane.YES_NO_CANCEL_OPTION);
    }
}
