package User;

//import L6User.Commands.*;

import CommonClasses.*;
//import CommonClasses.DataBlock;

import java.util.Scanner;

public class UserWork {

//    String fileAddress;
    TransferCenter transferCenter;
    User mainUser;

    public UserWork(TransferCenter transferCenter){
        this.transferCenter = transferCenter;
        login();
    }

    private void login(){
        System.out.println("Необходимо авторизироваться.");
        System.out.println("Войти - 0, зарегестрироваться - 1.");
        Scanner scanner = new Scanner(System.in);
        int scan;
        try {
            scan = Integer.valueOf(scanner.nextLine());
        }catch (Exception e){
            System.out.println("Это что-то страшное... Попробуем ещё раз!");
            login();
            return;
        }
        if(scan == 0){
            entering(scanner);
        }
        else {
            if(scan == 1){
                registering(scanner);
            }
            else {
                System.out.println("Нужно выбрать один из вариантов!");
                login();
                return;
            }
        }
    }

    private void entering(Scanner scanner){
        CheckConnection checkConnection = new CheckConnection(this);
        boolean end = false;
        while (!end){
            try {
                System.out.println("Введите имя пользователя:");
//                System.out.println("ttt");
                String name = scanner.nextLine();
//                System.out.println("ttt");
                System.out.println("Введите пароль:");
                String password = scanner.nextLine();
                User user = new User(false, name, password);

                DataBlock dataBlock = new DataBlock();
                dataBlock.setUser(user);
                dataBlock.setCommandsData(CommandsData.CHECKUSER);

                checkConnection.start();
                serverAnswer = false;
                transferCenter.sendObjectToServer(dataBlock);
                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                serverAnswer = true;

                if(dataBlock.getParameter().equals("true")){
                    System.out.println(dataBlock.getPhrase());
                    mainUser = user;
                    end = true;
                }
                else {
                    System.out.println(dataBlock.getParameter());
                    System.out.println(dataBlock.getPhrase());
                    System.out.println("Пробуем занова!");
                    entering(scanner);
                    return;
                }

            }catch (Exception e){ }
        }
    }

    private void registering(Scanner scanner){
        CheckConnection checkConnection = new CheckConnection(this);
        boolean end = false;
        while (!end){
            try {
                System.out.println("Введите имя пользователя:");
                String name = scanner.nextLine();
                System.out.println("Введите пароль:");
                String password = scanner.nextLine();
                User user = new User(true, name, password);

                DataBlock dataBlock = new DataBlock();
                dataBlock.setUser(user);
                dataBlock.setCommandsData(CommandsData.CHECKUSER);

                checkConnection.start();
                serverAnswer = false;
                transferCenter.sendObjectToServer(dataBlock);
                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                serverAnswer = true;

                if(dataBlock.getParameter().equals("true")){
                    System.out.println(dataBlock.getPhrase());
                    mainUser = user;
                    end = true;
                }
                else {
                    System.out.println(dataBlock.getPhrase());
                    System.out.println("Пробуем занова!");
                    registering(scanner);
                    return;
                }

            }catch (Exception e){ }
        }
    }

    public void startCheckingCommands(){
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        CommandCenter cc = new CommandCenter();
        String command = new String();
        CommandsData commandsDataForSendToServer = null;

        System.out.println("Для просмотра списка команд необходимо ввести \"help\"");

        while (!exit){
            System.out.println("Введите команду:");
            command = scanner.nextLine();
            if(command.equals("")){
                System.out.println("Необходимо ввести команду!");
            }
            else {
                commandsDataForSendToServer = cc.whatTheCommand(command);
//                System.out.println(commandsDataForSendToServer.getCreator());
//                System.out.println(commandsDataForSendToServer.name());
                if(commandsDataForSendToServer == null){
                    System.out.println("Такой команды не существует!");
                }
                else {
                    commandsDataForSendToServer.setCreator(null);
                    if(commandsDataForSendToServer.equals(CommandsData.EXIT)){

                        exit = true;
                    }
                    else {

                        cc.packingCommandInCommandsObject(command, commandsDataForSendToServer);

                        if(commandsDataForSendToServer.isCommandWithElementParameter()){
                            commandsDataForSendToServer.setFlat(Flat.createFlat(null));
                        }
//                        System.out.println(commandsDataForSendToServer.name());

                        communicateWithServerAboutCommand(commandsDataForSendToServer);
                    }
                }
            }
            commandsDataForSendToServer = null;
        }
        System.out.println("Выход из программы...");
    }



