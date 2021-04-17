package GraphicalUserInterface;

import CommonClasses.Flat;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PlaneCreator {
    public class Table implements WindowPart{

        private Flat[] flats;
        private String[][] rows;


        public Table(Flat[] flats){
            this.flats = flats;
            createRows();
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
            RowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);

            Filter filter = new Filter(filterTextField, comboBox, table);

            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    filter.filter();
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

            table.setRowSorter(sorter);
            JScrollPane scrollPane = new JScrollPane(table);

            JPanel jPanel = new JPanel();
            jPanel.add(comboBox);
            jPanel.add(filterTextField);
            jPanel.add(scrollPane);

            return jPanel;
        }

        public void update(Flat[] flats){
            this.flats = flats;
            createRows();
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
