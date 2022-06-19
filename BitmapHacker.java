// Mehrad Hajati
// Bitmap Hacker Class
// This class takes in a file of BMP and can flip the picture horizontally, blur the picture or completely erase one of RGB colors from the picture.
// It can also create a new file with the changes saved onto it

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;


public class BitmapHacker{

    //Instance Variables
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
    public BitmapHacker(File afile) throws IllegalArgumentException, IOException{
        if(afile == null){
            throw new IllegalArgumentException("The argument is Null");
        }
        if(!afile.isFile()){
            throw new IllegalArgumentException("The argument is not Null but does not specify a file on disk");
        }
        RandomAccessFile raf = new RandomAccessFile(afile, "r");

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
        raf.close();
    }


    // Getter Methods
    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    // Write Image To File method
    public void writeImageToFile(File afile) throws IllegalArgumentException, IOException{
        if(afile == null){
            throw new IllegalArgumentException("The file was null");
        }
        RandomAccessFile raf = new RandomAccessFile(afile, "rw");
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


    // Flip Method
    public void flip(){
        Pixel[][] temp = new Pixel[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                temp[i][j] = pixels[height-i-1][j];
            }
        }
        pixels = temp;
    }


    // method to calc average of surround 8 pixels and the pixel itself
    public int[] average(int row, int col){
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
    

    //Blur Method
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



    // Enhance method
    // This method sets one of the values of red blue or green to zero, completely erasing one color from the image
    public void enhance(String aString) throws IllegalArgumentException{
        if(aString == "red"){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    pixels[i][j].setRed(0);
                }
            }
        }
        else if(aString == "blue"){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    pixels[i][j].setBlue(0);
                }
            }
        }
        else if(aString == "green"){
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    pixels[i][j].setGreen(0);
                }
            }
        }
        else{
            throw new IllegalArgumentException("The string parameter has to be red, blue or green");
        }
    }


}