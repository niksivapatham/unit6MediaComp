import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture 
{
    ///////////////////// constructors //////////////////////////////////

    /**
     * Constructor that takes no arguments 
     */
    public Picture ()
    {
        /* not needed but use it to show students the implicit call to super()
         * child constructors always call a parent constructor 
         */
        super();  
    }

    /**
     * Constructor that takes a file name and creates the picture 
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName)
    {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     * @param height the height of the desired picture
     * @param width the width of the desired picture
     */
    public Picture(int height, int width)
    {
        // let the parent class handle this width and height
        super(width,height);
    }

    /**
     * Constructor that takes a picture and creates a 
     * copy of that picture
     * @param copyPicture the picture to copy
     */
    public Picture(Picture copyPicture)
    {
        // let the parent class do the copy
        super(copyPicture);
    }

    /**
     * Constructor that takes a buffered image
     * @param image the buffered image to use
     */
    public Picture(BufferedImage image)
    {
        super(image);
    }

    ////////////////////// methods ///////////////////////////////////////

    /**
     * Method to return a string with information about this picture.
     * @return a string with information about the picture such as fileName,
     * height and width.
     */
    public String toString()
    {
        String output = "Picture, filename " + getFileName() + 
            " height " + getHeight() 
            + " width " + getWidth();
        return output;

    }

    /** Method to set the blue to 0 */
    public void zeroBlue()
    {
        Pixel[][] pixels = this.getPixels2D();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj : rowArray)
            {
                pixelObj.setBlue(0);
            }
        }
    }

    /** Method that mirrors the picture around a 
     * vertical mirror in the center of the picture
     * from left to right */
    public void mirrorVertical()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    /** Mirror just part of a picture of a temple */
    public void mirrorTemple()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;
        Pixel[][] pixels = this.getPixels2D();

        // loop through the rows
        for (int row = 27; row < 97; row++)
        {
            // loop from 13 to just before the mirror point
            for (int col = 13; col < mirrorPoint; col++)
            {

                leftPixel = pixels[row][col];      
                rightPixel = pixels[row]                       
                [mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** copy from the passed fromPic to the
     * specified startRow and startCol in the
     * current picture
     * @param fromPic the picture to copy from
     * @param startRow the start row to copy to
     * @param startCol the start col to copy to
     */
    public void copy(Picture fromPic, 
    int startRow, int startCol)
    {
        Pixel fromPixel = null;
        Pixel toPixel = null;
        Pixel[][] toPixels = this.getPixels2D();
        Pixel[][] fromPixels = fromPic.getPixels2D();
        for (int fromRow = 0, toRow = startRow; 
        fromRow < fromPixels.length &&
        toRow < toPixels.length; 
        fromRow++, toRow++)
        {
            for (int fromCol = 0, toCol = startCol; 
            fromCol < fromPixels[0].length &&
            toCol < toPixels[0].length;  
            fromCol++, toCol++)
            {
                fromPixel = fromPixels[fromRow][fromCol];
                toPixel = toPixels[toRow][toCol];
                toPixel.setColor(fromPixel.getColor());
            }
        }   
    }

    /** Method to create a collage of several pictures */
    public void createCollage()
    {
        Picture flower1 = new Picture("flower1.jpg");
        Picture flower2 = new Picture("flower2.jpg");
        this.copy(flower1,0,0);
        this.copy(flower2,100,0);
        this.copy(flower1,200,0);
        Picture flowerNoBlue = new Picture(flower2);
        flowerNoBlue.zeroBlue();
        this.copy(flowerNoBlue,300,0);
        this.copy(flower1,400,0);
        this.copy(flower2,500,0);
        this.mirrorVertical();
        this.write("collage.jpg");
    }

    /** Method to show large changes in color 
     * @param edgeDist the distance for finding edges
     */
    public void edgeDetection(int edgeDist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels2D();
        Color rightColor = null;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; 
            col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) > 
                edgeDist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }

    /* Main method for testing - each class in Java can have a main 
     * method 
     */
    public static void main(String[] args) 
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

    /**
     * An example of a method - replace this comment with your own
     *  that describes the operation of the method
     *
     * @pre     preconditions for the method
     *          (what the method assumes about the method's parameters and class's state)
     * @post    postconditions for the method
     *          (what the method guarantees upon completion)
     * @param   y   description of parameter y
     * @return  description of the return value
     */
    public void mirrorVerticalRightToLeft()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - 1 - col];
                leftPixel.setColor(rightPixel.getColor());
            }
        } 
    }

    /**
     * An example of a method - replace this comment with your own
     *  that describes the operation of the method
     *
     * @pre     preconditions for the method
     *          (what the method assumes about the method's parameters and class's state)
     * @post    postconditions for the method
     *          (what the method guarantees upon completion)
     * @param   y   description of parameter y
     * @return  description of the return value
     */
    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels2D();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width / 2; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[pixels.length-1-row][col];
                rightPixel.setColor(leftPixel.getColor());
            }
        } 
    }

    /**
     * An example of a method - replace this comment with your own
     *  that describes the operation of the method
     *
     * @pre     preconditions for the method
     *          (what the method assumes about the method's parameters and class's state)
     * @post    postconditions for the method
     *          (what the method guarantees upon completion)
     * @param   y   description of parameter y
     * @return  description of the return value
     */
    public void cropAndCopy( Picture sourcePicture, int startSourceRow, int endSourceRow, int startSourceCol,int endSourceCol,int startDestRow, int startDestCol )
    {
        Pixel[][] sourcePixels = sourcePicture.getPixels2D();
        
        Pixel[][] croppedPixels = new Pixel[endSourceRow-startSourceRow][endSourceCol-startSourceCol];
        int rowCount = 0;
        int colCount = 0;
        System.out.println(endSourceCol);
        for (int row = startSourceRow; row<(endSourceRow); row++)
        {
            for (int col = startSourceCol; col<(endSourceCol); col++)
            {
                croppedPixels[rowCount][colCount] = sourcePixels[row][col];
                colCount++;
            }
            rowCount++;
            colCount=0;
        }
        
        rowCount = 0;
        colCount = 0;
        Pixel[][] pixels = this.getPixels2D();
        
        for (int row = startDestRow; row<((endSourceRow-startSourceRow)+startDestRow); row++)
        {
            for (int col = startDestCol; col<((endSourceCol-startSourceCol)+startDestCol); col++)
            {
                pixels[row][col].setColor(croppedPixels[rowCount][colCount].getColor());
                colCount++;
            }
            rowCount++;
            colCount = 0;
        }     
    }
    
    public void posterize()
    {
        Pixel[][] pixels = this.getPixels2D();
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width; col++)
            {
                int redValue = pixels[row][col].getRed();
                if (redValue<=255 && redValue>=192)
                {
                    redValue = 224;
                } else if (redValue<192 && redValue>=128){
                    redValue = 160;
                } else if (redValue<128 && redValue>=64){
                    redValue = 96;
                } else {
                    redValue = 32;
                }
                
                int blueValue = pixels[row][col].getBlue();
                if (blueValue<=255 && blueValue>=192)
                {
                    blueValue = 224;
                } else if (blueValue<192 && blueValue>=128){
                    blueValue = 160;
                } else if (blueValue<128 && blueValue>=64){
                    blueValue = 96;
                } else {
                    blueValue = 32;
                }
                
                int greenValue = pixels[row][col].getGreen();
                if (greenValue<=255 && greenValue>=192)
                {
                    greenValue = 224;
                } else if (greenValue<192 && greenValue>=128){
                    greenValue = 160;
                } else if (greenValue<128 && greenValue>=64){
                    greenValue = 96;
                } else {
                    greenValue = 32;
                }
                
                Color color = new Color(redValue, greenValue, blueValue);
                pixels[row][col].setColor(color);
            }
        } 
    }
        
    public void grayscale()
    {
        Pixel[][] pixels = this.getPixels2D();
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width ; col++)
            {
                int redValue = pixels[row][col].getRed();
                int greenValue = pixels[row][col].getGreen();
                int blueValue = pixels[row][col].getBlue();
                int average = (redValue+greenValue+blueValue)/3;
                Color color = new Color(average,average,average);
                pixels[row][col].setColor(color);
            }
        }
    }
    
    public void sepia() 
    {
        grayscale();
        Pixel[][] pixels = this.getPixels2D();
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < width; col++)
            {
                int redValue = pixels[row][col].getRed();
                System.out.println(redValue);
                int blueValue = pixels[row][col].getBlue();
                int greenValue = pixels[row][col].getGreen();
                if (redValue<60){
                    Color color = new Color((int) (.9*redValue), (int) (.9*greenValue), (int) (.9*blueValue));
                    pixels[row][col].setColor(color);
                } else if (redValue<190) {
                    Color color = new Color(redValue , greenValue, (int) (.8*blueValue));
                    pixels[row][col].setColor(color);
                } else {
                    Color color = new Color(redValue, greenValue, (int) (.9*blueValue));
                    pixels[row][col].setColor(color);
                }
                
            }
        }
    }
    
    public void pixelate()
    {
        Pixel[][] pixels = this.getPixels2D();
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row+=4)
        {
            for (int col = 0; col<width; col+=4)
            {
                int redAverage = 0;
                int greenAverage = 0;
                int blueAverage = 0;
                int COUNT = 16;
                for(int row1 = 0; row<4; row++){
                    for (int col1 = 0; col<4; col++){
                        redAverage += pixels[row+row1][col+col1].getRed();                        
                    }                    
                }
                for(int row1 = 0; row<4; row++){
                    for (int col1 = 0; col<4; col++){
                        greenAverage += pixels[row+row1][col+col1].getGreen();;                        
                    }                    
                }
                for(int row1 = 0; row<4; row++){
                    for (int col1 = 0; col<4; col++){
                        blueAverage += pixels[row+row1][col+col1].getBlue();                        
                    }                    
                }
                Color color = new Color(redAverage/16, greenAverage/16, blueAverage/16);
                for(int row1 = 0; row<4; row++){
                    for (int col1 = 0; col<4; col++){
                        pixels[row+row1][col+col1].setColor(color);                                             
                    }                    
                }
            }
        }
    }
} // this } is the end of class Picture, put all new methods before this
