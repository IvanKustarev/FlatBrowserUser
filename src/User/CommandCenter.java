package User;

//import L5.Creator;
//import L6User.Commands.*;

import CommonClasses.CommandsData;

import java.util.Scanner;

/**Класс, который создаётся в user и script и управляет работой команд*/
public class CommandCenter {

//    String fileAddress;
//    AddCommand addCommand;
//    AddIfMinCommand addIfMinCommand;
//    ClearCommand clearCommand;
//    ExecuteScriptCommand executeScriptCommand;
//    FilterLessThanTransportCommand filterLessThanTransportCommand;
//    HelpCommand helpCommand;
//    InfoCommand infoCommand;
//    PrintFieldAscendingNumberOfRoomsCommand printFieldAscendingNumberOfRoomsCommand;
//    RemoveByIdCommand removeByIdCommand;
//    RemoveHeadCommand removeHeadCommand;
//    RemoveLowerCommand removeLowerCommand;
//    SaveCommand saveCommand;
//    ShowCommand showCommand;
//    SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand;
//    UpdateIdCommand updateIdCommand;

//    public CommandCenter(String fileAddress, AddCommand addCommand, AddIfMinCommand addIfMinCommand, ClearCommand clearCommand, ExecuteScriptCommand executeScriptCommand, FilterLessThanTransportCommand filterLessThanTransportCommand, HelpCommand helpCommand, InfoCommand infoCommand, PrintFieldAscendingNumberOfRoomsCommand printFieldAscendingNumberOfRoomsCommand, RemoveByIdCommand removeByIdCommand, RemoveHeadCommand removeHeadCommand, RemoveLowerCommand removeLowerCommand, SaveCommand saveCommand, ShowCommand showCommand, SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand, UpdateIdCommand updateId) {
//        this.fileAddress = fileAddress;
//        this.addCommand = addCommand;
//        this.addIfMinCommand = addIfMinCommand;
//        this.clearCommand = clearCommand;
//        this.executeScriptCommand = executeScriptCommand;
//        this.filterLessThanTransportCommand = filterLessThanTransportCommand;
//        this.helpCommand = helpCommand;
//        this.infoCommand = infoCommand;
//        this.printFieldAscendingNumberOfRoomsCommand = printFieldAscendingNumberOfRoomsCommand;
//        this.removeByIdCommand = removeByIdCommand;
//        this.removeHeadCommand = removeHeadCommand;
//        this.removeLowerCommand = removeLowerCommand;
//        this.saveCommand = saveCommand;
//        this.showCommand = showCommand;
//        this.sumOfNumberOfRoomsCommand = sumOfNumberOfRoomsCommand;
//        this.updateIdCommand = updateId;
//    }

//    public void add(CommonClasses.CommandsData command){
//        addCommand.execute(command);
//    }
//
//    public void addIfMin(CommonClasses.CommandsData command){
//        addIfMinCommand.execute(command);
//    }
//
//    public void clear(CommonClasses.CommandsData command){
//        clearCommand.execute(command);
//    }
//
//    public void filterLessThanTransport(CommonClasses.CommandsData command){
//        filterLessThanTransportCommand.execute(command);
//    }
//
//    public void executeScript(CommonClasses.CommandsData command){
//        executeScriptCommand.execute(command);
//    }
//
//    public void help(CommonClasses.CommandsData command){
//        helpCommand.execute(command);
//    }
//
//    public void info(CommonClasses.CommandsData command){
//        infoCommand.execute(command);
//    }
//
//    public void printFieldAscendingNumberOfRooms(CommonClasses.CommandsData command){
//        printFieldAscendingNumberOfRoomsCommand.execute(command);
//    }
//
//    public void removeById(CommonClasses.CommandsData command){
//        removeByIdCommand.execute(command);
//    }
//
//    public void removeHead(CommonClasses.CommandsData command){
//        removeHeadCommand.execute(command);
//    }
//
//    public void removeLower(CommonClasses.CommandsData command){
//        removeLowerCommand.execute(command);
//    }
//
//    public void save(CommonClasses.CommandsData command){
//        saveCommand.execute(command);
//    }
//
//    public void show(CommonClasses.CommandsData command){
//        showCommand.execute(command);
//    }
//
//    public void sumOfNumberOfRooms(CommonClasses.CommandsData command){
//        sumOfNumberOfRoomsCommand.execute(command);
//    }
//
//    public void updateId(CommonClasses.CommandsData command){
//        updateIdCommand.execute(command);
//    }


    /**Отвечает за поиск введённого поля в списке команд. Если такой команды нет, выдаёт null*/
    public CommandsData whatTheCommand(String command){
        CommandsData[] commandsVariations = CommandsData.values();
        for(int i = 0; i < commandsVariations.length; i++){
            if(command.contains("add_if_min")){
                return CommandsData.ADDIFMIN;
            }
            else {
                if(command.contains(commandsVariations[i].toString())){
                    return commandsVariations[i];
                }
            }
        }
        return null;
    }

    public boolean isCommandWithParameter(String command){
        if(command.contains(CommandsData.UPDATE.toString()) | command.contains(CommandsData.REMOVEBYID.toString()) |
                command.contains(CommandsData.EXECUTESCRIPT.toString()) | command.contains(CommandsData.FILTERLESSTHANTRANSPORT.toString())){
            return true;
        }
        return false;
    }

