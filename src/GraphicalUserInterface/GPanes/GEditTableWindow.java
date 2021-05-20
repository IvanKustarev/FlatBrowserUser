package GraphicalUserInterface.GPanes;

import CommonClasses.ApartmentDescription.*;
import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import CommonClasses.Flat;
import GraphicalUserInterface.GInterface;
import GraphicalUserInterface.WindowPane;
import HelpingModuls.ConnectionException;
import User.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class GEditTableWindow implements WindowPane {

    private JTable table;
    private TransferCenter transferCenter;
    private int row;
    private GInterface gInterface;
    private UserWork userWork;
    private ProcessControlCenter processControlCenter;
    private ResourceBundle resourceBundle;
//    private JPanel abstractMainPanel;
    private JPanel generalPanel;
    private JPanel abstractMainPanel;

    public GEditTableWindow(JPanel abstractMainPanel, JTable table, TransferCenter transferCenter, int row, GInterface gInterface, UserWork userWork, ProcessControlCenter processControlCenter, JPanel mainPanel){
        this.table = table;
        this.transferCenter = transferCenter;
        this.row = row;
        this.gInterface = gInterface;
        this.userWork = userWork;
        this.processControlCenter = processControlCenter;
        this.abstractMainPanel = abstractMainPanel;

//        this.abstractMainPanel = abstractMainPanel;
        this.generalPanel = mainPanel;
    }

    @Override
    public JPanel getPanel(){
        return createEditPanel();
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private JPanel createEditPanel(){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton back = new JButton("НАЗАД");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                mainPanel.setVisible(false);
//                table.setVisible(true);
//                table.setPreferredSize(new Dimension(1000, 1000));
//                table.setSize(new Dimension(1000, 1000));
//                abstractMainPanel.remove(table);
//                abstractMainPanel.add(table);
//                abstractMainPanel.remove(mainPanel);
//                abstractMainPanel.setVisible(false);

                generalPanel.setVisible(true);
                abstractMainPanel.setVisible(false);
                gInterface.setSpaceForInteraction(generalPanel);
            }
        });

        back.setFont(new Font("Dialog", Font.PLAIN, 20));
        back.setBackground(new Color(0xFFD9ECEF, true));
        mainPanel.add(back);


        JPanel abstractFields = new JPanel();
        abstractFields.add(back);
        abstractFields.setLayout(new GridLayout(14, 2));
        abstractFields.setPreferredSize(new Dimension(500, 560));


        JLabel nameLabel = new JLabel("Имя");
        nameLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField nameTextField = new JTextField((String) table.getValueAt(row,2), 20);
        nameTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel coordinateXLabel = new JLabel("Координата x");
        coordinateXLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField coordinateXTextField = new JTextField((String) table.getValueAt(row,3), 20);
        coordinateXTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel coordinateYLabel = new JLabel("Координата y");
        coordinateYLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField coordinateYTextField = new JTextField((String) table.getValueAt(row,4), 20);
        coordinateYTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel areaLabel = new JLabel("Расположение");
        areaLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField areaTextField = new JTextField((String) table.getValueAt(row,6), 20);
        areaTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel numberOfRoomsLabel = new JLabel("Количество комнат");
        numberOfRoomsLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField numberOfRoomsTextField = new JTextField((String) table.getValueAt(row,7), 20);
        numberOfRoomsTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel furnishLabel = new JLabel("Мебель");
        furnishLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField furnishTextField = new JTextField((String) table.getValueAt(row,8), 20);
        furnishTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel viewLabel = new JLabel("Вид");
        viewLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField viewTextField = new JTextField((String) table.getValueAt(row,9), 20);
        viewTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel transportLabel = new JLabel("Транспортные маршруты");
        transportLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField transportTextField = new JTextField((String) table.getValueAt(row,10), 20);
        transportTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel houseNameLabel = new JLabel("Имя дома");
        houseNameLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField houseNameTextField = new JTextField((String) table.getValueAt(row,11), 20);
        houseNameTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel houseYearLabel = new JLabel("Год пострйки дома");
        houseYearLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField houseYearTextField = new JTextField((String) table.getValueAt(row,12), 20);
        houseYearTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel houseNumberOfFloorsLabel = new JLabel("Количество этажей в доме");
        houseNumberOfFloorsLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField houseNumberOfFloorsTextField = new JTextField((String) table.getValueAt(row,13), 20);
        houseNumberOfFloorsTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel houseNumberOfFlatsOnFloorLabel = new JLabel("Количество квартир на одном этаже");
        houseNumberOfFlatsOnFloorLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField houseNumberOfFlatsOnFloorTextField = new JTextField((String) table.getValueAt(row,14), 20);
        houseNumberOfFlatsOnFloorTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JLabel houseNumberOfLiftsLabel = new JLabel("Количество лифтов");
        houseNumberOfLiftsLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

        JTextField houseNumberOfLiftsTextField = new JTextField((String) table.getValueAt(row,15), 20);
        houseNumberOfLiftsTextField.setFont(new Font("Dialog", Font.PLAIN, 15));

        JButton saveEdit = new JButton("Сохранить изменения");
        saveEdit.setFont(new Font("Dialog", Font.PLAIN, 20));
        saveEdit.setBackground(new Color(0xFFD9ECEF, true));


        abstractFields.add(saveEdit);
        abstractFields.add(nameLabel);
        abstractFields.add(nameTextField);
        abstractFields.add(coordinateXLabel);
        abstractFields.add(coordinateXTextField);
        abstractFields.add(coordinateYLabel);
        abstractFields.add(coordinateYTextField);
        abstractFields.add(areaLabel);
        abstractFields.add(areaTextField);
        abstractFields.add(numberOfRoomsLabel);
        abstractFields.add(numberOfRoomsTextField);
        abstractFields.add(furnishLabel);
        abstractFields.add(furnishTextField);
        abstractFields.add(viewLabel);
        abstractFields.add(viewTextField);
        abstractFields.add(transportLabel);
        abstractFields.add(transportTextField);
        abstractFields.add(houseNameLabel);
        abstractFields.add(houseNameTextField);
        abstractFields.add(houseYearLabel);
        abstractFields.add(houseYearTextField);
        abstractFields.add(houseNumberOfFloorsLabel);
        abstractFields.add(houseNumberOfFloorsTextField);
        abstractFields.add(houseNumberOfFlatsOnFloorLabel);
        abstractFields.add(houseNumberOfFlatsOnFloorTextField);
        abstractFields.add(houseNumberOfLiftsLabel);
        abstractFields.add(houseNumberOfLiftsTextField);

        mainPanel.add(abstractFields);




        saveEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(nameTextField.getText().equals("")){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "У квартиры обязательно должно быть имя", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if(coordinateXTextField.getText().equals("")){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "У квартиры обязательно должна быть координата по X", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if(coordinateYTextField.getText().equals("")){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "У квартиры обязательно должно быть координата по Y", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if(areaTextField.getText().equals("")){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "У квартиры обязательно должно быть расположение", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if(numberOfRoomsTextField.getText().equals("")){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "У квартиры обязательно должно быть количество комнат", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if(furnishTextField.getText().equals("")){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "У квартиры обязательно должна быть мебель", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if((!houseNameTextField.getText().equals("") | !houseYearTextField.getText().equals("") |
                        !houseNumberOfFloorsTextField.getText().equals("") | !houseNumberOfFlatsOnFloorTextField.getText().equals("") | !houseNumberOfLiftsTextField.getText().equals("")) &
                        (houseNameTextField.getText().equals("") | houseYearTextField.getText().equals("") | houseNumberOfFloorsTextField.getText().equals("") |
                                houseNumberOfFlatsOnFloorTextField.getText().equals("") | houseNumberOfLiftsTextField.getText().equals(""))){

                    JOptionPane.showConfirmDialog(new JOptionPane(), "Все поля связанные с домом либо пустые, либо заполненные", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                if(!furnishTextField.getText().equals("")) {
                    try {
                        Furnish.valueOf(furnishTextField.getText());
                    } catch (IllegalArgumentException illegalArgumentException) {
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Мебель задана некорректно!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                }
                if(!transportTextField.getText().equals("")) {
                    try {
                        Transport.valueOf(transportTextField.getText());
                    } catch (IllegalArgumentException illegalArgumentException) {
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Транспорт задан некорректно!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                }
                if(!viewTextField.getText().equals("")) {
                    try {
                        View.valueOf(viewTextField.getText());
                    } catch (IllegalArgumentException illegalArgumentException) {
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Вид задан некорректно!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                }
                CommandsData commandsData = CommandsData.REMOVEBYID;
                commandsData.setParameter((String) table.getValueAt(row, 1));
                DataBlock dataBlock;

                try {
                    dataBlock = userWork.new CommunicateWithServerByCommands().processCommand(commandsData, transferCenter);
                }catch (ConnectionException connectionException){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Потеряно соединение с сервером!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                    processControlCenter.reConnect();
//                    processControlCenter.reConnect();
//                    processControlCenter.working();
                    return;
                }

                if(dataBlock.getPhrase().equals("Удаление прошло успешно.")){
//                    System.out.println("Удаление прошло успешно.");
                    commandsData = CommandsData.ADD;


                    Flat flat = new Flat();
                    flat.setUserName(userWork.getMainUser().getLogin());
                    flat.setId(Long.valueOf((String) table.getValueAt(row, 1)));
                    flat.setName(nameTextField.getText());
                    Coordinates coordinates = new Coordinates();
                    coordinates.setX(Double.valueOf(coordinateXTextField.getText()));
                    coordinates.setY(Integer.valueOf(coordinateYTextField.getText()));
                    flat.setCoordinates(coordinates);
                    flat.setCreationDate(new Date());
                    flat.setArea(Long.valueOf(areaTextField.getText()));
                    flat.setNumberOfRooms(Long.valueOf(numberOfRoomsTextField.getText()));
                    flat.setFurnish(Furnish.valueOf(furnishTextField.getText()));
                    if(viewTextField.getText().equals("")){
                        flat.setView(null);
                    }
                    else {
                        flat.setView(View.valueOf(viewTextField.getText()));
                    }
                    if(transportTextField.getText().equals("")){
                        flat.setTransport(null);
                    }
                    else {
                        flat.setTransport(Transport.valueOf(transportTextField.getText()));
                    }
                    House house = new House();
                    if(!houseNameTextField.getText().equals("")){
                        house.setName(houseNameTextField.getText());
                        house.setYear(Long.valueOf(houseYearTextField.getText()));
                        house.setNumberOfFloors(Long.valueOf(houseNumberOfFloorsTextField.getText()));
                        house.setNumberOfFlatsOnFloor(Integer.valueOf(houseNumberOfFlatsOnFloorTextField.getText()));
                        house.setNumberOfLifts(Long.valueOf(houseNumberOfLiftsTextField.getText()));
                    }
                    flat.setHouse(house);
//                    System.out.println("ttt");
//                    System.out.println(flat.show());
                    commandsData.setFlat(flat);


                    try {
                        userWork.new CommunicateWithServerByCommands().processCommand(commandsData, transferCenter);
                    }catch (ConnectionException connectionException){
                        JOptionPane.showConfirmDialog(new JOptionPane(), "Потеряно соединение с сервером!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                        processControlCenter.reConnect();
//                        processControlCenter.working();
                        return;
                    }

                    JOptionPane.showConfirmDialog(new JOptionPane(), "Объект успешно обновлён", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                }

            }
        });


        return mainPanel;
    }
}
