package User;

//import L6User.Commands.*;

import CommonClasses.CommandsData;
//import CommonClasses.DataBlock;
import CommonClasses.Creator;
import CommonClasses.DataBlock;
import CommonClasses.Flat;

import java.util.Scanner;

public class User {

//    String fileAddress;
    TransferCenter transferCenter;

    public User(TransferCenter transferCenter){
//        transferCenter = new TransferCenter();
        this.transferCenter = transferCenter;
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
                if(commandsDataForSendToServer == null){
                    System.out.println("Такой команды не существует!");
                }
                else {
                    if(commandsDataForSendToServer.equals(CommandsData.EXIT)){
                        exit = true;
                    }
                    else {
                        cc.packingCommandInCommandsObject(command, commandsDataForSendToServer);
//                        System.out.println(commandsDataForSendToServer.name());
                        if(commandsDataForSendToServer.isCommandWithElementParameter()){
                            commandsDataForSendToServer.setFlat(Flat.createFlat(null));
                        }
//                        commandsDataForSendToServer.getFlat().show();
//                        commandsDataForSendToServer.setParameter("aaaa");
//                        System.out.println(commandsDataForSendToServer.name());
//                        DataBlock dataBlock = new DataBlock();
//                        copyFieldsFromTo(commandsDataForSendToServer, dataBlock);
//                        dataBlock.setCommandsData(commandsDataForSendToServer);
////                        transferCenter.sendObjectToServer(commandsDataForSendToServer);
//                        transferCenter.sendObjectToServer(dataBlock);
////                        CommandsData commandsDataAnswer = (CommandsData) transferCenter.receiveObjectFromServer();
//                        dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
//                        CommandsData commandsDataAnswer = dataBlock.getCommandsData();
//                        copyFieldsFromTo(dataBlock, commandsDataAnswer);
                        communicateWithServerAboutCommand(commandsDataForSendToServer);
//                        System.out.println(transferCenter.receiveObjectFromServer().getClass().getName());
//                        DataBlock dataBlock = new DataBlock();
//                        dataBlock.setFlat(commandsDataForSendToServer.getFlat());
//                        dataBlock.setParameter(commandsDataForSendToServer.getParameter());

//                        System.out.println("ttt");
//                        transferCenter.checkConnection();
//                        System.out.println("ttt");
//                        transferCenter.sendObjectToServer(dataBlock);
//                        processingMessageFromServer((DataBlock) transferCenter.receiveObjectFromServer());

//                        DataBlock dataBlock = (DataBlock)transferCenter.receiveObjectFromServer();
//                        System.out.println(dataBlock.phrase);

//                        boolean commandCompleted = false;
//                        while (!commandCompleted){
//                            DataBlock dataBlock = transferCenter.reciveAnswerFromServer();
//                            DataBlock answerToServer = new DataBlock();
//                            commandCompleted = dataBlock.StartProcessingCommand(answerToServer);
//
//                            if(!commandCompleted){
//                                transferCenter.sendAnswerToServer(dataBlock);
//                            }
//                        }
                    }
                }
            }
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
        CheckConnection checkConnection = new CheckConnection(this);

        while (!end){

            if(!(commandsData.getCreator() == null)){
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
//                        transferCenter.sendObjectToServer(commandsDataForSendToServer);
                    transferCenter.sendObjectToServer(dataBlock);
//                        CommandsData commandsDataAnswer = (CommandsData) transferCenter.receiveObjectFromServer();
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
//                        transferCenter.sendObjectToServer(commandsDataForSendToServer);
                transferCenter.sendObjectToServer(dataBlock);
//                        CommandsData commandsData = (CommandsData) transferCenter.receiveObjectFromServer();
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





//
//    private void processingMessageFromServer(DataBlock dataBlock){
//        if (dataBlock.getPhrase() != null) {
//            print(dataBlock.getPhrase());
//        }
//        if (dataBlock.isUserNeedToShowFlatArr) {
//            Flat[] flats = dataBlock.getFlats();
//            for (Flat flat : flats) {
//                flat.show();
//            }
//        }
//        if(dataBlock.isAllRight()){
//            return;
//        }
//        else {
//            DataBlock answerForServer = new DataBlock();
//            if(dataBlock.isServerNeedStringParameter()){
//                answerForServer.setParameter(scan());
//            }
//            if(dataBlock.isServerNeedElementParameter){
//                answerForServer.setFlat(Flat.createFlat(null));
//            }
//            transferCenter.sendObjectToServer(answerForServer);
//            processingMessageFromServer((DataBlock) transferCenter.receiveObjectFromServer());
//        }
//    }
//
//    public static void print(Object object){
//        System.out.println(object);
//    }
//
//    public static String scan(){
//        Scanner scanner = new Scanner(System.in);
//        return  scanner.nextLine();
//    }
//
//    private String gettingNormalFormat(String str){ //меняет "_a" на "A" и тп и переводит первую букву в верхний регистр
//
//        String firstCarInCommand = str.substring(0,1);  //
//        firstCarInCommand = firstCarInCommand.toUpperCase();                    // переводит первую букву в верхний регистр
//        str = firstCarInCommand + str.substring(1);
//
//        boolean normalFormat = false;
//        while (!normalFormat){
//            normalFormat = true;
//            int ind;
//            if(str.contains("_")){
//                ind = str.indexOf("_");
//                str = str.substring(0, ind) + str.substring(ind+1, ind+2).toUpperCase() + str.substring(ind+2);
//                normalFormat = false;
//            }
//        }
//        return str;
//    }
//
//    public String printAndRead(String parameter){
//        System.out.println(parameter);
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
//        return str;
//    }
//
}