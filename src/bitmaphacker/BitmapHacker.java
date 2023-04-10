package bitmaphacker;

// Author: Mehrad Hajati
// Bitmap Hacker Class
// This class takes in a file of BMP and can flip the picture horizontally, blur the picture or completely erase one of RGB colors from the picture.
// It can also create a new file with the changes saved onto it

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import bitmaphacker.gui.JFrameExit;
import bitmaphacker.fileio.OpenAndSave;

/**
 *
 * @author Mehrad Hajati
 */
public final class BitmapHacker extends JFrameExit{
    
    // ------------------ Properties -------------------

    //Objects that manage various components of the application:
    private final ModelManager modelManager; // the model components
    private final ViewManager viewManager; // the GUI components
    private final InteractionManager interactionManager; // manages user interaction with the GUI
    private final OpenAndSave fileIOManager; 
    
    // ------------------ Main Method ------------------

    public static void main(String[] args) {
            BitmapHacker bitmapHacker = new BitmapHacker();
        //});
    }

    
    public BitmapHacker(){
        // Create the window with a title:
        super("BitmapHacker", "BitmapHacker");
        setSize(150, 100);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new FlowLayout() );       // set the layout manager
        modelManager = new ModelManager(this);
        interactionManager = new InteractionManager(this);
        viewManager = new ViewManager(this);
        fileIOManager = new OpenAndSave();
        // Resize the window to some percentage of the screen size and centre the window on the screen:
        resizeAndCentre();
        // Make the window visible:
        setVisible(true);
        requestFocusInWindow();
    }

    private void resizeAndCentre() {
        // Resize the window to some percentage of the screen size:
        Dimension dim = getToolkit().getScreenSize();
        //setSize( win.width , (int)(0.75*win.height) );
        setSize((int)(0.75*dim.width) , (int)(0.50*dim.height) );
        // Centre the window on the screen:
        Dimension siz = this.getSize();
        setLocation((int)((dim.width  - siz.width )*0.5),
                (int)((dim.height - siz.height)*0.5));
    }
    
    
    public void loadBMPFile(){
        modelManager.loadBMPFile();
        // Display the Image on panel
        viewManager.repaintPanel();
        // Display the name of the file
        viewManager.updateBar();
    }
    
    // Wrappers for the OpenAndSave Class
    public File getOpenDirectory(){ return fileIOManager.getOpenDirectory(); }
    public void setOpenDirectory(File f){ fileIOManager.setOpenDirectory(f); }
    
    //Wrappers for ModelManager Class
    public BitmapFile getBMPFile(){ return modelManager.getBMPFile(); }
    public BufferedImage getImage(){ return modelManager.getImage(); }
    public void flip(){ modelManager.flip(); }
    
}
