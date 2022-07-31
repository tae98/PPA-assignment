import javax.swing.*;
import java.util.*;  
import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
/**
 * Write a description of class About here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class About
{
    // instance variables - replace the example below with your own
    

    
    public About()
    {
       messageBox(); 
       
    }

    public void messageBox(){
        
       JFrame page = new JFrame(); 
       page.setTitle("About"); 
       Dimension pageSize = new Dimension(700,400); 
       page.setPreferredSize(pageSize);
       
       Container contents = page.getContentPane(); 
       JPanel holder = new JPanel(new GridLayout(5,1)); 
       
      
      
       JLabel firstTitle = new JLabel("How to use", JLabel.CENTER);
         
       JLabel description = new JLabel(
       "Choose the price range you wish to search for a house then press the '>' button"
       , JLabel.CENTER);
       JLabel description2 = new JLabel("Next choose your desired location and then you will see the list of available properties in that location"
       , JLabel.CENTER); 

       
       
       
       JLabel secondTitle = new JLabel("Developers", JLabel.CENTER); 
       
       JLabel developers = new JLabel ("This was developed by: Hasham Bashir, Lok Yin Chin, Asen Georgiev, Tae Hyun Kim", JLabel.CENTER); 
       
       
       
      
       
       

       
       
       
       contents.add(holder);
       
       holder.add(firstTitle); 
       holder.add(description); 
       holder.add(description2); 
       holder.add(secondTitle);
       holder.add(developers);
       holder.setBackground(Color.white);
       page.setVisible(true);
       page.pack();  
        
    }
}
