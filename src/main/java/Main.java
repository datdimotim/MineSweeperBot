import com.dimotim.minesweaper.engine.AnalysisResult;
import com.dimotim.minesweaper.engine.Engine;
import com.dimotim.saper_bot.Controller;
import com.dimotim.saper_bot.Recognizer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        Runtime runtime=Runtime.getRuntime();
        Process minesweeper=runtime.exec("gnome-mines --big");
        Thread.sleep(1000);
        Controller.leftClick(0,0);
        Thread.sleep(500);
        boolean[][] flags=new boolean[30][16];
        while (true) {
            Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = (new Robot().createScreenCapture(screen));
            int[][] field = Recognizer.recognize(image);
            AnalysisResult r = Engine.analysis(field);
            for (int[] pt : r.emptyPts) Controller.leftClick(pt[0], pt[1]);
            for (int[] pt : r.minedPts) {
                if(flags[pt[0]][pt[1]])continue;
                flags[pt[0]][pt[1]]=true;
                Controller.rightClick(pt[0], pt[1]);
            }
            if (r.emptyPts.size() == 0) {
                //minesweeper.destroy();
                return;
            }
            Controller.hideMouse();
            Thread.sleep(500);
        }

    }
}
