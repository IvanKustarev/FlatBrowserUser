package GraphicalUserInterface;

import Resources.Naming;
import Resources.ResourceControlCenter;
import User.UserWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceControlCenter implements GInterface {
    private MainWindow mainWindow;
    private JPanel abstractSpaceForInteraction;
    private JPanel abstractTopMainWindowPart;
    private JPanel menuTopMainWindowPart;
    private WindowPane nowPane;
    private WindowPane topPane;
    private JComboBox languages;

    ResourceControlCenter resourceControlCenter;

    public InterfaceControlCenter(ResourceControlCenter resourceControlCenter){
        this.resourceControlCenter = resourceControlCenter;
    }



    @Override
    public void setSpaceForInteraction(JPanel spaceForInteraction) {
        clearSpaceForInteraction();
        abstractSpaceForInteraction.add(spaceForInteraction);
        mainWindow.setVisible(true);
    }

    @Override
    public void setGPane(WindowPane windowPane) {
        nowPane = windowPane;
        nowPane.setLocale(resourceControlCenter.getMainResourceBundle());
        clearSpaceForInteraction();
        JPanel jPanel = nowPane.getPanel();
        setSpaceForInteraction(jPanel);
        jPanel.setVisible(true);
    }

    @Override
    public void clearSpaceForInteraction() {
        abstractSpaceForInteraction.removeAll();
    }



    @Override
    public void repaint() {
        setSpaceForInteraction(nowPane.getPanel());
        setTopPartOfWindow(topPane);
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

        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainWindow = new MainWindow();

        mainWindow.setMinimumSize(new Dimension(500, 650));

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
        mainWindow.setExtendedState(Frame.MAXIMIZED_BOTH);
        mainWindow.repaint();
    }

    @Override
    public Dimension  getMainWindowSize(){
        return mainWindow.getSize();
    }

    @Override
    public void sendNotification(String string, String title) {
        JOptionPane.showConfirmDialog(new JOptionPane(), resourceControlCenter.getMainResourceBundle().getString(string), resourceControlCenter.getMainResourceBundle().getString(title), JOptionPane.OK_CANCEL_OPTION);



//        JFrame jFrame = new JFrame(resourceControlCenter.getMainResourceBundle().getString(title));
//        jFrame.setBounds(mainWindow.getX(), mainWindow.getY(), 100, 100);
//        jFrame.setLayout(new GridLayout(2, 1));
//
//        JLabel textLabel = new JLabel(resourceControlCenter.getMainResourceBundle().getString(string));
//        textLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
//        jFrame.add(textLabel);
//
//        JButton okButton = new JButton(resourceControlCenter.getMainResourceBundle().getString("OK"));
//        okButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                jFrame.setVisible(false);
//            }
//        });
//        jFrame.add(okButton);
//
//        jFrame.setVisible(true);
    }


        @Override
    public void setTopPartOfWindow(WindowPane topPane) {
        this.topPane = topPane;
        clearTopPartOfWindow();
        topPane.setLocale(resourceControlCenter.getMainResourceBundle());
        abstractTopMainWindowPart.add(topPane.getPanel());
        mainWindow.setVisible(true);
    }

    private void clearTopPartOfWindow() {
        abstractTopMainWindowPart.removeAll();
//        repaint();
    }

    @Override
    public void setSizeForLanguagePale(Dimension dimension){
        menuTopMainWindowPart.setPreferredSize(dimension);
    }

    private void createMenuTopMainWindowPart(){

        String[] languagesNames = new String[resourceControlCenter.getResourceBundles().length];
        for(int i =0; i<languagesNames.length; i++){
            Naming naming = (Naming) resourceControlCenter.getResourceBundles()[i];
            languagesNames[i] = naming.getName();
        }
        languages = new JComboBox(languagesNames);
        languages.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (resourceControlCenter.getMainResourceBundle()){
                    resourceControlCenter.setIndex(languages.getSelectedIndex());
                    nowPane.setLocale(resourceControlCenter.getMainResourceBundle());
                    try {
                        topPane.setLocale(resourceControlCenter.getMainResourceBundle());
                        setTopPartOfWindow(topPane);
                    }catch (NullPointerException nullPointerException){ }
                    setSpaceForInteraction(nowPane.getPanel());
                }
            }
        });
        menuTopMainWindowPart = new JPanel();
        menuTopMainWindowPart.setLayout(new GridLayout(1,1));
        menuTopMainWindowPart.setPreferredSize(new Dimension(350, 40));
        languages.setBackground(new Color(0xFFD9ECEF, true));
        languages.setFont(new Font("Dialog", Font.PLAIN, 20));
        menuTopMainWindowPart.add(languages);
    }
    private void createAbstractSpaceForInteraction(){
        abstractSpaceForInteraction = new JPanel();

//        abstractSpaceForInteraction.setPreferredSize(new Dimension(700, 700));
//        abstractSpaceForInteraction.setBackground(Color.magenta);

        abstractSpaceForInteraction.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        abstractSpaceForInteraction.setPreferredSize(new Dimension(mainWindow.getWidth()-20, (mainWindow.getHeight()/11 * 9)));

        abstractSpaceForInteraction.setMinimumSize(new Dimension(500, 500));

        abstractSpaceForInteraction.setLayout(new GridLayout(1,1));
        abstractTopMainWindowPart = new JPanel();
        abstractTopMainWindowPart.setLayout(new GridLayout(1,1));
    }
}
