/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package circles;
import java.awt.Color;
import java.util.*;

/**
 *
 * @author NEWColbyCheeZe
 */
public class Circles {
    
    public static final Scanner CONSOLE = new Scanner(System.in);

   
    public static void main(String[] args) {
       System.out.println("Lab 5 Written by ColbyCheeZe");
       
       int radius1;
       int radius2;
       int x1, x2, y1, y2;
       
       System.out.println("Enter Radius");
       radius1 = CONSOLE.nextInt();
       System.out.println("Enter X");
       x1 = CONSOLE.nextInt();
       System.out.println("Enter Y");
       y1 = CONSOLE.nextInt();
       
       System.out.println("Enter Radius");
       radius2 = CONSOLE.nextInt();
       System.out.println("Enter X");
       x2 = CONSOLE.nextInt();
       System.out.println("Enter Y");
       y2 = CONSOLE.nextInt();
       
       drawCircles(radius1, x1, y1, radius2, x2, y2);
       int comparison = compareCircleSizes(radius1, radius2);
       
       if(comparison == -1){
           System.out.println("Red is smaller than Green");
       } else if(comparison == 0){
           System.out.println("Red is equal to Green");
       } else System.out.println("Red is larger than Green");
       
          
    }
    
    public static int compareCircleSizes(int radius1, int radius2){
        if(radius1 == radius2) return 0;
        if(radius1 < radius2) return -1;
        return 1;
    }
    
    public static void drawCircles(int radius1, int x1, int y1, int radius2, int x2, int y2){
        DrawingPanel dp = new DrawingPanel(400, 300);
      
        dp.getGraphics().setColor(Color.red);
        dp.getGraphics().fillOval(x1, y1, radius1, radius1);
        dp.getGraphics().setColor(Color.green);
        dp.getGraphics().fillOval(x2, y2, radius2, radius2);       
        
    }
    
}
