/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package randomwalk;

import java.util.*;
import java.awt.*;


public class RandomWalk {
  public static void main(String[] args) {
    System.out.println("Lab 7 written by Erynn Harris");
    System.out.println();
    
    Scanner console = new Scanner(System.in);
    System.out.print("Enter the a radius of a circle between 50 and 400 pixels: ");
    int radius = console.nextInt();
    while(radius < 50 || radius > 400) {
      System.out.println("Enter a valid radius between 50 and 400");
      radius = console.nextInt();
    }  
    
    System.out.print("Enter the color of the circle (Red or Orange): ");
    String ans = console.nextLine();
    
    boolean continueLoop = true;
    Color color = Color.WHITE;
    while(continueLoop){
      if(matchesChoice(ans, "red") == true) {
        color = Color.RED;
        continueLoop = false;
      } else if(matchesChoice(ans, "orange") == true) {
        color = Color.ORANGE;
        continueLoop = false;
      } else {
        System.out.print("Enter a valid color (Red or Orange): ");
        ans = console.nextLine();
      }
      
    }
    DrawingPanel panel = new DrawingPanel(radius*2 + 50, radius*2 +50);
    
    Graphics g = panel.getGraphics();
    
    g.setColor(color);
    g.drawOval(25, 25, radius*2, radius*2);
    
    g.setColor(Color.CYAN);
    
    
    
  }
  public static boolean matchesChoice(String answer, String choice) {
    String upAnswer = answer.toUpperCase().trim();
    String upChoice = choice.toUpperCase().trim();
    if(upAnswer.length() != 1)
        if(upAnswer.length() > 1)
            return upAnswer.equals(upChoice);
        else
            return false;
    else return upAnswer.charAt(0) == upChoice.charAt(0);
  }
}
  
