package GraphicalUserInterface.GPanes;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import CommonClasses.Flat;
import CommonClasses.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.DateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.GPanes.GEditTableWindow;
import GraphicalUserInterface.InterfaceControlCenter;
import GraphicalUserInterface.WindowPane;
import HelpingModuls.ConnectionException;
import User.*;

public class PlaneCreator {
    public class Table implements WindowPane {

        private Flat[] flats;
        private String[][] rows;
        private User user;
        private TransferCenter transferCenter;
        private GInterface gInterface;
        private UserWork userWork;
        private ProcessControlCenter processControlCenter;
        private ResourceBundle resourceBundle;


        public Table(Flat[] flats, User user, TransferCenter transferCenter, GInterface gInterface, UserWork userWork, ProcessControlCenter processControlCenter){
            this.user = user;
            this.flats = flats;
            this.transferCenter = transferCenter;
            this.gInterface = gInterface;
            this.userWork = userWork;
            this.processControlCenter =processControlCenter;
        }

        @Override
        public void setLocale(ResourceBundle resourceBundle) {
            this.resourceBundle = resourceBundle;
        }

        private void createRows(){
            rows = new String[flats.length][16];
            for(int i =0;i<flats.length;i++){
                rows[i][0] = flats[i].getUserName();
                rows[i][1] = String.valueOf(flats[i].getId());
                rows[i][2] = flats[i].getName();
                rows[i][3] = String.valueOf(flats[i].getCoordinates().getX());
                rows[i][4] = String.valueOf(flats[i].getCoordinates().getY());
                rows[i][5] = String.valueOf(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Main.getLocaleByResourceName(resourceBundle.getBaseBundleName())).format(flats[i].getCreationDate()));
                rows[i][6] = String.valueOf(flats[i].getArea());
                rows[i][7] = String.valueOf(flats[i].getNumberOfRooms());
                rows[i][8] = flats[i].getFurnish().name();
                try {
                    rows[i][9] = flats[i].getView().name();
                }catch (NullPointerException e){
                    rows[i][9] = "";
                }
                try {
                    rows[i][10] = flats[i].getTransport().name();
                }catch (NullPointerException e){
                    rows[i][10] = "";
                }
                try {
                    rows[i][11] = flats[i].getHouse().getName();
                    rows[i][12] = String.valueOf(flats[i].getHouse().getYear());
                    rows[i][13] = String.valueOf(flats[i].getHouse().getNumberOfFloors());
                    rows[i][14] = String.valueOf(flats[i].getHouse().getNumberOfFlatsOnFloor());
                    rows[i][15] = String.valueOf(flats[i].getHouse().getNumberOfLifts());
                }catch (NullPointerException e){
                    rows[i][11] = "";
                    rows[i][12] = "";
                    rows[i][13] = "";
                    rows[i][14] = "";
                    rows[i][15] = "";
                }


            }
        }

        @Override
        public JPanel getPanel() {
            return createTablePanel();
        }

