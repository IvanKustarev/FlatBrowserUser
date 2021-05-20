package GraphicalUserInterface.GPanes.VisualSpace;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.MainWindow;
import User.Main;

import javax.swing.*;
import java.awt.*;

public class Animation implements Runnable{

    private Integer animationCounter;
    private JPanel flatsOnDEK;
    private GInterface gInterface;
    private GVisualSpace visualSpace;
    JPanel dekartSK;

    public Animation(Integer animationCounter, JPanel jPanel, GInterface gInterface, GVisualSpace visualSpace, JPanel dekartSK){
        this.animationCounter = animationCounter;
        this.flatsOnDEK = jPanel;
        this.gInterface = gInterface;
        this.visualSpace = visualSpace;
        this.dekartSK = dekartSK;
    }


    @Override
    public void run() {
        for (animationCounter =4;animationCounter>=0;animationCounter--){
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gInterface.getMainWindow().repaint();
//            JFrame jFrame = new JFrame();
//            jFrame.setPreferredSize(new Dimension(1000, 1000));
            visualSpace.setAnimationCounter(animationCounter);
            dekartSK.remove(flatsOnDEK);
            dekartSK.add(flatsOnDEK);
//            flatsOnDEK.repaint();
//            jFrame.add(flatsOnDEK);
//            jFrame.setVisible(true);
//            System.out.println("t");
        }
    }
}
