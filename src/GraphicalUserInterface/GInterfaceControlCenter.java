package GraphicalUserInterface;

import CommonClasses.User;

import javax.swing.*;
import java.awt.*;

public class GInterfaceControlCenter implements WorkingWithGInterface{
    public MainWindow mainWindow;
    public JPanel abstractSpaceForInteraction;
//    JPanel spaceForInteraction;
    JPanel defaultMainWindowPart;
    User mainUser;

    public GInterfaceControlCenter(){
        creatingWindow();
    }

    public void setMainUser(User mainUser) {
        this.mainUser = mainUser;
    }

    @Override
    public void setSpaceForInteraction(JPanel spaceForInteraction) {
//        this.spaceForInteraction = spaceForInteraction;
        clearSpaceForInteraction();
        abstractSpaceForInteraction.add(spaceForInteraction);
        mainWindow.setVisible(true);
    }

//    @Override
    private void clearSpaceForInteraction() {
        abstractSpaceForInteraction.removeAll();
        repaint();
    }

//    @Override
    private void repaint() {
        mainWindow.repaint();
    }

    @Override
    public void removeWindow(){
        mainWindow.setVisible(false);
    }

    @Override
    public void restartWindow(){
        creatingWindow();
    }

    private void creatingWindow(){
        mainWindow = new MainWindow();
        abstractSpaceForInteraction = new JPanel();
        defaultMainWindowPart = new JPanel();
        mainWindow.setLayout(new GridBagLayout());

        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 0;
        constraints1.gridheight = 1;
        constraints1.gridwidth = 1;

        mainWindow.add(defaultMainWindowPart, constraints1);

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.weightx = 0;
        constraints2.weighty = 0;
        constraints2.gridx = 0;
        constraints2.gridheight = 9;
        constraints2.gridwidth = 1;

        mainWindow.add(abstractSpaceForInteraction, constraints2);

        mainWindow.setVisible(true);
        mainWindow.repaint();
    }

    private void addBack(){

    }

    private void addUser(User mainUser){

    }
}
