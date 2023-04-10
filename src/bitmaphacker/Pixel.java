package bitmaphacker;

// Mehrad Hajati
// Pixel Class
// This class is meant to create and hold the information for each pixel of BMP file

public class Pixel{
    
    // Instance Variables
    private int red;
    private int green;
    private int blue;
    private static final int MAX = 255;
    private static final int MIN = 0;


    // Constructor
    public Pixel(int color1, int color2, int color3){
        setRed(color3);
        setGreen(color2);
        setBlue(color1);
    }


    // Getter and Setter Methods
    public int getRed(){
        return this.red;
    }

    public int getGreen(){
        return this.green;
    }

    public int getBlue(){
        return this.blue;
    }

    public void setRed(int color1){
        if(color1 > MAX){
            this.red = MAX;
        }
        else{
            if(color1 < MIN){
                this.red = MIN;
            }
            else{
                this.red = color1;
            }
        }
    }

    public void setGreen(int color2){
        if(color2 > MAX){
            this.green = MAX;
        }
        else{
            if(color2 < MIN){
                this.green = MIN;
            }
            else{
                this.green = color2;
            }
        }
    }

    public void setBlue(int color3){
        if(color3 > MAX){
            this.blue = MAX;
        }
        else{
            if(color3 < MIN){
                this.blue = MIN;
            }
            else{
                this.blue = color3;
            }
        }
    }



}