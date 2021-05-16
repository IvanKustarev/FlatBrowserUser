package GraphicalUserInterface.GPanes;

import CommonClasses.Flat;
import GraphicalUserInterface.GPanes.VisualSpace.GVisualSpace;
import GraphicalUserInterface.WindowPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class GInfoFlat implements WindowPane {

    private Flat flat;
    private Dimension size;
    private GVisualSpace visualSpace;
    private ResourceBundle resourceBundle;

    public GInfoFlat(Flat flat, Dimension size, GVisualSpace visualSpace){
        this.flat = flat;
        this.size = size;
        this.visualSpace = visualSpace;
    }

    @Override
    public void setLocale(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    private JPanel createInfoFlatPale(){
        JPanel infoPanel = new JPanel();

        JButton edit = new JButton("Редактировать");
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualSpace.startEdit(flat);
            }
        });

        JLabel userNameLabel = new JLabel("Имя пользователя: " + flat.getUserName());
        JLabel idLabel = new JLabel("id: " + flat.getId());
        JLabel creationDateLabel = new JLabel("Дата создания объекта: " + flat.getCreationDate());
        JLabel nameLabel = new JLabel("Имя квартиры: " + flat.getName());
        JLabel coordinateXLabel = new JLabel("Координата x: " + flat.getCoordinates().getX());
        JLabel coordinateYLabel = new JLabel("Координата y: " + flat.getCoordinates().getY());
        JLabel areaLabel = new JLabel("Расположение: " + flat.getArea());
        JLabel numberOfRoomsLabel = new JLabel("Количество комнат: " + flat.getNumberOfRooms());
        JLabel furnishLabel = new JLabel("Мебель: " + flat.getFurnish().name());

        JLabel viewLabel = new JLabel();
        if(flat.getView() != null){
            viewLabel = new JLabel("Вид: " + flat.getView().name());
        }
        else {
            viewLabel = new JLabel("Вид: поле пустое");
        }
        JLabel transportLabel;
        if(flat.getView() != null){
            transportLabel = new JLabel("Транспортные маршруты: " + flat.getTransport().name());
        }
        else {
            transportLabel = new JLabel("Транспортные маршруты: поле пустое");
        }
        JLabel houseNameLabel = null;
        JLabel houseYearLabel;
        JLabel houseNumberOfFloorsLabel;
        JLabel houseNumberOfFlatsOnFloorLabel;
        JLabel houseNumberOfLiftsLabel;
        if(flat.getHouse() == null){
            houseNameLabel = new JLabel("Имя дома: поле пустое");
            houseYearLabel = new JLabel("Год пострйки дома: поле пустое");
            houseNumberOfFloorsLabel = new JLabel("Количество этажей в доме: поле пустое");
            houseNumberOfFlatsOnFloorLabel = new JLabel("Количество квартир на одном этаже: поле пустое");
            houseNumberOfLiftsLabel = new JLabel("Количество лифтов: поле пустое");
        }
        else {
            houseNameLabel = new JLabel("Имя дома: " + flat.getHouse().getName());
            houseYearLabel = new JLabel("Год пострйки дома: " + flat.getHouse().getYear());
            houseNumberOfFloorsLabel = new JLabel("Количество этажей в доме: " + flat.getHouse().getNumberOfFloors());
            houseNumberOfFlatsOnFloorLabel = new JLabel("Количество квартир на одном этаже: " + flat.getHouse().getNumberOfFlatsOnFloor());
            houseNumberOfLiftsLabel = new JLabel("Количество лифтов: " + flat.getHouse().getNumberOfLifts());
        }

        JPanel lables = new JPanel(new GridLayout(16,1));
        lables.add(userNameLabel);
        lables.add(idLabel);
        lables.add(creationDateLabel);
        lables.add(nameLabel);
        lables.add(coordinateXLabel);
        lables.add(coordinateYLabel);
        lables.add(areaLabel);
        lables.add(numberOfRoomsLabel);
        lables.add(furnishLabel);
        lables.add(viewLabel);
        lables.add(transportLabel);
        lables.add(houseNameLabel);
        lables.add(houseYearLabel);
        lables.add(houseNumberOfFloorsLabel);
        lables.add(houseNumberOfFlatsOnFloorLabel);
        lables.add(houseNumberOfLiftsLabel);

        infoPanel.add(edit);
        infoPanel.add(lables);

        return infoPanel;
    }



    @Override
    public JPanel getPanel() {
        return createInfoFlatPale();
    }
}