        private JPanel createTablePanel(){

            createRows();


            JComboBox comboBox = new JComboBox(new Object[]{/*"NOTHING", */"user name", "id","name","coordinate x","coordinate y","creation date",
                    "area","number of rooms","furnish","view","transport","house name","house year","house number of floors","house number of flats on floor","house number of lifts"});
            comboBox.setFont(new Font("Dialog", Font.PLAIN, 15));
            comboBox.setBackground(new Color(0xFFD9ECEF, true));

            JTextField filterTextField = new JTextField(20);
            filterTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
            filterTextField.setText(resourceBundle.getString("Введите фильтр"));
            filterTextField.setFont(new Font("Dialog", Font.ITALIC, 20));
            filterTextField.setForeground(Color.GRAY);

            filterTextField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    if(filterTextField.getText().equals(resourceBundle.getString("Введите фильтр"))){
                        filterTextField.setFont(new Font("Dialog", Font.PLAIN, 20));
                        filterTextField.setForeground(Color.BLACK);
                        filterTextField.setText("");
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(filterTextField.getText().equals("")){
                        filterTextField.setText(resourceBundle.getString("Введите фильтр"));
                        filterTextField.setFont(new Font("Dialog", Font.ITALIC, 20));
                        filterTextField.setForeground(Color.GRAY);
                    }
                }
            });

            comboBox.setSelectedIndex(0);


            String columns[] = {"userName", "id", "name", "coordinate x",
                    "coordinate y",  "creationDate", "area", "numberOfRooms",
                    "furnish", "view", "transport", "house name", "house year",
                    "house numberOfFloors", "house numberOfFlatsOnFloor", "house numberOfLifts"};

            TableModel tableModel = new DefaultTableModel(rows, columns);
            JTable table = new JTable(tableModel);
            table.setPreferredSize(new Dimension(1000, 1000));
            table.setSize(new Dimension(1000, 1000));
            RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);

            Filter filter = new Filter(filterTextField, comboBox, table);

            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    if(comboBox.getSelectedIndex() != 0){
                        filter.filter();
//                    }
                }
            });


            JPanel mainPanel = new JPanel();
            JScrollPane scrollPane = new JScrollPane(table);


            JButton edit = new JButton(resourceBundle.getString("Изменить"));
            edit.setFont(new Font("Dialog", Font.PLAIN, 20));
            edit.setBackground(new Color(0xFFD9ECEF, true));

            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowCount = table.getSelectedRow();
                    String rowUserName = null;
                    if(rowCount == -1){
                        JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Выберете строку!"), resourceBundle.getString("Уведомление"), JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                    else {
                        rowUserName = (String) table.getValueAt(rowCount, 0);
                    }
                    if(rowUserName.equals(user.getLogin())){
                        JPanel abstractMainPanel = new JPanel();
                        //!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        abstractMainPanel.setPreferredSize(new Dimension(1000, 1000));
                        JPanel editTable = new GEditTableWindow(abstractMainPanel, table, transferCenter, rowCount, gInterface, userWork, processControlCenter, mainPanel, resourceBundle).getPanel();
//                        editTable.setPreferredSize(new Dimension(1000, 1000));
                                //=======
                        abstractMainPanel.add(editTable);
//                        abstractMainPanel.add(editTable);
//                        table.setVisible(false);
                        gInterface.setSpaceForInteraction(abstractMainPanel);
//                        gInterface.setGPane();
                    }
                    else {
                        JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Объект принадлежит другому пльзователю!"), resourceBundle.getString("Уведомление"), JOptionPane.OK_CANCEL_OPTION);
                    }
                }
            });

            filterTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    filter.filter();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filter.filter();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filter.filter();
                }
            });

            JButton del = new JButton(resourceBundle.getString("Удалить"));
            del.setFont(new Font("Dialog", Font.PLAIN, 20));
            del.setBackground(new Color(0xFFD9ECEF, true));
            del.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowNumber = table.getSelectedRow();
                    if(rowNumber == -1){
                        JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Необходимо выбрать строку!"), resourceBundle.getString("Уведомление"), JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                    if(table.getValueAt(rowNumber, 0).equals(user.getLogin())){
                        CommandsData commandsData = CommandsData.REMOVEBYID;
                        commandsData.setParameter((String) table.getValueAt(rowNumber, 1));

                        try {
                            userWork.new CommunicateWithServerByCommands().processCommand(commandsData, transferCenter);
                        }catch (ConnectionException connectionException){
                            JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Сервер не отвечает!"), resourceBundle.getString("Ошибка подключения"), JOptionPane.OK_CANCEL_OPTION);



//                            processControlCenter.reConnect();
//                            processControlCenter.working();

//
                            processControlCenter.reConnect();
                            return;
                        }
                        JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Объект успешно удалён!"), resourceBundle.getString("Уведомление"), JOptionPane.OK_CANCEL_OPTION);
                        update();
                    }
                    else {
                        JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Объект принадлежит другому пльзователю!"), resourceBundle.getString("Уведомление"), JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                }
            });

            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setPreferredWidth(50);
            table.getColumnModel().getColumn(3).setPreferredWidth(25);
            table.getColumnModel().getColumn(4).setPreferredWidth(25);
            table.getColumnModel().getColumn(5).setPreferredWidth(200);
            table.getColumnModel().getColumn(6).setPreferredWidth(25);
            table.getColumnModel().getColumn(7).setPreferredWidth(25);
            table.getColumnModel().getColumn(8).setPreferredWidth(25);
            table.getColumnModel().getColumn(9).setPreferredWidth(25);
            table.getColumnModel().getColumn(10).setPreferredWidth(25);
            table.getColumnModel().getColumn(11).setPreferredWidth(25);
            table.getColumnModel().getColumn(12).setPreferredWidth(25);
            table.getColumnModel().getColumn(13).setPreferredWidth(25);
            table.getColumnModel().getColumn(14).setPreferredWidth(25);
            table.getColumnModel().getColumn(15).setPreferredWidth(25);

            table.setRowSorter(sorter);
