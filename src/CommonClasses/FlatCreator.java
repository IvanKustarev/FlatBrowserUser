package CommonClasses;



import CommonClasses.ApartmentDescription.*;

import java.io.BufferedReader;
import java.util.Scanner;
import User.Printer;

public class FlatCreator {

    BufferedReader bufferedReader;
//    CommonClasses.CommandsData commandsData;

    public FlatCreator(){
//        this.bufferedReader = commandsData.getBufferedReader();
//        this.commandsData = commandsData;
    }

    public String informationGetter(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public String createName(){

        Printer.println("Введите имя квартиры:");
        String name = informationGetter();
        if(name.length() == 0){
            name = null;
        }
        if(name == null){
            Printer.println("У квартиры обязательно должно быть имя!");
            name = (new FlatCreator()).createName();
        }
        boolean empty = true;
        for (int i =0;i<name.length();i++){
            if(name.charAt(i) != ' '){
                empty = false;
            }
        }
        if(empty){
            Printer.println("У квартиры обязательно должно быть имя!");
            name = (new FlatCreator()).createName();
        }
        return name;
    }

    public Coordinates createCoordinates(){
        Coordinates coordinates = new Coordinates();
        coordinates.setX(createXcoordinate());
        coordinates.setY(createYcoordinate());
        return coordinates;
    }

    private Double createXcoordinate(){
        Printer.println("Введите координату по X:");
        Double x;
        try {
            x = Double.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            x = (new FlatCreator()).createXcoordinate();
        }
        if(x <= -587){
            Printer.println("Значение поля должно быть больше -587!");
            x = (new FlatCreator()).createXcoordinate();
        }
        return x;
    }

    private Integer createYcoordinate(){
        Printer.println("Введите координату по Y:");
        Integer y;
        try {
            y = Integer.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            y = (new FlatCreator()).createYcoordinate();
        }
        if(y > 77){
            Printer.println("Максимальное значение поля: 77!");
            y = (new FlatCreator()).createYcoordinate();
        }
        return y;
    }

    public Long createArea(){
        Printer.println("Введите номер района:");
        Long area;
        try {
            area = Long.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            area = (new FlatCreator()).createArea();
        }
        if(area <= 0){
            Printer.println("Значение поля должно быть больше 0!");
            area = (new FlatCreator()).createArea();
        }
        return area;
    }

    public long createNumberOfRooms(){
        Printer.println("Введите количество комнат:");
        Long numberOfRooms;
        try {
            numberOfRooms = Long.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            numberOfRooms = (new FlatCreator()).createNumberOfRooms();
        }
        if((numberOfRooms <= 0) | (numberOfRooms == null)){
            Printer.println("Значение поля должно быть больше 0!");
            numberOfRooms = (new FlatCreator()).createNumberOfRooms();
        }
        return numberOfRooms;
    }

    public Furnish createFurnish(){
        Printer.println("Обстановка квартиры задаётся одной из следующих констант:");
        Furnish[] furnishes = Furnish.values();
        Furnish furnish;
        for (int i =0;i<furnishes.length;i++){
            Printer.print(furnishes[i].name() + " ");
        }
        Printer.println("\nНужно выбрать одну из них:");
        try {
            furnish = Furnish.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            furnish = (new FlatCreator()).createFurnish();
        }
        return furnish;
    }

    public CommonClasses.ApartmentDescription.View createView(){
        Printer.println("Вид из квартиры задаётся одной из следующих констант:");
        CommonClasses.ApartmentDescription.View[] views = CommonClasses.ApartmentDescription.View.values();
        CommonClasses.ApartmentDescription.View view;
        for (int i =0;i<views.length;i++){
            Printer.print(views[i].name() + " ");
        }
        Printer.println("Нужно выбрать одну из них");
        String str = informationGetter();
        if(str.length() == 0){
            Printer.println("Это поле остаётся пустым");
            view = null;
        }
        else {
            try {
                view = CommonClasses.ApartmentDescription.View.valueOf(str);
            } catch (Exception e) {
                Printer.println("Некорректный ввод данных!\nВведите поле занова");
                view = (new FlatCreator()).createView();
            }
        }
        return view;
    }

    public Transport createTransport(){
        Printer.println("Транспортные маршруты,проходящие у дома, задаётся одной из следующих констант:");
        Transport[] transports = Transport.values();
        Transport transport;
        for (int i =0;i<transports.length;i++){
            Printer.print(transports[i].name() + " ");
        }
        Printer.println("Нужно выбрать одну из них");
        String str = informationGetter();
        if(str.length() == 0){
            Printer.println("Это поле остаётся пустым");
            transport = null;
        }
        else {
            try {
                transport = Transport.valueOf(str);
            } catch (Exception e) {
                Printer.println("Некорректный ввод данных!\nВведите поле занова");
                transport = (new FlatCreator()).createTransport();
            }
        }
        return transport;
    }

    public House createHouse(){
        House house = new House();
        Printer.println("Следующее поле содержит пораметры дома, в котором находится квартира.\nЕсли это поле нужно оставить пустым - необходимо ввести 0,\nесли оно будет заполняться - 1");
        String str = informationGetter();
        Printer.println(str);
        if (str.equals("0")){
            Printer.println("Это поле остаётся пустым");
            house = null;
        }
        else {
            if(str.equals("1")){
                house.setName(createHouseName());
                house.setYear(createHouseYear());
                house.setNumberOfFloors(createHouseNumberOfFloors());
                house.setNumberOfFlatsOnFloor(createHouseNumberOfFlatsOnFloor());
                house.setNumberOfLifts(createHouseNumberOfLifts());
            }
            else {
                Printer.println("Некорректный ввод данных!\nВведите поле занова");
                house = (new FlatCreator()).createHouse();
            }
        }
        return house;
    }

    private String createHouseName(){
        Printer.println("Введите имя дома:");
        String houseName;
        houseName = informationGetter();
        if(houseName.length() == 0){
            Printer.println("Это поле обязательно для заполнения!");
            houseName = (new FlatCreator()).createHouseName();
        }
        return houseName;
    }

    private long createHouseYear(){
        Printer.println("Введите год постройки дома:");
        long houseYear;
        try {
            houseYear = Long.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            houseYear = (new FlatCreator()).createHouseYear();
        }
        if(houseYear <= 0){
            Printer.println("Значение поля должно быть больше 0!");
            houseYear = (new FlatCreator()).createHouseYear();
        }
        return houseYear;
    }

    private long createHouseNumberOfFloors(){
        Printer.println("Введите количество этажей в доме:");
        long houseNumberOfFloors;
        try {
            houseNumberOfFloors = Long.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfFloors = (new FlatCreator()).createHouseYear();
        }
        if((houseNumberOfFloors <= 0) | (houseNumberOfFloors >86)){
            Printer.println("Значение поля должно быть больше 0 и меньше 87!");
            houseNumberOfFloors = (new FlatCreator()).createHouseYear();
        }
        return houseNumberOfFloors;
    }

    private int createHouseNumberOfFlatsOnFloor(){
        Printer.println("Введите количество квартир на этаже:");
        int houseNumberOfFlatsOnFloor;
        try {
            houseNumberOfFlatsOnFloor = Integer.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfFlatsOnFloor = (new FlatCreator()).createHouseNumberOfFlatsOnFloor();
        }
        if(houseNumberOfFlatsOnFloor <= 0){
            Printer.println("Значение поля должно быть больше 0!");
            houseNumberOfFlatsOnFloor = (new FlatCreator()).createHouseNumberOfFlatsOnFloor();
        }
        return houseNumberOfFlatsOnFloor;
    }

    private int createHouseNumberOfLifts(){
        Printer.println("Введите количество лифтов в доме:");
        int houseNumberOfLifts;
        try {
            houseNumberOfLifts = Integer.valueOf(informationGetter());
        }catch (Exception e){
            Printer.println("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfLifts = (new FlatCreator()).createHouseNumberOfLifts();
        }
        if(houseNumberOfLifts <= 0){
            Printer.println("Значение поля должно быть больше 0!");
            houseNumberOfLifts = (new FlatCreator()).createHouseNumberOfLifts();
        }
        return houseNumberOfLifts;
    }
}
