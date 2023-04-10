package bitmaphacker.gui;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import bitmaphacker.BitmapHacker;
import bitmaphacker.MyPoint2D;
import bitmaphacker.BitmapFile;

/**
 * The TextBar object provides information about the current file
 * @author Mehrad Hajati
 */
public final class TextBar extends JPanel {
    
    private final JTextArea textArea;
    private final BitmapHacker controller;
    private MyPoint2D mouse;
    
    public TextBar(BitmapHacker con) {
        super();
        // Create the text area:
        textArea = new JTextArea();
        textArea.setEditable(false);
        setTextAreaBackground();
        // Set minimum and maximum sizes so that the text will alway be displayed:
        // (I found I had to do this because the border layout manager wasn't working well)
        setSizes();
        // Add the text area to the status bar:
        finishUp();
        controller = con;
        updateText();
        
    }
    
    private void setTextAreaBackground() {
        textArea.setBackground(this.getBackground());
    }
    
    private void setSizes() {
        Font f = new Font(null); // should be default font
        int pad = 4;
        Dimension dim = getToolkit().getScreenSize();
        Dimension minSize = new Dimension(0,f.getSize()+pad);
        Dimension maxSize = new Dimension(dim.width,f.getSize()+pad);
        textArea.setMinimumSize(minSize);
        textArea.setMaximumSize(maxSize);
        setMinimumSize(minSize);
        setMaximumSize(maxSize);
    }
    
    private void finishUp() {
        add(textArea);
    }

    // -------------------- Public Methods --------------------

    /** Writes a string inside the panel.
     * @param s
     */
    public void setText(String s) {
        // Display the text.
        textArea.setText(s);
    }

    @Override
    public void setToolTipText(String s) {
        // Display the text.
        textArea.setToolTipText(s);
    }

    /**
     * Method to update the text that the TextBar is showing
     */
    public void updateText() {
        // Create the text and tool tip strings:
        String text,tip;
        BitmapFile bmp = controller.getBMPFile();
        if ( bmp==null ) {
            text = "No BMP File loaded.";
            tip = text;
        } else {
            text = bmp.getNameExt();
            tip  = bmp.fileString();
        }
        if(mouse == null){
            // Display the text in the EBSD bar:
            setText(text);
            
        }
        else{
            setText(text + "  -  " + mouse.toString());
        }
        // Set the tool tip text in case the window is resized too small to see the whole path:
        setToolTipText(tip);
    }
        
    
    public void showCoordinates(MyPoint2D p){
        this.mouse = p;
        updateText();
    }
}
