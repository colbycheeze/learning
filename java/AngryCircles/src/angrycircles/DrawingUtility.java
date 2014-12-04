/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angrycircles;

import java.awt.event.*;

public class DrawingUtility implements KeyListener {
  
  private static int lastKeyPress = -1;
  
  public DrawingUtility(DrawingPanel panel) {
    panel.addKeyListener(this);
  }
  
  public void keyReleased(KeyEvent event) {
    char keyChar = event.getKeyChar();
    if (keyChar != -1)
      AngryCircles.keyUp(keyChar);
    lastKeyPress = -1;
  }
  
  public void keyPressed(KeyEvent event) {
    int keyCode = event.getKeyCode();
    char keyChar = event.getKeyChar();
    switch (keyCode) {
      case KeyEvent.VK_NUMPAD8:
      case KeyEvent.VK_UP:
        AngryCircles.targetUp();
        break;
      case KeyEvent.VK_NUMPAD2:
      case KeyEvent.VK_DOWN:
        AngryCircles.targetDown();
        break;
      case KeyEvent.VK_NUMPAD4:
      case KeyEvent.VK_LEFT:
        AngryCircles.angleDown();
        break;
      case KeyEvent.VK_NUMPAD6:
      case KeyEvent.VK_RIGHT:
        AngryCircles.angleUp();
        break;
      case KeyEvent.VK_EQUALS:
        AngryCircles.forceLarger();
        break;
      case KeyEvent.VK_MINUS:
        AngryCircles.forceSmaller();
        break;
      case KeyEvent.VK_ENTER:
        AngryCircles.fire();
        break;
    }
    // Prevent repeat on these keys
    if ((keyChar != -1) && (lastKeyPress != keyCode)){ 
      AngryCircles.keyDown(keyChar);
    }
    lastKeyPress = keyCode;
  }
  
  public void keyTyped(KeyEvent event) {
  }
  
  public static int getTriangleX(double length, double angle) {
    return (int)(length*Math.cos(angle*Math.PI/180)+.5);
  }
  
  public static int getTriangleY(double length, double angle) {
    return (int)(length*Math.sin(angle*Math.PI/180)+.5);
  }
  
}

