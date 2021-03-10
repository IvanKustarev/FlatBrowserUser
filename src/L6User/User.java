package L6User;

//import L6User.Commands.*;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import CommonClasses.Flat;

import java.util.Scanner;

public class User {

//    String fileAddress;


    public void startCheckingCommands(){
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        CommandCenter cc = new CommandCenter();
        String command = new String();
        CommandsData commandsDataForSendToServer = null;
        TransferCenter transferCenter = new TransferCenter();

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
                        if(commandsDataForSendToServer.isComandWithElementParameter()){
                            commandsDataForSendToServer.setFlat(Flat.createFlat(null));
                        }
//                        transferCenter.sendObjectToServer(commandsDataForSendToServer);


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

//    public boolean processingTheCommand(String command, CommandCenter cc) {
//
//        boolean startedRecurs = false;
//
//        if (command.contains("exit")) {
//            return true; //конец программы
//        }
//        else {
//            boolean commandWasFind = false;
//
//            if(!command.equals("")){
//                command = gettingNormalFormat(command);
//            }
//
//            if(command.contains("AddIfMin")){
//                cc.addIfMin();
//                commandWasFind = true;
//            }
//            else {
//                if(command.contains("Add")){
//                    cc.add();
//                    commandWasFind = true;
//                }
//            }
//            if(command.contains("Clear")){
//                cc.clear();
//                commandWasFind = true;
//            }
//            if(command.contains("Help")){
//                cc.help();
//                commandWasFind = true;
//            }
//            if(command.contains("Info")){
//                cc.info();
//                commandWasFind = true;
//            }
//            if(command.contains("PrintFieldAscendingNumberOfRooms")){
//                cc.printFieldAscendingNumberOfRooms();
//                commandWasFind = true;
//            }
//            if(command.contains("RemoveHead")){
//                cc.removeHead();
//                commandWasFind = true;
//            }
//            if(command.contains("RemoveLower")){
//                cc.removeLower();
//                commandWasFind = true;
//            }
//            if(command.contains("Save")){
//                cc.save();
//                commandWasFind = true;
//            }
//            if(command.contains("Show")){
//                cc.show();
//                commandWasFind = true;
//            }
//            if(command.contains("SumOfNumberOfRooms")){
//                cc.sumOfNumberOfRooms();
//                commandWasFind = true;
//            }
//
//            if(command.contains("ExecuteScript")){
//                String[] comWords = command.split(" ");
//                int index = 0;
//                for (int i = 0; i < comWords.length; i++){
//                    if(comWords[i].equals("ExecuteScript")){
//                        index = i+1;
//                        if(index==comWords.length){
//                            comWords = new String[index+1];
//                            comWords[index] = "";
//                        }
//                    }
//                }
//
//                cc.executeScript(comWords[index]);
//                commandWasFind = true;
////                if(ExecuteScriptCommandRealization.RecursFinder.wasRecursStart(comWords[index])){
////                    System.out.println("Неплохая попытка сделать рекурсию, но нет...");
////                    startedRecurs = true;
////                    System.out.println("KKK");
////                }
////                else {
////                    System.out.println("TTTT");
////                    ExecuteScriptCommandRealization.scriptStartWorking();
////                    cc.executeScript(comWords[index]);
////                }
//
//                commandWasFind = true;
//            }
//            if(command.contains("FilterLessThanTransport")){
//                String[] comWords = command.split(" ");
//                int index = 0;
//                for (int i = 0; i < comWords.length; i++) {
//                    if (comWords[i].equals("FilterLessThanTransport")) {
//                        index = i + 1;
//                        if(index==comWords.length){
//                            comWords = new String[index+1];
//                            comWords[index] = "";
//                        }
//                    }
//                }
//                cc.filterLessThanTransport(comWords[index]);
//                commandWasFind = true;
//            }
//            if(command.contains("Update")){
//                String[] comWords = command.split(" ");
//                int index = 0;
//                for (int i = 0; i < comWords.length; i++){
//                    if(comWords[i].equals("Update")){
//                        index = i+1;
//                        if(index==comWords.length){
//                            comWords = new String[index+1];
//                            comWords[index] = "";
//                        }
//                    }
//                }
//                cc.updateId(comWords[index]);
//                commandWasFind = true;
//            }
//
//            if(command.contains("RemoveById")){
//                String[] comWords = command.split(" ");
//                int index = 0;
//                for (int i = 0; i < comWords.length; i++){
//                    if(comWords[i].equals("RemoveById")){
//                        index = i+1;
//                        if(index==comWords.length){
//                            comWords = new String[index+1];
//                            comWords[index] = "";
//                        }
//                    }
//                }
//                cc.removeById(comWords[index]);
//                commandWasFind = true;
//            }
//
//            if(!commandWasFind){
//                System.out.println("Команда не была найдена! Чтобы увидеть список команд введите \"help\"");
//            }
//
//            if(startedRecurs){
//                return true;
//            }
//            else {
//                return false;//не конец программы
//            }
//        }
//    }

    private String gettingNormalFormat(String str){ //меняет "_a" на "A" и тп и переводит первую букву в верхний регистр

        String firstCarInCommand = str.substring(0,1);  //
        firstCarInCommand = firstCarInCommand.toUpperCase();                    // переводит первую букву в верхний регистр
        str = firstCarInCommand + str.substring(1);

        boolean normalFormat = false;
        while (!normalFormat){
            normalFormat = true;
            int ind;
            if(str.contains("_")){
                ind = str.indexOf("_");
                str = str.substring(0, ind) + str.substring(ind+1, ind+2).toUpperCase() + str.substring(ind+2);
                normalFormat = false;
            }
        }
        return str;
    }

//    public void setFileAddress(String fileAddress) {
//        this.fileAddress = fileAddress;
//    }

//    public void setFlatCollection(FlatCollection flatCollection) {
//        this.flatCollection = flatCollection;
//    }

//    public String gettingAddress(){
//        System.out.println("Введите адресс файла:");
//        Scanner scanner = new Scanner(System.in);
//        String fileAddress = scanner.nextLine();
//        try {
//            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileAddress));
//            System.out.println("Файл успешно загружен.");
//        }catch (Exception e){
//            System.out.println("Такого файла не существует.\nПовторите попытку!");
//            fileAddress = (new L6User.User()).gettingAddress();
//        }
//        return fileAddress;
//    }

    public String printAndRead(String parameter){
        System.out.println(parameter);
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

//    public String checkingFileAvailability(String parameter){
//        String fileAddress;
//        try {
////            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(parameter));
//            File file = new File(parameter);
//            fileAddress = parameter;
//        }catch (Exception e){
//            System.out.println("Такого файла не существует.");
//            fileAddress = checkingFileAvailability(printAndRead("Необходимо корректное имя файла:"));
//        }
//        return fileAddress;
//    }



//    public String gettingAddress(String[] args){
//
//        String fileAddress = null;
//
//        if(args.length == 0) {
//            while (fileAddress == null){
//                fileAddress = printAndRead("Хотелось бы получить адресс файла, а не пустоту:");
//            }
//        }
//        else {
//            fileAddress = args[0];
//        }
//
//        File file = new File(checkingFileAvailability(fileAddress));
//        if(!(file.canRead() & file.canWrite())){
//            if(!file.canRead()){
//                System.out.println("У файла нет права на чтение!");
//            }
//            if(!file.canWrite()){
//                System.out.println("У файла нет права на запись!");
//            }
//            String[] arr = new String[1];
//            arr[0] = printAndRead("Нужен другой файл:");
//            fileAddress = gettingAddress(arr);
//        }
////        System.out.println("Файл успешно загружен.");
//        return fileAddress;
//    }
}