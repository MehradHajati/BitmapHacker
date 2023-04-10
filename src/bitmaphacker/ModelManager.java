package bitmaphacker;

import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import java.awt.image.*;

/**
 *
 * @author Mehrad Hajati
 */
public class ModelManager {
    
    
    private final BitmapHacker controller;
    private BitmapFile bmp = null;
    
    public ModelManager(BitmapHacker con){
        controller = con;
    }
    
    public void loadBMPFile(){
        
        // Ask for the list of files
        JFileChooser chooser = new JFileChooser();
        File loadDirectory = controller.getOpenDirectory();
        chooser.setCurrentDirectory(loadDirectory);
        String title = "Load BMP File";
        chooser.setDialogTitle(title);
        chooser.setMultiSelectionEnabled(false);
        int response = chooser.showOpenDialog(controller);
        
        // Check response and get the list of files
        if(response  != JFileChooser.APPROVE_OPTION){ return; }
        File file = chooser.getSelectedFile();
        
        // Check file
        if(file == null){ return; }
        loadDirectory = chooser.getCurrentDirectory();
        controller.setOpenDirectory(loadDirectory);
        
        try {
            bmp = new BitmapFile(file);
        }
        catch(IOException e){
            Dialogs.error(controller, "Ran into a IOException", title);
        }
        catch(IllegalArgumentException e){
            Dialogs.error(controller, "Ran into an IllegalArgumentException", title);
        }
          
        
    }
    
    // Wrappers for BitmapFile Class
    public BitmapFile getBMPFile(){ return bmp; }
    public BufferedImage getImage(){ if (bmp != null){ return bmp.getImage(); } else { return null; } }
    public void flip(){ bmp.flip(); }
    
}
