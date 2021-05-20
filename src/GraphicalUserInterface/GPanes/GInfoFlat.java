package GraphicalUserInterface.GPanes;

import CommonClasses.Flat;
import GraphicalUserInterface.GPanes.VisualSpace.GVisualSpace;
import GraphicalUserInterface.WindowPane;
import User.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ResourceBundle;

public class GInfoFlat implements WindowPane {

    private Flat flat;
    private Dimension size;
    private GVisualSpace visualSpace;
    private ResourceBundle resourceBundle;
    private JButton back;

    public GInfoFlat(Flat flat, Dimension size, GVisualSpace visualSpace, JButton back, ResourceBundle resourceBundle){
        this.flat = flat;
        this.size = size;
        this.visualSpace = visualSpace;
        this.back = back;
        this.resourceBundle =resourceBundle;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private JPanel createInfoFlatPale(){
        JPanel infoPanel = new JPanel();

        JButton edit = new JButton(resourceBundle.getString("Изменить"));
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualSpace.startEdit(flat);
            }
        });
        edit.setFont(new Font("Dialog", Font.PLAIN, 20));
        edit.setBackground(new Color(0xFFD9ECEF, true));

        JLabel userNameLabel = new JLabel(resourceBundle.getString("Имя пользователя") + flat.getUserName());
        JLabel userNameLabel2 = new JLabel(flat.getUserName());
        JLabel idLabel = new JLabel(resourceBundle.getString("id"));
        JLabel idLabel2 = new JLabel(flat.getId() +"");
        JLabel creationDateLabel = new JLabel(resourceBundle.getString("Дата создания объекта"));
        JLabel creationDateLabel2 = new JLabel(DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, Main.getLocaleByResourceName(resourceBundle.getBaseBundleName())).format(flat.getCreationDate()));
        JLabel nameLabel = new JLabel(resourceBundle.getString("Имя квартиры"));
        JLabel nameLabel2 = new JLabel(flat.getName());
        JLabel coordinateXLabel = new JLabel(resourceBundle.getString("Координата x"));
        JLabel coordinateXLabel2 = new JLabel(flat.getCoordinates().getX() +"");
        JLabel coordinateYLabel = new JLabel(resourceBundle.getString("Координата y"));
        JLabel coordinateYLabel2 = new JLabel(flat.getCoordinates().getY() +"");
        JLabel areaLabel = new JLabel(resourceBundle.getString("Расположение"));
        JLabel areaLabel2 = new JLabel(flat.getArea() + "");
        JLabel numberOfRoomsLabel = new JLabel(resourceBundle.getString("Количество комнат"));
        JLabel numberOfRoomsLabel2 = new JLabel(flat.getNumberOfRooms() + "");
        JLabel furnishLabel = new JLabel(resourceBundle.getString("Мебель"));
        JLabel furnishLabel2 = new JLabel(flat.getFurnish().name());

        JLabel viewLabel = new JLabel();
        JLabel viewLabel2 = new JLabel();
        if(flat.getView() != null){
            viewLabel = new JLabel(resourceBundle.getString("Вид"));
            viewLabel2 = new JLabel(flat.getView().name());
        }
        else {
            viewLabel = new JLabel(resourceBundle.getString("Вид"));
            viewLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
        }
        JLabel transportLabel;
        JLabel transportLabel2;
        if(flat.getView() != null){
            transportLabel = new JLabel(resourceBundle.getString("Транспортные маршруты"));
            transportLabel2 = new JLabel(flat.getTransport().name());
        }
        else {
            transportLabel = new JLabel(resourceBundle.getString("Транспортные маршруты"));
            transportLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
        }
        JLabel houseNameLabel = null;
        JLabel houseNameLabel2 = null;
        JLabel houseYearLabel;
        JLabel houseYearLabel2;
        JLabel houseNumberOfFloorsLabel;
        JLabel houseNumberOfFloorsLabel2;
        JLabel houseNumberOfFlatsOnFloorLabel;
        JLabel houseNumberOfFlatsOnFloorLabel2;
        JLabel houseNumberOfLiftsLabel;
        JLabel houseNumberOfLiftsLabel2;
        if(flat.getHouse() == null){
            houseNameLabel = new JLabel(resourceBundle.getString("Имя дома"));
            houseNameLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
            houseYearLabel = new JLabel(resourceBundle.getString("Год пострйки дома"));
            houseYearLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
            houseNumberOfFloorsLabel = new JLabel(resourceBundle.getString("Количество этажей в доме"));
            houseNumberOfFloorsLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
            houseNumberOfFlatsOnFloorLabel = new JLabel(resourceBundle.getString("Количество квартир на одном этаже"));
            houseNumberOfFlatsOnFloorLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
            houseNumberOfLiftsLabel = new JLabel(resourceBundle.getString("Количество лифтов"));
            houseNumberOfLiftsLabel2 = new JLabel(resourceBundle.getString("поле пустое"));
        }
        else {
            houseNameLabel = new JLabel(resourceBundle.getString("Имя дома"));
            houseNameLabel2 = new JLabel(flat.getHouse().getName());
            houseYearLabel = new JLabel(resourceBundle.getString("Год пострйки дома"));
            houseYearLabel2 = new JLabel(flat.getHouse().getYear() + "");
            houseNumberOfFloorsLabel = new JLabel(resourceBundle.getString("Количество этажей в доме"));
            houseNumberOfFloorsLabel2 = new JLabel(flat.getHouse().getNumberOfFloors() + "");
            houseNumberOfFlatsOnFloorLabel = new JLabel(resourceBundle.getString("Количество квартир на одном этаже"));
            houseNumberOfFlatsOnFloorLabel2 = new JLabel(flat.getHouse().getNumberOfFlatsOnFloor() + "");
            houseNumberOfLiftsLabel = new JLabel(resourceBundle.getString("Количество лифтов"));
            houseNumberOfLiftsLabel2 = new JLabel(flat.getHouse().getNumberOfLifts() + "");
        }

        JPanel lables = new JPanel(new GridLayout(17,2));
        lables.setPreferredSize(new Dimension(500, 680));

        lables.add(back);
        lables.add(edit);
        lables.add(userNameLabel);
        lables.add(userNameLabel2);
        lables.add(idLabel);
        lables.add(idLabel2);
        lables.add(creationDateLabel);
        lables.add(creationDateLabel2);
        lables.add(nameLabel);
        lables.add(nameLabel2);
        lables.add(coordinateXLabel);
        lables.add(coordinateXLabel2);
        lables.add(coordinateYLabel);
        lables.add(coordinateYLabel2);
        lables.add(areaLabel);
        lables.add(areaLabel2);
        lables.add(numberOfRoomsLabel);
        lables.add(numberOfRoomsLabel2);
        lables.add(furnishLabel);
        lables.add(furnishLabel2);
        lables.add(viewLabel);
        lables.add(viewLabel2);
        lables.add(transportLabel);
        lables.add(transportLabel2);
        lables.add(houseNameLabel);
        lables.add(houseNameLabel2);
        lables.add(houseYearLabel);
        lables.add(houseYearLabel2);
        lables.add(houseNumberOfFloorsLabel);
        lables.add(houseNumberOfFloorsLabel2);
        lables.add(houseNumberOfFlatsOnFloorLabel);
        lables.add(houseNumberOfFlatsOnFloorLabel2);
        lables.add(houseNumberOfLiftsLabel);
        lables.add(houseNumberOfLiftsLabel2);

//        infoPanel.add(edit);
        infoPanel.add(lables);

        return infoPanel;
    }



    @Override
    public JPanel getPanel() {
        return createInfoFlatPale();
    }
}
