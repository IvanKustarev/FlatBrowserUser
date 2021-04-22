package GraphicalUserInterface;

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

import HelpingModuls.ConnectionException;
import User.*;

public class PlaneCreator {
    public class Table implements WindowPart{

        private Flat[] flats;
        private String[][] rows;
        private User user;
        private TransferCenter transferCenter;
        private WorkingWithGInterface gInterface;
        UserWork userWork;
        ProcessControlCenter processControlCenter;


        public Table(Flat[] flats, User user, TransferCenter transferCenter, WorkingWithGInterface gInterface, UserWork userWork, ProcessControlCenter processControlCenter){
            this.user = user;
            this.flats = flats;
            this.transferCenter = transferCenter;
            this.gInterface = gInterface;
            this.userWork = userWork;
            this.processControlCenter =processControlCenter;
        }

        private void createRows(){
            rows = new String[flats.length][16];
            for(int i =0;i<flats.length;i++){
                rows[i][0] = flats[i].getUserName();
                rows[i][1] = String.valueOf(flats[i].getId());
                rows[i][2] = flats[i].getName();
                rows[i][3] = String.valueOf(flats[i].getCoordinates().getX());
                rows[i][4] = String.valueOf(flats[i].getCoordinates().getY());
                rows[i][5] = String.valueOf(flats[i].getCreationDate());
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


            JComboBox comboBox = new JComboBox(new Object[]{"NOTHING", "user name", "id","name","coordinate x","coordinate y","creation date",
                    "area","number of rooms","furnish","view","transport","house name","house year","house number of floors","house number of flats on floor","house number of lifts"});
            JTextField filterTextField = new JTextField(20);
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
                    filter.filter();
                }
            });

            JButton edit = new JButton("Изменить");
            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowCount = table.getSelectedRow();
                    String rowUserName = null;
                    if(rowCount == -1){
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Выберете строку!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                    else {
                        rowUserName = (String) table.getValueAt(rowCount, 0);
                    }
                    if(rowUserName.equals(user.getLogin())){
                        gInterface.setSpaceForInteraction(new GEditTableWindow(table, transferCenter, rowCount, gInterface, userWork, processControlCenter).getPanel());
                    }
                    else {
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Объект принадлежит другому пльзователю!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
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

            JButton del = new JButton("Удалить");
            del.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int rowNumber = table.getSelectedRow();
                    if(rowNumber == -1){
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Необходимо выбрать строку!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                    if(table.getValueAt(rowNumber, 0).equals(user.getLogin())){
                        CommandsData commandsData = CommandsData.REMOVEBYID;
                        commandsData.setParameter((String) table.getValueAt(rowNumber, 1));

                        try {
                            userWork.new CommunicateWithServerByCommands().processCommand(commandsData, transferCenter);
                        }catch (ConnectionException connectionException){
                            JOptionPane.showConfirmDialog(new JOptionPane(), "Сервер не отвечает!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                            processControlCenter.reConnect();
                            processControlCenter.working();
                            return;
                        }
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Объект успешно удалён!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        update();
                    }
                    else {
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Объект принадлежит другому пльзователю!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                }
            });

            table.setRowSorter(sorter);
            JScrollPane scrollPane = new JScrollPane(table);

            JPanel jPanel = new JPanel();
            jPanel.add(edit);
            jPanel.add(del);
            jPanel.add(comboBox);
            jPanel.add(filterTextField);
            jPanel.add(scrollPane);

            jPanel.setVisible(true);
            return jPanel;
        }

        public void update(){
            DataBlock dataBlock;
            try {
                dataBlock = userWork.new CommunicateWithServerByCommands().processCommand(CommandsData.SHOW, transferCenter);
            }catch (ConnectionException connectionException){
                JOptionPane.showConfirmDialog(new JOptionPane(), "Сервер не отвечает!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
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

            public Filter(JTextField textField, JComboBox comboBox, JTable table){
                this.table = table;
                this.textField = textField;
                this.comboBox = comboBox;
            }

            private void filter(){

                TableRowSorter tableRowSorter = (TableRowSorter<TableModel>) table.getRowSorter();

                if(comboBox.getSelectedIndex() == 0){
                    table.setRowSorter(null);
                }
                else {
                    tableRowSorter.setRowFilter(RowFilter.regexFilter(textField.getText(), comboBox.getSelectedIndex() -1));
                }
            }
        }
    }
}