    /**берёт из строки команды пользователя её параметр и пакует это всё в L6User.Commands объект*/
    public void packingCommandInCommandsObject(String command, CommandsData commandObject){

        CommandsData[] commands = CommandsData.values();
        for (int i =0; i<commands.length; i++){

//            System.out.println(command);
            if(command.contains("add_if_min")){
                commandObject = CommandsData.ADDIFMIN;
            }
            else {
                if(command.contains(commands[i].toString())){
                    commandObject = commands[i];
                }
            }
        }
//        System.out.println(commandObject.name());
        if(isCommandWithParameter(command)){
            processingParameter(commandObject, command, commands);
        }
    }
    void processingParameter(CommandsData commandObject, String command, CommandsData[] commands){
        String[] comWords = command.split(" ");
        int index = 0;
        for (int i = 0; i < comWords.length; i++) {
            for(int j = 0; j < commands.length;j++){
                if (comWords[i].equals(commands[j].toString())) {
                    index = i + 1;
                    if(index==comWords.length){
                        comWords = new String[index+1];
                        comWords[index] = "";
                    }
                    break;
                }
                break;
            }
            break;
        }
//            System.out.println(comWords.length);
        if(comWords.length < 2){
            System.out.println("Для этой команды необходим параметр!");
            String par = new Scanner(System.in).nextLine();
            command = command + " " + par;
            processingParameter(commandObject, command, commands);
        }
        else {
            commandObject.setParameter(comWords[index+1]);
        }
    }

//    /**запуск команды осуществляемый user-ом*/
//    public void processingAndStartUserCommand(String command){
//        CommonClasses.CommandsData userCommand = packingCommandInCommandsObject(command, isCommandWithParameter(command));
////        if(userCommand.equals(CommonClasses.CommandsData.EXECUTESCRIPT)){
////            //дополнительные параметры для этой команды
////            try {
////                //создаем буффер для чтения файла со скриптом
////                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(userCommand.getParameter())));
////                userCommand.setBufferedReader(bufferedReader);
////
////                //создаётся стек для открытых файлов (ловить рекурсию)
////                Stack<String> openingFiles = new Stack<>();
////                userCommand.setOpeningFiles(openingFiles);
////                userCommand.addOpeningFile(userCommand.getParameter());
////            } catch (FileNotFoundException e) {
////                System.out.println("Проблемы с загрузкой файла со скриптом!");
////            }
////        }
//        startCommand(userCommand);
//    }

//    /**запуск команды осуществляемый user-ом*/
//    public void processingAndStartScriptCommand(String command, CommonClasses.CommandsData commandsData, BufferedReader bufferedReader){
//        CommonClasses.CommandsData scriptCommand = packingCommandInCommandsObject(command, isCommandWithParameter(command), Creator.SCRIPT);
//
//        boolean recursWasStarted = false;
//        scriptCommand.setBufferedReader(bufferedReader);
//        if(scriptCommand.equals(CommonClasses.CommandsData.EXECUTESCRIPT)){
//            //дополнительные параметры для этой команды
//
//            String[] nameOfOpenedFiles = new String[commandsData.getOpeningFiles().toArray().length];
//            for(int i =0; i < commandsData.getOpeningFiles().toArray().length; i++){
//                nameOfOpenedFiles[i] = (String) (commandsData.getOpeningFiles().toArray())[i];
//            }
//
//            for (String nameOfOpenedFile : nameOfOpenedFiles){
//                if(nameOfOpenedFile.equals(commandsData.getParameter())){
//                    recursWasStarted = true;
//                }
//            }
//        }
//
//        if(recursWasStarted){
//            System.out.println("Сорри, бро, тут рекурсия, мы прикрываем это лавочку...");
//        }
//        else {
//            if(scriptCommand.equals(CommonClasses.CommandsData.EXECUTESCRIPT)){
//                scriptCommand.addOpeningFile(scriptCommand.getParameter());
//            }
//            startCommand(scriptCommand);
//        }
//    }

//    /**получает уже запакованную со всеми параметрами команду и запускает её*/
//    public void startCommand(CommonClasses.CommandsData commandObject){
//        String commandName = gettingNormalFormatOfName(commandObject.toString());
////        System.out.println(commandName);
//        Method[] methods = getClass().getDeclaredMethods();
//        for (Method method : methods){
//            if(method.getName().equals(commandName)){
////                if(commandName.equals("executeScript")){
////                    executeScript(commandObject);
////                }
////                else {
////                    try {
////                        method.invoke(this, commandObject);
////                    } catch (Exception e) {
////                        System.out.println("Проблема с запуском метода из CommandCenter, тк поставлены неверные параметры!");
////                    }
////                }
//
//                try {
//                    method.invoke(this, commandObject);
//                } catch (Exception e) {
//                    System.out.println("Проблема с запуском метода из CommandCenter, тк поставлены неверные параметры!");
//                }
//            }
//        }
//    }

    /**меняет "_a" на "A" и тп и переводит первую букву в верхний регистр*/
    private String gettingNormalFormatOfName(String str){

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
}
