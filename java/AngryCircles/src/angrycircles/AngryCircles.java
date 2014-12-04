/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package angrycircles;

import java.awt.*;

/**
 *
 * @author colbycheeze
 */
public class AngryCircles {

    public static final int PANEL_WIDTH = 400;
    public static final int PANEL_HEIGHT = 400;
    public static final int CANNON_SIZE = 50;
    public static final int CANNONBALL_RADIUS = 5;
    public static final int TARGET_LENGTH = 50;
    public static final int TARGET_X = 280;
    public static final double GRAVITY = -0.2;
    
    public static DrawingPanel panel;
    public static Graphics g;
    public static double time = 0;
    public static int targetTop = 75; // Y pos of target
    public static double force = 10;
    public static int x0, y0; //starting position
    public static double vx, vy; // velocity
    public static boolean running = false;
    public static int numHits; // tracks the number of hits
    public static int numMisses; // and the misses
    
    public static double cannonAngle = 45;
    
    public static void main(String[] args) {
        panel = new DrawingPanel(PANEL_WIDTH, PANEL_WIDTH);
        g = panel.getGraphics();
        new DrawingUtility(panel);
        
        updateXY0();
        
        while(true){
            
            if(running){
                time += 1;
                int r = checkForHitOrMiss(getCircleX(time), getCircleY(time));
                if(r == - 1 ){
                    System.out.println("I FLEW OFF THE SCREEN!");
                    numMisses += 1;
                } else if ( r == 1){
                    System.out.println("I HIT THE TARGET!");
                    running = false;
                    time = 0;
                    numHits += 1;
                }
            }
            
            drawData();
            panel.sleep(50);
            
        }
        
    }
    
    // update all text data, and redraw graphics of simulation
    public static void drawData(){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        g.setColor(Color.red);
        g.drawString("Angry Circles written by ColbyCheeZe", 10, 15);
        g.setColor(Color.black);
        g.drawString("Cannon Angle: " + cannonAngle, 10, 30);
        g.setColor(Color.black);
        g.drawString("Target Y: " + targetTop, 10, 45);
        g.setColor(Color.black);
        g.drawString("Force: " + force, 10, 60);
        g.setColor(Color.black);
        g.drawString("Time: " + time, 10, 75);
        
        drawCannon(Color.BLACK);
        drawCannonballAtTime(time, Color.red);
        drawTarget();
    }
    
    
    public static void drawCannon(Color color){
        g.setColor(color);
        int x = DrawingUtility.getTriangleX(CANNON_SIZE, cannonAngle);
        int y = PANEL_HEIGHT - DrawingUtility.getTriangleY(CANNON_SIZE, cannonAngle);
        //System.out.println("Cannon x:" + x + ", y:" + y);
        g.drawLine(0, PANEL_HEIGHT, x, y);
    }
    
    public static void drawCannonball(int x, int y, Color color){
        g.setColor(color);
        g.drawOval(x - CANNONBALL_RADIUS, y - CANNONBALL_RADIUS, CANNONBALL_RADIUS*2, CANNONBALL_RADIUS*2);
    }
    
    public static void drawCannonballAtTime(double time, Color c){
        int x = getCircleX(time);       
        int y = getCircleY(time);
        
        //if(running) drawCannonball(x, y, c);
        drawCannonball(x, y, c);
    }
    
    public static int checkForHitOrMiss(int x, int y){
        
        // if the cannonball connects with the target, we return 1;
        if(x >= TARGET_X && y >= targetTop && y <= targetTop + TARGET_LENGTH) return 1;
        
        // If the cannonball flies off of the canvas we return -1
        if(x > PANEL_WIDTH + CANNONBALL_RADIUS || y > PANEL_HEIGHT + CANNONBALL_RADIUS ) return -1;
        
        //Well, the cannonball hasn't hit anything!
        return 0;
    }
            
    
    public static void updateXY0(){
        x0 =  DrawingUtility.getTriangleX(CANNON_SIZE + CANNONBALL_RADIUS, cannonAngle);
        y0 =  PANEL_HEIGHT - DrawingUtility.getTriangleY(CANNON_SIZE + CANNONBALL_RADIUS, cannonAngle);
        
    }
    
    public static int getCircleX(double time){
        return (int)(x0 + vx * time);
    }
    
    public static int getCircleY(double time){
        return (int)((y0 - vy * time) - (0.5 * GRAVITY * (time * time)));
    }
            
    
    public static void drawTarget(){
        g.setColor(Color.GREEN);
        g.drawLine(TARGET_X, targetTop, TARGET_X, targetTop + TARGET_LENGTH);
    }

    static void keyUp(char keyChar) {
        
    }

    static void targetUp() {
        targetTop -= 5;
        if(targetTop <= 0) targetTop = 0;
    }

    static void targetDown() {
        targetTop += 5;
        if(targetTop > PANEL_HEIGHT - TARGET_LENGTH) targetTop = PANEL_HEIGHT - TARGET_LENGTH;
    }

    static void angleDown() {
       cannonAngle += 1;
       if (cannonAngle > 90) cannonAngle = 90;
       
       updateXY0();
    }

    static void angleUp() {
        cannonAngle -= 1;
        if (cannonAngle < 0) cannonAngle = 0;
        updateXY0();
    }

    static void forceLarger() {
       force+=1;
    }

    static void forceSmaller() {
        force-=1;
        if(force < 1) force = 1;
    }

    static void fire() {
        vx = DrawingUtility.getTriangleX(force, cannonAngle);
        vy = DrawingUtility.getTriangleY(force, cannonAngle);
        running = true;
        time = 0;
    }

    static void keyDown(char keyChar) {
        if(keyChar == 'h'){
            
        }                    
    }
    
}
