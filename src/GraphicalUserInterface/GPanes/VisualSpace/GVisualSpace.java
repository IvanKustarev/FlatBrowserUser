package GraphicalUserInterface.GPanes.VisualSpace;

import CommonClasses.Flat;
import GraphicalUserInterface.GPanes.GEditVisualSpaceWindow;
import GraphicalUserInterface.GPanes.GInfoFlat;
import GraphicalUserInterface.WindowPane;
import GraphicalUserInterface.GInterface;
import User.ProcessControlCenter;
import User.TransferCenter;
import User.UserWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GVisualSpace implements WindowPane {

    private Flat[] flatsHere;
    private JPanel abstractPanel = new JPanel();
    private JPanel dekartSK = new JPanel();
    private JPanel columnPanel;
    private ProcessControlCenter processControlCenter;
//    private int nulX;
//    private int nulY;
//    private int mouseX;
//    private int mouseY;
    private final int sizeOfOneObjectX = 10;
    private final int sizeOfOneObjectY= 10;
    private String[][] userColourVariations;
    private GInterface gInterface;
    private Flat[] flatsWithThisCoordinates;
    private UserWork userWork;
    private VisualSpaceControlCenter visualSpaceControlCenter;
    private TransferCenter transferCenter;
    private ResourceBundle resourceBundle;


    public GVisualSpace(Flat[] flatsHere, ProcessControlCenter processControlCenter, String[][] userColourVariations, GInterface gInterface, UserWork userWork, VisualSpaceControlCenter visualSpaceControlCenter, TransferCenter transferCenter){
        this.flatsHere = flatsHere;
        this.transferCenter = transferCenter;
        this.processControlCenter = processControlCenter;
        this.userColourVariations = userColourVariations;
        this.gInterface = gInterface;
        this.userWork = userWork;
        this.visualSpaceControlCenter = visualSpaceControlCenter;

//        abstractPanel.setSize((int) (gInterface.getMainWindowSize().getWidth()) - 100, gInterface.getMainWindowSize().height/11 * 9 - 100);
        abstractPanel.setSize(800, 800);
        abstractPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        dekartSK.setSize(abstractPanel.getWidth(), abstractPanel.getHeight());
        dekartSK.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        printObjectsOnDekSK();
        abstractPanel.add(dekartSK);
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public JPanel getPanel() {
        return abstractPanel;
    }


    private void printObjectsOnDekSK(){
        dekartSK.removeAll();
        userColourVariations = DifferenceHandler.createUserColourVariations(flatsHere);

        int nulX = (int)dekartSK.getWidth()/(2);
        int nulY = (int)dekartSK.getHeight()/(2);

        JPanel flatsOnDEK = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                    for (Flat flat : flatsHere){

                        int x = nulX + (int)flat.getCoordinates().getX()*sizeOfOneObjectX - sizeOfOneObjectX/2;
                        int y = nulY - (int)flat.getCoordinates().getY()*sizeOfOneObjectY - sizeOfOneObjectY/2;
                        Color color = null;

                        for(int i =0; i< userColourVariations.length; i++){
                            if(flat.getUserName().equals(userColourVariations[i][0])){
                                color = new Color(Integer.valueOf(userColourVariations[i][1]));
                            }
                        }
                        g.setColor(color);

                        g.fillRect(x, y, sizeOfOneObjectX, sizeOfOneObjectY);
                    }
            }
            @Override
            public Dimension getPreferredSize(){
                return new Dimension(dekartSK.getWidth(), dekartSK.getHeight());
            }
        };


        flatsOnDEK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int nulXElementCorner = nulX - 5;
                int nulYElementCorner = nulY - 5;

                int mouseX = e.getX()-nulXElementCorner;
                int mouseY = -e.getY()+nulYElementCorner;
                int flatCoordinationMouseClickX = whatIsFlatCoordinateX(sizeOfOneObjectX, mouseX);
                int flatCoordinationMouseClickY = whatIsFlatCoordinateY(sizeOfOneObjectY, mouseY);


                ArrayList<Flat> flatsWithThisCoordinates = new ArrayList<>();
                for(Flat flat : flatsHere){
                    if(((flat.getCoordinates().getX() == flatCoordinationMouseClickX) & (flat.getCoordinates().getY() == flatCoordinationMouseClickY))){
                        flatsWithThisCoordinates.add(flat);
                    }
                }
                if(flatsWithThisCoordinates.size() != 0){
                    createColumnPanel(flatsWithThisCoordinates.toArray());
                }
                else {
                    JOptionPane.showConfirmDialog(new JOptionPane(), "В этих координатах нет квартир!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        });
        dekartSK.add(flatsOnDEK);
    }

    private int whatIsFlatCoordinateX(int objectSizeX, int mouseX){
        int i = 0;

        if(mouseX >= 0){

            i = mouseX/objectSizeX;
        }

        if(mouseX < 0){
            i = mouseX/objectSizeX;
            i--;
        }
        return i;
    }
    private int whatIsFlatCoordinateY(int objectSizeY, int mouseY){
        int i = 0;
        if(mouseY >= 0){

            i = mouseY/objectSizeY;

            if(mouseY%objectSizeY > 0){
                i++;
            }
        }

        if(mouseY < 0){
            i = mouseY/objectSizeY;
        }
        return i;
    }


    private void createColumnPanel(Object[] flatsWithThisCoordinatesObjects){

        this.flatsWithThisCoordinates = new Flat[flatsWithThisCoordinatesObjects.length];
        for(int i = 0; i< flatsWithThisCoordinatesObjects.length; i++){
            this.flatsWithThisCoordinates[i] = (Flat) flatsWithThisCoordinatesObjects[i];
        }

        Flat [] flatsWithThisCoordinates = this.flatsWithThisCoordinates;

        JPanel abstractColumnPanel = new JPanel();
        abstractColumnPanel.setSize(800, 800);
        int sizeX = abstractColumnPanel.getWidth();
        int sizeY = abstractColumnPanel.getHeight();


        abstractColumnPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.weightx = 0;
        constraints2.weighty = 0;
        constraints2.gridx = 0;
        constraints2.gridheight = 10;
        constraints2.gridwidth = 1;


        columnPanel = new JPanel();
        JButton backToDek = new JButton("Назад");
        backToDek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abstractPanel.removeAll();
                abstractPanel.add(dekartSK);
                gInterface.setSpaceForInteraction(abstractPanel);
            }
        });


        JPanel column = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){

                    int sizeYForOneFlat = sizeY / flatsWithThisCoordinatesObjects.length;
                    int xIndent = abstractPanel.getWidth()/40; //отступ от края
                    for (int i = 0; i < flatsWithThisCoordinatesObjects.length; i++) {

                        Color color = Color.BLACK;
                        for(int j =0; j< userColourVariations.length; j++){
                            if(((Flat)flatsWithThisCoordinatesObjects[i]).getUserName().equals(userColourVariations[j][0])){
                                color = new Color(Integer.valueOf(userColourVariations[j][1]));
                            }
                        }
                        g.setColor(color);


                        int y = sizeY - i * sizeYForOneFlat - sizeYForOneFlat;
                        int width = (int) ((Flat) flatsWithThisCoordinatesObjects[i]).getNumberOfRooms() * 10;
                        int h = sizeYForOneFlat;

                        g.fillRect(xIndent, y, width, h);
                        g.setColor(Color.BLACK);
                        g.fillRect(xIndent, y, abstractPanel.getWidth()/2,1);
                    }
            }
            @Override
            public Dimension getPreferredSize(){
                return new Dimension(sizeX, sizeY);
            }
        };

        column.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int oneComponentSizeY = abstractPanel.getHeight()/flatsWithThisCoordinates.length;

                Flat flat = flatsWithThisCoordinates[(abstractPanel.getHeight() - e.getY())/oneComponentSizeY];

                createFlatInfoPale(flat);
            }
        });



        column.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        abstractColumnPanel.add(backToDek, constraints);
        abstractColumnPanel.add(column, constraints2);

        abstractPanel.removeAll();
        abstractPanel.add(abstractColumnPanel);
        gInterface.setSpaceForInteraction(abstractPanel);
    }

    private void createFlatInfoPale(Flat flat){

        Component[] oldAbstractPaleComponents = abstractPanel.getComponents();
        abstractPanel.removeAll();

        JButton back = new JButton("Назад");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abstractPanel.removeAll();
                for(Component c : oldAbstractPaleComponents){
                    abstractPanel.add(c);
                    gInterface.setSpaceForInteraction(abstractPanel);
                }
            }
        });


        GInfoFlat gInfoFlat = new GInfoFlat(flat, abstractPanel.getSize(), this);
        JPanel infoPale = gInfoFlat.getPanel();

        abstractPanel.add(back);
        abstractPanel.add(infoPale);
        gInterface.setSpaceForInteraction(abstractPanel);
    }

    public void startEdit(Flat flat){
        if(flat.getUserName().equals(userWork.getMainUser().getLogin())){
            visualSpaceControlCenter.stopDifferenceHandler();
            GEditVisualSpaceWindow gEditVisualSpaceWindow = new GEditVisualSpaceWindow(flat, transferCenter, gInterface, userWork, processControlCenter);
            abstractPanel.removeAll();
            abstractPanel.add(gEditVisualSpaceWindow.getPanel());
            gInterface.setSpaceForInteraction(abstractPanel);
        }
        else {
            JOptionPane.showConfirmDialog(new JOptionPane(), "Объект принадлежит другому пользователю!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
        }
    }
}
