package GraphicalUserInterface;

import CommonClasses.ApartmentDescription.*;
import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import CommonClasses.Flat;
import User.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class GEditWindow implements WindowPart{

    private JTable table;
    private TransferCenter transferCenter;
    private int row;
    private WorkingWithGInterface gInterface;
    private UserWork userWork;
    private ProcessControlCenter processControlCenter;

    public GEditWindow(JTable table, TransferCenter transferCenter, int row, WorkingWithGInterface gInterface, UserWork userWork, ProcessControlCenter processControlCenter){
        this.table = table;
        this.transferCenter = transferCenter;
        this.row = row;
        this.gInterface = gInterface;
        this.userWork = userWork;
        this.processControlCenter = processControlCenter;
    }

    @Override
    public JPanel getPanel(){
        return createEditPanel();
    }

    private JPanel createEditPanel(){

        JLabel nameLabel = new JLabel("Имя");
        JTextField nameTextField = new JTextField((String) table.getValueAt(row,2), 20);
        JLabel coordinateXLabel = new JLabel("Координата x");
        JTextField coordinateXTextField = new JTextField((String) table.getValueAt(row,3), 20);
        JLabel coordinateYLabel = new JLabel("Координата y");
        JTextField coordinateYTextField = new JTextField((String) table.getValueAt(row,4), 20);
        JLabel areaLabel = new JLabel("Расположение");
        JTextField areaTextField = new JTextField((String) table.getValueAt(row,6), 20);
        JLabel numberOfRoomsLabel = new JLabel("Количество комнат");
        JTextField numberOfRoomsTextField = new JTextField((String) table.getValueAt(row,7), 20);
        JLabel furnishLabel = new JLabel("Мебель");
        JTextField furnishTextField = new JTextField((String) table.getValueAt(row,8), 20);
        JLabel viewLabel = new JLabel("Вид");
        JTextField viewTextField = new JTextField((String) table.getValueAt(row,9), 20);
        JLabel transportLabel = new JLabel("Транспортные маршруты");
        JTextField transportTextField = new JTextField((String) table.getValueAt(row,10), 20);
        JLabel houseNameLabel = new JLabel("Имя дома");
        JTextField houseNameTextField = new JTextField((String) table.getValueAt(row,11), 20);
        JLabel houseYearLabel = new JLabel("Год пострйки дома");
        JTextField houseYearTextField = new JTextField((String) table.getValueAt(row,12), 20);
        JLabel houseNumberOfFloorsLabel = new JLabel("Количество этажей в доме");
        JTextField houseNumberOfFloorsTextField = new JTextField((String) table.getValueAt(row,13), 20);
        JLabel houseNumberOfFlatsOnFloorLabel = new JLabel("Количество квартир на одном этаже");
        JTextField houseNumberOfFlatsOnFloorTextField = new JTextField((String) table.getValueAt(row,14), 20);
        JLabel houseNumberOfLiftsLabel = new JLabel("Количество лифтов");
        JTextField houseNumberOfLiftsTextField = new JTextField((String) table.getValueAt(row,15), 20);

        JButton saveEdit = new JButton("Сохранить изменения");




        JPanel jPanel = new JPanel();
        jPanel.add(saveEdit);
        jPanel.add(nameLabel);
        jPanel.add(nameTextField);
        jPanel.add(coordinateXLabel);
        jPanel.add(coordinateXTextField);
        jPanel.add(coordinateYLabel);
        jPanel.add(coordinateYTextField);
        jPanel.add(areaLabel);
        jPanel.add(areaTextField);
        jPanel.add(numberOfRoomsLabel);
        jPanel.add(numberOfRoomsTextField);
        jPanel.add(furnishLabel);
        jPanel.add(furnishTextField);
        jPanel.add(viewLabel);
        jPanel.add(viewTextField);
        jPanel.add(transportLabel);
        jPanel.add(transportTextField);
        jPanel.add(houseNameLabel);
        jPanel.add(houseNameTextField);
        jPanel.add(houseYearLabel);
        jPanel.add(houseYearTextField);
        jPanel.add(houseNumberOfFloorsLabel);
        jPanel.add(houseNumberOfFloorsTextField);
        jPanel.add(houseNumberOfFlatsOnFloorLabel);
        jPanel.add(houseNumberOfFlatsOnFloorTextField);
        jPanel.add(houseNumberOfLiftsLabel);
        jPanel.add(houseNumberOfLiftsTextField);




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
                try {
                    Furnish.valueOf(furnishTextField.getText());
                }catch (IllegalArgumentException illegalArgumentException){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Мебель задана некорректно!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                try {
                    Transport.valueOf(transportTextField.getText());
                }catch (IllegalArgumentException illegalArgumentException){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Транспорт задан некорректно!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                try {
                    View.valueOf(viewTextField.getText());
                }catch (IllegalArgumentException illegalArgumentException){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Вид задан некорректно!", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                    return;
                }
                CommandsData commandsData = CommandsData.REMOVEBYID;
                commandsData.setParameter((String) table.getValueAt(row, 1));
                DataBlock dataBlock;

                try {
                    dataBlock = userWork.new CommunicateWithServerByCommands().processCommand(commandsData, transferCenter);
                }catch (ConnectionException connectionException){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Потеряно соединение с сервером!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                    processControlCenter.reConnect();
                    processControlCenter.working();
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
                        processControlCenter.working();
                        return;
                    }

                    JOptionPane.showConfirmDialog(new JOptionPane(), "Объект успешно обновлён", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                }

            }
        });


        return jPanel;
    }
}
