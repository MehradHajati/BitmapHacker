package bitmaphacker;

import bitmaphacker.fileio.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 * This class holds the Bitmap File and can manipulate it
 * @author Mehrad Hajati
 */
public class BitmapFile{
    
    //Instance Variables
    private File file = null; // save the file of the original file
    private File changed = null; // save the changes made in a new file 
    private BufferedImage image = null; // save the BMP file as a bufferedImage for displaying
    private int width;
    private int height;
    private int headerSize;
    private int[] header;
    private Pixel[][] pixels;
    private int Padding;
    private static final int HEADERSIZE_LOCATION = 0x00A; 
    private static final int WIDTH_LOCATION = 0x012;
    private static final int HEIGHT_LOCATION = 0x016;
    private static final int PIXEL_LOCATION = 0x036;
    
    
    // Constructor
    public BitmapFile(File afile) throws IllegalArgumentException, IOException{
        if(afile == null){
            throw new IllegalArgumentException("The argument is Null");
        }
        if(!afile.isFile()){
            throw new IllegalArgumentException("The argument is not Null but does not specify a file on disk");
        }
        
        file = afile;
        loadImage();
        try (RandomAccessFile raf = new RandomAccessFile(afile, "r")) {
            // Find headerSize and calculate it
            raf.seek(HEADERSIZE_LOCATION);
            for(int i = 0; i < 3; i++){
                headerSize = headerSize + (int)(raf.read()*Math.pow(256, i)); 
            }
            
            // make header int array and fill it up
            header = new int[headerSize];
            raf.seek(0);
            for(int i = 0; i < headerSize; i++){
                header[i] = raf.read();
            }
            
            // find width and calculate it
            raf.seek(WIDTH_LOCATION);
            for(int i = 0; i < 3; i++){
                width = width + (int)(raf.read()*Math.pow(256, i));
            }
            
            // find height and calculate it
            raf.seek(HEIGHT_LOCATION);
            for(int i = 0; i < 3; i++){
                height = height + (int)(raf.read()*Math.pow(256, i));
            }
            
            // make pixels array and fill it up
            pixels = new Pixel[height][width];
            Padding = width*3 % 4;
            raf.seek(PIXEL_LOCATION);
            for(int i = height-1; i >= 0; i--){
                for(int j = 0; j < width; j++){
                    pixels[i][j] = new Pixel(raf.read(), raf.read(), raf.read());
                }
                if(Padding > 0){
                    for(int b = 0; b < 4-Padding; b++){
                        raf.read();
                    }
                }
            }
        }
    }


    // Getter Methods
    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public File getFile(){ return file; }
    
    
    /** Returns the image associated with the file or null if the file could not be read.
     * @return  */
    public BufferedImage getImage() {
        // Try to read the image if necessary:
        if ( image==null ) {
            if ( file==null ) { return null; }
            // Try to read the image file:
            try { image = ImageIO.read(file); } catch (IOException e) {}
        }
        return image;
    }

    private void loadImage() {
        if ( file==null ) { return; }
        try { image = ImageIO.read(file); } catch (IOException e) {}
    }
    
    /** Method to get name of file
     * @return  the name of the file name (the file name minus path and extension) or null if the file is null.
     */
    public String getName() {
        if ( file==null ) { return null; }
        return FileUtils.getName(file);
    }
    
    /**  Method to get name of file
     * @return  Returns the name of the file name with extension (the file name minus path) or null if the file is null.*/
    public String getNameExt() {
        if ( file==null ) { return null; }
        return FileUtils.getNameExt(file);
    }

    /** Returns the root of the file name (the file name minus extension) or null if the file is null.
     * @return  */
    public String getRoot() {
        if ( file==null ) { return null; }
        return FileUtils.getRoot(file);
    }

    /** Returns the file name (full absolute path + name + extension) or null if the file is null.
     * @return  */
    public String fileString() {
        if ( file==null ) { return null; }
        return file.getAbsolutePath();
    }
    
    
    /**
     * Method to write the new BMP to a new file
     * @param afile New file where we want to save the newly changed BMP
     * @throws IllegalArgumentException If the afile parameter is null
     * @throws IOException  If we cannot access the afile parameter
     */
    public void writeImageToFile(File afile) throws IllegalArgumentException, IOException{
        if(afile == null){
            throw new IllegalArgumentException("The file was null");
        }
        try (RandomAccessFile raf = new RandomAccessFile(afile, "rw")) {
            for(int i = 0; i < headerSize; i++){
                raf.write(header[i]);
            }
            for(int i = height-1; i >= 0; i--){
                for(int j = 0; j < width; j++){
                    raf.write(pixels[i][j].getBlue());
                    raf.write(pixels[i][j].getGreen());
                    raf.write(pixels[i][j].getRed());
                    
                }
                if(Padding > 0){
                    for(int b = 0; b < 4-Padding; b++){
                        raf.write(0);
                    }
                }
            }
            raf.close();
        }
    }

    
    /**
     * Method to flip the picture contained in the BMP file
     */
    public void flip(){
        Pixel[][] temp = new Pixel[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                temp[i][j] = pixels[height-i-1][j];
            }
        }
        pixels = temp;
    }


    /**
     * Helper method to calculate the average RGB values of surrounding pixels for a given pixel
     * @param row Row number of the pixel in question
     * @param col Column number of the pixel i question
     * @return the average RGB values of the neighboring pixels, in an array with the first entry being Red, then Green, then Blue
     */
    private int[] average(int row, int col){
        int totalBlue = 0;
        int totalRed = 0;
        int totalGreen = 0;
        int numcells = 0;
        for(int i = row-1; i <= row+1; i++){
            if(i < 0 || i > (height-1)){
                continue;
            }
            else{
                for(int j = col-1; j <= col+1; j++){
                    if(j < 0 || j > (width-1)){
                        continue;
                    }
                    else{
                        totalBlue = totalBlue + pixels[i][j].getBlue();
                        totalRed = totalRed + pixels[i][j].getRed();
                        totalGreen = totalGreen + pixels[i][j].getGreen();
                        numcells++;
                    }
                }
            }
        }
        int[] output = {totalBlue/numcells, totalGreen/numcells, totalRed/numcells};
        return output;
    }
    

    /**
     * Method to blur the image contained within the BMP file 
     */
    public void blur(){
        Pixel[][] temp = new Pixel[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int[] colors = average(i, j);
                temp[i][j] = new Pixel(colors[0], colors[1], colors[2]);
            }
        }
        pixels = temp;
    }



    /**
     * Method to completely erase one of the colors RGB from the image
     * @param aString must be either red, blue or green and it is the color that will be erased
     * @throws IllegalArgumentException Exception thrown when aString is not one of the colors RGB
     */
    public void enhance(String aString) throws IllegalArgumentException{
        if(null == aString){
            throw new IllegalArgumentException("The string parameter has to be red, blue or green");
        }
        else switch (aString) {
            case "red" -> {
                for(int i = 0; i < height; i++){
                    for(int j = 0; j < width; j++){
                        pixels[i][j].setRed(0);
                    }
                }
            }
            case "blue" -> {
                for(int i = 0; i < height; i++){
                    for(int j = 0; j < width; j++){
                        pixels[i][j].setBlue(0);
                    }
                }
            }
            case "green" -> {
                for(int i = 0; i < height; i++){
                    for(int j = 0; j < width; j++){
                        pixels[i][j].setGreen(0);
                    }
                }
            }
            default -> throw new IllegalArgumentException("The string parameter has to be red, blue or green");
        }
    }
    
}
