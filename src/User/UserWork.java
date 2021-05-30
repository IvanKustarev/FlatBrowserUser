package User;


import CommonClasses.*;
import GraphicalUserInterface.GPanes.GLogOrRegChoice;
import GraphicalUserInterface.GPanes.GLogin;
import GraphicalUserInterface.GInterface;
import HelpingModuls.*;
import Resources.ResourceControlCenter;
//import CommonClasses.DataBlock;

import javax.swing.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserWork{

    private User mainUser;
    private ResourceControlCenter resourceControlCenter;


    public UserWork(ResourceControlCenter resourceControlCenter){
        this.resourceControlCenter = resourceControlCenter;
    }

    public User getMainUser() {
        return mainUser;
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

//    private Boolean serverAnswer = true;



    public class CommunicateWithServerByCommands implements Runnable{

        TransferCenter transferCenter;
        GInterface gInterface;
        String command;
        Printer printer;
        ConsoleScanner consoleScanner;
        ProcessControlCenter processControlCenter;

        public CommunicateWithServerByCommands(TransferCenter transferCenter, GInterface gInterface, String command, Printer printer, ConsoleScanner consoleScanner, ProcessControlCenter processControlCenter){
            this.transferCenter = transferCenter;
            this.gInterface = gInterface;
            this.command = command;
            this.printer = printer;
            this.consoleScanner = consoleScanner;
            this.processControlCenter = processControlCenter;

            printer.println("\n");
        }

        public CommunicateWithServerByCommands(){}

        @Override
        public void run() {
            try {
                startCheckingCommands(transferCenter, gInterface, command, printer, consoleScanner);
            } catch (ConnectionException connectionException) {
//                processControlCenter.reConnect();

                return;
            }
        }

        public void startCheckingCommands(TransferCenter transferCenter, GInterface gInterface, String command, Printer printer, ConsoleScanner consoleScanner) throws  ConnectionException{

            CommandCenter cc = new CommandCenter();
            CommandsData commandsDataForSendToServer = null;

                if(command.equals("")){
                    printer.println("Необходимо ввести команду!");
                    return;
                }
                else {
                    commandsDataForSendToServer = cc.whatTheCommand(command);
                    if(commandsDataForSendToServer == null){
                        printer.println("Такой команды не существует!");
                        return;
                    }
                    else {
                        commandsDataForSendToServer.setCreator(null);
                        if(commandsDataForSendToServer.equals(CommandsData.EXIT)){
                            JOptionPane.showConfirmDialog(new JOptionPane(), "Введена команда exit. Завершаю работу...", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                            System.exit(0);
                        }
                        else {

                            cc.packingCommandInCommandsObject(command, commandsDataForSendToServer);

                            if(commandsDataForSendToServer.isCommandWithElementParameter()){
                                commandsDataForSendToServer.setFlat(Flat.createFlat(null, new FlatCreator(printer, consoleScanner)));
                            }

                            new CommunicateWithServerByCommands().communicateWithServerAboutCommand(commandsDataForSendToServer, transferCenter, gInterface, printer, consoleScanner);
                        }
                    }
                }
        }


        DataBlock dataBlock0;
        DataBlock dataBlock1;
        DataBlock dataBlock2;

        public void communicateWithServerAboutCommand(Object obj, TransferCenter transferCenter, GInterface gInterface, Printer printer, ConsoleScanner consoleScanner) throws ConnectionException{
            CommandsData commandsData = (CommandsData) obj;
            boolean end = false;

            while (!end){
//            Нужно оптимизировать так, чтобы находился вне цикла и не возникала ошибка при запуске execute_script
                dataBlock1 = new DataBlock();

                if(commandsData.getCreator() != null){
                    if(commandsData.getCreator().equals(Creator.SCRIPT)){


                        new TimeLimitedCode(5, TimeUnit.SECONDS){

                            @Override
                            public void codeBlock() {
                                dataBlock1 = (DataBlock) transferCenter.receiveObjectFromServer();
                            }
                        }.start();

                        commandsData = dataBlock1.getCommandsData();
                        copyFieldsFromTo(dataBlock1, commandsData);

                    }
                    else {
                        copyFieldsFromTo(commandsData, dataBlock1);
                        dataBlock1.setCommandsData(commandsData);
                        dataBlock1.setUser(mainUser);

                        transferCenter.sendObjectToServer(dataBlock1);

                        new TimeLimitedCode(5, TimeUnit.SECONDS){

                            @Override
                            public void codeBlock() {
                                dataBlock1 = (DataBlock) transferCenter.receiveObjectFromServer();
                            }
                        }.start();

                        commandsData = dataBlock1.getCommandsData();
                        copyFieldsFromTo(dataBlock1, commandsData);

                    }
                }
                else {
                    copyFieldsFromTo(commandsData, dataBlock1);
                    dataBlock1.setCommandsData(commandsData);
                    dataBlock1.setUser(mainUser);
                    dataBlock1.setResourceBundleName(resourceControlCenter.getMainResourceBundle().getBaseBundleName());
                    transferCenter.sendObjectToServer(dataBlock1);

                    new TimeLimitedCode(5, TimeUnit.SECONDS){

                        @Override
                        public void codeBlock() {
                            dataBlock1 = (DataBlock) transferCenter.receiveObjectFromServer();
                        }
                    }.start();

                    commandsData = dataBlock1.getCommandsData();
                    copyFieldsFromTo(dataBlock1, commandsData);

                }

                if(commandsData.equals(CommandsData.EXIT)){
                    JOptionPane.showConfirmDialog(new JOptionPane(), "Найдена команда exit. Завершаю работу...", "Уведомление", JOptionPane.OK_CANCEL_OPTION);
                }

                end = commandsData.isCommandEnded();
                printer.println(commandsData.getPhrase());

                if(commandsData.isServerNeedElementParameter){
                    printer.println("Необходимо задать квартиру.");
                    commandsData.setFlat(Flat.createFlat(null, new FlatCreator(printer, consoleScanner)));
                }

                if(commandsData.isServerNeedStringParameter){
                    consoleScanner.setNeeded(true);
                    try {
                        synchronized (consoleScanner){
                            consoleScanner.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    commandsData.setParameter(consoleScanner.getStr());
                }

                if(commandsData.isUserNeedToShowFlatArr()){
                    for(int i =0;i<commandsData.getFlats().length;i++){
                        String str = commandsData.getFlats()[i].show(Main.getLocaleByResourceName(resourceControlCenter.getMainResourceBundle().getBaseBundleName())) + "\n";
                        printer.println(str);
                    }
                }
            }

        }

        public DataBlock processCommand(CommandsData commandsData, TransferCenter transferCenter) throws ConnectionException{
            dataBlock2 = new DataBlock();
            dataBlock2.setUser(mainUser);
            dataBlock2.setCommandsData(commandsData);
            dataBlock2.setResourceBundleName(resourceControlCenter.getMainResourceBundle().getBaseBundleName());
            copyFieldsFromTo(commandsData, dataBlock2);
            transferCenter.sendObjectToServer(dataBlock2);
            new TimeLimitedCode(5, TimeUnit.SECONDS){

                @Override
                public void codeBlock() {
                    dataBlock2 = (DataBlock) transferCenter.receiveObjectFromServer();
                }
            }.start();
            return dataBlock2;
        }
    }


    public class Login{

//        Обязательно всегда создавать новый. Вынесен отдельно, чтобы работала проверка подключения в другом потоке.
        private DataBlock dataBlock;

        public void login(GInterface gInterface, TransferCenter transferCenter) throws ConnectionException{
            login(gInterface, new DataBlock(), transferCenter);
        }

        private void login(GInterface gInterface, DataBlock newDataBlock, TransferCenter transferCenter) throws ConnectionException{

            GLogOrRegChoice gLogOrRegChoice = new GLogOrRegChoice(gInterface);


            gInterface.setGPane(gLogOrRegChoice);
            while (gLogOrRegChoice.getAnswer() == null){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String answer = gLogOrRegChoice.getAnswer();

            int scan;
            try {
                scan = Integer.valueOf(answer);
            }catch (Exception e){
                gInterface.sendNotification("Это что-то страшное... Попробуем ещё раз!", "Ошибка подключения");
                login(gInterface, new DataBlock(), transferCenter);
                return;
            }
            if(scan == 0){
                entering(transferCenter, gInterface);
            }
            else {
                if(scan == 1){
                    registering(new DataBlock(), transferCenter, gInterface);
                }
                else {
                    gInterface.sendNotification("Нужно выбрать один из вариантов!", "Ошибка подключения");
                    login(gInterface, transferCenter);
                    return;
                }
            }
        }

        public void entering(TransferCenter transferCenter, GInterface gInterface) throws ConnectionException{
            entering(new DataBlock(), transferCenter, gInterface);
        }

        private void entering(DataBlock newDataBlock, TransferCenter transferCenter, GInterface gInterface) throws ConnectionException{
            boolean end = false;
            while (!end){
                Lock lock = new ReentrantLock();
                 lock.lock();
                 Condition condition = lock.newCondition();
                 GLogin gLogin = new GLogin(lock, condition, gInterface);
                gInterface.setGPane(gLogin);
                 try {
                     condition.await();
                 } catch (InterruptedException e) { }
                 lock.unlock();
                 String name = gLogin.getLogin();
                 String password = gLogin.getPassword();



                 User user = new User(false, name, password);

                 dataBlock = newDataBlock;
                 dataBlock.setUser(user);
                 dataBlock.setCommandsData(CommandsData.CHECKUSER);


                 new TimeLimitedCode(5, TimeUnit.SECONDS) {

                     @Override
                     public void codeBlock() {
                         transferCenter.sendObjectToServer(dataBlock);
                         dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                     }
                 }.start();


                 if(dataBlock.getParameter().equals("true")){
                     ConsolePrinter.println(dataBlock.getPhrase());
                     mainUser = user;
                     end = true;
                 }
                 else {
                     gInterface.sendNotification(dataBlock.getPhrase(), "Ошибка подключения");
                     gInterface.sendNotification("Попробуем снова...", "Ошибка подключения");
                     entering(transferCenter, gInterface);
                     return;
                 }


            }
        }

        public void registering(TransferCenter transferCenter, GInterface gInterface) throws ConnectionException{
            registering(new DataBlock(), transferCenter, gInterface);
        }

        private void registering(DataBlock newDataBlock, TransferCenter transferCenter, GInterface gInterface) throws ConnectionException{
//        CheckConnection checkConnection = new CheckConnection(this, gInterface);
            boolean end = false;
            while (!end){

//                Printer.println("Введите имя пользователя:");
//                String name = scanner.nextLine();
//                Printer.println("Введите пароль:");
//                String password = scanner.nextLine();

//                mainWindow.getContentPane().removeAll();
//                mainWindow.repaint();

                    Lock lock = new ReentrantLock();
                    lock.lock();
                    Condition condition = lock.newCondition();
                    GLogin gLogin = new GLogin(lock, condition, gInterface);
                    gInterface.setGPane(gLogin);
//                    gInterface.setSpaceForInteraction(GLogin.getPanel());
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
                try {
                    condition.await();
                } catch (InterruptedException e) { }
                lock.unlock();

//                Login login = new Login();
//                mainWindow.add(login.getPanel());
//                mainWindow.setVisible(true);
//                while (login.getLogin() == null | login.getPassword() == null){
//                    Thread.sleep(50);
//                }
                    String name = gLogin.getLogin();
                    String password = gLogin.getPassword();



                    User user = new User(true, name, password);

//                DataBlock dataBlock = new DataBlock();
                    dataBlock = newDataBlock;
                    dataBlock.setUser(user);
                    dataBlock.setCommandsData(CommandsData.CHECKUSER);


                    new TimeLimitedCode(5, TimeUnit.SECONDS){

                        @Override
                        public void codeBlock() {
                            transferCenter.sendObjectToServer(dataBlock);
                            dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
                        }
                    }.start();

//                checkConnection.start();
//                serverAnswer = false;
//                transferCenter.sendObjectToServer(dataBlock);
//                dataBlock = (DataBlock) transferCenter.receiveObjectFromServer();
//                serverAnswer = true;

                    if(dataBlock.getParameter().equals("true")){
//                    Printer.println(dataBlock.getPhrase());
//                        JOptionPane.showConfirmDialog(new JOptionPane(), dataBlock.getPhrase(), "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                        gInterface.sendNotification(dataBlock.getPhrase(), "Ошибка подключения");
                        mainUser = user;
                        end = true;
                    }
                    else {
//                    Printer.println(dataBlock.getPhrase());
//                    Printer.println("Пробуем занова!");
//                        JOptionPane.showConfirmDialog(new JOptionPane(), dataBlock.getPhrase() + "\nПробуем занова!", "Ошибка подключения", JOptionPane.OK_CANCEL_OPTION);
                        gInterface.sendNotification(dataBlock.getPhrase(), "Ошибка подключения");
                        gInterface.sendNotification("Попробуем снова...", "Ошибка подключения");
                        registering(new DataBlock(), transferCenter, gInterface);
                        return;
                    }

            }
        }
    }
}