    private void copyFieldsFromTo(CommandsData commandsData, DataBlock dataBlock){
        dataBlock.setCommandWithElementParameter(commandsData.isCommandWithElementParameter());
        dataBlock.setCreator(commandsData.getCreator());
        dataBlock.setParameter(commandsData.getParameter());
        dataBlock.setFlat(commandsData.getFlat());
        dataBlock.setBufferedReader(commandsData.getBufferedReader());
        dataBlock.setOpeningFiles(commandsData.getOpeningFiles());
        dataBlock.setCommandEnded(commandsData.isCommandEnded());
        dataBlock.setPhrase(commandsData.getPhrase());
        dataBlock.setServerNeedStringParameter(commandsData.isServerNeedStringParameter);
        dataBlock.setServerNeedElementParameter(commandsData.isServerNeedElementParameter);
        dataBlock.setUserNeedToShowFlatArr(commandsData.isUserNeedToShowFlatArr);
        dataBlock.setFlats(commandsData.getFlats());
    }

    private void copyFieldsFromTo(DataBlock dataBlock, CommandsData commandsData){
        commandsData.setCommandWithElementParameter(dataBlock.isCommandWithElementParameter());
        commandsData.setCreator(dataBlock.getCreator());
        commandsData.setParameter(dataBlock.getParameter());
        commandsData.setFlat(dataBlock.getFlat());
        commandsData.setFlats(dataBlock.getFlats());
        commandsData.setBufferedReader(dataBlock.getBufferedReader());
        commandsData.setOpeningFiles(dataBlock.getOpeningFiles());
        commandsData.setCommandEnded(dataBlock.isCommandEnded());
        commandsData.setPhrase(dataBlock.getPhrase());
        commandsData.setServerNeedStringParameter(dataBlock.isServerNeedStringParameter);
        commandsData.setServerNeedElementParameter(dataBlock.isServerNeedElementParameter);
        commandsData.setUserNeedToShowFlatArr(dataBlock.isUserNeedToShowFlatArr);
    }

    public Boolean serverAnswer = true;

    private void communicateWithServerAboutCommand(Object obj){
        CommandsData commandsData = (CommandsData) obj;
        boolean end = false;

        while (!end){
//            Нужно оптимизировать так, чтобы находился вне цикла и не возникала ошибка при запуске execute_script
            CheckConnection checkConnection = new CheckConnection(this);

            if(commandsData.getCreator() != null){
                if(commandsData.getCreator().equals(Creator.SCRIPT)){

                    DataBlock dataBlock;
                    serverAnswer = false;
                    checkConnection.start();
                    dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                    serverAnswer = true;
                    commandsData = dataBlock.getCommandsData();
                    copyFieldsFromTo(dataBlock, commandsData);

                }
                else {

                    DataBlock dataBlock = new DataBlock();
                    copyFieldsFromTo(commandsData, dataBlock);
                    dataBlock.setCommandsData(commandsData);
                    dataBlock.setUser(mainUser);

//                    System.out.println(dataBlock.getCommandsData().name());

                    transferCenter.sendObjectToServer(dataBlock);
                    serverAnswer = false;
                    checkConnection.start();
                    dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                    serverAnswer = true;
                    commandsData = dataBlock.getCommandsData();
                    copyFieldsFromTo(dataBlock, commandsData);

                }
            }
            else {

                DataBlock dataBlock = new DataBlock();
                copyFieldsFromTo(commandsData, dataBlock);
                dataBlock.setCommandsData(commandsData);
                dataBlock.setUser(mainUser);
//                System.out.println(dataBlock.getCommandsData().name());
                transferCenter.sendObjectToServer(dataBlock);
                serverAnswer = false;
                checkConnection.start();
                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                serverAnswer = true;
                commandsData = dataBlock.getCommandsData();
                copyFieldsFromTo(dataBlock, commandsData);

            }

//            System.out.println(commandsData.getCreator());



            end = commandsData.isCommandEnded();
            System.out.println(commandsData.getPhrase());

            if(commandsData.isServerNeedElementParameter){
                System.out.println("Необходимо задать квартиру.");
                commandsData.setFlat(Flat.createFlat(null));
            }

            if(commandsData.isServerNeedStringParameter){
                commandsData.setParameter((new Scanner(System.in)).nextLine());
            }

            if(commandsData.isUserNeedToShowFlatArr()){
                for(int i =0;i<commandsData.getFlats().length;i++){
                    commandsData.getFlats()[i].show();
                    System.out.println("");
                }
            }
        }

    }
}