//            JScrollPane scrollPane = new JScrollPane(table);

            gInterface.setMinimalSizeForMainWindow(new Dimension(800, 700));
            gInterface.setMinimumSpaceForInteractionSize(new Dimension(800, 700));
            scrollPane.setMinimumSize(new Dimension(750, 500));
//            scrollPane.setPreferredSize(new Dimension(gInterface.getMainWindowSize().width, gInterface.getMainWindowSize().height));

//            gInterface.setMinimalSizeForMainWindow(new Dimension(1000, 1200));
//            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            JPanel controlElements = new JPanel();
            controlElements.setLayout(new GridLayout(2, 2));
            controlElements.setPreferredSize(new Dimension(500, 80));
            controlElements.setMaximumSize(new Dimension(500, 500));
            controlElements.add(edit);
            controlElements.add(del);
            controlElements.add(comboBox);
            controlElements.add(filterTextField);
            mainPanel.add(controlElements);

            mainPanel.add(scrollPane);

            mainPanel.setVisible(true);
            return mainPanel;
        }

        public void update(){
            DataBlock dataBlock;
            try {
                dataBlock = userWork.new CommunicateWithServerByCommands().processCommand(CommandsData.SHOW, transferCenter);
            }catch (ConnectionException connectionException){
                JOptionPane.showConfirmDialog(new JOptionPane(), resourceBundle.getString("Сервер не отвечает!"), resourceBundle.getString("Ошибка подключения"), JOptionPane.OK_CANCEL_OPTION);
                processControlCenter.reConnect();
                processControlCenter.working();
                return;
            }

            this.flats = dataBlock.getFlats();
//            gInterface.repaint();


//            gInterface.setSpaceForInteraction(null);
            gInterface.setSpaceForInteraction(getPanel());
//            createRows();
//            processControlCenter.
//            gInterface.repaint();
        }

        private class Filter{

            JTable table;
            JTextField textField;
            JComboBox comboBox;
            String text = new String();

            public Filter(JTextField textField, JComboBox comboBox, JTable table){
                this.table = table;
                this.textField = textField;
                this.comboBox = comboBox;
            }

            public String getRealText(){
                if(textField.getText().equals(resourceBundle.getString("Введите фильтр"))){
                    return null;
                }
                else {
                    return textField.getText();
                }
            }

            private void filter(){

                TableRowSorter tableRowSorter = (TableRowSorter<TableModel>) table.getRowSorter();

                if(!textField.getText().equals(resourceBundle.getString("Введите фильтр"))) {
                    text = new String(textField.getText());
                }

//                if(comboBox.getSelectedIndex() == 0){
//                    table.setRowSorter(null);
//                }
//                else {
//                    if(!textField.getText().equals("") & !(textField.getText().equals(null)) & comboBox.getSelectedIndex() != 0){

                    System.out.println(comboBox.getSelectedIndex() -1);
                    int index = comboBox.getSelectedIndex();
                    tableRowSorter.setRowFilter(RowFilter.regexFilter(text, index));
                    System.out.println("t");
//                    }
//                }
            }
        }
    }
}
