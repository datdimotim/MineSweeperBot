package com.dimotim.saper_bot;

import java.awt.*;
import java.awt.event.InputEvent;

public class Controller {
    private static final int startX=110;
    private static final int startY=160;
    private static final int d=54;
    private static void click(int col,int row, int mask){
        Robot robot;
        try {
            robot=new Robot();
        }catch (AWTException e) {
            throw new RuntimeException(e);
        }

        robot.mouseMove(startX+col*d,startY+row*d);
        robot.mousePress(mask);
        robot.mouseRelease(mask);
    }
    public static void rightClick(int i,int j){
        click(i,j,InputEvent.BUTTON3_DOWN_MASK);
    }

    public static void leftClick(int i,int j){
        click(i,j,InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void hideMouse(){
        Robot robot;
        try {
            robot=new Robot();
        }catch (AWTException e) {
            throw new RuntimeException(e);
        }
        robot.mouseMove(0,0);
    }
}
