package CommonClasses;



import CommonClasses.ApartmentDescription.*;

import java.io.BufferedReader;

import HelpingModuls.ConsoleScanner;
import HelpingModuls.Printer;

public class FlatCreator {

    BufferedReader bufferedReader;
    Printer printer;
    ConsoleScanner scanner;

    public FlatCreator(Printer printer, ConsoleScanner consoleScanner){
        this.printer = printer;
        this.scanner = consoleScanner;
    }

    public String informationGetter(){
//        Scanner scanner = new Scanner(System.in);
//        return scanner.nextLine();
        scanner.setNeeded(true);
        try {
            synchronized (scanner){
                scanner.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return scanner.getStr();
    }

    private void pPrintln(String str){
        printer.println(str);
    }
    private void pPrint(String str){
        printer.print(str);
    }

    public String createName(){

        pPrintln("Введите имя квартиры:");
        String name = informationGetter();
        if(name.length() == 0){
            name = null;
        }
        if(name == null){
            pPrintln("У квартиры обязательно должно быть имя!");
            name = (new FlatCreator(printer, scanner)).createName();
        }
        boolean empty = true;
        for (int i =0;i<name.length();i++){
            if(name.charAt(i) != ' '){
                empty = false;
            }
        }
        if(empty){
            pPrintln("У квартиры обязательно должно быть имя!");
            name = (new FlatCreator(printer, scanner)).createName();
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
        pPrintln("Введите координату по X:");
        Double x;
        try {
            x = Double.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            x = (new FlatCreator(printer, scanner)).createXcoordinate();
        }
        if(x <= -587){
            pPrintln("Значение поля должно быть больше -587!");
            x = (new FlatCreator(printer, scanner)).createXcoordinate();
        }
        return x;
    }

    private Integer createYcoordinate(){
        pPrintln("Введите координату по Y:");
        Integer y;
        try {
            y = Integer.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            y = (new FlatCreator(printer, scanner)).createYcoordinate();
        }
        if(y > 77){
            pPrintln("Максимальное значение поля: 77!");
            y = (new FlatCreator(printer, scanner)).createYcoordinate();
        }
        return y;
    }

    public Long createArea(){
        pPrintln("Введите номер района:");
        Long area;
        try {
            area = Long.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            area = (new FlatCreator(printer, scanner)).createArea();
        }
        if(area <= 0){
            pPrintln("Значение поля должно быть больше 0!");
            area = (new FlatCreator(printer, scanner)).createArea();
        }
        return area;
    }

    public long createNumberOfRooms(){
        pPrintln("Введите количество комнат:");
        Long numberOfRooms;
        try {
            numberOfRooms = Long.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            numberOfRooms = (new FlatCreator(printer, scanner)).createNumberOfRooms();
        }
        if((numberOfRooms <= 0) | (numberOfRooms == null)){
            pPrintln("Значение поля должно быть больше 0!");
            numberOfRooms = (new FlatCreator(printer, scanner)).createNumberOfRooms();
        }
        return numberOfRooms;
    }

    public Furnish createFurnish(){
        pPrintln("Обстановка квартиры задаётся одной из следующих констант:");
        Furnish[] furnishes = Furnish.values();
        Furnish furnish;
        for (int i =0;i<furnishes.length;i++){
            pPrint(furnishes[i].name() + " ");
        }
        pPrintln("\nНужно выбрать одну из них:");
        try {
            furnish = Furnish.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            furnish = (new FlatCreator(printer, scanner)).createFurnish();
        }
        return furnish;
    }

    public CommonClasses.ApartmentDescription.View createView(){
        pPrintln("Вид из квартиры задаётся одной из следующих констант:");
        CommonClasses.ApartmentDescription.View[] views = CommonClasses.ApartmentDescription.View.values();
        CommonClasses.ApartmentDescription.View view;
        for (int i =0;i<views.length;i++){
            printer.print(views[i].name() + " ");
        }
        pPrintln("Нужно выбрать одну из них");
        String str = informationGetter();
        if(str.length() == 0){
            pPrintln("Это поле остаётся пустым");
            view = null;
        }
        else {
            try {
                view = CommonClasses.ApartmentDescription.View.valueOf(str);
            } catch (Exception e) {
                pPrintln("Некорректный ввод данных!\nВведите поле занова");
                view = (new FlatCreator(printer, scanner)).createView();
            }
        }
        return view;
    }

    public Transport createTransport(){
        pPrintln("Транспортные маршруты,проходящие у дома, задаётся одной из следующих констант:");
        Transport[] transports = Transport.values();
        Transport transport;
        for (int i =0;i<transports.length;i++){
            printer.print(transports[i].name() + " ");
        }
        pPrintln("Нужно выбрать одну из них");
        String str = informationGetter();
        if(str.length() == 0){
            pPrintln("Это поле остаётся пустым");
            transport = null;
        }
        else {
            try {
                transport = Transport.valueOf(str);
            } catch (Exception e) {
                pPrintln("Некорректный ввод данных!\nВведите поле занова");
                transport = (new FlatCreator(printer, scanner)).createTransport();
            }
        }
        return transport;
    }

    public House createHouse(){
        House house = new House();
        pPrintln("Следующее поле содержит пораметры дома, в котором находится квартира.\nЕсли это поле нужно оставить пустым - необходимо ввести 0,\nесли оно будет заполняться - 1");
        String str = informationGetter();
        printer.println(str);
        if (str.equals("0")){
            pPrintln("Это поле остаётся пустым");
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
                pPrintln("Некорректный ввод данных!\nВведите поле занова");
                house = (new FlatCreator(printer, scanner)).createHouse();
            }
        }
        return house;
    }

    private String createHouseName(){
        pPrintln("Введите имя дома:");
        String houseName;
        houseName = informationGetter();
        if(houseName.length() == 0){
            pPrintln("Это поле обязательно для заполнения!");
            houseName = (new FlatCreator(printer, scanner)).createHouseName();
        }
        return houseName;
    }

    private long createHouseYear(){
        pPrintln("Введите год постройки дома:");
        long houseYear;
        try {
            houseYear = Long.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            houseYear = (new FlatCreator(printer, scanner)).createHouseYear();
        }
        if(houseYear <= 0){
            pPrintln("Значение поля должно быть больше 0!");
            houseYear = (new FlatCreator(printer, scanner)).createHouseYear();
        }
        return houseYear;
    }

    private long createHouseNumberOfFloors(){
        pPrintln("Введите количество этажей в доме:");
        long houseNumberOfFloors;
        try {
            houseNumberOfFloors = Long.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfFloors = (new FlatCreator(printer, scanner)).createHouseYear();
        }
        if((houseNumberOfFloors <= 0) | (houseNumberOfFloors >86)){
            pPrintln("Значение поля должно быть больше 0 и меньше 87!");
            houseNumberOfFloors = (new FlatCreator(printer, scanner)).createHouseYear();
        }
        return houseNumberOfFloors;
    }

    private int createHouseNumberOfFlatsOnFloor(){
        pPrintln("Введите количество квартир на этаже:");
        int houseNumberOfFlatsOnFloor;
        try {
            houseNumberOfFlatsOnFloor = Integer.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfFlatsOnFloor = (new FlatCreator(printer, scanner)).createHouseNumberOfFlatsOnFloor();
        }
        if(houseNumberOfFlatsOnFloor <= 0){
            pPrintln("Значение поля должно быть больше 0!");
            houseNumberOfFlatsOnFloor = (new FlatCreator(printer, scanner)).createHouseNumberOfFlatsOnFloor();
        }
        return houseNumberOfFlatsOnFloor;
    }

    private int createHouseNumberOfLifts(){
        pPrintln("Введите количество лифтов в доме:");
        int houseNumberOfLifts;
        try {
            houseNumberOfLifts = Integer.valueOf(informationGetter());
        }catch (Exception e){
            pPrintln("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfLifts = (new FlatCreator(printer, scanner)).createHouseNumberOfLifts();
        }
        if(houseNumberOfLifts <= 0){
            pPrintln("Значение поля должно быть больше 0!");
            houseNumberOfLifts = (new FlatCreator(printer, scanner)).createHouseNumberOfLifts();
        }
        return houseNumberOfLifts;
    }
}
