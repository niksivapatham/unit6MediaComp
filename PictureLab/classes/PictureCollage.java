import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List
/**
 * Creates an awesome collage of images
 * 
 * @author Nikhil Sivapatham
 * @version 22 January 2015
 */
public class PictureCollage
{    
    /**
     * Imports a couple of picture from the picture file in the same folder
     * Applies different affects to them
     * And then changes a portion of the background picture to those picture
     */
    public static void main(String[] args)
    {
        //Importing all of the pictures
        Picture background = new Picture("Iron Man.jpg"); 
        Picture grayScaleIronMan = new Picture("smalliron.jpg");
        Picture posterIronMan = new Picture("smalliron.jpg");
        Picture horizontalIronMan = new Picture("smalliron.jpg");
        Picture verticalIronMan = new Picture("smalliron.jpg");        
        
        //Modifying all the pictures with filters and transformations
        grayScaleIronMan.grayscale();      
        posterIronMan.posterize();
        horizontalIronMan.mirrorHorizontal();
        verticalIronMan.mirrorVertical();
        
        //Copying all of the modified pictures to the background
        background.copy(grayScaleIronMan, 100,100);  
        background.copy(posterIronMan, 100,1250);       
        background.copy(horizontalIronMan, 632, 100);        
        background.copy(verticalIronMan, 632,1250);      
        
        //Displays the glory that is the final product
        background.explore();
        
        //Saves the collage as a single picture to the same folder as everything else
        background.write("MyCollage.jpg");
    }    
}
