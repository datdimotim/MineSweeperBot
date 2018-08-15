package com.dimotim.saper_bot;

import com.dimotim.minesweaper.engine.Engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Recognizer {
    private static final int UNKNOWN = Engine.UNKNOWN;
    private static final int FLAG = Engine.UNKNOWN;
    private static final int d=54;
    private static final Rectangle area=new Rectangle(85,137,30*d+10,16*d+8);
    public static int[][] recognize(BufferedImage screenshot){
        BufferedImage fieldImage=new BufferedImage(area.width,area.height,BufferedImage.TYPE_3BYTE_BGR);
        fieldImage.getGraphics().drawImage(screenshot,
                0,0,area.width,area.height,
                area.x,area.y,area.x+area.width,area.y+area.height,
                null);

        int[][] field=new int[30][16];
        for(int i=0;i<30;i++){
            for(int j=0;j<16;j++) {
                Rectangle cell = getExactCell(i, j,fieldImage);
                final int inf=(int) inf(cell,fieldImage)/10000;
                if(-4538954==inf(cell,fieldImage)){
                    field[i][j]= UNKNOWN;
                    continue;
                }
                if(inf==-423){
                    field[i][j]=1;
                    continue;
                }
                if(inf==-445){
                    field[i][j]=2;
                    continue;
                }
                if(inf==-453){
                    field[i][j]=3;
                    continue;
                }
                if(inf==-459){
                    field[i][j]=4;
                    continue;
                }
                if(inf==-461){
                    field[i][j]=5;
                    continue;
                }
                if(inf==-703){
                    field[i][j]=FLAG;
                    continue;
                }
                if(inf==-494){
                    field[i][j]=6;
                    continue;
                }
                if(inf==-217){
                    field[i][j]=0;
                    continue;
                }
                if(inf==-403){
                    field[i][j]=7;
                    continue;
                }
                if(inf==-500){
                    field[i][j]=8;
                    continue;
                }
                throw new RuntimeException("Unknown symbol: "+i+" "+j+" "+inf(cell, fieldImage));
            }
        }
        return field;
    }

    private static long inf(Rectangle r,BufferedImage img){
        long d=0;
        for(int i=r.x;i<r.x+r.width;i++){
            for(int j=r.y;j<r.y+r.height;j++){
                d+=img.getRGB(i,j);
            }
        }
        return d/r.width/r.height;
    }

    private static Rectangle getExactCell(int col,int row,BufferedImage img){
        int SPLIT_COLOR=-855568;
        Rectangle r=getCell(col, row);
        Point pt=new Point(r.x+r.width/2,r.y+r.height/2);
        int stX=pt.x;
        int fnX=pt.x;
        int stY=pt.y;
        int fnY=pt.y;
        while (img.getRGB(stX--,pt.y)!=SPLIT_COLOR);
        stX++;
        while (img.getRGB(fnX++,pt.y)!=SPLIT_COLOR);
        fnX--;
        while (img.getRGB(pt.x,stY--)!=SPLIT_COLOR);
        fnX++;
        while (img.getRGB(pt.x,fnY++)!=SPLIT_COLOR);
        fnY--;
        return new Rectangle(stX+7,stY+7,39,39);
    }

    private static Rectangle getCell(int col,int row){
        final int stX=57;
        final int stY=59;
        final int fnX=1578;
        final int fnY=818;
        final double dx=((double) fnX-stX)/28;
        final double dy=((double) fnY-stY)/14;
        return new Rectangle((int)(stX+(col-1)*dx)+5,(int) (stY+(row-1)*dy)+5,d-10,d-10);
    }
}
