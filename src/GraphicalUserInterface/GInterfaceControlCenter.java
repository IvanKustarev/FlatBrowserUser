package GraphicalUserInterface;

import Resources.Naming;
import Resources.ResourceControlCenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class GInterfaceControlCenter implements WorkingWithGInterface{
    private MainWindow mainWindow;
    private JPanel abstractSpaceForInteraction;
    private JPanel abstractTopMainWindowPart;
    private JPanel menuTopMainWindowPart;

    ResourceControlCenter resourceControlCenter;

    public GInterfaceControlCenter(ResourceControlCenter resourceControlCenter){
        this.resourceControlCenter = resourceControlCenter;
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

    @Override
    public void repaint() {
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

    @Override
    public void creatingWindow(){

        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();

        mainWindow = new MainWindow();

        mainWindow.setBounds(0,0, sSize.width, sSize.height);

        mainWindow.setLayout(new GridBagLayout());

        createAbstractSpaceForInteraction();

        createMenuTopMainWindowPart();

        GridBagConstraints constraints0 = new GridBagConstraints();
        constraints0.weightx = 0;
        constraints0.weighty = 0;
        constraints0.gridx = 0;
        constraints0.gridheight = 1;
        constraints0.gridwidth = 3;

        mainWindow.add(menuTopMainWindowPart, constraints0);

        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.weightx = 0;
        constraints1.weighty = 0;
        constraints1.gridx = 0;
        constraints1.gridheight = 1;
        constraints1.gridwidth = 3;

        mainWindow.add(abstractTopMainWindowPart, constraints1);

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.weightx = 0;
        constraints2.weighty = 0;
        constraints2.gridx = 0;
        constraints2.gridheight = 9;
        constraints2.gridwidth = 3;

        mainWindow.add(abstractSpaceForInteraction, constraints2);

        mainWindow.setVisible(true);
        mainWindow.repaint();
    }

    @Override
    public Dimension  getMainWindowSize(){
        return mainWindow.getSize();
    }

    @Override
    public void setTopPartOfWindow(JPanel topPartOfWindow) {
        clearTopPartOfWindow();
        abstractTopMainWindowPart.add(topPartOfWindow);
        mainWindow.setVisible(true);
    }

    private void clearTopPartOfWindow() {
        abstractTopMainWindowPart.removeAll();
        repaint();
    }

    private void createMenuTopMainWindowPart(){

        String[] languagesNames = new String[resourceControlCenter.getResourceBundles().length];
        for(int i =0; i<languagesNames.length; i++){
            Naming naming = (Naming) resourceControlCenter.getResourceBundles()[i];
            languagesNames[i] = naming.getName();
        }
        JComboBox languages = new JComboBox(languagesNames);
        languages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (resourceControlCenter.getMainResourceBundle()){
                    resourceControlCenter.setIndex(languages.getSelectedIndex());
                }
            }
        });
        menuTopMainWindowPart = new JPanel();
        menuTopMainWindowPart.add(languages);
    }
    private void createAbstractSpaceForInteraction(){
        abstractSpaceForInteraction = new JPanel();/*{
            @Override
            public Dimension getPreferredSize(){
                return
            }
        };*/
        abstractSpaceForInteraction.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        abstractSpaceForInteraction.setPreferredSize(new Dimension(mainWindow.getWidth()-20, (mainWindow.getHeight()/11 * 9)));



        abstractSpaceForInteraction.setLayout(new GridLayout(1,1));
        abstractTopMainWindowPart = new JPanel();
        abstractTopMainWindowPart.setLayout(new GridLayout(1,1));
    }
}
